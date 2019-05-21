package com.mc.util.wx;

import com.mc.pojo.dto.VoTextMsg;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

	/**
	 * 将xml转为map，用dom4j
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlTomap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();

		InputStream in = request.getInputStream();
		Document doc = reader.read(in);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();
		list.forEach(a -> {
			map.put(a.getName(), a.getText());
		});
		in.close();
		return map;
	}

	/**
	 * 将文本消息对象转为xml，用xStream
	 * @param vo
	 * @return
	 */
	public static String msgToxml(VoTextMsg vo) {
		XStream xstream = new XStream();
		xstream.alias("xml",VoTextMsg.class);
		return xstream.toXML(vo);
	}

}
