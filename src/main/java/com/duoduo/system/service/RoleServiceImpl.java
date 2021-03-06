package com.duoduo.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoduo.core.vo.Page;
import com.duoduo.system.manager.RoleManager;
import com.duoduo.system.manager.RolePermissionManager;
import com.duoduo.system.model.Role;
import com.duoduo.system.vo.RoleVO;

/**
 * 角色管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleManager roleManager;
	@Resource
	private RolePermissionManager rolePermissionManager;

	@Override
	public RoleVO getById(String id) {
		Role role = roleManager.getById(id);
		if (role == null) {
			return null;
		}
		return RoleVO.fromEntity(role);
	}

	@Override
	public RoleVO getByName(String name) {
		Role role = roleManager.getByName(name);
		if (role == null) {
			return null;
		}
		return RoleVO.fromEntity(role);
	}

	@Override
	public RoleVO create(RoleVO roleVO) {
		Role role = roleManager.create(RoleVO.toEntity(roleVO));
		if (role == null) {
			return null;
		}

		// 保存角色权限关系
		rolePermissionManager.saveOrUpdate("" + role.getId(), roleVO.getPermissionIds());

		return RoleVO.fromEntity(role);
	}

	@Override
	public void update(RoleVO roleVO) {
		roleManager.update(RoleVO.toEntity(roleVO));

		// 更新角色权限关系
		rolePermissionManager.saveOrUpdate("" + roleVO.getId(), roleVO.getPermissionIds());
	}

	@Override
	public boolean delete(String id) {
		boolean ret = roleManager.delete(id);

		// 删除角色权限关系
		rolePermissionManager.deleteByRoleId(id);

		return ret;
	}

	@Override
	public List<RoleVO> listAll() {
		return fromEntityList(roleManager.listAll());
	}

	@Override
	public List<RoleVO> listByUserId(String userId) {
		return fromEntityList(roleManager.listByUserId(userId));
	}

	@Override
	public Page<RoleVO> pagingList(String name, Page<RoleVO> page) {
		Page<Role> entityPage = roleManager.pagingList(name, toEntityPage(page));

		return fromEntityPage(entityPage);
	}

	/**
	 * 转换Page &lt;VO&gt; 为 Page &lt;Entity&gt;
	 * @param voPage
	 * @return
	 */
	private Page<Role> toEntityPage(Page<RoleVO> voPage) {
		Page<Role> entityPage = new Page<Role>();
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
	private Page<RoleVO> fromEntityPage(Page<Role> entityPage) {
		Page<RoleVO> voPage = new Page<RoleVO>();
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
	private List<RoleVO> fromEntityList(List<Role> entityList) {
		List<RoleVO> voList = null;
		if (entityList != null && !entityList.isEmpty()) {
			voList = new ArrayList<RoleVO>(0);
			for (Role entity : entityList) {
				voList.add(RoleVO.fromEntity(entity));
			}
		}
		return voList;
	}

}
