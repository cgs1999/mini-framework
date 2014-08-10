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
import com.duoduo.system.vo.PermissionCategoryVO;
import com.duoduo.system.vo.PermissionVO;
import com.duoduo.system.vo.ResourceVO;
import com.duoduo.system.vo.RoleVO;
import com.duoduo.system.vo.UserVO;

/**
 * 初始化数据
 * @author chengesheng@gmail.com
 * @date 2014-7-31 下午11:54:06
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestInitData extends BaseTest {

	private static Long adminUserId;
	private static Long adminRoleId;
	private static String permissionIds;
	private static String resourceIds;

	@javax.annotation.Resource
	private UserService userService;
	@javax.annotation.Resource
	private RoleService roleService;
	@javax.annotation.Resource
	private PermissionCategoryService permissionCategoryService;
	@javax.annotation.Resource
	private PermissionService permissionService;
	@javax.annotation.Resource
	private ResourceService resourceService;

	@Test
	public void initResourceData() {
		ResourceVO root = initResourceRootData();
		Assert.assertNotNull(root);
		System.out.println("rootRoleId=" + root.getId());

		// 初始化系统模块资源
		initResourceSystemData(root.getId());

		// 初始化其他模块资源
		// initOtherResourceData(root.getId());
	}

	@Test
	public void initPermissionCategoryAndPermissionData() {
		PermissionCategoryVO permissionCategory = new PermissionCategoryVO();
		permissionCategory.setName("用户管理");

		permissionCategory = permissionCategoryService.create(permissionCategory);
		Assert.assertNotNull(permissionCategory);

		PermissionVO permission = new PermissionVO();
		permission.setName("创建用户");
		permission.setPermissionCategoryId(permissionCategory.getId());
		// FIXME 创建用户的资源
		permission.setResourceIds(resourceIds);
		permission = permissionService.create(permission);
		Assert.assertNotNull(permission);
	}

	@Test
	public void initRoleData() {
		RoleVO role = initRoleAdminData();
		Assert.assertNotNull(role);
		adminRoleId = role.getId();
		System.out.println("adminRoleId=" + adminRoleId);
	}

	@Test
	public void initUserData() {
		UserVO user = new UserVO();
		user.setAccount("admin");
		user.setName("超级管理员");
		user.setEmail("chengesheng@gmail.com");
		user.setPhone("13312345678");
		user.setPassword("888888");
		user.setSalt(UUID.randomUUID().toString());
		user.setStatus(1);

		user.setRoleIds("" + adminRoleId);

		user = userService.create(user);
		Assert.assertNotNull(user);
		adminUserId = user.getId();
		System.out.println("adminUserId=" + adminUserId);
	}

	/**
	 * 初始化根节点资源
	 * @return
	 */
	private ResourceVO initResourceRootData() {
		ResourceVO resource = new ResourceVO();
		resource.setName("所有资源");
		resource.setUrl("");
		resource.setType("1");
		resource.setParentId(null);
		resource.setOrderIndex(0);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("所有资源");

		return resourceService.create(resource);
	}

	/**
	 * 初始化系统模块资源
	 */
	private void initResourceSystemData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("系统配置");
		resource.setUrl("");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(10);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("系统配置");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);

		// 初始化用户管理资源
		initResourceUserData(resource.getId());
		// 初始化角色管理资源
		initResourceRoleData(resource.getId());
		// 初始化权限分类管理资源
		initResourcePermissionCategoryData(resource.getId());
		// 初始化权限管理资源
		initResourcePermissionData(resource.getId());
		// 初始化资源管理资源
		initResource2Data(resource.getId());
	}

	/**
	 * 用户管理资源
	 */
	private void initResourceUserData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("用户管理");
		resource.setUrl("/system/user/list");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(1010);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("用户管理");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);

		// 创建用户
		initResourceUserCreateData(resource.getId());
		// 修改用户
		initResourceUserUpdateData(resource.getId());
		// 查看用户信息
		initResourceUserReadData(resource.getId());
		// 删除用户
		initResourceUserDeleteData(resource.getId());
		// 查看用户列表
		initResourceUserListData(resource.getId());
	}

	private void initResourceUserCreateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("创建用户信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(101010);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("创建用户信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceUserReadData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看用户信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(101020);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看用户信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceUserUpdateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("修改用户信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(101030);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("修改用户信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceUserDeleteData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("删除用户信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(101040);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("删除用户信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceUserListData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看用户列表");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(101050);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看用户列表");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	/**
	 * 角色管理资源
	 */
	private void initResourceRoleData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("角色管理");
		resource.setUrl("/system/role/list");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(1020);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("角色管理");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);

		// 创建角色
		initResourceRoleCreateData(resource.getId());
		// 修改角色
		initResourceRoleUpdateData(resource.getId());
		// 查看角色信息
		initResourceRoleReadData(resource.getId());
		// 删除角色
		initResourceRoleDeleteData(resource.getId());
		// 查看角色列表
		initResourceRoleListData(resource.getId());
	}

	private void initResourceRoleCreateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("创建角色信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(102010);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("创建角色信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceRoleReadData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看角色信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(102020);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看角色信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceRoleUpdateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("修改角色信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(102030);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("修改角色信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceRoleDeleteData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("删除角色信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(102040);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("删除角色信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourceRoleListData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看角色列表");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(102050);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看角色列表");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	/**
	 * 权限分类管理资源
	 */
	private void initResourcePermissionCategoryData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("权限分类管理");
		resource.setUrl("/system/permissionCategory/list");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(1030);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("权限分类管理");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);

		// 创建权限分类
		initResourcePermissionCategoryCreateData(resource.getId());
		// 修改权限分类
		initResourcePermissionCategoryUpdateData(resource.getId());
		// 查看权限分类信息
		initResourcePermissionCategoryReadData(resource.getId());
		// 删除权限分类
		initResourcePermissionCategoryDeleteData(resource.getId());
		// 查看权限分类列表
		initResourcePermissionCategoryListData(resource.getId());
	}

	private void initResourcePermissionCategoryCreateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("创建权限分类信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(103010);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("创建权限分类信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionCategoryReadData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看权限分类信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(103020);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看权限分类信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionCategoryUpdateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("修改权限分类信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(103030);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("修改权限分类信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionCategoryDeleteData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("删除权限分类信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(103040);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("删除权限分类信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionCategoryListData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看权限分类列表");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(103050);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看权限分类列表");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	/**
	 * 权限管理资源
	 */
	private void initResourcePermissionData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("权限管理");
		resource.setUrl("/system/permission/list");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(1040);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("权限管理");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);

		// 创建权限
		initResourcePermissionCreateData(resource.getId());
		// 修改权限
		initResourcePermissionUpdateData(resource.getId());
		// 查看权限信息
		initResourcePermissionReadData(resource.getId());
		// 删除权限
		initResourcePermissionDeleteData(resource.getId());
		// 查看权限列表
		initResourcePermissionListData(resource.getId());
	}

	private void initResourcePermissionCreateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("创建权限信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(104010);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("创建权限信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionReadData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看权限信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(104020);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看权限信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionUpdateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("修改权限信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(104030);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("修改权限信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionDeleteData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("删除权限信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(104040);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("删除权限信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResourcePermissionListData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看权限列表");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(104050);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看权限列表");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	/**
	 * 资源管理资源
	 */
	private void initResource2Data(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("资源管理");
		resource.setUrl("/system/resource/list");
		resource.setType("1");
		resource.setParentId(parentId);
		resource.setOrderIndex(1050);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("资源管理");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);

		// 创建资源
		initResource2CreateData(resource.getId());
		// 修改资源
		initResource2UpdateData(resource.getId());
		// 查看资源信息
		initResource2ReadData(resource.getId());
		// 删除资源
		initResource2DeleteData(resource.getId());
		// 查看资源列表
		initResource2ListData(resource.getId());
	}

	private void initResource2CreateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("创建资源信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(105010);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("创建资源信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResource2ReadData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看资源信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(105020);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看资源信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResource2UpdateData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("修改资源信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(105030);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("修改资源信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResource2DeleteData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("删除资源信息");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(105040);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("删除资源信息");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	private void initResource2ListData(Long parentId) {
		ResourceVO resource = new ResourceVO();
		resource.setName("查看资源列表");
		resource.setUrl("");
		resource.setType("2");
		resource.setParentId(parentId);
		resource.setOrderIndex(105050);
		resource.setEnable(Boolean.TRUE);
		resource.setMemo("查看资源列表");

		resource = resourceService.create(resource);
		Assert.assertNotNull(resource);
	}

	/**
	 * 初始化管理员角色
	 * @return
	 */
	private RoleVO initRoleAdminData() {
		RoleVO role = new RoleVO();
		role.setName("admin");
		role.setType("1");
		role.setEnable(Boolean.TRUE);
		role.setMemo("管理员");

		role.setPermissionIds(permissionIds);
		return roleService.create(role);
	}
}
