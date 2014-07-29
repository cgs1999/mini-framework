package com.duoduo.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.duoduo.core.dao.BaseDao;
import com.duoduo.system.model.UserRole;

/**
 * 用户角色关系管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-6-2 下午9:55:19
 * @version 1.0.0
 */
@Repository("userRoleDao")
public class UserRoleDao extends BaseDao {

	private static final RowMapper<UserRole> entityRowMapper = new RowMapper<UserRole>() {

		@Override
		public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			final UserRole userRole = new UserRole();
			userRole.setUserId(rs.getLong("user_id"));
			userRole.setRoleId(rs.getLong("role_id"));
			return userRole;
		}
	};

	private static final String listByUserIdSql = "select * from sys_user_role where user_id=?";

	/**
	 * 根据用户Id获取用户角色关系
	 */
	public List<UserRole> listByUserId(String userId) {
		try {
			List<UserRole> result = super.getJdbcTemplate().query(listByUserIdSql, entityRowMapper, userId);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String listByRoleIdSql = "select * from sys_user_role where role_id=?";

	/**
	 * 根据角色Id获取用户角色关系
	 */
	public List<UserRole> listByRoleId(String roleId) {
		try {
			List<UserRole> result = super.getJdbcTemplate().query(listByRoleIdSql, entityRowMapper, roleId);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_user_role(user_id,role_id) values (?,?)";

	/**
	 * 创建用户角色关系
	 */
	public synchronized boolean create(final UserRole userRole) {
		Object[] args = new Object[] {
				userRole.getUserId(), userRole.getRoleId()
		};
		super.getJdbcTemplate().update(insertSql, args);
		return true;
	}

	private static final String deleteByUserIdSql = "delete from sys_user_role where user_id=?";

	/**
	 * 根据用户Id删除用户角色关系
	 */
	public boolean deleteByUserId(String userId) {
		super.getJdbcTemplate().update(deleteByUserIdSql, userId);
		return true;
	}

	private static final String deleteByRoleIdSql = "delete from sys_user_role where role_id=?";

	/**
	 * 根据角色Id删除用户角色关系
	 */
	public boolean deleteByRoleId(String roleId) {
		super.getJdbcTemplate().update(deleteByRoleIdSql, roleId);
		return true;
	}
}
