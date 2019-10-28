<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

<head>

<meta name="google-site-verification" content="BXPkizi9Odf36c6Gleg7Q7f8RdZkQX-1_0L6_fhvvls" />
<meta name="baidu-site-verification" content="WLnqr1UQYR" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>xuup's blog</title>

<link rel='stylesheet' id='sytle-css'  href='${ctx}/static/resources/css/style.css' type='text/css' media='all' />
<link rel='stylesheet' id='gardenl-pc-css'  href='${ctx}/static/resources/css/home.css' type='text/css' media='all' />
<link rel='stylesheet' id='gardenl-pc-css'  href='${ctx}/static/resources/css/gardenl-pc.css' type='text/css' media='all' />
<link rel='stylesheet' id='gardenl-phone-css'  href='${ctx}/static/resources/css/gardenl-phone.css' type='text/css' media='all' />
<script type='text/javascript' src='${ctx}/static/resources/js/html5shiv.js'></script>
<script type='text/javascript' src='${ctx}/static/resources/js/css3-mediaqueries.js'></script>
<script type='text/javascript' src='${ctx}/static/resources/js/selectivizr-min.js'></script>
<script type='text/javascript' src='${ctx}/static/resources/js/jquery.1.11.1.js'></script>

<!-- 分页 -->
<link rel='stylesheet' href='${ctx}/static/resources/css/paging.css' type='text/css' media='all' />
<script type='text/javascript' src='${ctx}/static/resources/js/page/paging.js'></script>

</head>


<script type="text/javascript">
	$(document).ready(function(){
		$("#upload").bind("click",function(){
			$("#content").empty();
			console.log($('#uploadForm'));
			console.log($('#uploadForm')[0]);
			var formData = new FormData($('#uploadForm')[0]);
			console.log(formData);
			$.ajax({
				type:"post",
				url:"${ctx}/checkPic/upload",
				data:formData,
				cache:false,
				processData:false,
				contentType:false,
				success:function(res){
					console.log(res);
					$("#content").append("<img src='/static/resources/images/" + res["newFileName"] +"' style='width:50%' /> <br>");
					$("#content").append(res["createTime"] + "<br>" + res["make"] + "<br>" + res["model"] + "<br>" + res["location"]);
				},
				error:function(){
					console.log("error");
				}
			})
		});
	});
</script>


<body>
<!-- head -->
<%@ include file="../base/head.jsp"%>


<div id="main">
	
	<!-- container begin -->
	<form id="uploadForm" enctype="multipart/form-data" style="margin-bottom:5px">
		<input type="file" id="picFile" name="picFile">
	</form>
	
	<input type="button" name="upload" id="upload" value="upload"/>
		
	<div style="width:800px;height:800px;">
		<div id="content"></div>
	</div>
	
	<!-- container end -->
	
</div>
<!-- main end -->

<%@ include file="../base/foot.jsp"%>

</body>
</html>