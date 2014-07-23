package com.duoduo.core.dao;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.util.StringUtils;

public class BaseDao extends NamedParameterJdbcDaoSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void initJdbcTemplate() {
		super.setJdbcTemplate(jdbcTemplate);
	}

	/**
	 * 获取总的记录数
	 * @param sql
	 * @param args
	 * @return
	 */
	public Long getTotalCount(String sql, Object... args) {
		Long count = getJdbcTemplate().queryForObject(sql, Long.class, args);
		return count != null ? count.longValue() : 0;
	}

	/**
	 * 获取总的记录数
	 * @param sql
	 * @param args
	 * @return
	 */
	public Long getTotalCount(String sql, Map<String, ?> args) {
		Long count = getJdbcTemplate().queryForObject(sql, Long.class, args);
		return count != null ? count.longValue() : 0;
	}

	/**
	 * 过滤 %,供like 条件使用
	 * @param keyword
	 * @return
	 */
	public String filterKeyPara(String keyword) {
		if (!StringUtils.hasText(keyword)) {
			return null;
		}
		String key = keyword.trim();
		if (keyword.indexOf("%") >= 0) {
			key = key.replace("%", "/%");
			return "%" + key + "% ESCAPE '/'";
		} else {
			return "%" + key + "%";
		}
	}
}
