<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title><spring:message code="test.test" /></title>
  <!-- 通用部分 -->
  <%@ include file="/inc/common/page_base_css.jsp"%>
  <%@ include file="/inc/common/page_base_js.jsp"%>
  <!-- 插件部分 -->
  <%@ include file="/inc/plugins/plugin_easyui.jsp"%>
  <%@ include file="/inc/plugins/plugin_artdialog.jsp"%>
  <!-- 其它 -->
  <%@ include file="/inc/common/page_subFrame.jsp"%>
  <style type="text/css">
#pageWrapper {
	min-height: 100%;
    position: relative;
}

#pageWrapper .page-content {
	height: 400px;
	margin: 0 auto;
	overflow:hidden;
	padding: 0 0 40px;
	position: relative;
	width: 600px;
}

.page-content .title {
	margin: 10px 0;
	padding: 0 20px;
}

.page-content .title span {
	font-size: 16px;
}

.page-content .info {
	height: 300px;
	width: 570px;
	margin: 20px auto;
    position: relative;
    overflow: auto;
}

.page-content .item-msg {
	display:none;
	background: none repeat scroll 0 0 #222222;
    margin: 10px 0 10px 150px;
    width: 350px;
}

.page-content .item-msg .msg {
    color: #FFFFFF;
    font-weight: bold;
    height: 32px;
    line-height: 32px;
    margin: 0 20px;
}
  </style>
 </head>

<script>
	var baseUrl = "${BMC_APP_URL}/system/role";

	$.namespace("Mo.gridDetail");
	Mo.gridDetail = {
		init : function() {
			this.initPage();
			this.regEvent();
		},
		initPage : function() {
			$("#data-grid").datagrid({
				idField : 'id',
				rownumbers : true,
				striped : true,
				singleSelect : false,
				checked : true,
				border : false,
				fit : true,
				fitColumns : true,
				showHeader : true,
				loadMsg : '正在加载，请稍候...',
				url : baseUrl + "/listAll",
				columns : [ [ {
					field : 'id',
					hidden : true
				}, {
					field : 'checked',
					checkbox: true
				}, {
					field : 'name',
					title : '角色名称',
					halign : 'center',
					width : 100
				}, {
					field : 'level',
					title : '角色级别',
					halign : 'center',
					width : 80,
					formatter : function(value,row,index){
						return (value==1) ? "管理员" : "操作员";
					/* 	if(value==1) {
							return "<font color='red'>管理员</a>";
						} else {
							return "<font color='green'>操作员</a>";
						} */
					}
				}] ],
				onLoadSuccess: function() {
					initData();
				}
			});
			
			// 加载数据
			loadData();
		},
		regEvent : function() {

		}
	}

	$(function() {
		Mo.gridDetail.init();
		
		// 确定
		$("#detail-btn-ok").click(function(){
			var roleIds = "";
			var roleNames = "";
			var rows = $("#data-grid").datagrid("getChecked");
			for(var i=0, len=rows.length; i<len; i++) {
				roleIds += "," + rows[i].id;
				roleNames += "," + rows[i].name;
			}
			$.dialog.data("roleIds", roleIds.substr(1));
			$.dialog.data("roleNames", roleNames.substr(1));
			$.dialog.close();
		});
		
		// 取消
		$("#detail-btn-cancel").click(function(){
			$.dialog.close();
		});
		
		// 关闭对话框
		$(".W_close").click(function(){
			$.dialog.close();
		});
	});
	
	// 初始化选中相关角色
	function initData() {
        var roleIds = $.dialog.data("roleIds");
        if(roleIds!=null && roleIds!="") {
        	roleIds = "," + roleIds + ",";
        	var rows = $("#data-grid").datagrid("getRows");
        	for(var i=0, len=rows.length; i<len; i++) {
        		var find = roleIds.indexOf("," + rows[i].id + ",");
        		if(find!=-1) {
        			$("#data-grid").datagrid("checkRow", i);
        		}
        	}
        }
	}
	
	function loadData(params) {
        $("#data-grid").datagrid("load");
		
		// 初始化选中相关角色
		initData();
	}
</script>

<body>
	<div id="pageWrapper">
		<div class="page-content">
			<div class="title">
				<span>选择角色</span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
			</div>
			<div class="separater"></div>
			<div class="info">
				<div id="data-grid"></div>
				<div class="item-msg">
					<div class="msg"></div>
				</div>
			</div>
			<div class="btn-wrapper">
				<a id="detail-btn-ok" class="btn-blue confirm btn-x btn-x-left" >确定</a>
				<a id="detail-btn-cancel" class="btn-white cancel btn-x">取消</a>
			</div>
		</div>
	</div>
</body>
</html>