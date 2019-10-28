<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
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

</head>

<style>
.sitebar_list{
	width:446px;
}
.tagcloud{
	width:476px;
}
p{
	margin:10px;
}
</style>


<body>
<!-- head -->
<%@ include file="../base/head.jsp"%>


<div id="main">
	<!-- container begin -->
	<div id="container">
		<div class="sitebar_list">
			<h3 class="sitebar_title">自我介绍</h3>
			<div class="tagcloud">
				<p>梦想还是要有的</p>
			</div>
		</div>
		
		<div class="sitebar_list">
			<h3 class="sitebar_title">兴趣</h3>
			<div class="tagcloud">
				<p>篮球、摄影、2K、生活大爆炸、模拟城市5、dota1老年型选手</p>
			</div>
		</div>
		
		<div class="sitebar_list">
			<h3 class="sitebar_title">联系方式</h3>
			<div class="tagcloud">
				<p>微博：达拉崩吧斑的比卜</p>
				<p>主页：https://weibo.com/u/3303892304/home</p>
				<p>邮箱：xupeng0727@foxmail.com</p>
				<p><img src="${ctx}/static/resources/images/ercode.jpg" style="width:50%"></p>
			</div>
		</div>
		
	</div>
	
	<!-- container end -->
	
</div>
<!-- main end -->

<%@ include file="../base/foot.jsp"%>

</body>
</html>