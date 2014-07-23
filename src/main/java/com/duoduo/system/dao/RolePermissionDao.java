package com.duoduo.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.duoduo.core.dao.BaseDao;
import com.duoduo.system.model.RolePermission;

/**
 * 角色权限关系管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午9:55:35
 * @version 1.0.0
 */
@Repository("rolePermissionDao")
public class RolePermissionDao extends BaseDao {

	private static final RowMapper<RolePermission> entityRowMapper = new RowMapper<RolePermission>() {

		@Override
		public RolePermission mapRow(ResultSet rs, int rowNum) throws SQLException {
			final RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(rs.getLong("role_id"));
			rolePermission.setPermissionId(rs.getLong("permission_id"));
			return rolePermission;
		}
	};

	private static final String listByRoleIdSql = "select * from sys_role_permission where role_id=?";

	/**
	 * 根据角色Id获取角色权限关系
	 */
	public List<RolePermission> listByRoleId(String roleId) {
		try {
			List<RolePermission> result = super.getJdbcTemplate().query(listByRoleIdSql, entityRowMapper, roleId);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String listByPermissionIdSql = "select * from sys_role_permission where permission_id=?";

	/**
	 * 根据权限Id获取角色权限关系
	 */
	public List<RolePermission> listByPermissionId(String permissionId) {
		try {
			List<RolePermission> result = super.getJdbcTemplate().query(listByPermissionIdSql, entityRowMapper,
					permissionId);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_role_permission(role_id,permission_id) values (?,?)";

	/**
	 * 创建角色权限关系
	 */
	public synchronized boolean create(final RolePermission rolePermission) {
		Object[] args = new Object[] {
				rolePermission.getRoleId(), rolePermission.getPermissionId()
		};
		super.getJdbcTemplate().update(insertSql, args);
		return true;
	}

	private static final String deleteByRoleIdSql = "delete from sys_role_permission where role_id=?";

	/**
	 * 根据角色Id删除角色权限关系
	 */
	public boolean deleteByRoleId(String roleId) {
		super.getJdbcTemplate().update(deleteByRoleIdSql, roleId);
		return true;
	}

	private static final String deleteByPermissionIdSql = "delete from sys_role_permission where permission_id=?";

	/**
	 * 根据权限Id删除角色权限关系
	 */
	public boolean deleteByPermissionId(String permissionId) {
		super.getJdbcTemplate().update(deleteByPermissionIdSql, permissionId);
		return true;
	}
}
