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
import com.sl.web.mapper.SlProjectProductMapper;
import com.sl.web.mapper.SlUserBrandMapper;
import com.sl.web.model.ProjectProductInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlProduct;
import com.sl.web.model.db.SlProjectProduct;
import com.sl.web.model.db.SlUserBrand;
import com.sl.web.model.result.ApiArrayResult;

@Service
public class ProjectProductService {
	@Autowired
	private SlUserBrandMapper userBrandMapper;
	@Autowired
	private SlProductMapper productMapper;
	@Autowired
	private SlProjectProductMapper ppMapper;
	@Autowired
	private CommonService commonService;
	
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
	
	public ApiArrayResult<ProjectProductInfo> getList(Long pjId, SigninResult auth){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			List<ProjectProductInfo> pds = this.ppMapper.getProducts(bdId, pjId);
			
			if(CollectionUtils.isNotEmpty(pds)){
				this.commonService.setProjectProductImageUrl(pds);
			}
			
			return new ApiArrayResult<>(pds);	
		}
		
		return new ApiArrayResult<>(new ArrayList<>());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, ProjectProductInfo pp){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlProduct product = this.productMapper.selectByPrimaryKey(pp.getPdId());
		if(product != null){
			SlProjectProduct ppAdded = new SlProjectProduct(
					this.commonService.nextId(), 
					pp.getPjId(), 
					product.getPdtpId(), 
					product.getPdId(), 
					ts, 
					ts);
			
			return this.ppMapper.insert(ppAdded) == 1;
			
		}else{
			return false;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Example example = new Example(SlProjectProduct.class);
	    example.createCriteria().andIn("pjpId", ids);
	    
	    this.ppMapper.deleteByExample(example);
	    
	    return true;
	}
}
