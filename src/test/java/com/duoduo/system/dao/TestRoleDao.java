package com.duoduo.system.dao;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.core.test.BaseTest;
import com.duoduo.system.model.Role;

/**
 * TODO
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:34:46
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRoleDao extends BaseTest {

	private static Long entityId;

	@Resource
	private RoleDao roleDao;

	@Test
	public void test00() {
		System.out.println(getClass());
	}

	@Test
	public void test10Save() {
		Role role = new Role();
		role.setName("admin");
		role.setType("1");
		role.setEnable(Boolean.TRUE);
		role.setMemo("备注信息");
		role = roleDao.create(role);

		Assert.assertNotNull(role);

		entityId = role.getId();
		System.out.println("entityId=" + entityId);
	}

	@Test
	public void test20GetById() {
		if (entityId != null) {
			Role role = roleDao.getById("" + entityId);
			Assert.assertNotNull(role);
			Assert.assertEquals(role.getName(), "admin");
		} else {
			System.out.println("testGetById entityId is: " + entityId);
		}
	}

	@Test
	public void test30Update() {
		if (entityId != null) {
			Role role = roleDao.getById("" + entityId);
			Assert.assertNotNull(role);

			role.setType("2");
			role.setMemo("修改后的备注信息");

			roleDao.update(role);

			role = roleDao.getById("" + entityId);
			Assert.assertNotNull(role);
			Assert.assertEquals(role.getType(), "2");
		} else {
			System.out.println("testUpdate entityId is: " + entityId);
		}
	}

	@Test
	public void test40Delete() {
		if (entityId != null) {
			Role role = roleDao.getById("" + entityId);
			Assert.assertNotNull(role);

			roleDao.delete("" + entityId);

			role = roleDao.getById("" + entityId);
			Assert.assertNull(role);

			entityId = null;
		} else {
			System.out.println("testDelete entityId is: " + entityId);
		}
	}
}
