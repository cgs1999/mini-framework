package com.duoduo.core.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI树形组件所使用的节点模型
 * @author chengesheng@gmail.com
 * @date 2014-6-3 上午1:46:41
 * @version 1.0.0
 */
public class EasyUiTreeNode extends BaseVO {

	/** 节点显示名称 */
	private String text;
	/** 下级节点列表 */
	private List<EasyUiTreeNode> children = new ArrayList<EasyUiTreeNode>(0);
	/** 节点状态，open表示打开，closed表示关闭 */
	private String state;
	/** 节点图标 */
	private String iconCls;
	/** 复选框选定状态 */
	private Boolean checked;
	/** 自定义的节点属性 */
	private Object attributes = null;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<EasyUiTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EasyUiTreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

}
