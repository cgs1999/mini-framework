<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title><spring:message code="home.head.title" /></title>
  <!-- 通用部分 -->
  <%@ include file="/inc/common/page_base_css.jsp"%>
  <%@ include file="/inc/common/page_base_js.jsp"%>
  <!-- 插件部分 -->
  <%@ include file="/inc/plugins/plugin_artdialog.jsp"%>
  <!-- 本地 -->
  <script type="text/javascript" src="${RESOUCE_STATIC_URL}/js/mainFrame.js?t=5.0.3316434933"></script>
 </head>
 
<script>
$.namespace("Mo.Menu");
Mo.Menu = {
	rootMenuId: ${rootMenuId},
	allMenus: ${allMenus}
};

$(function(){
    Mo.Home.init();
});
</script>

<body>
<div id="wrap-all" class="wrap-all">
	<div id="header" class="hd-main">
		<a class="logo" href="#">/ 科达视讯云</a>
    <div class="info">
      <a class="info-i user-info"><s></s>张三</a>
      <div class="info-i setting">
			<a class="info-i setting-info icon">设置</a>
			<div class="pulldown setting-list" style="display:none">
          		<div class="pulldown-arrow"></div>
					<div class="content">
						<span class="li">
              			<a id="modifyUser" href="javascript:"><s></s>个人资料</a>
						</span>
						<span class="li">
						<a id="modifyPassword" href="javascript:"><s></s>修改密码</a>
						</span>
						<span class="li">
						<a id="about" href="javascript:"><s></s>关于</a>
						</span>
					</div>
				</div>
			</div><a class="info-i logout logout-info icon">退出</a>
    </div>
    <div class="navs">
       <a class="navs-i">首页</a>
       <a class="navs-i">业务控制管理中心<b></b></a>
    </div>
	</div>
 
	<div id="inner-main" class="inner-main">
    <div id="inner-main-content" class="inner-main-content">
    <div class="line"></div>
	  <div id="main-nav" class="main-nav">
		<ul>
			<li><a herf=""></a></li>
		</ul>
	  </div>
	  <div id="main-content">
		  <iframe id="main-frame" src="welcome.jsp" name="main-frame" border="0" frameborder="no"></iframe>
	  </div>
    <div class="line"></div>
			<div id="detail-content" style="display:none;">
				<iframe id="detail-frame" src="welcome.jsp" name="detail-frame" border="0" frameborder="no"></iframe>
				<a id="e-bn-fold" class="icon" href="javascript:;"></a>
			</div>
		</div>
    <div id="aside" class="aside">
			<div class="aside-content">
        <div class="line"></div>
				<div class="search-div">
          			<input type="text" class="search-input">
				</div>
      
				<div id="divAsideMenu" class="aside-menu"></div>
			</div>
		</div>
	</div>	
</div>  
<div style="display:none">
	<div id="modifyUserWrapper">
		<div class="modifyUser-content mo-dialog-content">
			<div class="title">
				<span><spring:message code='home.user.modify.title' /></span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
				<input type="hidden" id="oldAccount" name="oldAccount" value="${currentUser.user.account }"/>
				<input type="hidden" id="oldMobile" name="oldMobile" value="${currentUser.user.mobile }"/>
				<input type="hidden" id="oldEmail" name="oldEmail" value="${currentUser.user.email }"/>
				<input type="hidden" id="oldOfficeLocation" name="oldOfficeLocation" value="${currentUser.user.officeLocation }"/>
			</div>
			<div class="separater"></div>
			<div class="info clearfix">
				<div class="setting-item">
					<label><spring:message code='home.user.modify.label.account' /></label>
					<div class="setting-item-main">
						<input type="text" id="account" name="account" class="input-text" value="${currentUser.user.account }" />
					</div>
				</div>
				<div class="setting-item">
					<label><spring:message code='home.user.modify.label.mobile' /></label>
					<div class="setting-item-main">
						<input type="text" id="mobile" name="mobile" class="input-text" value="${currentUser.user.mobile }" />
					</div>
				</div>
				<div class="setting-item">
					<label><spring:message code='home.user.modify.label.email' /></label>
					<div class="setting-item-main">
						<input type="text" id="email" name="email" class="input-text" value="${currentUser.user.email }" />
					</div>
				</div>
				<div class="setting-item">
					<label><spring:message code='home.user.modify.label.address' /></label>
					<div class="setting-item-main">
						<input type="text" id="officeLocation" name="officeLocation" class="input-text" value="${currentUser.user.officeLocation }" />
					</div>
				</div>
				<div class="item-msg">
					<div class="msg"></div>
				</div>
				<div class="btn-wrapper">
					<a class="btn-gray confirm btn-x btn-x-left" href="javascript:"><spring:message code='home.user.modify.btn.confirm' /></a>
					<a class="btn-gray cancel btn-x" href="javascript:"><spring:message code='home.user.modify.btn.cancel' /></a>
				</div>
			</div>
		</div>
	</div>
	
	<div id="modifyPasswordWrapper">
		<div class="modifyPassword-content mo-dialog-content">
			<div class="title">
				<span><spring:message code='home.password.modify.title' /></span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
			</div>
			<div class="separater"></div>
			<div class="info">
				<div class="setting-item">
					<label><spring:message code='home.password.modify.label.oldPassword' /></label>
					<div class="setting-item-main">
						<input type="password" id="oldPassword" name="oldPassword" class="input-text item-old-password" value="" />
					</div>
				</div>
				<div class="setting-item">
					<label><spring:message code='home.password.modify.label.newPassword' /></label>
					<div class="setting-item-main">
						<input type="password" id="newPassword" name="newPassword" class="input-text item-new-password" value="" />
						<div class="text-tips"><spring:message code='home.password.modify.info.password.format' /></div>
					</div>
				</div>

				<div class="setting-item">
					<label><spring:message code='home.password.modify.label.conPassword' /></label>
					<div class="setting-item-main">
						<input type="password" id="confirmPassword" name="confirmPassword" class="input-text item-confirm-password" value="" />
					</div>
				</div>
				<div class="item-msg">
					<div class="msg"></div>
				</div>
				<div class="btn-wrapper">
					<a class="btn-gray confirm btn-x btn-x-left" href="javascript:"><spring:message code='home.password.modify.btn.confirm' /></a>
					<a class="btn-gray cancel btn-x" href="javascript:"><spring:message code='home.password.modify.btn.cancel' /></a>
				</div>
			</div>
		</div>
	</div>
	
	<div id="aboutWrapper">
		<div class="about-content mo-dialog-content">
			<div class="title">
				<span>关于</span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
			</div>
			<div class="separater"></div>
			<div class="info">
				<div class="company"><span>KEDACOM | 科达服务管理中心</span></div>
				<div class="version"><span>版本号5.0.0.0</span></div>
				<div class="corpright"><span>版权所有：2013069科达科技有限公司</span></div>
			</div>
		</div>
	</div>
	
	<div id="logoutWrapper">
		<div class="logout-content mo-dialog-content">
			<div class="title">
				<span>退出系统</span>
				<a href="javascript:void(0);" class="W_close" hidefocus="true"></a>
			</div>
			<div class="separater"></div>
			<div class="info">
				<div class="msg"><span>确认退出系统？</span></div>
				<div class="btn-wrapper">
					<a class="btn-gray confirm btn-x btn-x-left" href="javascript:">确认</a>
					<a class="btn-gray cancel btn-x" href="javascript:">取消</a>
				</div>
			</div>
			
		</div>
	</div>

</div>             
</body>
</html>
