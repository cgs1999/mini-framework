package com.duoduo.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.RoleVO;
import com.duoduo.system.vo.UserVO;

/**
 * 角色管理Service
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:53:24
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRoleService extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;

	@Test
	public void test10Create() {
		// 创建角色1
		RoleVO role = roleService.create(TestHelper.createRoleVO1(null));

		Assert.assertNotNull(role);
		entityId1 = role.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建角色2
		role = roleService.create(TestHelper.createRoleVO2(null));

		Assert.assertNotNull(role);
		entityId2 = role.getId();
		System.out.println("entityId2=" + entityId2);

	}

	@Test
	public void test20GetById() {
		RoleVO role = roleService.getById("" + entityId1);
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getName(), "admin");
	}

	@Test
	public void test22GetByName() {
		RoleVO role = roleService.getByName("admin");
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getId(), entityId1);
	}

	@Test
	public void test24ListAll() {
		List<RoleVO> roleList = roleService.listAll();
		Assert.assertNotNull(roleList);
		Assert.assertEquals(roleList.size(), 2);

		TestHelper.printVOList(roleList);
	}

	@Test
	public void test26ListByUserId() {
		// 创建用户
		UserVO user = userService.create(TestHelper.createUserVO1(entityId1 + "," + entityId2));
		Assert.assertNotNull(user);
		Long userId = user.getId();

		List<RoleVO> roleList = roleService.listByUserId("" + userId);
		Assert.assertNotNull(roleList);
		Assert.assertEquals(roleList.size(), 2);

		TestHelper.printVOList(roleList);

		// 删除用户
		userService.delete("" + userId);
	}

	@Test
	public void test28PageList() {
		// 账号作为关键字进行搜索
		Page<RoleVO> page = new Page<RoleVO>();
		page = roleService.pagingList("admin", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);
	}

	@Test
	public void test30Update() {
		RoleVO role = roleService.getById("" + entityId1);
		Assert.assertNotNull(role);

		role.setType("2");
		role.setMemo("修改后的备注信息");

		roleService.update(role);

		role = roleService.getById("" + entityId1);
		Assert.assertNotNull(role);
		Assert.assertEquals(role.getType(), "2");
	}

	@Test
	public void test40Delete() {
		Assert.assertTrue(roleService.delete("" + entityId1));
		RoleVO role = roleService.getById("" + entityId1);
		Assert.assertNull(role);

		Assert.assertTrue(roleService.delete("" + entityId2));
		role = roleService.getById("" + entityId2);
		Assert.assertNull(role);
	}
}
