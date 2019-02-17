package com.sl.web;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.sl.web.util.HttpClientUtil;

public class TestXX {
	public Long nextId(){
		try {
			String value = HttpClientUtil.httpGet("http://10.8.10.100:11002/nextId");
			return Long.parseLong(value);
					
		} catch (Exception e) {
			throw new RuntimeException("get id failed");
		}
	}
	
	public List<Long> nextId(int size){
		try {
			String value = HttpClientUtil.httpGet(String.format("http://10.8.10.100:11002/nextId/%d", size));
			
			return JSON.parseArray(value, Long.class);
					
		} catch (Exception e) {
			throw new RuntimeException("get id failed");
		}
	}
	
	@Test
	public void testss(){
		System.out.println(this.nextId());
		System.out.println(JSON.toJSONString(this.nextId(3)));
	}
}
