package com.duoduo.security.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoduo.security.context.SessionUser;
import com.duoduo.system.service.RoleService;
import com.duoduo.system.service.UserService;
import com.duoduo.system.vo.RoleVO;
import com.duoduo.system.vo.UserVO;

/**
 * 取用户信息（用户名、密码）及对应权限
 * @author chengesheng@gmail.com
 * @date 2014-8-4 上午1:17:45
 * @version 1.0.0
 */
@Service("myUserDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

	/**
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		SessionUser sessionUser = new SessionUser();
		try {
			UserVO userVO = null;
			try {
				userVO = userService.getByAccount(userName);
			} catch (Exception e) {
				throw new RuntimeException("您输入的帐号或密码有误！");
			}
			if (userVO == null) {
				throw new RuntimeException("您输入的帐号或密码有误！");
			}

			sessionUser.setUsername(userName);
			sessionUser.setUser(userVO);

			/*************************** 权限控制 *****************************/
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			List<RoleVO> listUserRole = roleService.listByUserId("" + userVO.getId());
			for (RoleVO roleVO : listUserRole) {
				SimpleGrantedAuthority general = new SimpleGrantedAuthority(String.valueOf(roleVO.getId()));
				auths.add(general);
			}

			SimpleGrantedAuthority general = new SimpleGrantedAuthority("0");
			auths.add(general);

			sessionUser.setAuthorities(auths);
		} catch (RuntimeException e) {
			throw new UsernameNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new UsernameNotFoundException("系统异常，请联系管理员！");
		}
		return sessionUser;
	}
}
