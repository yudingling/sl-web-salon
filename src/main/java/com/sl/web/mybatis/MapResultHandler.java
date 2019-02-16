package com.sl.web.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

@SuppressWarnings("rawtypes")
public class MapResultHandler<K, V> implements ResultHandler<Map> {
	private Map<K, V> result = new HashMap<>();
	
	private String keyField;
	private String valueField;
	
	public MapResultHandler(String keyField, String valueField) {
		super();
		this.keyField = keyField.toLowerCase();
		this.valueField = valueField.toLowerCase();
	}
	
	public Map<K, V> getResult(){
		return this.result;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void handleResult(ResultContext<? extends Map> resultContext) {
		Map m = resultContext.getResultObject();
		
		this.result.put((K) m.get(this.keyField), (V) m.get(this.valueField));
	}
}
