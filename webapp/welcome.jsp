<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/resource.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>Movision-BMC</title>
  <!-- 通用部分 -->
  <%@ include file="/inc/common/page_base_css.jsp"%>
  <%@ include file="/inc/common/page_base_js.jsp"%>
  <!-- detail部分 -->
  <%@ include file="/inc/common/page_subFrame.jsp"%>
 </head>
 
<script>
$.namespace("Mo.Config");
Mo.Config = {
  appUrl: "${BMC_APP_URL}",
  staticUrl:"${RESOUCE_STATIC_URL}"
};

$(function(){
	  Mo.SubFrame.init();
})
</script>

<body border="0">

<div id="wrap-all" class="wrap-all">
	
<a href="welcome.jsp" target="detail-frame">打开detail框架</a><br />

</div>

</body>
</html>
