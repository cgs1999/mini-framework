package com.duoduo.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.duoduo.core.dao.BaseDao;
import com.duoduo.core.vo.Page;
import com.duoduo.system.model.User;

/**
 * 用户管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-5-30 下午1:24:06
 * @version 1.0.0
 */
@Repository("userDao")
public class UserDao extends BaseDao {

	private static final RowMapper<User> entityRowMapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			final User user = new User();
			user.setId(rs.getLong("id"));
			user.setAccount(rs.getString("account"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setSalt(rs.getString("salt"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			user.setStatus(rs.getInt("status"));
			user.setCreateTime(rs.getTimestamp("create_time"));
			user.setUpdateTime(rs.getTimestamp("update_time"));
			return user;
		}
	};

	private static final String getByIdSql = "select * from sys_user where id=?";

	/**
	 * 根据Id获取用户
	 */
	public User getById(String id) {
		try {
			User user = super.getJdbcTemplate().queryForObject(getByIdSql, entityRowMapper, id);
			return user;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String getByAccountSql = "select * from sys_user where account=?";

	/**
	 * 根据帐号获取用户
	 */
	public User getByAccount(String account) {
		try {
			User user = super.getJdbcTemplate().queryForObject(getByAccountSql, entityRowMapper, account);
			return user;
		} catch (DataAccessException e) {
		}
		return null;
	}

	private static final String insertSql = "insert into sys_user(account,name,password,salt,email,phone,status,create_time,update_time) values"
			+ "(?,?,?,?,?,?,?,now(),now())";

	/**
	 * 创建用户
	 */
	public synchronized User create(final User user) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		super.getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {
					"id"
				});
				int count = 1;
				ps.setString(count++, user.getAccount());
				ps.setString(count++, user.getName());
				ps.setString(count++, user.getPassword());
				ps.setString(count++, user.getSalt());
				ps.setString(count++, user.getEmail());
				ps.setString(count++, user.getPhone());
				ps.setInt(count++, user.getStatus());
				return ps;
			}
		}, keyHolder);

		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	private static final String updateSql = "update sys_user set account=?,name=?,password=?,"
			+ "salt=?,email=?,phone=?,status=?,update_time=now() where id=?";

	/**
	 * 修改用户
	 */
	public void update(User user) {
		Object[] args = new Object[] {
				user.getAccount(), user.getName(), user.getPassword(), user.getSalt(), user.getEmail(),
				user.getPhone(), user.getStatus(), user.getId()
		};
		super.getJdbcTemplate().update(updateSql, args);
	}

	private static final String deleteSql = "delete from sys_user where id=?";

	/**
	 * 删除用户
	 */
	public boolean delete(String id) {
		super.getJdbcTemplate().update(deleteSql, id);
		return true;
	}

	/**
	 * 分页查询用户列表（模糊查询，条件为：帐号、姓名、电子邮箱、电话）
	 */
	public Page<User> pagingList(String account, String name, String email, String phone, Page<User> page) {
		String countSql = "select count(id) from sys_user where 1=1";
		String queryByPageSql = "select * from sys_user where 1=1";

		if (StringUtils.hasText(account)) {
			countSql += " and account like :likeAccount";
			queryByPageSql += " and account like :likeAccount";
		}

		if (StringUtils.hasText(name)) {
			countSql += " and name like :likeName";
			queryByPageSql += " and name like :likeName";
		}

		if (StringUtils.hasText(email)) {
			countSql += " and email like :likeEmail";
			queryByPageSql += " and email like :likeEmail";
		}

		if (StringUtils.hasText(phone)) {
			countSql += " and phone like :likePhone";
			queryByPageSql += " and phone like :likePhone";
		}

		queryByPageSql += " limit :start,:limit";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("likeAccount", super.filterKeyPara(account));
		params.put("likeName", super.filterKeyPara(name));
		params.put("likeEmail", super.filterKeyPara(email));
		params.put("likePhone", super.filterKeyPara(phone));
		params.put("start", page.getStart());
		params.put("limit", page.getLimit());

		page.setTotal(super.getTotalCount(countSql, params));
		page.setRows(super.getJdbcTemplate().query(queryByPageSql, entityRowMapper, params));
		return page;
	}
}
