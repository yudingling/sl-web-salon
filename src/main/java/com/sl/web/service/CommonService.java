package com.sl.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sl.web.model.BrandInfo;
import com.sl.web.model.EventInfo;
import com.sl.web.model.ProductInfo;
import com.sl.web.model.ProjectProductInfo;
import com.sl.web.model.UserInfo;
import com.sl.web.util.HttpClientUtil;

@Configuration
@Service
public class CommonService {
	@Value("${file.url}")
	private String fileUrl;
	@Value("${id.url}")
	private String idUrl;
	
	public void setUserIconUrl(UserInfo info){
		if(info.getuIcon() != null){
			info.setuIconUrl(String.format("%s/%s%s", this.fileUrl, info.getFilePfx(), info.getFileNm()));
		}
	}
	
	public void setBrandIconUrl(List<BrandInfo> brands){
		brands.forEach(brand -> {
			if(brand.getBdLogo() != null){
				brand.setBdLogoUrl(String.format("%s/%s%s", this.fileUrl, brand.getFilePfx(), brand.getFileNm()));
			}
		});
	}
	
	public void setEventImageUrl(List<EventInfo> events){
		events.forEach(event -> {
			if(event.getEventImg() != null){
				event.setEventImgUrl(String.format("%s/%s%s", this.fileUrl, event.getFilePfx(), event.getFileNm()));
			}
		});
	}
	
	public void setProductImageUrl(List<ProductInfo> products){
		products.forEach(pd -> {
			if(pd.getPdIcon() != null){
				pd.setPdIconUrl(String.format("%s/%s%s", this.fileUrl, pd.getFilePfx(), pd.getFileNm()));
			}
		});
	}
	
	public void setProjectProductImageUrl(List<ProjectProductInfo> products){
		products.forEach(pd -> {
			if(pd.getPdIcon() != null){
				pd.setPdIconUrl(String.format("%s/%s%s", this.fileUrl, pd.getFilePfx(), pd.getFileNm()));
			}
		});
	}
	
	public Long nextId(){
		try {
			String value = HttpClientUtil.httpGet(this.idUrl);
			return Long.parseLong(value);
					
		} catch (Exception e) {
			throw new RuntimeException("get id failed");
		}
	}
	
	public List<Long> nextId(int size){
		try {
			String value = HttpClientUtil.httpGet(String.format("%s/%d", this.idUrl, size));
			
			return JSON.parseArray(value, Long.class);
					
		} catch (Exception e) {
			throw new RuntimeException("get id failed");
		}
	}
}
