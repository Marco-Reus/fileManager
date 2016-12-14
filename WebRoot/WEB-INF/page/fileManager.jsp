<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文件管理</title>
</head>

<body>
	<h2>上传文件</h2>
	<form method="post" enctype="multipart/form-data" 
		action="${pageContext.request.contextPath }/servlet/UploadServlet">
		<input type="file" name="file" /> 
		文件描述<input type="text" name="description" /> <br /> 
		<input type="submit" value="upload" onclick='return check();'>
	</form>
	<div style="color:#336699">
		允许上传文件类型:<b>.zip .rar .js .css .xml .7z .ico .pdf .ppt .pptx .xap
			.xpi .swf .apk .cdf .gif .tar .gz .sh .bmp</b><br /> 空间总容量: <b>100</b>MB，
		一次上传文件大小限制: <b>10</b>MB， 目前已用空间: <b>11991</b>KB， 剩余空间: <b>90409</b>KB<br />
	</div>

	<div>
		<h2><a href="${pageContext.request.contextPath }/servlet/ListServlet">上传文件管理</a></h2>
	</div>
	<table frame="border" border="1" width="75%">
		<tr>
			<th>文件名(<a
				href="${pageContext.request.contextPath }/servlet/ListServlet?order=1">排序</a>)</th>
			<th>上传时间(<a
				href="${pageContext.request.contextPath }/servlet/ListServlet?order=2">排序</a>)</th>
			<th>文件描述</th>
			<th>操作</th>
		</tr>
		<c:forEach var="item" items="${list }">
			<tr align="center">
				<td>${item.filename}</td>
				<td>${item.uptime}</td>
				<td>${item.description}</td>
				<td><a href="${pageContext.request.contextPath }/servlet/DownloadServlet?id=${item.id}">下载</a>
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<a 	href="${pageContext.request.contextPath }/servlet/DeleteServlet?id=${item.id}" onclick="return confirmDelete(this);">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>