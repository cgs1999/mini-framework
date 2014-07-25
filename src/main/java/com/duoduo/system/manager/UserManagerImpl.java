package com.duoduo.system.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.UserDao;
import com.duoduo.system.model.User;
import com.duoduo.system.vo.UserVO;

/**
 * 用户管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Transactional
@Service("userManager")
public class UserManagerImpl implements UserManager {

	@Resource
	private UserDao userDao;
	@Resource
	private UserRoleManager userRoleManager;

	@Override
	public UserVO getById(String id) {
		User user = userDao.getById(id);
		if (user == null) {
			return null;
		}
		return UserVO.fromEntity(user);
	}

	@Override
	public UserVO getByAccount(String account) {
		User user = userDao.getByAccount(account);
		if (user == null) {
			return null;
		}
		return UserVO.fromEntity(user);
	}

	@Override
	public UserVO create(UserVO userVO) {
		User user = userDao.create(UserVO.toEntity(userVO));
		if (user == null) {
			return null;
		}

		// 保存用户角色关系
		userRoleManager.saveOrUpdateUserRoles("" + user.getId(), userVO.getRoleIds());

		return UserVO.fromEntity(user);
	}

	@Override
	public void update(UserVO userVO) {
		userDao.update(UserVO.toEntity(userVO));

		// 更新用户角色关系
		userRoleManager.saveOrUpdateUserRoles("" + userVO.getId(), userVO.getRoleIds());
	}

	@Override
	public boolean delete(String id) {
		boolean ret = userDao.delete(id);

		// 删除用户角色关系
		userRoleManager.deleteByUserId(id);

		return ret;
	}

	@Override
	public Page<UserVO> pagingList(String account, String name, String email, String phone, Page<UserVO> page) {
		Page<User> entityPage = userDao.pagingList(account, name, email, phone, toEntityPage(page));

		return fromEntityPage(entityPage);
	}

	/**
	 * 转换Page &lt;VO&gt; 为 Page &lt;Entity&gt;
	 * @param voPage
	 * @return
	 */
	private Page<User> toEntityPage(Page<UserVO> voPage) {
		Page<User> entityPage = new Page<User>();
		entityPage.setStart(voPage.getStart());
		entityPage.setLimit(voPage.getLimit());
		entityPage.setTotal(voPage.getTotal());
		entityPage.setSort(voPage.getSort());
		entityPage.setDir(voPage.getDir());
		return entityPage;
	}

	/**
	 * 转换Page &lt;Entity&gt; 为 Page &lt;VO&gt;
	 * @param entityPage
	 * @return
	 */
	private Page<UserVO> fromEntityPage(Page<User> entityPage) {
		Page<UserVO> voPage = new Page<UserVO>();
		voPage.setStart(entityPage.getStart());
		voPage.setLimit(entityPage.getLimit());
		voPage.setTotal(entityPage.getTotal());
		voPage.setRows(fromEntityList(entityPage.getRows()));
		voPage.setFooter(fromEntityList(entityPage.getFooter()));
		voPage.setSort(entityPage.getSort());
		voPage.setDir(entityPage.getDir());
		return voPage;
	}

	/**
	 * 转换List &lt;Entity&gt; 为 List &lt;VO&gt;
	 * @param voList
	 * @return
	 */
	private List<UserVO> fromEntityList(List<User> entityList) {
		List<UserVO> voList = new ArrayList<UserVO>(0);
		if (entityList != null && !entityList.isEmpty()) {
			for (User entity : entityList) {
				voList.add(UserVO.fromEntity(entity));
			}
		}
		return voList;
	}

}
