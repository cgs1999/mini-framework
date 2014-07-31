package com.duoduo.core.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Junit测试基类
 * @author chengesheng@gmail.com
 * @date 2014-7-28 下午5:27:34
 * @version 1.0.0
 */
// 使用@RunWith(SpringJUnit4ClassRunner.class),才能使测试运行于Spring测试环境
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration 注解有以下两个常用的属性：
// locations：可以通过该属性手工指定 Spring 配置文件所在的位置,可以指定一个或多个 Spring 配置文件
// inheritLocations：是否要继承父测试类的 Spring 配置文件，默认为 true
// 如果只有一个配置文件就直接写locations=“配置文件路径+名”
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseTest {

	@Rule
	public TestName name = new TestName();

	@Before
	public void before() {
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("Test[" + getClassName() + "." + getMethodName() + "] start...");
	}

	@After
	public void after() {
		System.out.println("Test[" + getClassName() + "." + getMethodName() + "] end...");
	}

	private String getClassName() {
		return getClass().getName();
	}

	private String getMethodName() {
		return name.getMethodName();
	}
}
