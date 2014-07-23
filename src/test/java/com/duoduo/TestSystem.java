package com.duoduo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.duoduo.system.TestSystemDao;
import com.duoduo.system.TestSystemManager;
import com.duoduo.system.TestSystemService;

@RunWith(Suite.class)
@SuiteClasses({
		TestSystemDao.class, TestSystemManager.class, TestSystemService.class
})
public class TestSystem {

}
