package com.sl.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.sl.web.model.db.SlBrandWechat;
import com.sl.web.mybatis.MyMapper;

public interface SlBrandWechatMapper extends MyMapper<SlBrandWechat> {
	
	@Update("update sl_brand_wechat set bdw_appid=#{appId}, bdw_secret=#{appSecret}, bdw_wechatpay_id=#{wePayId}, upt_ts=#{uptTs} where bd_id=#{bdId}")
	int updateBrandWechat(@Param("bdId") String bdId, @Param("appId") String appId, @Param("appSecret") String appSecret, @Param("wePayId") String wePayId, @Param("uptTs") Long uptTs);
}
