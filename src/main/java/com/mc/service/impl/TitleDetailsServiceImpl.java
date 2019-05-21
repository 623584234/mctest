package com.mc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.dao.TitleDetailsMapper;
import com.mc.pojo.TitleDetails;
import com.mc.service.TitleDetailsService;

@Service("titleDetailsService")
public class TitleDetailsServiceImpl implements TitleDetailsService{
	
	@Autowired
	private TitleDetailsMapper titleDetailsMapper;
	@Override
	public Integer insert(TitleDetails titleDetails) {
		int count = titleDetailsMapper.insert(titleDetails);
		return count;
	}
	@Override
	public List<TitleDetails> getDetails(String attach) {
		return titleDetailsMapper.selectByAttach(attach);
	}

}
