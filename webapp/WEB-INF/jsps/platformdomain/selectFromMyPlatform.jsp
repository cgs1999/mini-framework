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
	padding: 0 0 50px;
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
	height: 320px;
	width: 300px;
	margin: 20px auto;
    position: relative;
    overflow: auto;
}

.page-content .item-msg {
	display:none;
	background: none repeat scroll 0 0 #222222;
    margin: 5px 0 10px 80px;
    width: 150px;
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
	var baseUrl = "${BMC_APP_URL}/domain/platformDomain";

	$.namespace("Mo.gridDetail");
	Mo.gridDetail = {
		init : function() {
			this.initPage();
			this.regEvent();
		},
		initPage : function() {
			$("#tree").tree({
				animate: true,
				//checkbox: true,
				cascadeCheck: false,
				url : baseUrl + "/listMyAdminTree",
				onLoadSuccess: function(node, data) {
					// 初始化选中数据
					initCheckData(data);
				},
				onBeforeSelect: function(node) {
					if(node.attributes.type==1) {
						$(".msg").html("请选择平台域节点");
						$(".item-msg").show();
						setTimeout(function() {$(".item-msg").hide();}, 1500);
						return false;
					}
				}
			});
		},
		regEvent : function() {

		}
	};

	$(function() {
		Mo.gridDetail.init();
		
		// 确定
		$("#detail-btn-ok").click(function(){
			var platformDomainMoid = "";
			var platformDomainName = "";
			var node = $("#tree").tree("getSelected");
			if(node!=null) {
				platformDomainMoid = node.attributes.moid;
				platformDomainName = node.text;
			}
			// alert(platformDomainMoid + "=" + platformDomainName);
			// 设置返回值并关闭对话框
			$.dialog.data("platformDomainMoid", platformDomainMoid);
			$.dialog.data("platformDomainName", platformDomainName);
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
        var platformDomainMoid = $.dialog.data("platformDomainMoid");
        if(platformDomainMoid!=null && platformDomainMoid!="") {
        	var root = $("#tree").tree("find", -1);
        	if(root!=null) {
				var node = findNode(root, platformDomainMoid);
				if(node!=null) {
					node = $("#tree").tree("find", node.id);
					$("#tree").tree("select", node.target);
            	}
        	}
        }
	}
	
	// 递归查找指定moid的节点
	function findNode(node, moid) {
		if(node!=null && node.attributes.moid==moid) {
			return node;
		}
		//alert(node.attributes.moid + "==" + moid);
		if(node.children && node.children.length>0) {
			var len = node.children.length;
			for(var i=0; i<len; i++) {
				var nd = findNode(node.children[i], moid);
				if(nd!=null) {
					return nd;
				}
			}
		}
		return null;
	}
</script>

<body>
	<div id="pageWrapper">
		<div class="page-content">
			<div class="title">
				<span>选择平台域</span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
			</div>
			<div class="separater"></div>
			<div class="info">
				<div id="tree" class="easyui-tree" data-options="fit:true"></div>
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