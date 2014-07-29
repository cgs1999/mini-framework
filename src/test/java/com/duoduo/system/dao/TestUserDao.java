package com.duoduo.system.dao;

import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.model.User;

/**
 * 用户管理Dao测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:21:27
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserDao extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	@Resource
	private UserDao userDao;

	@Test
	public void test10Create() {
		// 创建用户1
		User user = userDao.create(TestHelper.createUser1());

		Assert.assertNotNull(user);
		entityId1 = user.getId();
		System.out.println("entityId1=" + entityId1);

		// 创建用户2
		user = userDao.create(TestHelper.createUser2());

		Assert.assertNotNull(user);
		entityId2 = user.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		if (entityId1 != null) {
			User user = userDao.getById("" + entityId1);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getAccount(), "cgs");
		} else {
			System.out.println("testGetById entityId is: " + entityId1);
		}
	}

	@Test
	public void test22GetByAccount() {
		if (entityId1 != null) {
			User user = userDao.getByAccount("cgs");
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getId(), entityId1);
		} else {
			System.out.println("testGetByAccount entityId is: " + entityId1);
		}
	}

	@Test
	public void test24PageList() {
		// 账号作为关键字进行搜索
		Page<User> page = new Page<User>();
		page = userDao.pagingList("cgs", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 姓名作为关键字进行搜索
		page = new Page<User>();
		page = userDao.pagingList("陈", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		// 邮箱作为关键字进行搜索
		page = new Page<User>();
		page = userDao.pagingList("chengesheng", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 手机作为关键字进行搜索
		page = new Page<User>();
		page = userDao.pagingList("12345678", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);
	}

	@Test
	public void test26PageList() {
		// 账号作为关键字进行搜索
		Page<User> page = new Page<User>();
		page = userDao.pagingList("cgs", null, null, null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 姓名作为关键字进行搜索
		page = new Page<User>();
		page = userDao.pagingList(null, "格生", null, null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 邮箱作为关键字进行搜索
		page = new Page<User>();
		page = userDao.pagingList(null, null, "chengesheng", null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 手机作为关键字进行搜索
		page = new Page<User>();
		page = userDao.pagingList(null, null, null, "12345678", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		// 所有关键字一起进行搜索
		page = new Page<User>();
		page = userDao.pagingList("cgs", "格生", "chengesheng", "12345678", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);
	}

	@Test
	public void test30Update() {
		if (entityId1 != null) {
			User user = userDao.getById("" + entityId1);
			Assert.assertNotNull(user);

			String salt = UUID.randomUUID().toString();
			user.setSalt(salt);

			userDao.update(user);

			user = userDao.getById("" + entityId1);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getSalt(), salt);
		} else {
			System.out.println("testUpdate entityId is: " + entityId1);
		}
	}

	@Test
	public void test40Delete() {
		// 删除用户1
		if (entityId1 != null) {
			User user = userDao.getById("" + entityId1);
			Assert.assertNotNull(user);

			userDao.delete("" + entityId1);

			user = userDao.getById("" + entityId1);
			Assert.assertNull(user);

			entityId1 = null;
		} else {
			System.out.println("testDelete entityId1 is: " + entityId1);
		}

		// 删除用户2
		if (entityId2 != null) {
			User user = userDao.getById("" + entityId2);
			Assert.assertNotNull(user);

			userDao.delete("" + entityId2);

			user = userDao.getById("" + entityId2);
			Assert.assertNull(user);

			entityId2 = null;
		} else {
			System.out.println("testDelete entityId2 is: " + entityId2);
		}
	}
}
