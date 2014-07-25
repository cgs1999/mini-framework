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
	width: 360px;
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
	width: 300px;
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
	var baseUrl = "${BMC_APP_URL}/system/menu";

	$.namespace("Mo.gridDetail");
	Mo.gridDetail = {
		init : function() {
			this.initPage();
			this.regEvent();
		},
		initPage : function() {
			$("#tree").tree({
				animate: true,
				checkbox: true,
				cascadeCheck: false,
				url : baseUrl + "/listAllSimple",
				onLoadSuccess: function(node, data) {
					// 初始化选中数据
					initCheckData(data);
				}
			});
		},
		regEvent : function() {

		}
	}

	$(function() {
		Mo.gridDetail.init();
		
		// 确定
		$("#detail-btn-ok").click(function(){
			var menuIds = "";
			var menuNames = "";
			var rows = $("#tree").tree("getChecked");
			for(var i=0, len=rows.length; i<len; i++) {
				menuIds += "," + rows[i].id;
				menuNames += "," + rows[i].text;
			}
			
			// 设置返回值并关闭对话框
			$.dialog.data("menuIds", menuIds.substr(1));
			$.dialog.data("menuNames", menuNames.substr(1));
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
	
	// 初始化选中数据
	function initCheckData(data) {
        var menuIds = $.dialog.data("menuIds");
        if(menuIds!=null && menuIds!="") {
        	var ids = menuIds.split(",");
        	for(var i=0, len=ids.length; i<len; i++) {
        		var node = $("#tree").tree("find", parseInt(ids[i]));
        		if(node!=null) {
        			$("#tree").tree("check", node.target);
        		}
        	}
        }
	}
</script>

<body>
	<div id="pageWrapper">
		<div class="page-content">
			<div class="title">
				<span>选择菜单</span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
			</div>
			<div class="separater"></div>
			<div class="info">
				<div id="tree" class="easyui-tree"></div>
				<div class="item-msg">
					<div class="msg"></div>
				</div>
			</div>
			<div class="btn-wrapper">
				<a id="detail-btn-ok" class="btn-gray confirm btn-x btn-x-left" >确定</a>
				<a id="detail-btn-cancel" class="btn-gray cancel btn-x">取消</a>
			</div>
		</div>
	</div>
</body>
</html>