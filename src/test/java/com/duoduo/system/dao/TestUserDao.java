package com.duoduo.system.dao;

import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duoduo.system.model.User;

/**
 * TODO
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:21:27
 * @version 1.0.0
 */
// 使用@RunWith(SpringJUnit4ClassRunner.class),才能使测试运行于Spring测试环境
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration 注解有以下两个常用的属性：
// locations：可以通过该属性手工指定 Spring 配置文件所在的位置,可以指定一个或多个 Spring 配置文件
// inheritLocations：是否要继承父测试类的 Spring 配置文件，默认为 true
// 如果只有一个配置文件就直接写locations=“配置文件路径+名”
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserDao {

	private static Long userId;

	@Resource
	private UserDao userDao;

	@Test
	public void test() {
		System.out.println(getClass());
	}

	@Test
	public void testSave() {
		User user = new User();
		user.setAccount("cgs");
		user.setName("陈格生");
		user.setEmail("chengesheng@gmail.com");
		user.setPassword("888888");
		user.setStatus(1);
		user = userDao.create(user);

		Assert.assertNotNull(user);

		userId = user.getId();
		System.out.println("userId=" + userId);
	}

	@Test
	public void testGetById() {
		if (userId != null) {
			User user = userDao.getById("" + userId);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getAccount(), "cgs");
		} else {
			System.out.println("testGetById userId is: " + userId);
		}
	}

	@Test
	public void testUpdate() {
		if (userId != null) {
			User user = userDao.getById("" + userId);
			Assert.assertNotNull(user);

			String salt = UUID.randomUUID().toString();
			user.setSalt(salt);

			userDao.update(user);

			user = userDao.getById("" + userId);
			Assert.assertNotNull(user);
			Assert.assertEquals(user.getSalt(), salt);
		} else {
			System.out.println("testUpdate userId is: " + userId);
		}
	}

	@Test
	public void testDelete() {
		if (userId != null) {
			User user = userDao.getById("" + userId);
			Assert.assertNotNull(user);

			userDao.delete("" + userId);

			user = userDao.getById("" + userId);
			Assert.assertNull(user);

			userId = null;
		} else {
			System.out.println("testDelete userId is: " + userId);
		}
	}
}
