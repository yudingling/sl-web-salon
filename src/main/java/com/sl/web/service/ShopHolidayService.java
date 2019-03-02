package com.sl.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlShopHolidayMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.HolidayInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlShopHoliday;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.DateUtil;

@Service
public class ShopHolidayService {
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlShopHolidayMapper holidayMapper;
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
	
	public ApiPageResult<HolidayInfo> getList(int pageNum, int pageSize, SigninResult auth, String desc){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		if(StringUtils.isEmpty(desc)){
			desc = null;
		}else{
			desc = "%" + desc + "%";
		}
		
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<HolidayInfo> holidays = this.holidayMapper.getHolidays(startIndex, size, shopId, desc);
			
			return new ApiPageResult<>(this.holidayMapper.getHolidayCount(shopId, desc), holidays);
			
		}else{
			return new ApiPageResult<>(0l, new ArrayList<>());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, HolidayInfo holiday){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlShopHoliday sh = new SlShopHoliday(
				this.commonService.nextId(), 
				auth.getBrandId(), 
				shopId, 
				holiday.getSofDesc().trim(), 
				DateUtil.toDate(holiday.getSofStmStr(), "yyyy-MM-dd HH:mm").getTime(), 
				DateUtil.toDate(holiday.getSofEtmStr(), "yyyy-MM-dd HH:mm").getTime(), 
				ts, 
				ts);
		
		return this.holidayMapper.insert(sh) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlShopHoliday.class);
	    example.createCriteria().andEqualTo("shopId", shopId).andIn("sofId", ids);
	    
	    this.holidayMapper.deleteByExample(example);
	    
	    return true;
	}
}
