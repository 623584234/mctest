package com.mc.util;
//package com.gdcacert.data.service.impl.utils;
//
//import javax.xml.namespace.QName;
//
//import org.apache.axiom.om.OMAbstractFactory;
//import org.apache.axiom.om.OMElement;
//import org.apache.axiom.om.OMFactory;
//import org.apache.axiom.om.OMNamespace;
//import org.apache.axis2.AxisFault;
//import org.apache.axis2.addressing.EndpointReference;
//import org.apache.axis2.client.Options;
//import org.apache.axis2.client.ServiceClient;
//import org.apache.axis2.rpc.client.RPCServiceClient;
//import org.apache.axis2.transport.http.HTTPConstants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class UtilsWebService {
//	private static Logger log = LoggerFactory.getLogger(UtilsWebService.class);
//
//	public static Object callMethod(String methodName, String wsUrl, Object[] obj, String qurl) throws Exception {
//		Object objReturn = null;
//		try {
//			log.info("开始调用地址[" + wsUrl + "]的方法[" + methodName + "]");
//
////			EndpointReference targetEPR = WebServiceUtilNew.getInstance().getTarget(wsUrl);
//			EndpointReference targetEPR = new EndpointReference(wsUrl);
//
//			QName opAddEntry = new QName(qurl, methodName);
//			Class[] objCla = new Class[obj.length];
//			for (int i = 0, len = obj.length; i < len; i++) {
//				objCla[i] = obj[i].getClass();
//			}
//
//			RPCServiceClient serviceClient = new RPCServiceClient();
//			Options options = serviceClient.getOptions();
//			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
//			options.setProperty(HTTPConstants.CHUNKED, false);
////			options.setTimeOutInMilliSeconds(SystemCfg.getInt("WebService_OverTime"));
//			options.setTimeOutInMilliSeconds(30000);
//
//			serviceClient.getOptions().setTo(targetEPR);
//			objReturn = (serviceClient.invokeBlocking(opAddEntry, obj, objCla))[0];
//			serviceClient.cleanupTransport();
//
//		} catch (AxisFault e) {
//			log.error("WebServcies调用出错", e);
//			throw new Exception("WebServcies调用出错");
//		} catch (Exception ex) {
//			log.error("WebServcies调用出错", ex);
//			throw new Exception("WebServcies调用出错");
//		}
//
//		return objReturn;
//	}
//
//	public static Object callMethodV2(String methodName, String wsUrl, Object[] obj, String qurl, String paramName)
//			throws Exception {
//		OMElement result = null;
//		try {
//			RPCServiceClient sender = new RPCServiceClient();
//			EndpointReference endpointReference = new EndpointReference(wsUrl);
//			Options options = new Options();
//			options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
//			options.setProperty(HTTPConstants.CHUNKED, false);
//			String action = qurl + methodName;
//			options.setAction(action);
//			options.setTo(endpointReference);
//			sender.setOptions(options);
//			OMFactory fac = OMAbstractFactory.getOMFactory();
//			// 这个和qname差不多，设置命名空间
//			OMNamespace omNs = fac.createOMNamespace(qurl, methodName);
//			OMElement data = fac.createOMElement(methodName, omNs);
//			// 对应参数的节点
////			String[] strs = new String[] { "theRegionCode" };
//			// 参数值
////			String[] val = new String[] { "北京" };
////			for (int i = 0; i < strs.length; i++) {
////				OMElement inner = fac.createOMElement(strs[i], omNs);
////				inner.setText(val[i]);
////				data.addChild(inner);
////			}
//			OMElement inner = fac.createOMElement(paramName, omNs);
//			inner.setText(obj[0].toString());
//			data.addChild(inner);
//			// 发送数据，返回结果
//			result = sender.sendReceive(data);
//
//		} catch (AxisFault ex) {
//			log.error("WebServcies调用出错", ex);
//		}
//		return result.toString();
//	}
//}
