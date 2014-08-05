package com.duoduo.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import com.duoduo.security.context.UserContext;

/**
 * 登录校验（可以支持GET请求）
 * @author yinjian
 * @date 2012-2-28
 */

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SPRING_SECURITY_FORM_CALLBACK_KEY = "j_callback";
	public static final String SPRING_SECURITY_FORM_PASSWORD_ENCODER_KEY = "j_password_encoder";
	public static final String SPRING_SECURITY_FORM_ENABLE_VERIFY_CODE = "j_enableVerifyCode";
	public static final String SPRING_SECURITY_FORM_VERIFY_CODE_KEY = "j_verifyCode";
	public static final String SPRING_SECURITY_FORM_USER_TYPE_KEY = "j_user_type";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	private boolean postOnly = true;
	private static String SESSION_KEY = "adminVerifyCode";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			if (postOnly && !request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException("必须是POST请求！");
			}
			String username = obtainUsername(request);
			String password = obtainPassword(request);
			username = username.trim();
			password = password.trim();

			String passwordEncoder = obtainEncoder(request);
			boolean enableVerifyCode = obtainEnableVerifyCode(request);
			String verifyCode = obtainVerifyCode(request);

			HttpSession session = request.getSession(false);
			if (enableVerifyCode) {
				if (StringUtils.isEmpty(verifyCode)) {
					throw new UsernameNotFoundException("验证码不能为空!");
				}
				if (null != session.getAttribute(SESSION_KEY)
						&& !verifyCode.toLowerCase().equals(session.getAttribute(SESSION_KEY))) {
					throw new UsernameNotFoundException("验证码不正确!");
				}
			}

			// 判断密码是否已经加过密,没有加密就用md5方式加密
			if ("false".equalsIgnoreCase(passwordEncoder)) {
				Md5PasswordEncoder md5 = new Md5PasswordEncoder();
				password = md5.encodePassword(password, null);
			}
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				throw new AuthenticationServiceException("用户名或密码不能为空！");
			}

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
					password);
			if (session != null || getAllowSessionCreation()) {
				request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY,
						TextEscapeUtils.escapeEntities(username));
			}

			// Allow subclasses to set the "details" property
			setDetails(request, authRequest);

			return this.getAuthenticationManager().authenticate(authRequest);
		} catch (UsernameNotFoundException notFount) {
			request.getSession(true).setAttribute(UserContext.getException(), notFount.getMessage());
			throw notFount;
		} catch (AuthenticationServiceException e) {
			request.getSession(true).setAttribute(UserContext.getException(), e.getMessage());
			throw e;
		}
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
		return null == obj ? "" : obj.toString().trim();
	}

	protected String obtainCallback(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_CALLBACK_KEY);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainEncoder(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_ENCODER_KEY);
		return null == obj ? "" : obj.toString();
	}

	protected boolean obtainEnableVerifyCode(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_ENABLE_VERIFY_CODE);
		if (obj != null && "0".equals(obj)) {
			return false;
		}

		return true;
	}

	protected String obtainVerifyCode(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_VERIFY_CODE_KEY);
		return null == obj ? "" : obj.toString().trim();
	}

	protected String obtainUserType(HttpServletRequest request) {
		Object obj = request.getParameter(SPRING_SECURITY_FORM_USER_TYPE_KEY);
		return null == obj ? "" : obj.toString();
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter. If set to true, and an authentication
	 * request is received which is not a POST request, an exception will be raised immediately and authentication will
	 * not be attempted. The <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
	 * authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
