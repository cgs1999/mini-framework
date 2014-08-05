package com.duoduo.security.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.duoduo.security.util.AntUrlPathMatcher;
import com.duoduo.security.util.UrlMatcher;
import com.duoduo.system.manager.ResourceManager;
import com.duoduo.system.manager.RoleManager;
import com.duoduo.system.model.Resource;
import com.duoduo.system.model.Role;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	private RoleManager roleManager;
	private ResourceManager resourceManager;

	public MyInvocationSecurityMetadataSource(RoleManager roleManager, ResourceManager resourceManager) {
		super();
		this.resourceManager = resourceManager;
		this.resourceManager = resourceManager;
		loadResourceDefine();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		// 查出所有角色
		List<Role> roles = roleManager.listAll();
		if (roles != null) {
			for (Role role : roles) {
				// 查出某个角色可以访问的资源
				List<Resource> resources = resourceManager.listByRoleId("" + role.getId());
				if (resources != null) {
					for (Resource resource : resources) {
						Collection<ConfigAttribute> configAttributes = null;
						ConfigAttribute configAttribute = new SecurityConfig("" + role.getId());
						if (resourceMap.containsKey(resource.getUrl())) {
							configAttributes = resourceMap.get(resource.getUrl());
							configAttributes.add(configAttribute);
						} else {
							configAttributes = new ArrayList<ConfigAttribute>();
							configAttributes.add(configAttribute);
							resourceMap.put(resource.getUrl(), configAttributes);
						}
					}

				}

			}
		}

		// 其他权限
		Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
		list.add(new SecurityConfig("0"));
		resourceMap.put("/", list);
		resourceMap.put("/**", list);
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			String tempURL = resURL;
			if (!"/**".equals(resURL) && !"/".equals(resURL)) {
				tempURL = new StringBuilder().append("/").append(tempURL).toString();
			}
			if (urlMatcher.pathMatchesUrl(tempURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
