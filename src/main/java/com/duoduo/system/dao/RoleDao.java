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
import com.duoduo.system.model.Role;

/**
 * 角色管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-5-30 下午1:24:06
 * @version 1.0.0
 */
@Repository("roleDao")
public class RoleDao extends BaseDao {

	private static final RowMapper<Role> entityRowMapper = new RowMapper<Role>() {

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			final Role role = new Role();
			role.setId(rs.getLong("id"));
			role.setName(rs.getString("name"));
			role.setType(rs.getString("type"));
			role.setEnable(rs.getBoolean("enable"));
			role.setMemo(rs.getString("memo"));
			role.setCreateTime(rs.getTimestamp("create_time"));
			role.setUpdateTime(rs.getTimestamp("update_time"));
			return role;
		}
	};

	private static final String getByIdSql = "select * from sys_role where id=?";

	/**
	 * 根据Id获取角色
	 */
	public Role getById(String id) {
		try {
			Role role = super.getJdbcTemplate().queryForObject(getByIdSql, entityRowMapper, id);
			return role;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String getByNameSql = "select * from sys_role where name=?";

	/**
	 * 根据名称获取角色
	 */
	public Role getByName(String name) {
		try {
			Role role = super.getJdbcTemplate().queryForObject(getByNameSql, entityRowMapper, name);
			return role;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_role(name,type,enable,memo,create_time,update_time) values"
			+ "(?,?,?,?,now(),now())";

	/**
	 * 创建角色
	 */
	public synchronized Role create(final Role role) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		super.getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {
					"id"
				});
				int count = 1;
				ps.setString(count++, role.getName());
				ps.setString(count++, role.getType());
				ps.setInt(count++, role.getEnable() ? 1 : 0);
				ps.setString(count++, role.getMemo());
				return ps;
			}
		}, keyHolder);

		role.setId(keyHolder.getKey().longValue());
		return role;
	}

	private static final String updateSql = "update sys_role set name=?,type=?,enable=?,memo=?,update_time=now() where id=?";

	/**
	 * 修改角色
	 */
	public void update(Role role) {
		Object[] args = new Object[] {
				role.getName(), role.getType(), role.getEnable(), role.getMemo(), role.getId()
		};
		super.getJdbcTemplate().update(updateSql, args);
	}

	private static final String deleteSql = "delete from sys_role where id=?";

	/**
	 * 删除角色
	 */
	public boolean delete(String id) {
		super.getJdbcTemplate().update(deleteSql, id);
		return true;
	}

	private static final String listAllSql = "select * from sys_role";

	/**
	 * 获取用户的角色列表
	 */
	public List<Role> listAll() {
		try {
			return super.getJdbcTemplate().query(listAllSql, entityRowMapper);
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String listByUserIdSql = "select r.* from sys_role r"
			+ " left join sys_user_role ur on ur.role_id = r.id where ur.user_id = ?";

	/**
	 * 获取用户的角色列表
	 */
	public List<Role> listByUserId(String userId) {
		try {
			return super.getJdbcTemplate().query(listByUserIdSql, entityRowMapper, userId);
		} catch (DataAccessException e) {
		}
		return null;
	}

	/**
	 * 分页查询角色列表（模糊查询，条件为：角色名称）
	 */
	public Page<Role> pagingList(String name, Page<Role> page) {
		String countSql = "select count(id) from sys_role where 1=1";
		String queryByPageSql = "select * from sys_role where 1=1";

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
