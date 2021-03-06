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
	var baseUrl = "${BMC_APP_URL}/system/language";

	$(function() {
		Mo.gridDetail.init({
			baseUrl: baseUrl,
			datagridOptions : {
				url : baseUrl + "/getPageList",
				columns : [ [ {
					field : 'id',
					hidden : true
				}, {
					field : 'name',
					title : '语言包名称',
					width : 150
				}, {
					field : 'i18nTag',
					title : '国际化标识',
					width : 100
				}, {
					field : 'orderIndex',
					title : '排序索引',
					width : 100
				}, {
					field : 'enable',
					title : '状态',
					width : 100,
					formatter : function(value,row,index){
						return value==1 ? "启用" : "禁用";
					}
				}, {
					field : 'note',
					title : '备注',
					width : 150
				} ] ],

				onBeforeLoad:function(params){
					params.name = Mo.gridDetail.getMoInputVal("#name");
					params.start = (params.page-1)*params.rows;
					params.limit = params.rows;
				}
			}
		});
		
		Mo.SubFrame.controller.setDetailSize(56,0);
	});

	function doCreate() {
		Mo.SubFrame.controller.setDetailFrame(baseUrl + "/create");
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
	<div data-options="region:'center',border:false">
		<table id="data-grid"></table>
	</div>

	<div id="tb" class="inline">
		<div class="search-bar">
			<input type="text" id="name" name="name" class="input-text" mo-hint="请输入语言包名称" maxlength="20">
			<a href="javascript: doSearch()" class="easyui-linkbutton sep" iconCls="icon-search" plain="true"></a>
		</div>
		<div class="operate-bar"> 	
			<a href="javascript: doCreate()" class="btn btn-x btn-gray">新建</a>
		</div>
	</div>
</body>
</html>