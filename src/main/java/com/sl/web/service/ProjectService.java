package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlProjectMapper;
import com.sl.web.mapper.SlProjectProductMapper;
import com.sl.web.mapper.SlUserBrandMapper;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlProject;
import com.sl.web.model.db.SlProjectProduct;
import com.sl.web.model.db.SlUserBrand;
import com.sl.web.model.result.ApiPageResult;

@Service
public class ProjectService {
	@Autowired
	private SlUserBrandMapper userBrandMapper;
	@Autowired
	private SlProjectMapper projectMapper;
	@Autowired
	private SlProjectProductMapper ppMapper;
	@Autowired
	private CommonService commonService;
	
	private int getStartIndex(int pageNum, int pageSize){
		int startIndex = (pageNum - 1) * pageSize;
		if(startIndex < 0){
			startIndex = 0;
		}
		
		return startIndex;
	}
	
	private int getSize(int pageSize){
		return pageSize > 0 ? pageSize : 10;
	}
	
	private String getBrandId(SigninResult auth){
		Example example = new Example(SlUserBrand.class);
	    example.createCriteria().andEqualTo("uId", auth.getUserId());
	    
	    List<SlUserBrand> users = this.userBrandMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(users)){
	    	return users.get(0).getBdId();
	    	
	    }else{
	    	return null;
	    }
	}
	
	public ApiPageResult<SlProject> getList(int pageNum, int pageSize, SigninResult auth, String name){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		if(StringUtils.isEmpty(name)){
			name = null;
		}else{
			name = "%" + name + "%";
		}
		
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			List<SlProject> projects = this.projectMapper.getProjects(startIndex, size, bdId, name);
			
			return new ApiPageResult<>(this.projectMapper.getProjectCount(bdId, name), projects);	
		}
		
		return new ApiPageResult<>(0l, new ArrayList<>());
	}
	
	public SlProject get(SigninResult auth, Long pjId){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			List<SlProject> products = this.projectMapper.getProject(bdId, pjId);
			
			if(CollectionUtils.isNotEmpty(products)){
				return products.get(0);
			}
		}
		
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, SlProject project){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlProject pj = new SlProject(
				this.commonService.nextId(), 
				project.getPjNm(), 
				bdId, 
				1, 
				project.getPjPrice(), 
				project.getPjHour(), 
				ts, 
				ts);
		
		return this.projectMapper.insert(pj) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SigninResult auth, SlProject project){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		Example example = new Example(SlProject.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("pjId", project.getPjId());
		
	    SlProject se = new SlProject();
	    se.setPjEnable(project.getPjEnable());
	    se.setPjHour(project.getPjHour());
	    se.setPjPrice(project.getPjPrice());
	    se.setPjNm(project.getPjNm());
	    se.setUptTs(ts);
	    
		return this.projectMapper.updateByExampleSelective(se, example) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Example example = new Example(SlProject.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andIn("pjId", ids);
	    this.projectMapper.deleteByExample(example);
	    
	    Example example2 = new Example(SlProjectProduct.class);
	    example2.createCriteria().andIn("pjId", ids);
	    this.ppMapper.deleteByExample(example2);
	    
	    return true;
	}
}
