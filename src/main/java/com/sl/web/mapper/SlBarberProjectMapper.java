package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.BarberProjectInfo;
import com.sl.web.model.db.SlBarberProject;
import com.sl.web.mybatis.MyMapper;

public interface SlBarberProjectMapper extends MyMapper<SlBarberProject> {
	
	@Select("<script> select count(1) from sl_barber_project a inner join sl_user_barber b on a.u_id = b.u_id and b.shop_id = #{shopId} "
			+ "<when test='uId != null'> "
			+ " and a.u_Id = #{uId} "
			+ "</when> "
			+ "</script>")
	@ResultType(Long.class)
	Long getProjectCount(@Param("uId") Long uId, @Param("shopId") Long shopId);
	
	@Select("<script> select b.*, a.bbp_price, a.u_id, a.bbp_Id, d.u_nm  from sl_barber_project a inner join sl_project b on a.pj_id = b.pj_id and b.pj_enable=1 "
			+ " inner join sl_user_barber c on a.u_id = c.u_id and c.shop_id = #{shopId} inner join sl_user d on a.u_id = d.u_id "
			+ "<when test='uId != null'> "
			+ " and a.u_Id = #{uId} "
			+ "</when> "
			+ " order by a.crt_ts, a.u_id desc limit #{startIndex}, #{size} </script>")
	List<BarberProjectInfo> getProjects(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("uId") Long uId, @Param("shopId") Long shopId);
	
	@Select("select b.*, a.bbp_price, a.u_id, a.bbp_Id from sl_barber_project a inner join sl_project b on a.pj_id = b.pj_id and b.pj_enable=1 and a.bbp_id = #{bbpId}")
	List<BarberProjectInfo> getProject(@Param("bbpId") Long bbpId);
}
