package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.db.SlUser;
import com.sl.web.mybatis.MyMapper;

public interface SlUserMapper extends MyMapper<SlUser> {
	
	@Select("<script> select y.u_id, y.u_nm, y.u_thirdid from sl_user_shop x inner join sl_user y on x.u_id = y.u_id and x.shop_id = #{shopId} and x.role_id ='SHOP_WORKER' "
			+ "<when test='phoneLike != null'> "
			+ " and y.u_thirdid like #{phoneLike} "
			+ "</when> "
			+ " order by y.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<SlUser> getShopWorkers(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("shopId") Long shopId, @Param("phoneLike") String phoneLike);
}
