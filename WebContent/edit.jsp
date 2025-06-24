<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>つぶやきの編集</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="main-contents">
		<div class="header">
			<a href="./">ホーム</a>
			<a href="setting">設定</a>
			<a href="logout">ログアウト</a>
		</div>

		<div class="profile">
			<div class="name">
				<h2>
					<c:out value="${loginUser.name}" />
				</h2>
			</div>
			<div class="account">
				@
				<c:out value="${loginUser.account}" />
			</div>
			<div class="description">
				<c:out value="${loginUser.description}" />
			</div>
		</div>

		<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="errorMessage">
						<li><c:out value="${errorMessage}" />
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<form action="edit" method="post">
			<input name="id" value="${message.id}" id="id" type="hidden" />
			<input name="userId" value="${message.userId}" id="id" type="hidden" />
			<label for="">つぶやき（140文字まで）</label>
			<textarea name="text" cols="100" rows="5" id="text"><c:out value="${message.text}" /></textarea><br />
			<input type="submit" value="更新" /> <br />
			<a href="./">戻る</a>
		</form>

		<div class="copyright">Copyright(c)Shoko Ogasawara</div>
	</div>
</body>

</html>