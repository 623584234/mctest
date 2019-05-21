package com.mc.util.wx;

import com.mc.pojo.dto.VoToken;

import java.util.Arrays;


public class CheckTokenUtil {

	private static final String token = "lmc";

	public static boolean checkSignnature(VoToken vo) {

		String[] arr = new String[] { token, vo.getTimestamp(), vo.getNonce() };

		Arrays.sort(arr);

		StringBuffer sb = new StringBuffer();
		for (int i = 0, j = arr.length; i < j; i++) {
			sb.append(arr[i]);
		}

		String temp = SHAUtils.SHA1(sb.toString());
		
		return temp.equals(vo.getSignature());
	}

}
