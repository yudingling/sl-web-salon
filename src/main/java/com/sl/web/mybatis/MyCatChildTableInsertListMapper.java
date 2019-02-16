package com.sl.web.mybatis;

import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface MyCatChildTableInsertListMapper<T>{
	
    @InsertProvider(type = MyCatChildTableSpecialProvider.class, method = "dynamicSQL")
	int insertListForMyCatChildTable(List<T> recordList);
}
