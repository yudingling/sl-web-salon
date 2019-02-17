package com.sl.web.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.sl.web.mapper.SlBrandMapper;
import com.sl.web.mapper.SlBrandWechatMapper;
import com.sl.web.model.BrandInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlBrand;
import com.sl.web.model.db.SlBrandWechat;
import com.sl.web.model.result.ApiPageResult;
import com.sl.web.util.Common;

@Service
public class BrandService {
	@Autowired
	private SlBrandMapper slBrandMapper;
	@Autowired
	private SlBrandWechatMapper brandWechatMapper;
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
	
	public ApiPageResult<BrandInfo> getList(int pageNum, int pageSize, String idOrName){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		idOrName = Common.valString(idOrName);
		List<BrandInfo> brands = StringUtils.isEmpty(idOrName) ? 
				this.slBrandMapper.getBrands(startIndex, size) : this.slBrandMapper.searchBrands(startIndex, size, idOrName, "%" + idOrName + "%");
		
		if(CollectionUtils.isNotEmpty(brands)){
			this.commonService.setIconUrl(brands);
		}
		
		int totalSize = this.slBrandMapper.selectCountByExample(null);
		
		return new ApiPageResult<>((long)totalSize, brands);
	}
	
	public BrandInfo get(String bdId){
		List<BrandInfo> brands = this.slBrandMapper.getBrand(bdId);
		
		return CollectionUtils.isNotEmpty(brands) ? brands.get(0) : null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, BrandInfo brand){
		if(!this.isValid(brand)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlBrand bd = new SlBrand(brand.getBdId(), brand.getBdNm(), brand.getBdLogo(), brand.getBdUrl(), ts, ts);
		if(this.slBrandMapper.insert(bd) == 1){
			SlBrandWechat bdWechat = new SlBrandWechat(
					this.commonService.nextId(), 
					bd.getBdId(), 
					brand.getBdwAppid(), 
					brand.getBdwSecret(), 
					brand.getBdwWechatpayId(), 
					ts, 
					ts);
			
			return this.brandWechatMapper.insert(bdWechat) == 1;
			
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SigninResult auth, BrandInfo brand){
		if(!this.isValid(brand)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		if(this.slBrandMapper.updateBrand(brand.getBdId(), brand.getBdNm(), brand.getBdUrl(), brand.getBdLogo(), ts) == 1){
			return this.brandWechatMapper.updateBrandWechat(brand.getBdId(), brand.getBdwAppid(), brand.getBdwSecret(), brand.getBdwWechatpayId(), ts) == 1;
			
		}else{
			return false;
		}
	}
	
	private boolean isValid(BrandInfo brand){
		if(StringUtils.isEmpty(brand.getBdId()) || StringUtils.isEmpty(brand.getBdNm()) 
				|| StringUtils.isEmpty(brand.getBdwAppid()) || StringUtils.isEmpty(brand.getBdwSecret()) || StringUtils.isEmpty(brand.getBdwWechatpayId())){
			return false;
			
		}else{
			return true;
		}
	}
}
