package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.sl.web.model.db.SlProject;
import com.sl.web.mybatis.MyMapper;

public interface SlProjectMapper extends MyMapper<SlProject> {
	@Select("<script> select a.* from sl_project a where a.bd_id=#{bdId}  "
			+ "<when test='nameLike != null'> "
			+ " and a.pj_nm like #{nameLike} "
			+ "</when> "
			+ " order by a.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<SlProject> getProjects(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("bdId") String bdId, @Param("nameLike") String nameLike);
	
	@Select("select a.* from sl_project a where a.bd_id=#{bdId} and a.pj_id=#{pjId}")
	List<SlProject> getProject(@Param("bdId") String bdId, @Param("pjId") Long pjId);
}
