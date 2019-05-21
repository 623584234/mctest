package com.mc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mc.pojo.TitleDetails;
import com.mc.pojo.dto.VoAccessToken;
import com.mc.pojo.dto.VoToken;
import com.mc.service.TitleDetailsService;
import com.mc.util.HttpUtil;
import com.mc.util.MapTransformUtils;
import com.mc.util.UUID;
import com.mc.util.wx.CheckTokenUtil;
import com.mc.util.wx.MessageUtil;
import com.mc.util.wx.httpsUtil;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WxController {

	private static final Logger logger = LoggerFactory.getLogger(WxController.class);

	private static String appID;
	private static String appsecret;
	private static Properties properties;
	private static final String TITLEEVENT= "submit_invoice_title";
	
	private static final String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	private static final String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	//微信发票抬头获取相关地址
	private static final String accessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String getUserTitleUrl ="https://api.weixin.qq.com/card/invoice/biz/getselecttitleurl?access_token=ACCESSTOKEN";
	
	
	@Autowired
	private TitleDetailsService titleDetailsService;
	
	static {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("properties/wx.properties");
			appID = properties.getProperty("appID");
			appsecret = properties.getProperty("appsecret");
		} catch (Exception e) {
			logger.error("获取wx配置文件失败", e);
		}
	}

	/**
	 * 服务器验证接口
	 *
	 * @param vo
	 * @param response
	 */
	@GetMapping(value = "serverValidation")
	public void checkToken(VoToken vo, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			if (CheckTokenUtil.checkSignnature(vo)) {
				out.println(vo.getEchostr());
			}
		} catch (IOException e) {
			logger.error("Token验证失败", e);
		}
	}

	/**
	 * 微信授权接口
	 *
	 * @return
	 */
	@GetMapping(value = "auth")
	public String auth() {
		String backUrl = properties.getProperty("backUrl");
		@SuppressWarnings("deprecation")
		String url = codeUrl.replace("APPID", appID).replace("REDIRECT_URI", URLEncoder.encode(backUrl))
				.replace("SCOPE", "snsapi_userinfo");
		return "redirect:" + url;

	}

	/**
	 * 授权后返回的接口
	 *
	 * @return
	 */
	@PostMapping(value = "callback")
	public String callback(String code) {
		String url = accessTokenUrl.replace("APPID", appID).replace("SECRET", appsecret).replace("CODE", code);
		try {
			String s = HttpUtil.sendGet(url);
			System.out.println(s);
		} catch (Exception e) {
			logger.error("获取accessToken失败", e);
		}

		return null;
	}

	//-------------------------以下为扫码获取微信抬头代码-----------------------
	/**
	 * 获取access_token
	 * @return
	 */
	@GetMapping(value="token")
	private static VoAccessToken accessToken() {
		String result=null;
		try {
			String url =accessToken.replace("APPID", appID).replace("APPSECRET", appsecret);
			result = HttpUtil.sendGet(url);
			System.out.println(result);
		} catch (Exception e) {
			logger.error("获取accessToken失败", e);
		} 
		
		//此处应该把获得的access_token存到redis，因为只有2小时时效
		
		VoAccessToken vo = parseJsonWithGson(result, VoAccessToken.class);
		return vo;
	 }
	
	/**
	 * 获取抬头的二维码链接地址
	 * @return
	 */
	@PostMapping(value="getUserTitleUrl")
	@ResponseBody
	public static String getUserTitleUrl(String attach) {
		JSONObject result = null;
//		if() {
//			//判断如果redis的token过期，才重新获取token
//		}
		VoAccessToken tokenvo = accessToken();
		String token = tokenvo.getAccess_token();
		
//		String token = "21_vG3FJAT3dFJsx3ERP46W6iBlOvigoWLCDG-eCzQN40L_xyw3Rts2Ug4_wnSKh4zkh2K7z7L-flKlDPtInFSDaYGirlBR46PEDHrFN2peLmjGHWEatAJPNjb_VwQGYSjABAPHB";
		Map<String ,String> map = new HashMap<>();
			map.put("attach", attach);
			map.put("biz_name", "数安时代发票系统");
			JSONObject json = new JSONObject();
			json.putAll(map);
			System.out.println(map);
			try {
				result =httpsUtil.doPostJson(getUserTitleUrl.replace("ACCESSTOKEN", token), json);
			} catch (Exception e) {
				logger.error("获取商家二维地址失败",e);
			}
			System.out.println(result);
//		VoUserTitleUrl vo = parseJsonWithGson(result,VoUserTitleUrl.class);
		String src =result.getString("url");
		System.out.println(src);
		return src;
	}
	
	/**
	 * 用户提交统一接收接口
	 * 回调信息:
	 *  		TITLEEVENT：抬头明细
	 * @param request
	 */
	@PostMapping(value = "serverValidation")
	public void getTitle(HttpServletRequest request) {
		try {
			Map<String, String> map = MessageUtil.xmlTomap(request);
			if(map.isEmpty()) {
				logger.error("回调信息为空!");
				return;
			}
			System.out.println("回调信息："+map);
			if(map.get("Event").equals(TITLEEVENT)) {
				addTitleDetails(map);
			}
		} catch (IOException | DocumentException e) {
			logger.error("获取抬头信息失败!",e);
		} catch (Exception e) {
			logger.error("map转换实体类失败!",e);
		}
	}
	
	/**
	 * 前端获取数据库抬头信息
	 * @param mv
	 * @param attach
	 * @return
	 */
	@GetMapping(value="getTitleDetails")
	public String getTitleDetails(Model mv,String attach) {
		List<TitleDetails> titleList = titleDetailsService.getDetails(attach);
		mv.addAttribute("titleList", titleList);
		return "titleDetails";
	} 
	
	private void addTitleDetails(Map<String,String> map) throws Exception {
		long lcc_time = Long.valueOf(map.get("CreateTime"));  
		Date createTime = new Date(lcc_time * 1000L);
		System.out.println(createTime);
		TitleDetails vo = MapTransformUtils.mapToObject(map,TitleDetails.class);
		vo.setId(UUID.get32UID());
		vo.setBankNo(map.get("bank_no"));
		vo.setBankType(map.get("bank_type"));
		vo.setTaxNo(map.get("tax_no"));
		vo.setTitleType(map.get("title_type"));
		vo.setAttach(map.get("attach"));
		vo.setCreateTime(createTime);
		vo.setFromUserName(map.get("FromUserName"));
		vo.setToUserName(map.get("ToUserName"));
		vo.setMsgType(map.get("MsgType"));
		vo.setEvent(map.get("Event"));
		vo.setCodeAuthKey(map.get("code_auth_key"));
		titleDetailsService.insert(vo);
	}
	
	/**
	 * 将json的String转换为实体类
	 * @param jsonData
	 * @param type
	 * @return
	 */
	private static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
		Gson gson = new Gson();
		T result = gson.fromJson(jsonData, type);
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		 getUserTitleUrl("测试受理单");
	}
	
}
