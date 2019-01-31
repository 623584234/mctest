package com.zhaohang.util;

import java.util.Calendar;
import java.util.Date;


public class UUID {
  public static String[] code = new String[]{"1","2","3","4","5","6","7","8","9","0","a","b","c","d",
  	"e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
  
	public static String getUID(int len){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String date = String.valueOf(cal.get(Calendar.DATE));
		String minute = String.valueOf(cal.get(Calendar.MINUTE));
		String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		String second = String.valueOf(cal.get(Calendar.SECOND));
		String milliseond = String.valueOf(cal.get(Calendar.MILLISECOND));
		String id = String.valueOf(cal.get(Calendar.YEAR))
				+ ("00" + month).substring(month.length())
				+ ("00" + date).substring(date.length())
				+ ("00" + hour).substring(hour.length())
				+ ("00" + minute).substring(minute.length())
				+ ("00" + second).substring(second.length())
				+ ("000" + milliseond).substring(milliseond.length()); 
		if (len >= id.length()){
			if (len > id.length()){
				id += StringUtil.genRandomNum(len - id.length());
			}
		}else{
			id = StringUtil.genRandomNum(len);
		}
		return id;
	}
	
	/**
	 * 获取32位UUID
	 * @author liangxy
	 * @return
	 */
	public static String getUUID(){
		String uuid = java.util.UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		
		return uuid;
	}
	
	public static String get32UID(){
		String uid = UUID.getUID(17);
		while(uid.length() < 32){
			int len = uid.length()%3;
			String str = StringUtil.genRandomNum(len==0?2:len);
			int num = Integer.parseInt(str);
			while(num >= code.length){
				num /= 2;
			}
			uid += code[num];
		}
		if (uid.length() > 32){
			uid = uid.substring(0, 31);
		}
		return uid;
	}

	public static String get36UID(){
		String uid = UUID.getUID(17);
		while(uid.length() < 36){
			int len = uid.length()%3;
			String str = StringUtil.genRandomNum(len==0?2:len);
			int num = Integer.parseInt(str);
			while(num >= code.length){
				num /= 2;
			}
			uid += code[num];
		}
		if (uid.length() > 36){
			uid = uid.substring(0, 31);
		}
		return uid;
	}
	 
//	public static void main(String[] args){
//		System.out.println(UUID.getUUID());
//	}
}
