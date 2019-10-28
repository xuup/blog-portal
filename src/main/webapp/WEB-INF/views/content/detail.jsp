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

<!-- 分页 -->
<link rel='stylesheet' href='${ctx}/static/resources/css/paging.css' type='text/css' media='all' />
<script type='text/javascript' src='${ctx}/static/resources/js/page/paging.js'></script>

<!-- ad -->
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-8693186167883139",
    enable_page_level_ads: true
  });
</script>

</head>


<script type="text/javascript">
	$(document).ready(function(){
		initLabels();
		initHotArticle();
		initUrlLinks();
	});
	
	
	
	function initLabels(){
		 $.ajax({
			 url:'${ctx}/getLabels',
			 method:"post",
			 dataType:"json",
			 success:function(res){
				 console.log(res);
				 //动态生成标签 
				 html = '';
				 for(i=0;i<res.length;i++){
					 html += "<a href='' style='font-size:15px'>"+res[i].categoryName+"</a>"
				 }
				 
				 $("#tagcloud").append(html);
				 
				 
			 },
			 error:function(){
				 alert("获取标签信息异常");
			 }
		 });
	}
	
	
	/**
		随机热文内容获取 
	**/
	function initHotArticle(){
		$.ajax({
			url:"${ctx}/getHotArticle",
			method:"post",
			dataType:"json",
			success:function(res){
				console.log(res);
				html = '';
				for(i=0;i<res.length;i++){
					html += "<li><a href=''>"+res[i].title+"</a></li>"
				}
				$("#sitebar_list_ul").append(html);
				
			},
			error:function(){
				alert("获取随机热文信息异常！");
			}
		});
	}
	
	
	
	function initUrlLinks(){
		$.ajax({
			url:'${ctx}/getUrlLinks',
			method:"post",
			dataType:"json",
			success:function(res){
				html = '';
				for(i=0;i<res.length;i++){
					html += "<li><a href='"+res[i].urlValue+"' target='_blank'>"+res[i].urlName+"</a></li>"
				}
				$("#friendsite_list_ul").append(html);
				console.log(res);
			},
			error:function(){
				alert("获取友情链接异常！");
			}
		});
	}
	
</script>


<body>
<!-- head -->
<%@ include file="../base/head.jsp"%>


<div id="main">
	<input type="hidden" name="total" id="total" value="${total}"/>
	<input type="hidden" name="curentPage" id="curentPage" value="${curentPage}"/>
	<!-- container begin -->
	<div id="container">
		<article class="content">
          <header class="contenttitle">
            <h1 class="mscctitle"> <a href="javascript:;"> ${article.title} </a> </h1>
            <address class="msccaddress ">
              <time> ${article.createTime}</time>
              -
              ${article.categoryName}
              
              	阅读量:${article.readNumber}
            </address>
          </header>
          <div class="content-text"> 
          	${article.context}
          <!--content_text-->
          <div class="zhuan">
            <p>本文来自 " xuup的blog "<br></p> 
          </div>
        </article>
	</div>
	
	<!-- container end -->
	
	<%@ include file="../base/rightSide.jsp"%>
	
</div>
<!-- main end -->

<%@ include file="../base/foot.jsp"%>

</body>
</html>