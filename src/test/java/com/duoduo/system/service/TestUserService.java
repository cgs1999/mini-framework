package com.duoduo.system.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.UserVO;

/**
 * 用户管理Service
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:53:15
 * @version 1.0.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserService extends BaseTest {

	private static Long entityId1;
	private static Long entityId2;

	@Resource
	private UserService userService;

	@Test
	public void test10Create() {
		UserVO userVO = new UserVO();
		userVO = userService.create(TestHelper.createUserVO1(null));
		Assert.assertNotNull(userVO);
		entityId1 = userVO.getId();
		System.out.println("entityId1=" + entityId1);

		userVO = new UserVO();
		userVO = userService.create(TestHelper.createUserVO2(null));
		Assert.assertNotNull(userVO);
		entityId2 = userVO.getId();
		System.out.println("entityId2=" + entityId2);
	}

	@Test
	public void test20GetById() {
		UserVO user = userService.getById("" + entityId1);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getAccount(), "cgs");
	}

	@Test
	public void test22GetByAccount() {
		UserVO user = userService.getByAccount("cgs");
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getId(), entityId1);
	}

	@Test
	public void test24PageList() {
		// 账号作为关键字进行搜索
		Page<UserVO> page = new Page<UserVO>();
		page = userService.pagingList("cgs", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 姓名作为关键字进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList("陈", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		// 邮箱作为关键字进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList("chengesheng", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 手机作为关键字进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList("12345678", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);
	}

	@Test
	public void test26PageList() {
		// 账号作为关键字进行搜索
		Page<UserVO> page = new Page<UserVO>();
		page = userService.pagingList("cgs", null, null, null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 姓名作为关键字进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList(null, "格生", null, null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 邮箱作为关键字进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList(null, null, "chengesheng", null, page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);

		// 手机作为关键字进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList(null, null, null, "12345678", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 2);

		// 所有关键字一起进行搜索
		page = new Page<UserVO>();
		page = userService.pagingList("cgs", "格生", "chengesheng", "12345678", page);
		Assert.assertNotNull(page);
		Assert.assertEquals(page.getRows().size(), 1);
	}

	@Test
	public void test30Update() {
		UserVO user = userService.getById("" + entityId1);
		Assert.assertNotNull(user);

		String salt = UUID.randomUUID().toString();
		user.setSalt(salt);

		userService.update(user);

		user = userService.getById("" + entityId1);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getSalt(), salt);
	}

	@Test
	public void test40Delete() {
		Assert.assertTrue(userService.delete("" + entityId1));
		UserVO userVO = userService.getById("" + entityId1);
		Assert.assertNull(userVO);

		Assert.assertTrue(userService.delete("" + entityId2));
		userVO = userService.getById("" + entityId2);
		Assert.assertNull(userVO);
	}
}
