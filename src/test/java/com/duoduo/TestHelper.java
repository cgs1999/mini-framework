package com.duoduo;

import com.duoduo.system.model.Permission;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.model.Role;
import com.duoduo.system.model.User;

/**
 * 测试帮助类
 * @author chengesheng@gmail.com
 * @date 2014-7-29 下午3:24:31
 * @version 1.0.0
 */
public class TestHelper {

	// 创建用户1
	public static User createUser1() {
		User user = new User();
		user.setAccount("cgs");
		user.setName("陈格生");
		user.setEmail("chengesheng@gmail.com");
		user.setPhone("13312345678");
		user.setPassword("888888");
		user.setStatus(1);
		return user;
	}

	// 创建用户2
	public static User createUser2() {
		User user = new User();
		user.setAccount("jason");
		user.setName("陈小生");
		user.setEmail("jason@126.com");
		user.setPhone("15912345678");
		user.setPassword("888888");
		user.setStatus(1);
		return user;
	}

	// 创建角色1
	public static Role createRole1() {
		Role role = new Role();
		role.setName("admin");
		role.setType("1");
		role.setEnable(Boolean.TRUE);
		role.setMemo("备注信息");
		return role;
	}

	// 创建角色2
	public static Role createRole2() {
		Role role = new Role();
		role.setName("user");
		role.setType("1");
		role.setEnable(Boolean.TRUE);
		role.setMemo("普通用户");
		return role;
	}

	// 创建权限分类1
	public static PermissionCategory createPermissionCategory1() {
		PermissionCategory permissionCategory = new PermissionCategory();
		permissionCategory.setName("用户管理");
		return permissionCategory;
	}

	// 创建权限分类2
	public static PermissionCategory createPermissionCategory2() {
		PermissionCategory permissionCategory = new PermissionCategory();
		permissionCategory.setName("角色管理");
		return permissionCategory;
	}

	// 创建权限1
	public static Permission createPermission1(Long permissionCategoryId) {
		Permission permission = new Permission();
		permission.setName("创建记录");
		permission.setPermissionCategoryId(permissionCategoryId);
		return permission;
	}

	// 创建权限2
	public static Permission createPermission2(Long permissionCategoryId) {
		Permission permission = new Permission();
		permission.setName("删除记录");
		permission.setPermissionCategoryId(permissionCategoryId);
		return permission;
	}
}
