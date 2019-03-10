package com.sl.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.sl.web.mapper.SlReservationMapper;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.mapper.SlUserShopMapper;
import com.sl.web.model.ReservationInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlUser;
import com.sl.web.model.db.SlUserShop;
import com.sl.web.model.result.ApiArrayResult;

@Service
public class ReservationService {
	@Autowired
	private SlUserShopMapper userShopMapper;
	@Autowired
	private SlReservationMapper reservationMapper;
	@Autowired
	private SlUserMapper userMapper;
	
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
	
	public ApiArrayResult<ReservationInfo> getReservations(SigninResult auth){
		Long shopId = this.getShopId(auth);
		if(shopId != null){
			List<ReservationInfo> reservations = this.reservationMapper.getReservations(shopId);
			
			if(CollectionUtils.isNotEmpty(reservations)){
				this.setOdBarberInfo(reservations);
			}
			
			return new ApiArrayResult<>(reservations);
					
		}else{
			return new ApiArrayResult<>(new ArrayList<>());
		}
	}
	
	private void setOdBarberInfo(List<ReservationInfo> reservations){
		Set<Long> uIds = reservations.stream().map(ReservationInfo::getRvBarberUid).collect(Collectors.toSet());
		
		Example example = new Example(SlUser.class);
	    example.createCriteria().andIn("uId", uIds);
		
		List<SlUser> barbers = this.userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(barbers)){
			Map<Long, SlUser> bbMap = new HashMap<>();
			barbers.forEach(bb -> {
				bbMap.put(bb.getuId(), bb);
			});
	    	
	    	for(ReservationInfo rv : reservations){
	    		SlUser tmp = bbMap.get(rv.getRvBarberUid());
	    		if(tmp != null){
	    			rv.setRvBarberUnm(tmp.getuNm());
	    		}
	    	}
		}
	}
}
