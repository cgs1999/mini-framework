package com.duoduo.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.duoduo.core.dao.BaseDao;
import com.duoduo.core.vo.Page;
import com.duoduo.system.model.PermissionCategory;

/**
 * 权限分类管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-5-30 下午1:24:06
 * @version 1.0.0
 */
@Repository("permissionCategoryDao")
public class PermissionCategoryDao extends BaseDao {

	private static final RowMapper<PermissionCategory> entityRowMapper = new RowMapper<PermissionCategory>() {

		@Override
		public PermissionCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
			final PermissionCategory permissionCategory = new PermissionCategory();
			permissionCategory.setId(rs.getLong("id"));
			permissionCategory.setName(rs.getString("name"));
			permissionCategory.setMemo(rs.getString("memo"));
			permissionCategory.setCreateTime(rs.getTimestamp("create_time"));
			permissionCategory.setUpdateTime(rs.getTimestamp("update_time"));
			return permissionCategory;
		}
	};

	private static final String getByIdSql = "select * from sys_permission_category where id=?";

	/**
	 * 根据Id获取权限分类
	 */
	public PermissionCategory getById(String id) {
		try {
			PermissionCategory permissionCategory = super.getJdbcTemplate().queryForObject(getByIdSql, entityRowMapper,
					id);
			return permissionCategory;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String getByNameSql = "select * from sys_permission_category where name=?";

	/**
	 * 根据名称获取权限分类
	 */
	public PermissionCategory getByName(String name) {
		try {
			PermissionCategory permissionCategory = super.getJdbcTemplate().queryForObject(getByNameSql,
					entityRowMapper, name);
			return permissionCategory;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_permission_category(name,memo,create_time,update_time) values"
			+ "(?,?,now(),now())";

	/**
	 * 创建权限分类
	 */
	public synchronized PermissionCategory create(final PermissionCategory permissionCategory) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		super.getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {
					"id"
				});
				int count = 1;
				ps.setString(count++, permissionCategory.getName());
				ps.setString(count++, permissionCategory.getMemo());
				return ps;
			}
		}, keyHolder);

		permissionCategory.setId(keyHolder.getKey().longValue());
		return permissionCategory;
	}

	private static final String updateSql = "update sys_permission_category set name=?,memo=?,update_time=now() where id=?";

	/**
	 * 修改权限分类
	 */
	public void update(PermissionCategory permissionCategory) {
		Object[] args = new Object[] {
				permissionCategory.getName(), permissionCategory.getMemo(), permissionCategory.getId()
		};
		super.getJdbcTemplate().update(updateSql, args);
	}

	private static final String deleteSql = "delete from sys_permission_category where id=?";

	/**
	 * 删除权限分类
	 */
	public boolean delete(String id) {
		super.getJdbcTemplate().update(deleteSql, id);
		return true;
	}

	private static final String listAllSql = "select * from sys_permission_category";

	/**
	 * 获取用户的权限分类列表
	 */
	public List<PermissionCategory> listAll() {
		try {
			return super.getJdbcTemplate().query(listAllSql, entityRowMapper);
		} catch (DataAccessException e) {
		}
		return null;
	}

	/**
	 * 分页查询权限分类列表（模糊查询，条件为：权限分类名称）
	 */
	public Page<PermissionCategory> pagingList(String name, Page<PermissionCategory> page) {
		String countSql = "select count(id) from sys_permission_category where 1=1";
		String queryByPageSql = "select * from sys_permission_category where 1=1";

		if (StringUtils.hasText(name)) {
			countSql += " and name like :likeName";
			queryByPageSql += " and name like :likeName";
		}

		queryByPageSql += " limit :start,:limit";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("likeName", super.filterKeyPara(name));
		params.put("start", page.getStart());
		params.put("limit", page.getLimit());

		page.setTotal(super.getTotalCount(countSql, params));
		page.setRows(super.getJdbcTemplate().query(queryByPageSql, entityRowMapper, params));
		return page;
	}
}
