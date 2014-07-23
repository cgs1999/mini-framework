package com.duoduo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * TODO
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:25:36
 * @version 1.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({
		TestCore.class, TestSystem.class
})
// @//ContextConfiguration(locations = "classpath:config/applicationContext.xml")
public class TestAll {

}
