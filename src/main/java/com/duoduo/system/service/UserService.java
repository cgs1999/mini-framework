package com.duoduo.system.service;

import com.duoduo.core.vo.Page;
import com.duoduo.system.vo.UserVO;

/**
 * 用户管理业务处理接口
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:26:09
 * @version 1.0.0
 */
public interface UserService {

	/**
	 * 根据Id获取用户
	 * @param id
	 * @return
	 */
	public UserVO getById(String id);

	/**
	 * 根据帐号获取用户
	 * @param account
	 * @return
	 */
	public UserVO getByAccount(String account);

	/**
	 * 创建用户
	 * @param userVO
	 * @return
	 */
	public UserVO create(final UserVO userVO);

	/**
	 * 修改用户
	 * @param userVO
	 */
	public void update(UserVO userVO);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 分页查询用户列表（模糊查询，条件为：帐号、姓名、电子邮箱、电话）
	 * @param account 帐号
	 * @param name 姓名
	 * @param email 电子邮箱
	 * @param phone 电话
	 * @param page
	 * @return
	 */
	public Page<UserVO> pagingList(String account, String name, String email, String phone, Page<UserVO> page);
}
