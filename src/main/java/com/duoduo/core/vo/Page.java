package com.duoduo.core.vo;

import java.util.List;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装, 用于EasyUI的封装
 * @author chengesheng@gmail.com
 * @date 2014-3-21 上午12:42:48
 * @version 1.0.0
 */
public class Page<T> {

	public static final int LIMIT_NO_PAGINATION = -1;

	/** 分页开始索引 */
	private int start = 0;
	/** 每页显示的数量 */
	private int limit = 20;
	/** 记录总数 */
	private long total = 0;
	/** EasyUI当前页数据 */
	private List<T> rows;
	/** EasyUI页眉数据，用于统计等的处理 */
	private List<T> footer;

	private String sort;
	private String dir; // 枚举

	public Page() {
	}

	public Page(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageCount() {
		int pageCount = (int) this.total / this.limit;
		int remainder = (int) this.total % this.limit;
		if (remainder == 0) {
			return pageCount;
		}
		return pageCount + 1;
	}

}
