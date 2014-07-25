<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>抱歉，您无权限访问此页面(Sorry,you do not have permission to access this page!)</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="UTF-8" />

<style>
BODY {
	FONT-SIZE: 9pt;
	COLOR: #842b00;
	LINE-HEIGHT: 16pt;
	FONT-FAMILY: "Tahoma", "宋体";
	TEXT-DECORATION: none
}

table {
	FONT-SIZE: 9pt;
	COLOR: #842b00;
	LINE-HEIGHT: 16pt;
	FONT-FAMILY: "Tahoma", "宋体";
	TEXT-DECORATION: none
}

td {
	FONT-SIZE: 9pt;
	COLOR: #842b00;
	LINE-HEIGHT: 16pt;
	FONT-FAMILY: "Tahoma", "宋体";
	TEXT-DECORATION: none
}

BODY {
	SCROLLBAR-HIGHLIGHT-COLOR: buttonface;
	SCROLLBAR-SHADOW-COLOR: buttonface;
	SCROLLBAR-3DLIGHT-COLOR: buttonhighlight;
	SCROLLBAR-trACK-COLOR: #eeeeee;
	BACKGROUND-COLOR: #ffffff
}

A {
	FONT-SIZE: 9pt;
	COLOR: #842b00;
	LINE-HEIGHT: 16pt;
	FONT-FAMILY: "Tahoma", "宋体";
	TEXT-DECORATION: none
}

A:hover {
	FONT-SIZE: 9pt;
	COLOR: #0188d2;
	LINE-HEIGHT: 16pt;
	FONT-FAMILY: "Tahoma", "宋体";
	TEXT-DECORATION: underline
}

H1 {
	FONT-SIZE: 9pt;
	FONT-FAMILY: "Tahoma", "宋体"
}
</style>

</head>
<BODY topMargin=20>
	<center>
		<table cellSpacing=0 width=600 align=center border=0 cepadding="0">
			<tbody>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;<img height=128
						src="${pageContext.request.contextPath}/images/error.jpg"
						width=128 border=0 />
						<h1>
							描述：<strong>抱歉，您无权访问此页面！</strong>
						</h1>
						<hr noShade SIZE=0 />
						<p>
							<font size=-1>&nbsp;&nbsp;&nbsp;&nbsp;<strong>您要访问的网页需要特定的权限，请联系管理员申请！</strong>
							</font>
						</p>
						<p>
							<font size=-1>&nbsp;&nbsp;&nbsp;&nbsp;<strong>Page
									you want to access needs specific permission, please contact the administrator for!
									</strong> </font>
						</p>
						<p>
							<font size=-1><strong>您可以选择以下操作继续浏览网页：<br /> <br />-&gt;&gt;&nbsp;<a
									href="javascript:history.go(-1)">返回上一个页面</a> </strong><a
								href="javascript:history.go(-1)"></a> </font>
						</p>
						<p>
							<font size=-1><strong>您还可以致电<strong>400-828-2866转5</strong>直接与客户服务中心联系！<br><br>
						</p>
						<hr noShade SIZE=0>
					</td>
				</tr>
			</tbody>
		</table>
	</center>
	<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', 'UA-24737345-5' ]);
		_gaq.push([ '_trackPageview' ]);
		(function() {
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl'
					: 'http://www')
					+ '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
	</script>
</body>
</html>
