package com.mc.service;

import java.util.List;

import com.mc.pojo.TitleDetails;

public interface TitleDetailsService {
	
	Integer insert(TitleDetails titleDetails);
	
	List<TitleDetails> getDetails(String attach);
}
