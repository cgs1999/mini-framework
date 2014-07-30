package com.duoduo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 执行全部测试
 * @author chengesheng@gmail.com
 * @date 2014-7-21 下午4:25:36
 * @version 1.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({
		TestCore.class, TestSystem.class
})
public class TestAll {

}
