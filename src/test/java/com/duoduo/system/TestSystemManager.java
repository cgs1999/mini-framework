package com.duoduo.system;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.duoduo.system.manager.TestPermissionManager;
import com.duoduo.system.manager.TestPermissionResourceManager;
import com.duoduo.system.manager.TestPerssiomCategoryManager;
import com.duoduo.system.manager.TestResourceManager;
import com.duoduo.system.manager.TestRoleManager;
import com.duoduo.system.manager.TestRolePermissionManager;
import com.duoduo.system.manager.TestUserManager;
import com.duoduo.system.manager.TestUserRoleManager;

/**
 * TODO
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:52:24
 * @version 1.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({
		TestUserManager.class, TestRoleManager.class, TestPerssiomCategoryManager.class, TestPermissionManager.class,
		TestResourceManager.class, TestUserRoleManager.class, TestRolePermissionManager.class,
		TestPermissionResourceManager.class
})
public class TestSystemManager {

}
