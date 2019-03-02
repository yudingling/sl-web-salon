package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.EventInfo;
import com.sl.web.model.db.SlShopEvent;
import com.sl.web.mybatis.MyMapper;

public interface SlShopEventMapper extends MyMapper<SlShopEvent> {
	@Select("<script> select a.*, b.file_pfx, b.file_nm from sl_shop_event a left join sl_file b on a.event_img = b.file_id where a.shop_id=#{shopId}  "
			+ "<when test='nameLike != null'> "
			+ " and a.event_nm like #{nameLike} "
			+ "</when> "
			+ " order by a.event_stm desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<EventInfo> getEvents(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("shopId") Long shopId, @Param("nameLike") String nameLike);
	
	@Select("<script> select count(1) from sl_shop_event a left join sl_file b on a.event_img = b.file_id where a.shop_id=#{shopId}  "
			+ "<when test='nameLike != null'> "
			+ " and a.event_nm like #{nameLike} "
			+ "</when> "
			+ "</script>")
	@ResultType(Long.class)
	Long getEventCount(@Param("shopId") Long shopId, @Param("nameLike") String nameLike);
	
	@Select("select a.*, b.file_pfx, b.file_nm from sl_shop_event a left join sl_file b on a.event_img = b.file_id where a.shop_id=#{shopId} and a.event_id=#{eventId}")
	List<EventInfo> getEvent(@Param("eventId") Long eventId, @Param("shopId") Long shopId);
}
