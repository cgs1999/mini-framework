package com.duoduo.core.vo;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 界面层使用的消息传递类
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午2:32:25
 * @version 1.0.0
 */
public class Message<T> implements Serializable {

	private static final long serialVersionUID = -6568659263635887814L;

	private boolean success; // 操作是否成功
	private String description; // 操作结果信息
	private T data; // 操作的数据，可以不设置

	public Message() {
	}

	/**
	 * 根据业务层异常封装成消息传递类
	 * @param e 操作时发生的异常
	 */
	public Message(Exception e) {
		this.success = false;
		this.description = e.getMessage();
	}

	/**
	 * 根据业务层异常封装成消息传递类
	 * @param e 操作时发生的异常
	 * @param data 操作的数据
	 */
	public Message(Exception e, T data) {
		this(e);
		this.data = data;
	}

	public Message(boolean success, String description) {
		this.success = success;
		this.description = description;
	}

	public Message(boolean success, String description, T data) {
		this.success = success;
		this.description = description;
		this.data = data;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 转换为JSON字符串
	 */
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

}
