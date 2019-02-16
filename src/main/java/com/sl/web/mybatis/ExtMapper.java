package com.sl.web.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface ExtMapper <T> {
	@InsertProvider(type = ExtMapperProvider.class, method = "dynamicSQL")
	int insertUpdate(@Param("record") T record);
	
	@InsertProvider(type = ExtMapperProvider.class, method = "dynamicSQL")
	int insertUpdateList(@Param("list") List<T> recordList);
	
	@InsertProvider(type = ExtMapperProvider.class, method = "dynamicSQL")
    int insertList(@Param("list") List<T> recordList);
	
	/**
	 * batch update, update by primary key and using 'update..case..when' syntax.
	 */
	@UpdateProvider(type = ExtMapperProvider.class, method = "dynamicSQL")
	int updateBatchById(@Param("list") List<T> recordList, @Param("updateFields") List<String> updateFields);
	
	/**
	 * batch update, using 'multi-sql' syntax.
	 * 
	 * @param recordList
	 * @param updateFields
	 * @param whereFields unique key for 'where condition', the order of the fields in the 'where' statement is the same as the order in the list.
	 */
	@UpdateProvider(type = ExtMapperProvider.class, method = "dynamicSQL")
	int updateBatch(@Param("list") List<T> recordList, @Param("updateFields") List<String> updateFields, @Param("whereFields") List<String> whereFields);
}
