package com.zhaohang.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.zhaohang.dao.ZhaohangBillingMapper;
import com.zhaohang.pojo.ZhaohangBilling;
import com.zhaohang.service.BillingService;
import com.zhaohang.util.ReadExcelTools;
import com.zhaohang.util.ServerResponse;
import com.zhaohang.util.UUID;

@SuppressWarnings("rawtypes")
@Service("billingService")
public class BillingServiceImpl implements BillingService {

//	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	private static Properties properties;
	@Autowired
	ZhaohangBillingMapper zhaohangBillingMapper;

	public ServerResponse getData() {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("properties/exeLocation.properties");
		} catch (IOException e1) {
			ServerResponse.responseByError("读取配置文件失败");
		}
		// 流水号重复条数，成功插入数，excel总条数
		int eCount = 0, sCount = 0, total = 0;
		String location = properties.getProperty("location");
		File file = new File(location);
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] fileList = file.listFiles();
		if (fileList.length == 0) {
			return ServerResponse.responseByError(location + "文件夹内没有Excel文件");
		}
		List<String[]> readExcel = null;
		try {
			readExcel = ReadExcelTools.readExcel(fileList[0]);
		} catch (IOException e) {
			return ServerResponse.responseByError("读取Excel信息失败");
		}
		total = readExcel.size();
		// System.out.println(gson.toJson(readExcel.get(0)));
		List<String> serialNumberlist = zhaohangBillingMapper.selectSerialNumber();

		try {
			Class<?> clazz = Class.forName("com.zhaohang.pojo.ZhaohangBilling");
			stop: for (String[] str : readExcel) {
				ZhaohangBilling zhaohangBilling = (ZhaohangBilling) clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();

				if (serialNumberlist.size() != 0) {
					for (String item : serialNumberlist) {
						if (item.equals(str[8])) {
							eCount++;
							continue stop;
						}
					}
				}
				zhaohangBilling.setId(UUID.get32UID());
				for (int i = 1, n = fields.length; i < n; i++) {
					String name = fields[i].getName();
					String methodStr = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
					Method method = clazz.getMethod(methodStr, new Class[] { fields[i].getType() });
					method.invoke(zhaohangBilling, str[i - 1]);
//					System.out.println(gson.toJson(str[i - 1]));
				}
				zhaohangBillingMapper.insertSelective(zhaohangBilling);
				sCount++;
//				System.out.println(gson.toJson(zhaohangBilling));
			}

		} catch (Exception e) {
			// TODO: handle exception
			return ServerResponse.responseByError("信息插入失败");
		}

		System.out.println("excel总条数：" + total);
		System.out.println("流水号重复条数: " + eCount);
		System.out.println("成功插入数: " + sCount);

		return ServerResponse.responseBySuccess("执行成功");
	}

	public ServerResponse<Boolean> checkExe(String exeName) {
		boolean flag = false;
		Process p;
		try {
			p = Runtime.getRuntime().exec("cmd /c tasklist ");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream os = p.getInputStream();
			byte b[] = new byte[256];
			int len = -1;
			while ((len = os.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			String s = baos.toString();
			System.out.println(s);

			if (s.indexOf(exeName) >= 0) {
				flag = true;
			} else {
				flag = false;
			}
			os.close();
			baos.close();
			System.out.println(flag);
		} catch (IOException e) {
			return ServerResponse.responseBySuccess("检测进程失败");
		}
		return ServerResponse.responseBySuccess("检测进程完毕", flag);
	}

	public ServerResponse getBillingList(String code) {
		List<ZhaohangBilling> list = zhaohangBillingMapper.selectAll(code);
		return ServerResponse.responseBySuccessData(list);
	}

}