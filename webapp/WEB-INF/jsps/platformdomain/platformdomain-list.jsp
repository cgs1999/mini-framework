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
  <!-- 其它 -->
  <%@ include file="/inc/common/page_subFrame.jsp"%>
 </head>

<script>
	var baseUrl = "${BMC_APP_URL}/domain/platformDomain";
	var serviceDomainUrl = "${BMC_APP_URL}/domain/serviceDomain";
	var serverUrl = "${BMC_APP_URL}/domain/server";

	$(function() {
		Mo.gridDetail.init({
			baseUrl: baseUrl,
			selectedTreeMoid: -1,
			selectedNodeType: 1,
			lastSelectedNodeType: "1",
			treeOptions : {
				url: baseUrl + "/listMyAdminTree",
				onLoadSuccess : function(node,data) {
					if(data.length>0) {
						// 设置初始选定节点
						var node = data[0];
						if(node.id==-1) {
							node = data[0].children[0];
						}
						// 获取选定节点的moid及类型
						Mo.gridDetail.selectedTreeMoid = node.attributes.moid;
						Mo.gridDetail.selectedNodeType = node.attributes.type;
						Mo.gridDetail.lastSelectedNodeType = Mo.gridDetail.selectedNodeType;
						// 加载数据
						Mo.gridDetail.load();
					}
				},
				onClick : function(node) {
					if(node.id==-1) {
						return;
					}

					Mo.gridDetail.lastSelectedNodeType = Mo.gridDetail.selectedNodeType;
					// 获取选定节点的moid及类型
					Mo.gridDetail.selectedTreeMoid = node.attributes.moid;
					Mo.gridDetail.selectedNodeType = node.attributes.type;
					// 若节点类型发生变化则重新设置平台域/服务器列表属性
					if(Mo.gridDetail.lastSelectedNodeType != Mo.gridDetail.selectedNodeType) {
						// 关闭打开的表单
						Mo.SubFrame.controller.hideDetail();
						// 若没有搜索条件则清空搜索框的内容
						if(Mo.gridDetail.getMoInputVal("#name")=="") {
							$("#name").val("");
						}
						if(Mo.gridDetail.selectedNodeType=="0") {
							$("#btnCreate").html("新建服务器");
							// 重新设置搜索框提示
							$("#name").attr("mo-hint","请输入服务器名称");
							// 重新设置datagrid为服务器列表属性
							Mo.gridDetail.reinit({
								baseUrl : serverUrl,
								datagridOptions : getServerListOptions()
							});
						} else {
							$("#btnCreate").html("新建平台域");
							// 重新设置搜索框提示内容
							$("#name").attr("mo-hint","请输入平台域名称");
							// 重新设置datagrid为平台域列表属性
							Mo.gridDetail.reinit({
								baseUrl : baseUrl,
								datagridOptions : getPlatformListOptions()
							});
						}
					} else {
						// 节点类型没有变化，则直接加载数据
						Mo.gridDetail.load();
					}
				}
			},
			datagridOptions : getPlatformListOptions()
		});
		
		Mo.SubFrame.controller.setDetailSize(56,260);
	});
	
	function getPlatformListOptions() {
		var platformColumns = [ [ {
			field : 'moid',
			hidden : true
		}, {
			field : 'name',
			title : '平台域名称',
			width : 100
		}, {
			field : 'serviceDomainName',
			title : '所属服务域',
			width : 100
		}, {
			field : 'domainGuid',
			title : '平台域GUID',
			width : 200
		}, {
			field : 'queueName',
			title : '平台域队列名称',
			width : 100
/*		}, {
			field : 'enable',
			title : '状态',
			width : 60,
			formatter : function(value,row,index){
				return value==1 ? "启用" : "禁用";
			}*/
		} ] ];
		
		return getInitOptions(baseUrl, platformColumns);
	}
	
	function getServerListOptions() {
		var serverColumns = [ [ {
			field : 'moid',
			hidden : true
		}, {
			field : 'deviceGuid',
			title : '服务器GUID',
			width : 150
		}, {
			field : 'name',
			title : '服务器名称',
			width : 100
		}, {
			field : 'serverTypeName',
			title : '类型',
			width : 80
/* 		}, {
			field : 'netDomain',
			title : '网络域名',
			width : 100
		}, {
			field : 'maxRoam',
			title : '最大漫游数',
			width : 70
		}, {
			field : 'maxOut',
			title : '最大出局数',
			width : 70
		}, {
			field : 'maxIn',
			title : '最大入局数',
			width : 70 */
		}, {
			field : 'enable',
			title : '状态',
			width : 60,
			formatter : function(value,row,index){
				return value==1 ? "启用" : "禁用";
			}
		}] ];
		
		return getInitOptions(serverUrl, serverColumns);
	}
	
	function getInitOptions(parentUrl, columns) {
		return {
			idField : "moid",
			url : parentUrl + "/getPageList",
			columns : columns,
			
			onBeforeLoad:function(params){
				if(Mo.gridDetail.selectedTreeMoid==-1) {
					return false;
				}
				
				if(Mo.gridDetail.selectedNodeType=="0") {
					// 点击的是平台域节点
					params.platformDomainMoid = Mo.gridDetail.selectedTreeMoid;
				} else {
					// 点击的是服务域节点
					params.serviceDomainMoid = Mo.gridDetail.selectedTreeMoid;
				}
				params.name = Mo.gridDetail.getMoInputVal("#name");
				params.start = (params.page-1)*params.rows;
				params.limit = params.rows;
			}
		};
	}

	function doCreate() {
		Mo.SubFrame.controller.setDetailFrame(Mo.gridDetail.baseUrl + "/create");
/* 		if(Mo.gridDetail.selectedNodeType=="0") {
			// 新建服务器
			Mo.SubFrame.controller.setDetailFrame(serverUrl + "/create");
		} else {
			// 新建平台域
			Mo.SubFrame.controller.setDetailFrame(baseUrl + "/create");
		} */
	}
	
	function doSearch() {
		// 加载数据
		Mo.gridDetail.load();
	}
	
	function loadData() {
		// 用于表单操作后的列表刷新，采用reload可使datagrid保持在当前分页
		Mo.gridDetail.reload();
	}
</script>

<body id="grid-panel" class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',border:false,width:165" style="padding-right:54px;">
		<div  class="easyui-layout" data-options="fit:true" >
			<div data-options="region:'north',border:false,height:10" style="overflow:hidden">
			</div>
			<div data-options="region:'center',border:false">
				<div id="mytree" class="nav-tree"></div>
			</div>
		</div>
	</div>
	
	<div data-options="region:'center',border:false">
		<table id="data-grid"></table>
	</div>

	<div id="tb" class="inline">
		<div class="search-bar">
			<input type="text" id="name" name="name" class="input-text" mo-hint="请输入平台域名称" maxlength="20">
			<a href="javascript: doSearch()" class="easyui-linkbutton sep" iconCls="icon-search" plain="true"></a>
		</div>
		<div class="operate-bar"> 	
			<a id="btnCreate" href="javascript: doCreate()" class="btn btn-x btn-gray">新建平台域</a>
		</div>
	</div>
</body>
</html>