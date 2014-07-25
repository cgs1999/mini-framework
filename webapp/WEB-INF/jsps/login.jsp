<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="login.head.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<link rel="stylesheet" href="${RESOUCE_STATIC_URL}/css/common.min.css?t=5.0.1776366324"/>
<link rel="stylesheet" href="${RESOUCE_STATIC_URL}/css/login.min.css?t=5.0.2397414766"/>
<%@ include file="/inc/common/page_base_js.jsp"%>
<script type="text/javascript" src="${RESOUCE_STATIC_URL}/js/login.min.js?t=5.0.2995151809"></script>
<script type="text/javascript">
if(top.location.href != location.href) {
	top.location.href = location.href;
}

$(document).ready(function(){
	Mo.Login.init();
});
</script>
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<div class="header-left">
				<span class="ent"></span>
				<span class="sep">|</span>
				<span class="sys"><spring:message code="login.header.sys"/></span>
				
			</div>
			<div class="header-right">
				<div class="item-setting-language">
					<a hidefocus="true" class="arrow-down" href="javascript:void(0);"></a>
					<span class="current-language">
						<c:choose>
							<c:when test="${currentLocale != null && currentLocale != ''}">
								<c:if test="${currentLocale == 'zh_CN'}"><spring:message code="login.header.language.zh_CN"/></c:if>
								<c:if test="${currentLocale == 'zh_TW'}"><spring:message code="login.header.language.zh_TW"/></c:if>
								<c:if test="${currentLocale == 'en_US'}"><spring:message code="login.header.language.en_US"/></c:if>
							</c:when>
							<c:otherwise><spring:message code="login.header.default.language"/></c:otherwise>
						</c:choose>
					</span>
				</div>
				<div class="item-forgot-password">
					<label class="forgot-password-icon"></label>
					<a class="forgot-password" href="javascript:void(0);"><spring:message code="login.header.forgot.password.entrance"/></a>				
				</div>
				
				<div class="item-language-list hidden">
					<label class="arrow-up-icon"></label>
					<ul class="ul-language-list">
	            		<li class="setting-language" vId="zh_CN" value="<spring:message code="login.header.language.zh_CN"/>">
	            			<a hidefocus="true" href="javascript:void(0);"><spring:message code="login.header.language.zh_CN"/></a>
	            		</li>
	            		<li class="setting-language" vId="zh_TW" value="<spring:message code="login.header.language.zh_TW"/>">
	            			<a hidefocus="true" href="javascript:void(0);"><spring:message code="login.header.language.zh_TW"/></a>
	            		</li>
	            		<li class="setting-language" vId="en_US" value="<spring:message code="login.header.language.en_US"/>">
	            			<a hidefocus="true" href="javascript:void(0);"><spring:message code="login.header.language.en_US"/></a>
	            		</li>
	            	</ul>
				</div>
			</div>
		</div>
		
		<div id="content">
			<div id="main">
				<div id="login-wrapper">
					<div class="title">
						<span><spring:message code="login.content.login.title"/></span>
					</div>
					<div class="login">
						<div class=" item item-account">
							<input type="text" id="account" name="account" class="input-text input-login" value="" />
							<label class="username-icon"></label>
							<label class="reg-label" for="account"><spring:message code="login.content.login.label.account"/></label>
						</div>
						<div class="item item-password">
							<input type="password" id="password" name="password" class="input-text input-login" value="" />
							<label class="password-icon"></label>
							<label class="reg-label" for="password"><spring:message code="login.content.login.label.password"/></label>
						</div>
						<div class="item item-verifyCode hidden">
							<input type="text" id="verifyCode" name="verifyCode" class="input-text input-verify" value="" />
							<label class="verifycode-icon"></label>
							<label class="reg-label" for="verifyCode"><spring:message code="login.content.login.label.verifyCode"/></label>
							<img align="middle" id="verifyImage" src="${BMC_APP_URL}/adminVerifyImage">
						</div>
						<div class="item-msg">
							<div class="msg-div"><label class="msg-icon"></label><span class="msg"></span></div>
						</div>
						<div>
							<a id="loginBtn" class="btn-login"><spring:message code="login.content.login.btn.login"/></a>
						</div>
					</div>
				</div>
				
				<div id="forgotPassword-wrapper">
					<div class="title">
						<span><spring:message code="login.content.forgot.title"/></span>
						<a href="javascript:void(0);" class="W_close" hidefocus="true" title="<spring:message code="login.content.forgot.close"/>"></a>
					</div>
					<div class="separater"></div>
					<div class="forgotPassword">
						<div class="item-reg-email">
							<input type="text" id="reg-email" name="reg-email" class="input-text input-email" value="" />
							<label class="reg-email-icon"></label>
							<label class="reg-label" for="reg-email"><spring:message code="login.content.forgot.label.email"/></label>
						</div>
						<div class="item-msg">
							<div class="msg-div"><label class="msg-icon"></label><span class="msg"></span></div>
						</div>
						<div>
							<a id="forgotPasswordBtn" class="btn-forgotPassword"><spring:message code="login.content.forgot.btn.confirm"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="footer">
			<span><spring:message code="login.footer.ent.name"/>&nbsp;<spring:message code="login.footer.ent.copyright"/>&nbsp;<spring:message code="login.footer.sys.version"/></span>
		</div>
	</div>
</body>
</html>
