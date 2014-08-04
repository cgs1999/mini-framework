package com.duoduo.security.context;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


public class UserContext {

	// 自定义SESSION，用于外部应用登录微博时保存【回调函数】
	private static final String SESSION_CALLBACK = "session_callback";

	// 自定义SESSION,用于判断登入入口
	private static final String SESSION_LOGIN_TYPE = "session_login_type";

	// 自定义SESSION，用于保存自定义异常
	private static final String SESSION_EXCEPTION = "session_exception";

	// CAS服务路径
	public static final String DEFAULT_CAS_SERVICE_PARAMETER = "service";

	// CAS重定向路径
	public static final String DEFAULT_CAS_REDIRECT_PARAMETER = "redirect";

	public static SessionUser getCurrentUser() {
		SessionUser user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication && !(authentication instanceof AnonymousAuthenticationToken)) {
			user = (SessionUser) authentication.getPrincipal();
		}
		return user;
	}

	public static void setCurrentUser(SessionUser user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			if (authentication instanceof AbstractAuthenticationToken) {
				AbstractAuthenticationToken abstractAuthenticationToken = (AbstractAuthenticationToken) authentication;
				abstractAuthenticationToken.setDetails(user);
			}
		}
	}

	public static List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication && !(authentication instanceof AnonymousAuthenticationToken)) {
			for (GrantedAuthority authority : authentication.getAuthorities()) {
				auths.add(authority);
			}
		}
		return auths;
	}

	public static String getCallback() {
		return SESSION_CALLBACK;
	}

	public static String getLoginType() {
		return SESSION_LOGIN_TYPE;
	}

	public static String getException() {
		return SESSION_EXCEPTION;
	}

}
