package com.duoduo.security.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.duoduo.system.vo.UserVO;

/**
 * 用户在session中的对象
 * @author chengesheng
 * @date 2014-3-10 下午2:06:57
 */
public class SessionUser implements UserDetails, Serializable {

	private static final long serialVersionUID = 4750909576331758050L;

	private String username; // 用户名
	private UserVO user; // 用户信息

	private List<GrantedAuthority> authorities; // 用户权限

	public void setUsername(String username) {
		this.username = username;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO userVO) {
		this.user = userVO;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
