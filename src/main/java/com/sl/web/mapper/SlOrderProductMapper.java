package com.sl.web.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.OrderProductInfo;
import com.sl.web.model.db.SlOrderProduct;
import com.sl.web.mybatis.MyMapper;

public interface SlOrderProductMapper extends MyMapper<SlOrderProduct> {
	
	@Select("<script> select a.*, b.pdtp_nm, d.file_pfx, d.file_nm, c.pd_nm, c.pd_icon from sl_order_product a inner join sl_product_type b on a.pdtp_id=b.pdtp_id "
			+ " inner join sl_product c on a.pd_id = c.pd_id "
			+ " left join sl_file d on c.pd_icon = d.file_id where a.od_id in "
			+ " <foreach item='odId' index='index' collection='odIds' open='(' separator=',' close=')'> "
			+ " #{odId} "
			+ " </foreach> "
			+ "</script>")
	List<OrderProductInfo> getOrderProducts(@Param("odIds") Set<Long> odIds);
}
