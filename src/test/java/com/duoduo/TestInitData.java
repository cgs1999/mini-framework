package com.duoduo;

import java.util.UUID;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.core.test.BaseTest;
import com.duoduo.system.service.PermissionCategoryService;
import com.duoduo.system.service.PermissionService;
import com.duoduo.system.service.ResourceService;
import com.duoduo.system.service.RoleService;
import com.duoduo.system.service.UserService;
import com.duoduo.system.vo.UserVO;

/**
 * 初始化数据
 * @author chengesheng@gmail.com
 * @date 2014-7-31 下午11:54:06
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestInitData extends BaseTest {

	private Long adminUserId;
	private Long adminRoleId;

	@javax.annotation.Resource
	private UserService userService;
	@javax.annotation.Resource
	private RoleService roleService;
	@javax.annotation.Resource
	private PermissionCategoryService permissionCategoryService;
	@javax.annotation.Resource
	private PermissionService permissionpService;
	@javax.annotation.Resource
	private ResourceService resourceService;

	@Test
	public void initResourceData() {

	}

	@Test
	public void initPermissionCategoryData() {

	}

	@Test
	public void initPermissionData() {

	}

	@Test
	public void initRoleData() {

	}

	@Test
	public void initUserData() {
		UserVO user = new UserVO();
		user.setAccount("cgs");
		user.setName("陈格生");
		user.setEmail("chengesheng@gmail.com");
		user.setPhone("13312345678");
		user.setPassword("888888");
		user.setSalt(UUID.randomUUID().toString());
		user.setStatus(1);

		user.setRoleIds("" + adminRoleId);

		user = userService.create(user);
		Assert.assertNotNull(user);
	}
}
