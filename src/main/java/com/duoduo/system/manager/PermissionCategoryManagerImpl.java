package com.duoduo.system.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoduo.core.vo.Page;
import com.duoduo.system.dao.PermissionCategoryDao;
import com.duoduo.system.model.PermissionCategory;
import com.duoduo.system.vo.PermissionCategoryVO;

/**
 * 权限分类管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Transactional
@Service("permissionCategoryManager")
public class PermissionCategoryManagerImpl implements PermissionCategoryManager {

	@Resource
	private PermissionCategoryDao permissionCategoryDao;

	@Override
	public PermissionCategoryVO getById(String id) {
		PermissionCategory permissionCategory = permissionCategoryDao.getById(id);
		if (permissionCategory == null) {
			return null;
		}
		return PermissionCategoryVO.fromEntity(permissionCategory);
	}

	@Override
	public PermissionCategoryVO getByName(String name) {
		PermissionCategory permissionCategory = permissionCategoryDao.getByName(name);
		if (permissionCategory == null) {
			return null;
		}
		return PermissionCategoryVO.fromEntity(permissionCategory);
	}

	@Override
	public PermissionCategoryVO create(PermissionCategoryVO permissionCategoryVO) {
		PermissionCategory permissionCategory = permissionCategoryDao.create(PermissionCategoryVO
				.toEntity(permissionCategoryVO));
		if (permissionCategory == null) {
			return null;
		}
		return PermissionCategoryVO.fromEntity(permissionCategory);
	}

	@Override
	public void update(PermissionCategoryVO permissionCategoryVO) {
		permissionCategoryDao.update(PermissionCategoryVO.toEntity(permissionCategoryVO));
	}

	@Override
	public boolean delete(String id) {
		return permissionCategoryDao.delete(id);
	}

	@Override
	public List<PermissionCategoryVO> listAll() {
		return fromEntityList(permissionCategoryDao.listAll());
	}

	@Override
	public Page<PermissionCategoryVO> pagingList(String name, Page<PermissionCategoryVO> page) {
		Page<PermissionCategory> entityPage = permissionCategoryDao.pagingList(name, toEntityPage(page));

		return fromEntityPage(entityPage);
	}

	/**
	 * 转换Page &lt;VO&gt; 为 Page &lt;Entity&gt;
	 * @param voPage
	 * @return
	 */
	private Page<PermissionCategory> toEntityPage(Page<PermissionCategoryVO> voPage) {
		Page<PermissionCategory> entityPage = new Page<PermissionCategory>();
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
	private Page<PermissionCategoryVO> fromEntityPage(Page<PermissionCategory> entityPage) {
		Page<PermissionCategoryVO> voPage = new Page<PermissionCategoryVO>();
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
	private List<PermissionCategoryVO> fromEntityList(List<PermissionCategory> entityList) {
		List<PermissionCategoryVO> voList = null;
		if (entityList != null && !entityList.isEmpty()) {
			voList = new ArrayList<PermissionCategoryVO>(0);
			for (PermissionCategory entity : entityList) {
				voList.add(PermissionCategoryVO.fromEntity(entity));
			}
		}
		return voList;
	}

}
