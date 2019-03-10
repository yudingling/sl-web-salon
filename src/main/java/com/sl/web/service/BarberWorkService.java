package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlBarberWorkMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.BarberWorkInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlBarberWork;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;

@Service
public class BarberWorkService {
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlBarberWorkMapper workMapper;
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
	
	public ApiPageResult<BarberWorkInfo> getList(int pageNum, int pageSize, SigninResult auth, Long barberUId){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<BarberWorkInfo> works = this.workMapper.getWorks(startIndex, size, shopId, barberUId);
			
			if(CollectionUtils.isNotEmpty(works)){
				this.commonService.setWorkImageUrl(works);
			}
			
			return new ApiPageResult<>(this.workMapper.getWorkCount(shopId, barberUId), works);
		}
		
		return new ApiPageResult<>(0l, new ArrayList<>());
	}
	
	public BarberWorkInfo get(SigninResult auth, Long bbwId){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<BarberWorkInfo> works = this.workMapper.getWork(bbwId);
			
			if(CollectionUtils.isNotEmpty(works)){
				this.commonService.setWorkImageUrl(works);
				
				return works.get(0);
			}
		}
		
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, BarberWorkInfo work){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlBarberWork sw = new SlBarberWork(
				this.commonService.nextId(), 
				work.getuId(), 
				auth.getBrandId(), 
				work.getBbwTitle(), 
				work.getBbwImg(), 
				0l, 
				ts, 
				ts);
		
		return this.workMapper.insert(sw) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlBarberWork.class);
	    example.createCriteria().andIn("bbwId", ids);
	    
	    this.workMapper.deleteByExample(example);
	    
	    return true;
	}
}
