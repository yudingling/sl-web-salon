package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.sl.web.model.ProductInfo;
import com.sl.web.model.db.SlProduct;
import com.sl.web.mybatis.MyMapper;

public interface SlProductMapper extends MyMapper<SlProduct> {
	
	@Select("<script> select a.*, c.pdtp_nm, b.file_pfx, b.file_nm from sl_product a inner join sl_product_type c on a.pdtp_id=c.pdtp_id"
			+ " left join sl_file b on a.pd_icon = b.file_id where a.bd_id=#{bdId}  "
			+ "<when test='nameLike != null'> "
			+ " and a.pd_nm like #{nameLike} "
			+ "</when> "
			+ "<when test='pdtpId != null'> "
			+ " and a.pdtp_id = #{pdtpId} "
			+ "</when> "
			+ "<when test='enabled != null'> "
			+ " and a.pd_enable = #{enabled} "
			+ "</when> "
			+ " order by a.pdtp_id, a.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<ProductInfo> getProducts(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("bdId") String bdId, 
			@Param("pdtpId") Long pdtpId, @Param("nameLike") String nameLike, @Param("enabled") Integer enabled);
	
	@Select("select a.*, c.pdtp_nm, b.file_pfx, b.file_nm from sl_product a inner join sl_product_type c on a.pdtp_id=c.pdtp_id"
			+ " left join sl_file b on a.pd_icon = b.file_id where a.bd_id=#{bdId} and a.pd_id=#{pdId}")
	List<ProductInfo> getProduct(@Param("bdId") String bdId, @Param("pdId") Long pdId);
}
