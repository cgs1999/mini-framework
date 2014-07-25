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
  <%@ include file="/inc/plugins/plugin_artdialog.jsp"%>
  <%@ include file="/inc/plugins/plugin_easyui.jsp"%>
  <!-- detail部分 -->
  <%@ include file="/inc/common/page_subFrame.jsp"%>
 </head>

<script type="text/javascript">
	var baseUrl = "${BMC_APP_URL}/domain/platformDomain";
	var serverUrl = "${BMC_APP_URL}/domain/server";
	
	$(function() {
		Mo.SubFrame.init();
		
		// 选择所属服务域
		$("#btnSelectParent").click(function(){
			Mo.Dialog.selectServiceDomain("serviceDomainMoid", "serviceDomainName");
		});
		
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

			var moid = $('#moid').val();
			if(!moid) {
				alert('记录不存在，删除失败');
				Mo.Base.throttle.unLock(url);
				return;
			}
			
			if(!confirm('确定要删除吗？')) {
				Mo.Base.throttle.unLock(url);
				return;
			}
			
			$.post(url, {
				moid : moid
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
				alert("出现未知错误，请检查网络是否正常！");
				Mo.Base.throttle.unLock(url);
			});
		});

		// 启用灾备恢复
		$("#detail-btn-restore").click(function() {
			var url = baseUrl + "/restore";
			if (Mo.Base.throttle.isLock(url)) {
				return false;
			}
			
			Mo.Base.throttle.lock(url); // 正在处理中...

			var moid = $('#moid').val();
			if(!moid) {
				alert('记录不存在，启用灾备恢复失败');
				Mo.Base.throttle.unLock(url);
				return;
			}
			
			Mo.Dialog.selectPlatformDomain("platformDomainMoid", "platformDomainName");
			var platformDomainMoid = $("#platformDomainMoid").val();
			if(platformDomainMoid=="") {
				Mo.Base.throttle.unLock(url);
				return;
			}
			
			if(!confirm('确定要启用灾备恢复吗？')) {
				Mo.Base.throttle.unLock(url);
				return;
			}
			
			$.post(url, {
				moid : moid,
				platformDomainMoid: platformDomainMoid
			}, function(t) {
				if (t.success) {
					alert("启用灾备恢复成功");
				} else {
					alert("启用灾备恢复失败，原因：" + t.description);
				}
				Mo.Base.throttle.unLock(url);
			}, "json").error(function() {
				alert("出现未知错误，请检查网络是否正常！");
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
			moid : $('#moid').val(),
			name : $.trim($('#name').val()),
			serviceDomainMoid : $('#serviceDomainMoid').val(),
			queueName : $.trim($('#queueName').val())
		};

		if (data.name == "") {
			alert('请输入平台域名称');
			$('#name').focus();
			return false;
		}
		if (data.serviceDomainMoid == "") {
			alert('请选择所属服务域');
			return false;
		}

		return data;
	}
</script>

<body>
<div id="wrap-all" class="wrap-detail">
	<div class="detail-body">
    <div class="detail-header">
      <div class="title">平台域信息</div>
    </div>
    
    
    <div class= "detail-content">
      <div id="input-all">
      	<input type="hidden" id='moid' name="moid"  value="${data.moid}" />
        <ul id="editor-ui">
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">平台域名称</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      <input class="e-input" type="text" id="name" name="name" value="${data.name}" maxlength="100">
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
                <td class="title">所属服务域</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      	<input type="hidden" id="serviceDomainMoid" name="serviceDomainMoid" value="${data.serviceDomainMoid}" />
                      	<input class="e-input" type="text" id="serviceDomainName" name="serviceDomainName" readonly="readonly" value="${data.serviceDomainName}" />
                    </div>
                </td>
                <td class="operate" >
                	<div class="required"></div>
                	<div style="padding-left: 20px;"><input type="button" id="btnSelectParent" name="btnSelectParent" class="btn btn-x btn-gray btn-x-select" value="选择"></div>
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">平台域队列名称</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      	<input class="e-input" type="text" id="queueName" name="queueName" value="${data.queueName}" maxlength="50" />
                    </div>
                </td>
                <td class="operate"></td>
              </tr>
            </table>
          </li>
          <li class="data-li" <c:if test="${data.moid == null || data.moid == '' || domainChangeLogs == null}">style="display:none;"</c:if>>
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title middle">所属域变更记录</td>
                <td class="input" colspan="2" style="width: 100%">
                    <div>
                      	<table class="change-table" style="width: 95%;">
						<tr class="Thead">
							<td style="width: 50%;">所属服务域</td>
							<td style="width: 40%;">修改时间</td>
						</tr>
						<c:forEach items="${domainChangeLogs}" var="domainChangeLog">
						<tr>
							<td>${domainChangeLog.serviceDomainName}</td>
							<td>${domainChangeLog.updateTime}</td>
						</tr>
						</c:forEach>
						</table>
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
    <input type="hidden" id="platformDomainMoid" name="platformDomainMoid" value="" />
    <input type="hidden" id="platformDomainName" name="platformDomainName" value="" />
	<div id="detail-btn-save" class="btn-gray btn-x btn-x-left">保存</div>
	<div id="detail-btn-delete" class="btn-gray btn-x btn-x-left" <c:if test="${data.moid == null}">style="display: none"</c:if>>删除</div>
	<div id="detail-btn-restore" class="btn-gray btn-x btn-x-left" <c:if test="${data.moid == null}">style="display: none"</c:if>>启用灾备恢复</div>
	<div id="detail-btn-cancel" class="btn-gray btn-x">取消</div>
  </div>
</div>


</body>
</html>