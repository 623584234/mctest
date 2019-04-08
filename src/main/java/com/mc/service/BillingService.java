package com.mc.service;


import com.mc.util.ServerResponse;

@SuppressWarnings("rawtypes")
public interface BillingService {
	
	ServerResponse getData();
	
	ServerResponse<Boolean> checkExe(String exeName);
	
	ServerResponse getBillingList( String code);

}
