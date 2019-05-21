package com.mc.controller;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mc.service.BillingService;
import com.mc.service.WebSocket;
import com.mc.util.ServerResponse;
import com.mysql.fabric.xmlrpc.base.Array;


@Controller
@SuppressWarnings({ "rawtypes" })
@RequestMapping("/")
public class BillingController {
	private static Logger log = LoggerFactory.getLogger(BillingController.class);
	
	private static Properties properties;

	@Autowired
	public BillingService billingService;
	
	@Autowired
	WebSocket webSocket;

//	@Scheduled(cron = "0/10 * * * * ?")
//	public ServerResponse test() {
//		System.out.println("开始执行,间隔时间10S...");
//		ServerResponse getdata = billingService.getData();
//		if (!getdata.isSuccess()) {
//			System.out.println("执行失败:" + getdata.getMessage());
//			return getdata;
//		}
//		System.out.println(getdata.getMessage());
//		return ServerResponse.responseBySuccess(getdata.getMessage());
//	}
//	@Scheduled(cron = "0/10 * * * * ?")
	@PostMapping(value = "start")
	@ResponseBody
	public ServerResponse startExe() throws IOException {
		properties = PropertiesLoaderUtils.loadAllProperties("properties/exeLocation.properties");
		String exeName = properties.getProperty("exeName");
		String exeLocation = properties.getProperty("exeLocation");
		
		System.out.println("脚本开始执行...");
		boolean result = true;
		while (result) {
			ServerResponse<Boolean> checkExe = billingService.checkExe(exeName);
			if(!checkExe.isSuccess()) {
				return checkExe;
			}
			result = checkExe.getData();
			if(result) {
				Runtime.getRuntime().exec("taskkill /f /t /im "+exeName);
			}
		}
		Runtime.getRuntime().exec(exeLocation+exeName);
		return ServerResponse.responseBySuccess("脚本执行成功");
	}

	@PostMapping(value ="getBilling")
	public String getBilling(Model model,String code)  {
		ServerResponse billingList = billingService.getBillingList(code);
		log.info("------------------");
		model.addAttribute("billingList", billingList.getData());
		return "check";
	}

	@PostMapping(value ="testSM2")
	@ResponseBody
	public Object testSM2() {

			Map<String, String> param = new HashMap<String, String>();
			param.put("clientId",  "2222");
			String random,sendPost = null;
			try {
				random = com.mc.util.HttpUtil.sendPost("http://192.168.130.189:8081/zhaohangBilling/getRandom",param);
			System.out.println(random);
			System.out.println("进行签名");
			String signValue = com.mc.util.PkiUtils.sign(random, "2222");
			param.put("serialNumber", "G0993400085575C");
			param.put("random", "20190103144016334dm2ji1ro5ev8on0zh135481");
			param.put("signValue", signValue);
			sendPost = com.mc.util.HttpUtil.sendPost("http://192.168.130.189:8081/zhaohangBilling/getDetails",param);
			System.out.println(sendPost);
			} catch (Exception e) {
				e.printStackTrace();
			System.out.println("调用接口失败");
			}
		return sendPost;
	}
	@PostMapping(value="test")
	@ResponseBody
	public void test(@Param(value = "user") String user,@Param(value = "msg") String msg) throws IOException {
		webSocket.sendToUser(user,msg);
	}
	
}
