package com.duoduo;

import java.util.List;

import com.duoduo.system.model.Permission;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.model.Resource;
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

	// 创建资源(根菜单)
	public static Resource createMenuRoot() {
		Resource resource = new Resource();
		resource.setName("系统配置");
		resource.setUrl("");
		resource.setType("1");
		resource.setParentId(null);
		resource.setOrderIndex(0);
		resource.setEnable(Boolean.TRUE);
		return resource;
	}

	// 创建资源(菜单)1
	public static Resource createMenu1(Long parentId) {
		Resource resource = new Resource();
		resource.setName("用户管理");
		resource.setUrl("module/createRecord");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(10);
		resource.setEnable(Boolean.TRUE);
		return resource;
	}

	// 创建资源(菜单)2
	public static Resource createMenu2(Long parentId) {
		Resource resource = new Resource();
		resource.setName("角色管理");
		resource.setUrl("module/deleteRecord");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(20);
		resource.setEnable(Boolean.TRUE);
		return resource;
	}

	// 创建资源1
	public static Resource createResource1(Long parentId) {
		Resource resource = new Resource();
		resource.setName("创建记录");
		resource.setUrl("module/createRecord");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(1010);
		resource.setEnable(Boolean.TRUE);
		return resource;
	}

	// 创建资源2
	public static Resource createResource2(Long parentId) {
		Resource resource = new Resource();
		resource.setName("删除记录");
		resource.setUrl("module/deleteRecord");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(2010);
		resource.setEnable(Boolean.TRUE);
		return resource;
	}

	/**
	 * 获取所有权限名称，结果以逗号分隔，例如：create,update,delete
	 * @param permissionList
	 * @return
	 */
	public static String getPermissionNames(List<Permission> permissionList) {
		String ret = "";
		for (Permission p : permissionList) {
			ret += "," + p.getName();
		}
		return ret.substring(1);
	}

	/**
	 * 获取所有资源名称，结果以逗号分隔，例如：create,update,delete
	 * @param resourceList
	 * @return
	 */
	public static String getResourceNames(List<Resource> resourceList) {
		String ret = "";
		for (Resource r : resourceList) {
			ret += "," + r.getName();
		}
		return ret.substring(1);
	}
}
