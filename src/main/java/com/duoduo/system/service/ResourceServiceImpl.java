package com.duoduo.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duoduo.core.vo.Page;
import com.duoduo.system.Constants;
import com.duoduo.system.dao.ResourceDao;
import com.duoduo.system.model.Resource;
import com.duoduo.system.vo.ResourceVO;

/**
 * 资源管理业务实现类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午12:23:59
 * @version 1.0.0
 */
@Transactional
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	private static final String TYPE_MENU = "1";

	@javax.annotation.Resource
	private ResourceDao resourceDao;

	@Override
	public ResourceVO getById(String id) {
		Resource resource = resourceDao.getById(id);
		if (resource == null) {
			return null;
		}
		return ResourceVO.fromEntity(resource);
	}

	@Override
	public ResourceVO getByName(String name) {
		Resource resource = resourceDao.getByName(name);
		if (resource == null) {
			return null;
		}
		return ResourceVO.fromEntity(resource);
	}

	@Override
	public ResourceVO create(ResourceVO resourceVO) {
		Resource resource = resourceDao.create(ResourceVO.toEntity(resourceVO));
		if (resource == null) {
			return null;
		}
		return ResourceVO.fromEntity(resource);
	}

	@Override
	public void update(ResourceVO resourceVO) {
		resourceDao.update(ResourceVO.toEntity(resourceVO));
	}

	@Override
	public boolean delete(String id) {
		return resourceDao.delete(id);
	}

	@Override
	public Page<ResourceVO> pagingList(String name, Page<ResourceVO> page) {
		Page<Resource> entityPage = resourceDao.pagingList(name, toEntityPage(page));

		return fromEntityPage(entityPage);
	}

	@Override
	public List<ResourceVO> listByUserId(String userId) {
		return fromEntityList(resourceDao.listByUserId(userId));
	}

	@Override
	public List<ResourceVO> listByRoleId(String roleId) {
		return fromEntityList(resourceDao.listByRoleId(roleId));
	}

	@Override
	public List<ResourceVO> listSubResource(String parentId) {
		return fromEntityList(resourceDao.listSubResource(parentId));
	}

	@Override
	public List<ResourceVO> listAllMenuSimple() {
		return toSimpleList(resourceDao.listByType(TYPE_MENU));
	}

	@Override
	public List<ResourceVO> listAllMenu() {
		return fromEntityList(resourceDao.listByType(TYPE_MENU));
	}

	@Override
	public List<ResourceVO> listRootMenu() {
		return fromEntityList(resourceDao.listSubResourceByType("" + Constants.ROOT_MENU_ID, TYPE_MENU));
	}

	/**
	 * 转换Page &lt;VO&gt; 为 Page &lt;Entity&gt;
	 * @param voPage
	 * @return
	 */
	private Page<Resource> toEntityPage(Page<ResourceVO> voPage) {
		Page<Resource> entityPage = new Page<Resource>();
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
	private Page<ResourceVO> fromEntityPage(Page<Resource> entityPage) {
		Page<ResourceVO> voPage = new Page<ResourceVO>();
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
	private List<ResourceVO> fromEntityList(List<Resource> entityList) {
		List<ResourceVO> voList = null;
		if (entityList != null && !entityList.isEmpty()) {
			voList = new ArrayList<ResourceVO>(0);
			for (Resource entity : entityList) {
				voList.add(ResourceVO.fromEntity(entity));
			}
		}
		return voList;
	}

	/**
	 * 转换List &lt;Entity&gt; 为简单的 List &lt;VO&gt;
	 * @param voList
	 * @return
	 */
	private List<ResourceVO> toSimpleList(List<Resource> entityList) {
		List<ResourceVO> voList = null;
		if (entityList != null && !entityList.isEmpty()) {
			voList = new ArrayList<ResourceVO>(0);
			for (Resource entity : entityList) {
				voList.add(ResourceVO.toSimpleVO(entity));
			}
		}
		return voList;
	}

}
