package com.duoduo.security.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源 ;做最终的访问控制决定
 * <p>
 * 判断用户拥有权限和请求URL权限是否匹配
 * </p>
 * @author chengesheng@gmail.com
 * @date 2014-8-4 上午1:17:22
 * @version 1.0.0
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	// In this method, need to compare authentication with configAttributes.
	// 1, A object is a URL, a filter was find permission configuration by this URL, and pass to here.
	// 2, Check authentication has attribute in permission configuration(configAttributes)
	// 3, If not match corresponding authentication, throw a AccessDeniedException.
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		// System.out.println(object.toString()); // object is a URL.
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			Collection<GrantedAuthority> authorities = null;
			if ("anonymousUser".equals(authentication.getPrincipal())) {
				authorities = getAuthorities(authentication.getAuthorities());
			} else {
				authorities = getAuthorities(((UserDetails) authentication.getPrincipal()).getAuthorities());
			}
			for (GrantedAuthority ga : authorities) {
				// System.out.println(needRole.equals(ga.getAuthority()));
				if (("0".equals(needRole) && !"anonymousUser".equals(authentication.getPrincipal()))
						|| needRole.equals(ga.getAuthority())) { // ga is user's role.
					return;
				}
			}
		}
		throw new AccessDeniedException("没有权限访问！");
	}

	/**
	 * 启动时候被AbstractSecurityInterceptor调用，决定AccessDecisionManager是否可以执行传递ConfigAttribute。
	 */
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * 被安全拦截器实现调用，包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型
	 */
	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * 转换数据类型
	 * @param authorities
	 * @return
	 */
	private List<GrantedAuthority> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
		List<GrantedAuthority> ret = new ArrayList<GrantedAuthority>(0);
		for (GrantedAuthority authority : authorities) {
			ret.add(authority);
		}
		return ret;
	}
}
