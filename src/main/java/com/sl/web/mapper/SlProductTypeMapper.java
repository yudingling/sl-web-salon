package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.db.SlProductType;
import com.sl.web.mybatis.MyMapper;

public interface SlProductTypeMapper extends MyMapper<SlProductType> {
	
	@Select("select a.* from sl_product_type a where a.bd_id=#{bdId} order by a.crt_ts desc limit #{startIndex}, #{size}")
	List<SlProductType> getProductTypes(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("bdId") String bdId);
}
