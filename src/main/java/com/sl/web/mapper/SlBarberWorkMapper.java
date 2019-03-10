package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.BarberWorkInfo;
import com.sl.web.model.db.SlBarberWork;
import com.sl.web.mybatis.MyMapper;

public interface SlBarberWorkMapper extends MyMapper<SlBarberWork> {
	@Select("<script> select a.*, c.file_pfx, c.file_nm, d.u_nm from sl_barber_work a inner join sl_user_barber b on a.u_id = b.u_id inner join sl_user d on a.u_id = d.u_id "
			+ " left join sl_file c on a.bbw_img = c.file_id where b.shop_id = #{shopId}"
			+ "<when test='barberUId != null'> "
			+ " and a.u_id = #{barberUId} "
			+ "</when> "
			+ " order by a.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<BarberWorkInfo> getWorks(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("shopId") Long shopId, @Param("barberUId") Long barberUId);
	
	@Select("<script> select count(1) from sl_barber_work a inner join sl_user_barber b on a.u_id = b.u_id  "
			+ " left join sl_file c on a.bbw_img = c.file_id where b.shop_id = #{shopId}"
			+ "<when test='barberUId != null'> "
			+ " and a.u_id = #{barberUId} "
			+ "</when> "
			+ "</script>")
	@ResultType(Long.class)
	Long getWorkCount(@Param("shopId") Long shopId, @Param("barberUId") Long barberUId);
	
	@Select("select a.*, c.file_pfx, c.file_nm, d.u_nm from sl_barber_work a inner join sl_user_barber b on a.u_id = b.u_id inner join sl_user d on a.u_id = d.u_id "
			+ " left join sl_file c on a.bbw_img = c.file_id where a.bbw_id = #{bbwId}")
	List<BarberWorkInfo> getWork(@Param("bbwId") Long bbwId);
}
