package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sl.web.model.BrandInfo;
import com.sl.web.model.db.SlBrand;
import com.sl.web.mybatis.MyMapper;

public interface SlBrandMapper extends MyMapper<SlBrand> {
	
	@Select("select a.bd_id, a.bd_nm, a.bd_logo, a.bd_url, b.file_pfx, b.file_nm, c.bdw_appid, c.bdw_secret, c.bdw_wechatpay_id, y.u_thirdid as brand_user_phone "
			+ " from sl_brand a inner join sl_brand_wechat c on a.bd_id = c.bd_id inner join sl_user_brand x on a.bd_id=x.bd_id and x.role_id ='BRAND' inner join sl_user y on x.u_id = y.u_id"
			+ " left join sl_file b on a.bd_logo = b.file_id"
			+ " order by a.crt_ts desc limit #{startIndex}, #{size}")
	List<BrandInfo> getBrands(@Param("startIndex") Integer startIndex, @Param("size") Integer size);
	
	@Select("select a.bd_id, a.bd_nm, a.bd_logo, a.bd_url, b.file_pfx, b.file_nm, c.bdw_appid, c.bdw_secret, c.bdw_wechatpay_id, y.u_thirdid as brand_user_phone "
			+ " from sl_brand a inner join sl_brand_wechat c on a.bd_id = c.bd_id inner join sl_user_brand x on a.bd_id=x.bd_id and x.role_id ='BRAND' inner join sl_user y on x.u_id = y.u_id"
			+ " left join sl_file b on a.bd_logo = b.file_id"
			+ " where a.bd_id = #{bdId} or a.bd_nm like #{nameLike} "
			+ " order by a.crt_ts desc limit #{startIndex}, #{size}")
	List<BrandInfo> searchBrands(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("bdId") String bdId, @Param("nameLike") String nameLike);
	
	@Select("select a.bd_id, a.bd_nm, a.bd_logo, a.bd_url, b.file_pfx, b.file_nm, c.bdw_appid, c.bdw_secret, c.bdw_wechatpay_id, y.u_thirdid as brand_user_phone "
			+ " from sl_brand a inner join sl_brand_wechat c on a.bd_id = c.bd_id inner join sl_user_brand x on a.bd_id=x.bd_id and x.role_id ='BRAND' inner join sl_user y on x.u_id = y.u_id"
			+ " left join sl_file b on a.bd_logo = b.file_id"
			+ " where a.bd_id = #{bdId}")
	List<BrandInfo> getBrand(@Param("bdId") String bdId);
	
	@Update("update sl_brand set bd_nm=#{bdNm}, bd_url=#{bdUrl}, bd_logo=#{bdLogo}, upt_ts=#{uptTs} where bd_id=#{bdId}")
	int updateBrand(@Param("bdId") String bdId, @Param("bdNm") String bdNm, @Param("bdUrl") String bdUrl, @Param("bdLogo") Long bdLogo, @Param("uptTs") Long uptTs);
}
