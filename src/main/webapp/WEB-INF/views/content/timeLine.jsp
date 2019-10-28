<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
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
		initArticle();
		initPageNumber();
		initLabels();
		initHotArticle();
		initUrlLinks();
	});
	
	function initArticle(){
		//清空之前的内容 
		$("#context").empty();
		
		$.ajax({
			url:'${ctx}/getArticle',
			method:"post",
			dataType:"json",
			data:{
				"limit":10,
				"offset":$("#curentPage").val(),
				"keyWord":$("#keyWordTitle").val(),
				"category":$("#categoryId").val()
			},
			success:function(res){
				console.log("加载成功");
				//动态生成文章内容
				html = '';
				className = '';
				className1 = '';
				for(i=0;i<res.length;i++){
					console.log(i);
					if(i==0){
						beforehtml = "<a href='javascript:void(0)' target='_blank' class='iu' style='font-size:38px;'>"+res[i].createTime.substring(0,4)+"</a>";
						className = 'zaiyao';
						className1 = 'mecc';
					}else{
						beforehtml = '';
						className = 'zuiyao';
						className1 = 'mucc';
					}
					html = "<section class='list' style='padding:15px 0px'>"+
					"<div class="+className1+">"+beforehtml+"<h2 class='mecctitle'><a href='javascript:void(0);' onclick='detail("+res[i].id+")'>"
					+ "<address class='meccaddress'><time>" + res[i].createTime.substring(0,11) + "&nbsp"+ res[i].title  + "</address></time>"  + "</a></h2>"+
					"</div></section> "
					
					$("#context").append(html);                          
				}
				//pageHtml = "<div class='posts-nav'><span class='page-numbers current'>1</span><a class='page-numbers' href=''>2</s></div>"
				//pageHtml1 = "<div class='posts-nav' id='posts-nav'></div>";
				//$("#container").append("<div id='pageTool'>123213</div>");
				
			},
			error:function(){
				alert("获取内容异常!");
			}
		});
	}
	
	
	function initPageNumber(){
		var total = $("#total").val();
		var maxNum = total / 10;    //最大页码数
		var tempNum = total % 10;
		if(tempNum > 0){
			maxNum += 1;
		}
		var currentNum = $("#curentPage").val();		//当前页码数 
		
		var page = new Paging();
		page.init({	target: $('#pageTool'), pagesize: 9, count: total, current:1,callback: function (pagecount, size, count) {
				console.log("----" + arguments)
				//ajax获取页面内容 
				$("#curentPage").val((pagecount-1)*10);
				initArticle();
				page.render({ count: total, current: pagecount });
			}
		});
	}
	
	
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
					 html += "<a href='javascript:void(0)' onclick='initArticleByTag("+res[i].categoryId+")' style='font-size:15px'>"+res[i].categoryName+"</a>"
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
	
	function detail(articleId){
		console.log(articleId);
		window.location.href = "${ctx}/detail?articleId=" + articleId;
	}
	
	function initArticleByTag(tagId){
		window.location.href = "${ctx}/?categoryId=" + tagId;
	}
	
	
</script>


<body>
<!-- head -->
<%@ include file="../base/head.jsp"%>


<div id="main">
	<input type="text" name="total" id="total" value="${total}"/>
	<input type="hidden" name="curentPage" id="curentPage" value="${curentPage}"/>
	<input type="hidden" name="keyWordTitle" id="keyWordTitle" value="${keyWord}"/> 
	<input type="hidden" name="categoryId" id="categoryId" value="${categoryId}"/>
	
	<!-- container begin -->
	<div id="container">
		<div id="context"></div>
		<div id='pageTool' class='posts-nav'></div>
	</div>
	
	<!-- container end -->
	
	<%@ include file="../base/rightSide.jsp"%>
	
</div>
<!-- main end -->

<%@ include file="../base/foot.jsp"%>

</body>
</html>