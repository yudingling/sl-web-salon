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

import com.sl.web.mapper.SlShopEventMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.EventInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlShopEvent;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.DateUtil;

@Service
public class ShopEventService {
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlShopEventMapper eventMapper;
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
	
	public ApiPageResult<EventInfo> getList(int pageNum, int pageSize, SigninResult auth, String name){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		if(StringUtils.isEmpty(name)){
			name = null;
		}else{
			name = "%" + name + "%";
		}
		
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<EventInfo> events = this.eventMapper.getEvents(startIndex, size, shopId, name);
			
			if(CollectionUtils.isNotEmpty(events)){
				this.commonService.setEventImageUrl(events);
			}
			
			return new ApiPageResult<>(this.eventMapper.getEventCount(shopId, name), events);
		}
		
		return new ApiPageResult<>(0l, new ArrayList<>());
	}
	
	public EventInfo get(SigninResult auth, Long eventId){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<EventInfo> events = this.eventMapper.getEvent(eventId, shopId);
			
			if(CollectionUtils.isNotEmpty(events)){
				this.commonService.setEventImageUrl(events);
				
				EventInfo event = events.get(0);
				
				event.setEventStmStr(DateUtil.toString(event.getEventStm(), "yyyy-MM-dd"));
				event.setEventEtmStr(DateUtil.toString(event.getEventEtm(), "yyyy-MM-dd"));
				
				return event;
			}
		}
		
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, EventInfo event){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlShopEvent se = new SlShopEvent(
				this.commonService.nextId(), 
				event.getEventNm(), 
				auth.getBrandId(), 
				shopId, 
				event.getEventImg(), 
				event.getEventUrl(), 
				DateUtil.toDate(event.getEventStmStr(), "yyyy-MM-dd").getTime(), 
				DateUtil.toDate(event.getEventEtmStr(), "yyyy-MM-dd").getTime(), 
				1, 
				ts, 
				ts);
		
		return this.eventMapper.insert(se) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SigninResult auth, EventInfo event){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		Example example = new Example(SlShopEvent.class);
	    example.createCriteria().andEqualTo("shopId", shopId).andEqualTo("eventId", event.getEventId());
		
		SlShopEvent se = new SlShopEvent();
		se.setEventUrl(event.getEventUrl());
		se.setEventNm(event.getEventNm());
		se.setEventImg(event.getEventImg());
		se.setEventStm(DateUtil.toDate(event.getEventStmStr(), "yyyy-MM-dd").getTime());
		se.setEventEtm(DateUtil.toDate(event.getEventEtmStr(), "yyyy-MM-dd").getTime());
		se.setUptTs(ts);
		
		return this.eventMapper.updateByExampleSelective(se, example) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		Long shopId = this.getShopId(auth);
		if(shopId == null){
			return false;
		}
		
		Example example = new Example(SlShopEvent.class);
	    example.createCriteria().andEqualTo("shopId", shopId).andIn("eventId", ids);
	    
	    this.eventMapper.deleteByExample(example);
	    
	    return true;
	}
}
