package com.duoduo.common.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.duoduo.core.util.ResponseUtils;
import com.duoduo.core.vo.Message;
import com.duoduo.security.context.UserContext;

/**
 * 登录Controller
 * @author chengesheng@gmail.com
 * @date 2014-8-4 下午5:00:10
 * @version 1.0.0
 */
@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String SPRING_SECURITY_URL_LOGIN = "/j_spring_security_check";
	private static final String SPRING_SECURITY_URL_LOGOUT = "/j_spring_security_logout";
	private String loginPage = "/login";
	private String noPermission = "/noPermission";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, ModelMap model) {
		return loginPage;
	}

	@RequestMapping("/toLogin")
	public void toLogin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		obj.put("isLogout", true);
		try {
			boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
			if (isAjax) {
				ResponseUtils.renderJson(response, obj);
			} else {
				request.setAttribute("errorMessage", "登录已过期，原因：登录后超时！");
				request.getRequestDispatcher("/login").forward(request, response);
			}
		} catch (Exception e) {
			ResponseUtils.renderJson(response, obj);
			logger.info(e.getMessage());
		}
	}

	/** 登录 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response, String account, String password,
			String enableVerifyCode, String verifyCode) {
		try {
			login(account, password, false, false, enableVerifyCode, verifyCode, response, request);
		} catch (Exception e) {
			Message<String> message = new Message<String>(false, "系统异常，请与管理员联系！");
			ResponseUtils.renderJson(response, message);
			logger.info(e.getMessage());
		}
	}

	/** 退出 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Message<String> message = new Message<String>(true, "退出系统成功！");
		try {
			logout(response, request);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setDescription("系统异常，请与管理员联系！");
			logger.info(e.getMessage());
		}

		ResponseUtils.renderJson(response, message);
	}

	@RequestMapping(value = "/login/success", method = RequestMethod.GET)
	public void loginSuccess(HttpServletResponse response, HttpServletRequest request) {
		Message<String> message = new Message<String>(true, "登录成功！");
		ResponseUtils.renderJson(response, message);
	}

	@RequestMapping(value = "/login/failure", method = RequestMethod.GET)
	public void loginFailure(HttpServletResponse response, HttpServletRequest request) {
		String exception = (String) request.getSession(true).getAttribute(UserContext.getException());
		if (StringUtils.isEmpty(exception)) {
			exception = "您输入的帐号或密码有误！";
		}
		request.getSession(true).removeAttribute(UserContext.getException());

		Message<String> message = new Message<String>(false, exception);
		ResponseUtils.renderJson(response, message);
	}

	@RequestMapping(value = "/access/denied")
	public String deniedVisit(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		return noPermission;
	}

	/**
	 * 适合只有用户名、密码的登录
	 * @param userName
	 * @param passWord
	 * @param response
	 * @throws IOException
	 */
	private void login(String userName, String passWord, Boolean rememberMe, Boolean isMD5, String enableVerifyCode,
			String verifyCode, HttpServletResponse response, HttpServletRequest request) throws IOException {

		StringBuilder url = new StringBuilder(request.getContextPath()).append(SPRING_SECURITY_URL_LOGIN)
				.append("?j_password_encoder=").append(isMD5).append("&j_username=")
				.append(URLEncoder.encode(userName, "UTF-8")).append("&j_password=")
				.append(URLEncoder.encode(passWord, "UTF-8")).append("&j_enableVerifyCode=").append(enableVerifyCode)
				.append("&j_verifyCode=").append(verifyCode).append("&_spring_security_remember_me=")
				.append(rememberMe);
		response.sendRedirect(url.toString());
	}

	/**
	 * 登出调用spring security
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	public void logout(HttpServletResponse response, HttpServletRequest request) throws IOException {
		StringBuilder url = new StringBuilder(request.getContextPath()).append(SPRING_SECURITY_URL_LOGOUT);
		response.sendRedirect(url.toString());
	}
}
