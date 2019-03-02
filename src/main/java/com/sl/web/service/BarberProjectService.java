package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlBarberProjectMapper;
import com.sl.web.mapper.SlProjectMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.BarberProjectInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlBarberProject;
import com.sl.web.model.db.SlProject;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiArrayResult;
import com.sl.web.model.result.ApiPageResult;

@Service
public class BarberProjectService {
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlBarberProjectMapper bbProjectMapper;
	@Autowired
	private SlProjectMapper projectMapper;
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
	
	private Long getShopId(SigninResult auth){
		Example example = new Example(SlUserShop.class);
	    example.createCriteria().andEqualTo("uId", auth.getUserId());
	    
	    List<SlUserShop> users = this.userShopMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(users)){
	    	return users.get(0).getShopId();
	    	
	    }else{
	    	return null;
	    }
	}
	
	public ApiArrayResult<SlProject> getAllProjects(SigninResult auth){
		Example example = new Example(SlProject.class);
	    example.createCriteria().andEqualTo("bdId", auth.getBrandId()).andEqualTo("pjEnable", 1);
	    
	    List<SlProject> data = this.projectMapper.selectByExample(example);
	    
	    return new ApiArrayResult<>(data);
	}
	
	public ApiPageResult<BarberProjectInfo> getList(int pageNum, int pageSize, Long uId, SigninResult auth){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		Long shopId = this.getShopId(auth);
		
		if(shopId != null){			
			List<BarberProjectInfo> data = this.bbProjectMapper.getProjects(startIndex, size, uId, shopId);
			
			return new ApiPageResult<>(this.bbProjectMapper.getProjectCount(uId, shopId), data);
			
		}else{
			return new ApiPageResult<>(0l, new ArrayList<>());
		}
	}
	
	public BarberProjectInfo get(SigninResult auth, Long bbpId){
		Long shopId = this.getShopId(auth);
		
		if(shopId != null){			
			List<BarberProjectInfo> data = this.bbProjectMapper.getProject(bbpId);
			
			return CollectionUtils.isNotEmpty(data) ? data.get(0) : null;
			
		}else{
			return null;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, BarberProjectInfo pp){
		Long shopId = this.getShopId(auth);
		
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlBarberProject sb = new SlBarberProject(
				this.commonService.nextId(), 
				pp.getuId(), 
				pp.getPjId(), 
				pp.getBbpPrice(), 
				ts, 
				ts);
		
		return this.bbProjectMapper.insert(sb) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean edit(SigninResult auth, BarberProjectInfo pp){
		Long shopId = this.getShopId(auth);
		
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlBarberProject sb = new SlBarberProject();
		sb.setBbpId(pp.getBbpId());
		sb.setBbpPrice(pp.getBbpPrice());
		sb.setUptTs(ts);
		
		return this.bbProjectMapper.updateByPrimaryKeySelective(sb) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		Long shopId = this.getShopId(auth);
		
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlBarberProject.class);
	    example.createCriteria().andIn("bbpId", ids);
	    
	    this.bbProjectMapper.deleteByExample(example);
	    
	    return true;
	}
}
