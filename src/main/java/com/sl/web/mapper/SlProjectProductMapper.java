package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.ProjectProductInfo;
import com.sl.web.model.db.SlProjectProduct;
import com.sl.web.mybatis.MyMapper;

public interface SlProjectProductMapper extends MyMapper<SlProjectProduct> {
	
	@Select("select a.*, b.pdtp_nm, c.pd_nm, c.pd_price, c.pd_icon, x.file_pfx, x.file_nm from sl_project_product a inner join sl_product_type b on a.pdtp_id = b.pdtp_id inner join sl_product c on a.pd_id = c.pd_id"
			+ " inner join sl_project d on a.pj_id = d.pj_id "
			+ " left join sl_file x on c.pd_icon = x.file_id"
			+ " where"
			+ " a.pj_id = #{pjId} and d.bd_id = #{bdId} order by a.crt_ts desc")
	List<ProjectProductInfo> getProducts(@Param("bdId") String bdId, @Param("pjId") Long pjId);
}
