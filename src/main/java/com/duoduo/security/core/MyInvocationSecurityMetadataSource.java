package com.duoduo.security.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.duoduo.security.dao.RoleResourceDao;
import com.duoduo.security.util.AntUrlPathMatcher;
import com.duoduo.security.util.UrlMatcher;
import com.duoduo.security.vo.RoleResourceVO;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private RoleResourceDao roleResourceDao;
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MyInvocationSecurityMetadataSource(RoleResourceDao roleResourceDao) {
		super();
		this.roleResourceDao = roleResourceDao;
		loadResourceDefine();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	private void loadResourceDefine() {
		resourceMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();

		List<RoleResourceVO> roleResourceList = roleResourceDao.listAll();
		for (RoleResourceVO roleResource : roleResourceList) {
			if (StringUtils.isNotEmpty(roleResource.getUrl()) && !resourceMap.containsKey(roleResource.getUrl())) {
				resourceMap.put(roleResource.getUrl(), listRoleToCollection(roleResource.getUrl(), roleResourceList));
			}
		}

		// 其他权限
		Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
		list.add(new SecurityConfig("0"));
		resourceMap.put("/", list);
		resourceMap.put("/**", list);
	}

	private Collection<ConfigAttribute> listRoleToCollection(String url, List<RoleResourceVO> roleResourceList) {
		List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
		for (RoleResourceVO roleResource : roleResourceList) {
			if (url.equals(roleResource.getUrl())) {
				list.add(new SecurityConfig(String.valueOf(roleResource.getRoleId())));
			}
		}
		return list;
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
