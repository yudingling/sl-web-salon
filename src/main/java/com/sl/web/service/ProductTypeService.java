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

import com.sl.web.mapper.SlProductMapper;
import com.sl.web.mapper.SlProductTypeMapper;
import com.sl.web.mapper.SlUserBrandMapper;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlProduct;
import com.sl.web.model.db.SlProductType;
import com.sl.web.model.db.SlUserBrand;
import com.sl.web.model.result.ApiPageResult;

@Service
public class ProductTypeService {
	@Autowired
	private SlUserBrandMapper userBrandMapper;
	@Autowired
	private SlProductTypeMapper productTypeMapper;
	@Autowired
	private SlProductMapper productMapper;
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
	
	private long getProductCount(String bdId){
		Example example = new Example(SlProductType.class);
	    example.createCriteria().andEqualTo("bdId", bdId);
	    
	    return (long) this.productTypeMapper.selectCountByExample(example);
	}
	
	public ApiPageResult<SlProductType> getList(int pageNum, int pageSize, SigninResult auth){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			List<SlProductType> productTypes = this.productTypeMapper.getProductTypes(startIndex, size, bdId);
			
			return new ApiPageResult<>(this.getProductCount(bdId), productTypes);
			
		}else{
			return new ApiPageResult<>(0l, new ArrayList<>());
		}
	}
	
	public SlProductType get(SigninResult auth, Long pdtpId){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			Example example = new Example(SlProductType.class);
		    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("pdtpId", pdtpId);
			
			List<SlProductType> products = this.productTypeMapper.selectByExample(example);
			
			if(CollectionUtils.isNotEmpty(products)){
				return products.get(0);
			}
		}
		
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, SlProductType pt){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlProductType added = new SlProductType(this.commonService.nextId(), pt.getPdtpNm(), bdId, ts, ts);
		
		return this.productTypeMapper.insert(added) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SigninResult auth, SlProductType pt){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		Example example = new Example(SlProductType.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("pdtpId", pt.getPdtpId());
		
	    SlProductType se = new SlProductType();
	    se.setPdtpNm(pt.getPdtpNm());
	    se.setUptTs(ts);
	    
		return this.productTypeMapper.updateByExampleSelective(se, example) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Example example = new Example(SlProductType.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andIn("pdtpId", ids);
	    this.productTypeMapper.deleteByExample(example);
	    
	    SlProduct upt = new SlProduct();
	    upt.setPdEnable(0);
	    upt.setUptTs(System.currentTimeMillis());
	    
	    Example example2 = new Example(SlProduct.class);
	    example2.createCriteria().andEqualTo("bdId", bdId).andIn("pdtpId", ids);
	    this.productMapper.updateByExampleSelective(upt, example2);
	    
	    return true;
	}
}
