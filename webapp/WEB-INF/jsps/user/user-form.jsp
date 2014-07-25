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
  <!-- detail部分 -->
  <%@ include file="/inc/common/page_subFrame.jsp"%>
 </head>

<script type="text/javascript">
	var baseUrl = "${BMC_APP_URL}/system/user";

	$(function() {
		Mo.SubFrame.init();
    Mo.SubFrame.controller.setDetailSize(56,0);

		$('input[type="radio"]').ezMark();
		
		// 选择所属服务域
		$("#btnSelectServiceDomain").click(function(){
			Mo.Dialog.selectServiceDomain("serviceDomainMoid", "serviceDomainName");
		});
		
		// 选择所属用户域
		$("#btnSelectUserDomain").click(function(){
			Mo.Dialog.selectUserDomain("userDomainMoid", "userDomainName");
		});

		// 选择角色
		$("#btnSelectRole").click(function(){
			Mo.Dialog.selectRoles("roleIds", "roleNames");
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

		// 取消
		$("#detail-btn-cancel").click(function() {
			Mo.SubFrame.controller.hideDetail();
		});
	});
	
	function beforeSave() {
		var data = {
			moid : $('#moid').val(),
			serviceDomainMoid : $('#serviceDomainMoid').val(),
			userDomainMoid : $('#userDomainMoid').val(),
			account : $.trim($('#account').val()),
			name : $.trim($('#name').val()),
			password : $('#password').val(),
			confirmPassword : $('#confirmPassword').val(),
			mobile : $.trim($('#mobile').val()),
			officeLocation : $('#officeLocation').val(),
			email : $.trim($('#email').val()),
			roleIds : $('#roleIds').val(),
			enable : $('input:radio[name=enable]:checked').val()
		};

		if (data.serviceDomainMoid == "") {
			alert('请选择用户所属服务域');
			$('#serviceDomainMoid').focus();
			return false;
		}
		
		// 校验用户帐号
		if (data.account == "") {
			alert('请输入用户帐号');
			$('#account').focus();
			return false;
		}
		if(!Mo.Base.account.checkAccount(data.account)) {
			$('#account').focus();
			return false;
		}
		
		// 校验用户姓名
		if (data.name == "") {
			alert('请输入用户姓名');
			$('#name').focus();
			return false;
		}
		if(!Mo.Base.account.checkName(data.name)) {
			$('#name').focus();
			return false;
		}
		
		// 校验密码
		if (data.password == "") {
			alert('请输入密码');
			$('#password').focus();
			return false;
		}
		if (data.confirmPassword == "") {
			alert('请输入确认密码');
			$('#confirmPassword').focus();
			return false;
		}
		if (data.password != data.confirmPassword) {
			alert('输入的密码和确认密码不一致');
			$('#password').focus();
			return;
		}
		if(!Mo.Base.account.checkPassword(data.password)) {
			$('#password').focus();
			return false;
		}

		// 若手机号码不为空则校验手机号码
		if(data.mobile == "") {
			alert('请输入手机号码');
			$('#mobile').focus();
			return false;
		}
		if(!Mo.Base.account.checkMobile(data.mobile)) {
			$('#mobile').focus();
			return false;
		}

		// 校验电子邮箱
		if (data.email == "") {
			alert('请输入电子邮箱');
			$('#email').focus();
			return false;
		}
		if(!Mo.Base.account.checkEmail(data.email)) {
			$('#email').focus();
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
      <div class="title">用户信息</div>
    </div>

    
    <div class= "detail-content">
      <div id="input-all">
      	<input type="hidden" id='moid' name="moid"  value="${data.moid}" />
        <ul id="editor-ui">
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
                	<input type="button" id="btnSelectServiceDomain" name="btnSelectServiceDomain" class="btn btn-x btn-gray btn-x-select" value="选择">
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">所属用户域</td>
                <td class="input">
                    <div style="padding-right:10px;position:relative;">
                      	<input type="hidden" id="userDomainMoid" name="userDomainMoid" value="${data.userDomainMoid}" />
                      	<input class="e-input" type="text" id="userDomainName" name="userDomainName" readonly="readonly" value="${data.userDomainName}" />
                    </div>
                </td>
                <td class="operate" >
                	<input type="button" id="btnSelectUserDomain" name="btnSelectUserDomain" class="btn btn-x btn-gray btn-x-select" value="选择">
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">用户帐号</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input"  id="account" name="account" value="${data.account}" />
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
                <td class="title">用户姓名</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input"  id="name" name="name" value="${data.name}" />
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
                <td class="title">密　　码</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" type="password" id="password" name="password"  value="">
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
                <td class="title">确认密码</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" type="password" id="confirmPassword" name="confirmPassword"  value="">
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
                <td class="title">手机号码</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input"  id="mobile" name="mobile" value="${data.mobile}" />
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
                <td class="title">联系地址</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" id="officeLocation" name="officeLocation" value="${data.officeLocation}" />
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
                <td class="title">电子邮箱</td>
                <td class="input">
                    <div class="inputDiv">
                      <input class="e-input" id="email" name="email" value="${data.email}" />
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
                <td class="title">角色分配</td>
                <td class="input">
                    <div class="inputDiv">
                      	<input type="hidden" id="roleIds" name="roleIds" value="${data.roleIds}" />
                      	<textarea rows="3" cols="70" id="roleNames" name="roleNames" readonly="readonly" class="e-textarea">${data.roleNames}</textarea>
                    </div>
                </td>
                <td class="operate" >
                	<input type="button" id="btnSelectRole" name="btnSelectRole" class="btn btn-x btn-gray btn-x-select" value="选择">
                </td>
              </tr>
            </table>
          </li>
          <li class="data-li">
            <table style="table-layout:fixed">
            <tbody>
              <tr>
                <td class="title">用户状态</td>
                <td class="input">
                    <div class="checkDiv">
                      <input type="radio" id="enableT" name="enable" value="1" <c:if test="${data.enable}"> checked</c:if> />
                      <label for="enableT">启用</label>
                      <input type="radio" id="enableF" name="enable" value="0" <c:if test="${!data.enable}"> checked</c:if> />
                      <label for="enableF">禁用</label>
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
	<div id="detail-btn-cancel" class="btn-gray btn-x">取消</div>
  </div>
</div>

</body>
</html>