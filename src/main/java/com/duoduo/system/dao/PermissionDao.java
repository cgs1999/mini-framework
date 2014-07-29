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
import com.duoduo.system.model.Permission;

/**
 * 权限管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-5-30 下午1:24:06
 * @version 1.0.0
 */
@Repository("permissionDao")
public class PermissionDao extends BaseDao {

	private static final RowMapper<Permission> entityRowMapper = new RowMapper<Permission>() {

		@Override
		public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
			final Permission permission = new Permission();
			permission.setId(rs.getLong("id"));
			permission.setName(rs.getString("name"));
			permission.setPermissionCategoryId(rs.getLong("permission_category_id"));

			try {
				permission.setPermissionCategoryName(rs.getString("permissionCategoryName"));
			} catch (SQLException e) {
			}

			permission.setMemo(rs.getString("memo"));
			permission.setCreateTime(rs.getTimestamp("create_time"));
			permission.setUpdateTime(rs.getTimestamp("update_time"));
			return permission;
		}
	};

	private static final String getByIdSql = "select *,pc.name as permissionCategoryName from sys_permission p"
			+ " left join sys_permission_category pc on pc.id=p.permission_category_id" + " where p.id=?";

	/**
	 * 根据Id获取权限
	 */
	public Permission getById(String id) {
		try {
			Permission permission = super.getJdbcTemplate().queryForObject(getByIdSql, entityRowMapper, id);
			return permission;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String getByNameSql = "select * from sys_permission where name=?";

	/**
	 * 根据名称获取权限
	 */
	public Permission getByName(String name) {
		try {
			Permission permission = super.getJdbcTemplate().queryForObject(getByNameSql, entityRowMapper, name);
			return permission;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_permission(name,permission_category_id,memo,create_time,update_time) values"
			+ "(?,?,?,now(),now())";

	/**
	 * 创建权限
	 */
	public synchronized Permission create(final Permission permission) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		super.getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {
					"id"
				});
				int count = 1;
				ps.setString(count++, permission.getName());
				ps.setLong(count++, permission.getPermissionCategoryId());
				ps.setString(count++, permission.getMemo());
				return ps;
			}
		}, keyHolder);

		permission.setId(keyHolder.getKey().longValue());
		return permission;
	}

	private static final String updateSql = "update sys_permission set name=?,permission_category_id=?,memo=?,update_time=now() where id=?";

	/**
	 * 修改权限
	 */
	public void update(Permission permission) {
		Object[] args = new Object[] {
				permission.getName(), permission.getPermissionCategoryId(), permission.getMemo(), permission.getId()
		};
		super.getJdbcTemplate().update(updateSql, args);
	}

	private static final String deleteSql = "delete from sys_permission where id=?";

	/**
	 * 删除权限
	 */
	public boolean delete(String id) {
		super.getJdbcTemplate().update(deleteSql, id);
		return true;
	}

	private static final String listAllSql = "select * from sys_permission";

	/**
	 * 获取所有权限列表
	 */
	public List<Permission> listAll() {
		try {
			return super.getJdbcTemplate().query(listAllSql, entityRowMapper);
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String listByCategorySql = "select * from sys_permission where permission_category_id=?";

	/**
	 * 获取指定权限分类的权限列表
	 */
	public List<Permission> listByCategory(String permissionCategoryId) {
		try {
			return super.getJdbcTemplate().query(listByCategorySql, entityRowMapper, permissionCategoryId);
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String listByUserIdSql = "select p.* from sys_permission p"
			+ " left join sys_role_permission rp on rp.permission_id = p.id"
			+ " left join sys_user_role ur on ur.role_id = rp.role_id" + " where ur.user_id = ?";

	/**
	 * 获取用户的权限列表
	 */
	public List<Permission> listByUserId(String userId) {
		try {
			return super.getJdbcTemplate().query(listByUserIdSql, entityRowMapper, userId);
		} catch (DataAccessException e) {
		}
		return null;
	}

	/**
	 * 分页查询权限列表（模糊查询，条件为：权限名称）
	 */
	public Page<Permission> pagingList(String name, Page<Permission> page) {
		String countSql = "select count(id) from sys_permission where 1=1";
		String queryByPageSql = "select * from sys_permission where 1=1";

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
		page.setRows(super.getNamedParameterJdbcTemplate().query(queryByPageSql, params, entityRowMapper));
		return page;
	}

	// /**
	// * 分页查询权限列表（模糊查询，条件为：权限名称）
	// */
	// public Page<Permission> pagingList(String name, Page<Permission> page) {
	// String countSql = "select count(id) from sys_permission where 1=1";
	// String queryByPageSql = "select * from sys_permission where 1=1";
	//
	// if (StringUtils.hasText(name)) {
	// countSql += " and name like ?";
	// queryByPageSql += " and name like ?";
	// }
	//
	// queryByPageSql += " limit ?,?";
	//
	// String likeName = super.filterKeyPara(name);
	// page.setTotal(super.getTotalCount(countSql, likeName));
	// page.setRows(super.getJdbcTemplate().query(queryByPageSql, entityRowMapper, likeName, page.getStart(),
	// page.getLimit()));
	// return page;
	// }
}
