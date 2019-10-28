<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>xuup's blog</title>
</head>

<script>
function index(){
	window.location.href = "${ctx}/";
}

function aboutMe(){
	window.location.href = "${ctx}/aboutMe";
}


function timeLine(){
	window.location.href = "${ctx}/timeLine";
}

function wxTool(){
	window.location.href = "${ctx}/wxTool";
}

</script>

<body>
<header id="header-web">
	<div class="header-main">	
	<a href="${ctx}/" class="logo"><img src="${ctx}/static/resources/images/logo.png" align="logo"/></a>
		<nav class="header-nav">
			<div>
				<ul>
					<li><a href="javascript:void(0)" onclick="index()">首页</a></li>

					<li><a href ="javascript:void(0);" onclick="timeLine()">时间轴</a></li>
					<li><a href ="javascript:return false;" onclick="wxTool()">小程序</a></li>
					<li><a href ="javascript:void(0);" onclick="aboutMe()" >联系我</a></li>

				</ul>
			</div>
		</nav>
		<form method="get" class="search" action="${ctx}/">
	      <input class="text" type="text" name="keyWord" id="keyWord" placeholder="搜索">
	    </form>
    </div>
</header>
</body>
</html>