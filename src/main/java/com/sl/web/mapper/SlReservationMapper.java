package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.sl.web.model.ReservationInfo;
import com.sl.web.model.db.SlReservation;
import com.sl.web.mybatis.MyMapper;

public interface SlReservationMapper extends MyMapper<SlReservation> {
	
	@Select("select a.rv_id,a.shop_id,a.rv_uid, a.rv_barber_uid,a.pj_id,a.rv_stm,a.rv_etm,a.rv_available,a.rv_active,a.rv_issystem, a.crt_ts,a.upt_ts, "
			+ " u.u_nm as rv_unm, u.u_phone as rv_uphone, c.pj_nm "
			+ " from sl_reservation a inner join sl_shop b on a.shop_id = b.shop_id inner join sl_project c on a.pj_id = c.pj_id "
			+ " inner join sl_user u on a.rv_uid = u.u_id "
			+ " where b.shop_id = #{shopId} and a.rv_available=1 and a.rv_active = 0 "
			+ " order by a.crt_ts desc")
	List<ReservationInfo> getReservations(@Param("shopId") Long shopId);
}
