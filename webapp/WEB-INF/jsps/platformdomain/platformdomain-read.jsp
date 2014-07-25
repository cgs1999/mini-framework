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
	$(function() {
		Mo.SubFrame.init();

		// 取消
		$("#detail-btn-cancel").click(function() {
			Mo.SubFrame.controller.hideDetail();
		});
	});
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
                    <div class="read">
                      <span class="e-span">${data.name}</span>
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
                <td class="title">所属服务域</td>
                <td class="input">
                    <div class="read">
                      <span class="e-span">${data.serviceDomainName}</span>
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
                <td class="title">平台域队列名称</td>
                <td class="input">
                    <div class="read">
                      <span class="e-span">${data.queueName}</span>
                    </div>
                </td>
                <td class="operate"></td>
              </tr>
            </table>
          </li>
          <li class="data-li" <c:if test="${domainChangeLogs == null}">style="display:none;"</c:if>>
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
	<div id="detail-btn-cancel" class="btn-gray btn-x">关闭</div>
  </div>
</div>


</body>
</html>