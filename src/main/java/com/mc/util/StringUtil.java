package com.mc.util;

import java.security.NoSuchAlgorithmException;


public class StringUtil {
	//private static Logger log = LoggerFactory.getLogger(StringUtil.class);

	public static final Double toDouble(String str) throws Exception {
		Double result = new Double(0.0d);
		if (str != null && !"".equals(str)) {
			try {
				result = new Double(str);
			} catch (NumberFormatException nfe) {
//				log.debug("[" + str + "]格式不正确或者超出double类型的值的范围", nfe);
				throw new Exception(nfe);
			} catch (Exception e) {
//				log.debug("Exception:", e);
				throw new Exception(e);
			}
		}
		return result;
	}

	public static String nullToString(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	public static boolean isNull(String str) {
		return str == null || "".equals(str);
	}

	public static boolean isNull(StringBuffer str) {
		return (str == null) || isNull(str.toString());
	}

	public static boolean noNull(String str) {
		return !isNull(str);
	}

	public static boolean noNull(StringBuffer str) {
		return (str != null) && noNull(str.toString());
	}

	/**
	 * 生成随机数
	 * 
	 * @param pwd_len
	 *          生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int len) {
		String str = "";
		while (str.length() < len) {
			str += String.valueOf(Math.round(Math.random() * 10));
		}
		return str.substring(0, len);
	}

	/**
	 * 指定字符串在源字符里出现的次数
	 * 
	 * @param src
	 *          源字符串
	 * @param target
	 *          指定字符串
	 * @return 次数
	 */
	public static int getTokenCount(String src, String target) {
		int count = 0;
		if (isNull(src)) {
			return count;
		}
		for (int i = src.indexOf(target); i > -1; src = src.substring(i
				+ target.length()), i = src.indexOf(target)) {
			count++;
		}
		return count;
	}

	public static boolean equals(String str1, String str2) {
		if (str1 == null && str2 != null) {
			return false;
		} else if (str1 != null && str2 == null) {
			return false;
		} else if (str1 == null && str2 == null) {
			return true;
		} else {
			return str1.equals(str2);
		}
	}

	/**
	 * 去左边空格和制表符
	 * 
	 * @param param
	 * @return
	 */
	public static String trimLeft(String param) {
		if (noNull(param)) {
			return param.replaceAll("^[ |\t]", "");
		} else {
			return param;
		}
	}

	/**
	 * 去右边空格和制表符
	 * 
	 * @param param
	 * @return
	 */
	public static String trimRight(String param) {
		if (noNull(param)) {
			return param.replaceAll("[ |\t]$", "");
		} else {
			return param;
		}
	}

	/**
	 * 去两边空格和制表符
	 * 
	 * @param param
	 * @return
	 */
	public static String trim(String param) {
		return trimRight(trimLeft(param));
	}

	public static String sha1(String input) {
		return sha1(input.getBytes());
	}

	public static String sha1(byte[] input) {
		try {
			java.security.MessageDigest algb = java.security.MessageDigest
					.getInstance("SHA-1");
			algb.update(input);
			return bytes2Hex(algb.digest());
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des.toUpperCase();
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
}
