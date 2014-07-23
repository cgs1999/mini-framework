package com.duoduo.system;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.duoduo.system.service.TestPermissionService;
import com.duoduo.system.service.TestPerssiomCategoryService;
import com.duoduo.system.service.TestResourceService;
import com.duoduo.system.service.TestRoleService;
import com.duoduo.system.service.TestUserService;

/**
 * TODO
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:31:35
 * @version 1.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({
		TestUserService.class, TestRoleService.class, TestPerssiomCategoryService.class, TestPermissionService.class,
		TestResourceService.class
})
public class TestSystemService {

}
