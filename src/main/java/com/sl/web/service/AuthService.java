package com.sl.web.service;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.web.mapper.SlRoleApiMapper;
import com.sl.web.model.UserType;
import com.sl.web.model.db.SlRoleApi;

@Service
public class AuthService {
	@Autowired
	private SlRoleApiMapper slRoleApiMapper;
	
	public Map<UserType, Set<String>> getAllRoleApi() {
		Map<UserType, Set<String>> map = new EnumMap<>(UserType.class);
		
	    List<SlRoleApi> roleApis = this.slRoleApiMapper.selectAll();
		if(CollectionUtils.isNotEmpty(roleApis)){
			for(SlRoleApi api : roleApis){
				if(StringUtils.isNotEmpty(api.getApiUrl())){
					UserType tp = UserType.valueOf(api.getRoleId());
					
					Set<String> tmp = map.get(tp);
					if(tmp == null){
						tmp = new HashSet<>();
						map.put(tp, tmp);
					}
					
					String url = api.getApiUrl().trim().toLowerCase();
					if (api.getHttpGet() > 0) {
						tmp.add("get:" + url);
					}

					if (api.getHttpPost() > 0) {
						tmp.add("post:" + url);
					}

					if (api.getHttpPut() > 0) {
						tmp.add("put:" + url);
					}

					if (api.getHttpDelete() > 0) {
						tmp.add("delete:" + url);
					}
				}
			}
		}
		
		return map;
	}
}
