package com.duoduo.system.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.duoduo.TestHelper;
import com.duoduo.core.test.BaseTest;
import com.duoduo.system.vo.UserVO;

/**
 * TODO
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
	public void test40Delete() {
		Assert.assertTrue(userService.delete("" + entityId1));
		UserVO userVO = userService.getById("" + entityId1);
		Assert.assertNull(userVO);

		Assert.assertTrue(userService.delete("" + entityId2));
		userVO = userService.getById("" + entityId2);
		Assert.assertNull(userVO);
	}
}
