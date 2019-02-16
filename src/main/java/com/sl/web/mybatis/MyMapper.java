package com.sl.web.mybatis;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * base mapper, should not be scanned by @MapperScan
 */
@RegisterMapper
public interface MyMapper<T> extends ExtMapper<T>, MyCatChildTableInsertListMapper<T>, Mapper<T> {
	
}
