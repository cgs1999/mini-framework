package com.duoduo.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.duoduo.core.dao.BaseDao;
import com.duoduo.system.model.PermissionResource;

/**
 * 权限资源关系管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午10:55:35
 * @version 1.0.0
 */
@Repository("permissionResourceDao")
public class PermissionResourceDao extends BaseDao {

	private static final RowMapper<PermissionResource> entityRowMapper = new RowMapper<PermissionResource>() {

		@Override
		public PermissionResource mapRow(ResultSet rs, int rowNum) throws SQLException {
			final PermissionResource permissionResource = new PermissionResource();
			permissionResource.setPermissionId(rs.getLong("permission_id"));
			permissionResource.setResourceId(rs.getLong("resource_id"));
			return permissionResource;
		}
	};

	private static final String listByPermissionIdSql = "select * from sys_permission_resource where permission_id=?";

	/**
	 * 根据权限Id获取权限资源关系
	 */
	public List<PermissionResource> listByPermissionId(String permissionId) {
		try {
			List<PermissionResource> result = super.getJdbcTemplate().query(listByPermissionIdSql, entityRowMapper,
					permissionId);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String listByResourceIdSql = "select * from sys_permission_resource where resource_id=?";

	/**
	 * 根据资源Id获取权限资源关系
	 */
	public List<PermissionResource> listByResourceId(String resourceId) {
		try {
			List<PermissionResource> result = super.getJdbcTemplate().query(listByResourceIdSql, entityRowMapper,
					resourceId);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_permission_resource(permission_id, resource_id) values (?,?)";

	/**
	 * 创建权限资源关系
	 */
	public synchronized boolean create(final PermissionResource permissionResource) {
		Object[] args = new Object[] {
				permissionResource.getPermissionId(), permissionResource.getResourceId()
		};
		super.getJdbcTemplate().update(insertSql, args);
		return true;
	}

	private static final String deleteByPermissionIdSql = "delete from sys_permission_resource where permission_id=?";

	/**
	 * 根据权限Id删除权限资源关系
	 */
	public boolean deleteByPermissionId(String permissionId) {
		super.getJdbcTemplate().update(deleteByPermissionIdSql, permissionId);
		return true;
	}

	private static final String deleteByResourceIdSql = "delete from sys_permission_resource where resource_id=?";

	/**
	 * 根据资源Id删除权限资源关系
	 */
	public boolean deleteByResourceId(String resourceId) {
		super.getJdbcTemplate().update(deleteByResourceIdSql, resourceId);
		return true;
	}
}
