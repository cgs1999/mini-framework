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
	var baseUrl = "${BMC_APP_URL}/system/language";
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
			i18nTag : $.trim($('#i18nTag').val()),
			orderIndex : $.trim($('#orderIndex').val()),
			enable : $('input:radio[name=enable]:checked').val(),
			remark : $('#remark').val()
		};

		if (data.name == "") {
			alert('请输入语言包名称');
			$('#name').focus();
			return false;
		}
		if (data.i18nTag == "") {
			alert('请输入国际化标识');
			$('#i18nTag').focus();
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

<body>

<div id="wrap-all" class="wrap-detail">
	<div class="detail-body">
    <div class="detail-header">
      <div class="title">语言包信息</div>
    </div>

    
    <div class= "detail-content">
      <div id="input-all">
      	<input type="hidden" id='id' name="id"  value="${data.id}" />
        <ul id="editor-ui">
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">语言包名称</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input"  id="name" name="name" value="${data.name}" maxlength="50" />
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
                <td class="title">国际化标识</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" type="text" id="i18nTag" name="i18nTag"  value="${data.i18nTag}" maxlength="20">
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
                <td class="title">排序索引</td>
                <td class="input">
                    <div class="inputDiv">
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
                <td class="title">状态</td>
                <td class="input">
                    <div class="checkDiv">
                      <input type="radio" id="enableT" name="enable" value="1" <c:if test="${data.enable==1}"> checked</c:if> />
                      <label for="enableT">启用</label>
                      <span class="space"></span>
                      <input type="radio" id="enableF" name="enable" value="0" <c:if test="${data.enable!=1}"> checked</c:if> />
                      <label for="enableF">禁用</label>
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
                <td class="title">备注</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      <textarea rows="3" cols="70" id="remark" name="remark" class="e-textarea" maxlength="250">${data.remark}</textarea>
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
	<div id="detail-btn-save" class="btn-gray btn-x">保存</div>
	<div id="detail-btn-delete" class="btn-gray btn-x" <c:if test="${data.id == null}">style="display: none"</c:if>>删除</div>
	<div id="detail-btn-cancel" class="btn-gray btn-x">取消</div>
  </div>
</div>

</body>
</html>