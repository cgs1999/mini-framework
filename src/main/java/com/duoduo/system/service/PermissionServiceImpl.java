package com.duoduo.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.PermissionDao;
import com.duoduo.system.model.Permission;
import com.duoduo.system.vo.PermissionVO;

/**
 * 权限管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Transactional
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionDao permissionDao;

	@Override
	public PermissionVO getById(String id) {
		Permission permission = permissionDao.getById(id);
		if (permission == null) {
			return null;
		}
		return PermissionVO.fromEntity(permission);
	}

	@Override
	public PermissionVO getByName(String name) {
		Permission permission = permissionDao.getByName(name);
		if (permission == null) {
			return null;
		}
		return PermissionVO.fromEntity(permission);
	}

	@Override
	public PermissionVO create(PermissionVO permissionVO) {
		Permission permission = permissionDao.create(PermissionVO.toEntity(permissionVO));
		if (permission == null) {
			return null;
		}
		return PermissionVO.fromEntity(permission);
	}

	@Override
	public void update(PermissionVO permissionVO) {
		permissionDao.update(PermissionVO.toEntity(permissionVO));
	}

	@Override
	public boolean delete(String id) {
		return permissionDao.delete(id);
	}

	@Override
	public List<PermissionVO> listAll() {
		return fromEntityList(permissionDao.listAll());
	}

	@Override
	public List<PermissionVO> listByUserId(String userId) {
		return fromEntityList(permissionDao.listByUserId(userId));
	}

	@Override
	public Page<PermissionVO> pagingList(String name, Page<PermissionVO> page) {
		Page<Permission> entityPage = permissionDao.pagingList(name, toEntityPage(page));

		return fromEntityPage(entityPage);
	}

	/**
	 * 转换Page &lt;VO&gt; 为 Page &lt;Entity&gt;
	 * @param voPage
	 * @return
	 */
	private Page<Permission> toEntityPage(Page<PermissionVO> voPage) {
		Page<Permission> entityPage = new Page<Permission>();
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
	private Page<PermissionVO> fromEntityPage(Page<Permission> entityPage) {
		Page<PermissionVO> voPage = new Page<PermissionVO>();
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
	private List<PermissionVO> fromEntityList(List<Permission> entityList) {
		List<PermissionVO> voList = null;
		if (entityList != null && !entityList.isEmpty()) {
			voList = new ArrayList<PermissionVO>(0);
			for (Permission entity : entityList) {
				voList.add(PermissionVO.fromEntity(entity));
			}
		}
		return voList;
	}

}
