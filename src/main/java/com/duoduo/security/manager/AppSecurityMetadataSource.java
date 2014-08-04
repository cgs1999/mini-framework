package com.duoduo.security.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.duoduo.system.manager.ResourceManager;
import com.duoduo.system.manager.RoleManager;
import com.duoduo.system.model.Resource;
import com.duoduo.system.model.Role;

/**
 * 从数据库中查询出资源和权限（角色），并将它们的关系对应起来
 * @author chengesheng@gmail.com
 * @date 2014-8-4 下午4:01:52
 * @version 1.0.0
 */
public class AppSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Logger log = LoggerFactory.getLogger(AppSecurityMetadataSource.class);

	private RoleManager roleManager;
	private ResourceManager resourceManager;

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	private AntPathMatcher urlMatcher = new AntPathMatcher();

	/* 保存资源和权限的对应关系 key-资源url value-权限 */
	private Map<String, Collection<ConfigAttribute>> relationMap = null;

	public AppSecurityMetadataSource(RoleManager roleManager, ResourceManager resourceManager) {
		log.info("执行 AppSecurityMetadataSource 构造方法");
		this.roleManager = roleManager;
		this.resourceManager = resourceManager;
		loadResourceAndRoleRelation();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 根据请求的url,获取访问该url所需的权限（角色）
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
		// 获取请求的url地址
		String requestUrl = ((FilterInvocation) obj).getRequestUrl();
		log.info("请求的 requestUrl :" + requestUrl);
		Iterator<String> it = relationMap.keySet().iterator();
		while (it.hasNext()) {
			String url = it.next();
			log.info("配置的 url :" + url);
			if (requestUrl.indexOf("?") != -1) {
				requestUrl = requestUrl.substring(0, url.indexOf("?"));
			}
			log.info("hey man :" + url);
			if (urlMatcher.match(url, requestUrl)) {
				log.info("已匹配 :" + url);
				return relationMap.get(url);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	/**
	 * 加载资源和角色的对应关系
	 */
	private void loadResourceAndRoleRelation() {
		relationMap = new HashMap<String, Collection<ConfigAttribute>>();
		// 查出所有角色
		List<Role> roles = roleManager.listAll();
		if (roles != null) {
			for (Role role : roles) {
				// 查出某个角色可以访问的资源
				List<Resource> resources = resourceManager.listByRoleId("" + role.getId());
				if (resources != null) {
					for (Resource resource : resources) {
						Collection<ConfigAttribute> configAttributes = null;
						ConfigAttribute configAttribute = new SecurityConfig(role.getName());
						if (relationMap.containsKey(resource.getUrl())) {
							configAttributes = relationMap.get(resource.getUrl());
							configAttributes.add(configAttribute);
						} else {
							configAttributes = new ArrayList<ConfigAttribute>();
							configAttributes.add(configAttribute);
							relationMap.put(resource.getUrl(), configAttributes);
						}
					}

				}

			}
		}
	}

}
