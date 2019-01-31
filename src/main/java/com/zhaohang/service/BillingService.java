package com.zhaohang.service;


import com.zhaohang.util.ServerResponse;

@SuppressWarnings("rawtypes")
public interface BillingService {
	
	ServerResponse getData();
	
	ServerResponse<Boolean> checkExe(String exeName);
	
	ServerResponse getBillingList( String code);

}
