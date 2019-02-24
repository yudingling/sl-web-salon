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
import com.sl.web.mapper.SlUserBrandMapper;
import com.sl.web.model.ProductInfo;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlProduct;
import com.sl.web.model.db.SlUserBrand;
import com.sl.web.model.result.ApiPageResult;

@Service
public class ProductService {
	@Autowired
	private SlUserBrandMapper userBrandMapper;
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
		Example example = new Example(SlProduct.class);
	    example.createCriteria().andEqualTo("bdId", bdId);
	    
	    return (long) this.productMapper.selectCountByExample(example);
	}
	
	public ApiPageResult<ProductInfo> getList(int pageNum, int pageSize, SigninResult auth, String name, Long pdtpId, Integer enabled){
		int startIndex = this.getStartIndex(pageNum, pageSize);
		int size = this.getSize(pageSize);
		
		if(StringUtils.isEmpty(name)){
			name = null;
		}else{
			name = "%" + name + "%";
		}
		
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			List<ProductInfo> products = this.productMapper.getProducts(startIndex, size, bdId, pdtpId, name, enabled);
			
			if(CollectionUtils.isNotEmpty(products)){
				this.commonService.setProductImageUrl(products);
				
				return new ApiPageResult<>(this.getProductCount(bdId), products);
			}	
		}
		
		return new ApiPageResult<>(0l, new ArrayList<>());
	}
	
	public ProductInfo get(SigninResult auth, Long pdId){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isNotEmpty(bdId)){
			List<ProductInfo> products = this.productMapper.getProduct(bdId, pdId);
			
			if(CollectionUtils.isNotEmpty(products)){
				this.commonService.setProductImageUrl(products);
				
				return products.get(0);
			}
		}
		
		return null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SigninResult auth, ProductInfo product){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		SlProduct pd = new SlProduct(
				this.commonService.nextId(), 
				product.getPdNm(), 
				bdId, 
				product.getPdtpId(), 
				product.getPdDesc(), 
				product.getPdIcon(), 
				product.getPdPrice(), 
				1, 
				ts, 
				ts);
		
		return this.productMapper.insert(pd) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean update(SigninResult auth, ProductInfo product){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Long ts = System.currentTimeMillis();
		
		Example example = new Example(SlProduct.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andEqualTo("pdId", product.getPdId());
		
	    SlProduct se = new SlProduct();
	    se.setPdDesc(product.getPdDesc());
	    se.setPdEnable(product.getPdEnable());
	    se.setPdIcon(product.getPdIcon());
	    se.setPdNm(product.getPdNm());
	    se.setPdPrice(product.getPdPrice());
	    se.setPdtpId(product.getPdtpId());
	    se.setUptTs(ts);
	    
		return this.productMapper.updateByExampleSelective(se, example) == 1;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(SigninResult auth, Set<Long> ids){
		String bdId = this.getBrandId(auth);
		if(StringUtils.isEmpty(bdId)){
			return false;
		}
		
		Example example = new Example(SlProduct.class);
	    example.createCriteria().andEqualTo("bdId", bdId).andIn("pdId", ids);
	    
	    this.productMapper.deleteByExample(example);
	    
	    return true;
	}
}
