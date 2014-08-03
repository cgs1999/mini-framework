package com.duoduo.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.duoduo.core.dao.BaseDao;
import com.duoduo.security.vo.RoleResourceVO;

/**
 * 角色资源关系管理Dao
 * @author chengesheng@gmail.com
 * @date 2014-8-4 上午2:00:26
 * @version 1.0.0
 */
@Repository("roleResourceDao")
public class RoleResourceDao extends BaseDao {

	private static final RowMapper<RoleResourceVO> entityRowMapper = new RowMapper<RoleResourceVO>() {

		@Override
		public RoleResourceVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			final RoleResourceVO roleResourceVO = new RoleResourceVO();
			roleResourceVO.setRoleId(rs.getLong("role_id"));
			roleResourceVO.setResourceId(rs.getLong("resource_id"));
			roleResourceVO.setUrl(rs.getString("url"));
			return roleResourceVO;
		}
	};

	private static final String listAllSql = "select rp.role_id,pr.resource_id,r.url from sys_role_permission rp"
			+ " left join sys_permission_resource pr on rp.permission_id=pr.permission_id"
			+ " left join sys_resource r on pr.resource_id=r.id";

	/**
	 * 获取所有角色资源关系
	 */
	public List<RoleResourceVO> listAll() {
		try {
			List<RoleResourceVO> result = super.getJdbcTemplate().query(listAllSql, entityRowMapper);
			return result;
		} catch (DataAccessException e) {
		}
		return null;
	}
}
