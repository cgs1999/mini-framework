<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title><spring:message code="test.test" /></title>
  <!-- 通用部分 -->
  <%@ include file="/inc/common/page_base_css.jsp"%>
  <%@ include file="/inc/common/page_base_js.jsp"%>
  <!-- detail部分 -->
  <%@ include file="/inc/common/page_subFrame.jsp"%>
 </head>

<script type="text/javascript">
	var baseUrl = "${BMC_APP_URL}/system/paramConfig";
	$(function() {
		Mo.SubFrame.init();

		// 保存
		$("#detail-btn-save").click(function() {
			var url = baseUrl + "/save";
			if (Mo.Base.throttle.isLock(url)) {
				return false;
			}
			
			Mo.Base.throttle.lock(url); // 正在处理中...
			
			var data = beforeSave();
	    	if(data == false){
	    		Mo.Base.throttle.unLock(url);
	        	return;
	        }
	        
			$.post(url, data, function(t) {
				if (t.success) {
					Mo.SubFrame.controller.hideDetail();
					alert("保存成功");
					reloadDataGrid();
				} else {
					alert("保存失败，原因：" + t.description);
				}
				Mo.Base.throttle.unLock(url);
			}, "json").error(function() {
				alert("保存出现未知错误，请检查网络是否正常！");
				Mo.Base.throttle.unLock(url);
			});
		});

		// 删除
		$("#detail-btn-delete").click(function() {
			var url = baseUrl + "/delete";
			if (Mo.Base.throttle.isLock(url)) {
				return false;
			}
			
			Mo.Base.throttle.lock(url); // 正在处理中...

			var id = $('#id').val();
			if(!id) {
				alert('记录不存在，删除失败');
				Mo.Base.throttle.unLock(url);
				return;
			}
			
			if(!confirm('确定要删除吗？')) {
				Mo.Base.throttle.unLock(url);
				return;
			}
					
			$.post(url, {
				id : id
			}, function(t) {
				if (t.success) {
					Mo.SubFrame.controller.hideDetail();
					alert("删除成功");
					reloadDataGrid();
				} else {
					alert("删除失败，原因：" + t.description);
				}
				Mo.Base.throttle.unLock(url);
			}, "json").error(function() {
				alert("保存出现未知错误，请检查网络是否正常！");
				Mo.Base.throttle.unLock(url);
			});
		});

		// 取消
		$("#detail-btn-cancel").click(function() {
			Mo.SubFrame.controller.hideDetail();
		});
	});
	
	function beforeSave() {
		var data = {
			id : $('#id').val(),
			configKey : $.trim($('#configKey').val()),
			configName : $.trim($('#configName').val()),
			configType : $.trim($('#configType').val()),
			configValue : $.trim($('#configValue').val()),
			note : $('#note').val()
		};

		if (data.configKey == "") {
			alert('请输入参数主键');
			$('#configKey').focus();
			return false;
		}
		if (data.configName == "") {
			alert('请输入参数名称');
			$('#configName').focus();
			return false;
		}
		if (data.configType == "") {
			alert('请输入参数类型');
			$('#configType').focus();
			return false;
		}
		if (data.configValue == "") {
			alert('请输入参数值');
			$('#configValue').focus();
			return false;
		}

		return data;
	}
	
	function reloadDataGrid() {
		// 刷新datagrid的数据
    	Mo.SubFrame.controller.callMainFrameFn("loadData");
	}
</script>

<body>
<div id="wrap-all" class="wrap-detail">
	<div class="detail-body">
    <div class="detail-header">
      <div class="title">参数配置信息</div>
    </div>

    
    <div class= "detail-content">
      <div id="input-all">
      	<input type="hidden" id='id' name="id"  value="${data.id}" />
        <ul id="editor-ui">
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">参数主键</td>
                <td class="input">
                    <div class="inputDiv" <c:if test="${data.id != null}">style="display: none"</c:if>>
                      <input class="e-input"  id="configKey" name="configKey" value="${data.configKey}" maxlength="50"/>
                    </div>
                    <div class="read" <c:if test="${data.id == null}">style="display: none"</c:if>>
                      <span class="e-span">${data.configKey}</span>
                    </div>
                </td>
                <td class="operate"><div class="required" <c:if test="${data.id != null}">style="display: none"</c:if>></div></td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">参数名称</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" type="text" id="configName" name="configName" value="${data.configName}" maxlength="50">
                    </div>
                </td>
                <td class="operate">
                  <div class="required"></div>
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">参数类型</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" type="text" id="configType" name="configType" value="${data.configType}" maxlength="50">
                    </div>
                </td>
                <td class="operate">
                  <div class="required"></div>
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">参数值</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" type="text" id="configValue" name="configValue" value="${data.configValue}" maxlength="500">
                    </div>
                </td>
                <td class="operate">
                  <div class="required"></div>
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">备注</td>
                <td class="input">
                    <div class="inputDiv">
                      <textarea rows="3" cols="70" id="note" name="note" class="e-textarea" maxlength="500">${data.note}</textarea>
                    </div>
                </td>
                <td class="operate"></td>
              </tr>
            </table>
          </li>
        </ul>
    </div>
  </div>
  </div>
  <div class="detail-btn">
	<div id="detail-btn-save" class="btn-gray btn-x btn-x-left">保存</div>
	<div id="detail-btn-delete" class="btn-gray btn-x btn-x-left" <c:if test="${data.id == null}">style="display: none"</c:if>>删除</div>
	<div id="detail-btn-cancel" class="btn-gray btn-x">取消</div>
  </div>
</div>

</body>
</html>