package com.duoduo.system.dao;

import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.core.test.BaseTest;
import com.duoduo.system.model.User;

/**
 * TODO
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:21:27
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserDao extends BaseTest {

	private static Long entityId;

	@Resource
	private UserDao userDao;

	@Test
	public void test00() {
		System.out.println(getClass());
	}

	@Test
	public void test10Save() {
		User user = new User();
		user.setAccount("cgs");
		user.setName("陈格生");
		user.setEmail("chengesheng@gmail.com");
		user.setPassword("888888");
		user.setStatus(1);
		user = userDao.create(user);

		Assert.assertNotNull(user);

		entityId = user.getId();
		System.out.println("entityId=" + entityId);
	}

	@Test
	public void test20GetById() {
		if (entityId != null) {
			User user = userDao.getById("" + entityId);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getAccount(), "cgs");
		} else {
			System.out.println("testGetById entityId is: " + entityId);
		}
	}

	@Test
	public void test30Update() {
		if (entityId != null) {
			User user = userDao.getById("" + entityId);
			Assert.assertNotNull(user);

			String salt = UUID.randomUUID().toString();
			user.setSalt(salt);

			userDao.update(user);

			user = userDao.getById("" + entityId);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getSalt(), salt);
		} else {
			System.out.println("testUpdate entityId is: " + entityId);
		}
	}

	@Test
	public void test40Delete() {
		if (entityId != null) {
			User user = userDao.getById("" + entityId);
			Assert.assertNotNull(user);

			userDao.delete("" + entityId);

			user = userDao.getById("" + entityId);
			Assert.assertNull(user);

			entityId = null;
		} else {
			System.out.println("testDelete entityId is: " + entityId);
		}
	}
}
