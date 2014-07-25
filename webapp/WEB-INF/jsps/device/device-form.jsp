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
	var baseUrl = "${BMC_APP_URL}/domain/device";
	$(function() {
		Mo.SubFrame.init();

		$('input[type="radio"]').ezMark();

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
			name : $.trim($('#name').val()),
			typeId : $.trim($('#typeId').val()),
			status : $('input:radio[name=status]:checked').val(),
			orderIndex : $.trim($('#orderIndex').val()),
			terminalType : $('input:radio[name=terminalType]:checked').val()
		};

		// 校验设备名称
		if (data.name == "") {
			alert('请输入名称');
			$('#name').focus();
			return false;
		}
		if (!Mo.Base.validation.isRegExp(regexEnum.name,value)) {
			alert('名称只允许中文、英文字母、数字或下划线字符!');
			return false;
		};
		
		if (data.typeId == "") {
			alert('请输入类型ID');
			$('#typeId').focus();
			return false;
		}
		
		if (data.orderIndex != "" && !Mo.Base.validation.isUnsignedNumeric(data.orderIndex)) {
			alert('请填写正整数');
			$('#orderIndex').focus();
			return false;
		}

		return data;
	}
	
	function reloadDataGrid() {
		// 刷新datagrid的数据
    	Mo.SubFrame.controller.callMainFrameFn("loadData");
	}
</script>
<style>

</style>
<body>

<div id="wrap-all" class="wrap-detail">
	<div class="detail-body">
    <div class="detail-header">
      <div class="title">设备类型信息</div>
    </div>

    
    <div class= "detail-content">
      <div id="input-all">
      	<input type="hidden" id='id' name="id"  value="${data.id}" />
        <ul id="editor-ui">
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">名称</td>
                <td class="input">
                    <div class="inputDiv" <c:if test="${data.id != null}">style="display: none"</c:if>>
                      <input class="e-input" id="name" name="name" value="${data.name}" maxlength="48" />
                    </div>
                    <div class="read" <c:if test="${data.id == null}">style="display: none"</c:if>>
                      <span class="e-span">${data.name}</span>
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
                <td class="title">类型编号</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      <input class="e-input" id="typeId" name="typeId" value="${data.typeId}" maxlength="20" />
                    </div>
                </td>
                <td class="operate"><div class="required"></div></td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">状态</td>
                <td class="input">
                    <div class="checkDiv">
                      <input type="radio" id="statusT" name="status" value="1" <c:if test="${data.status=='1'}"> checked</c:if> />
                      <label for="statusT">启用</label>
                      <input type="radio" id="statusF" name="status" value="0" <c:if test="${data.status!='1'}"> checked</c:if> />
                      <label for="statusF">禁用</label>
                    </div>
                </td>
                <td class="operate"></td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">排序索引</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      <input class="e-input" id="orderIndex" name="orderIndex" value="${data.orderIndex}" maxlength="5" />
                    </div>
                </td>
                <td class="operate"></td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">终端类型</td>
                <td class="input">
                    <div class="checkDiv">
                      <input type="radio" id="terminalTypeT" name="terminalType" value="1" <c:if test="${data.terminalType=='1'}"> checked</c:if> />
                      <label for="terminalTypeT">硬终端</label>
                      <input type="radio" id="terminalTypeF" name="terminalType" value="0" <c:if test="${data.terminalType!='1'}"> checked</c:if> />
                      <label for="terminalTypeF">软终端</label>
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