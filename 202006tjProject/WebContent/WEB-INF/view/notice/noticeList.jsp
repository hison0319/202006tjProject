<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>

<head>
<title>notice</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<script src="/js/notice/noticeList.js"></script>
<link rel="stylesheet" href="/css/main.css" />
</head>

<body class="is-preload">

	<!-- Header -->
	<header id="header">
        <nav id="nav">
            <ul>
				<li><a href="${pageContext.request.contextPath}/">홈</a></li>
				<li><a href="#">단어장</a></li>
				<li><a
					href="${pageContext.request.contextPath}/notice/showList">공지사항</a>
				</li>
				<c:choose>
					<c:when test="${sessionScope.loginMember == null}">
						<li style="white-space: nowrap;">
							<a href="${pageContext.request.contextPath}/login/form" class="button">LogIn</a>
						</li>
						<li style="white-space: nowrap;">
							<a href="${pageContext.request.contextPath}/signup/form" class="button">SignUp</a>
						</li>
					</c:when>
					<c:when test="${sessionScope.loginMember != null}">
						<li>
							<a href="${pageContext.request.contextPath}/account/showInfo">마이페이지</a>
						</li>
						<li style="white-space: nowrap;">
							<a href="${pageContext.request.contextPath}/login/logout" class="button">LogOut</a>
						</li>
					</c:when>
				</c:choose>
			</ul>
        </nav>
    </header>

	<!-- container -->
	<section class="wrapper major-pad">
		<div class="inner">
			<div class="table-wrapper">
				<table>
					<thead>
						<tr>
							<th>작성자</th>
							<th>제목</th>
							<th>공지일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="nL" items="${noticeList}">
							<tr>
								<td>${nL.writerId}</td>
								<td><a href="show?id=${nL.id}">${nL.title}</a></td>
								<td>${nL.regDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="center_position">
					<ul class="pagination">
						<c:if test="${pageNum>1}">
						<li><a href="showList?pageNumStr=${pageNum-1}" class="button">Prev</a></li>
						</c:if>
						<c:if test="${pageNum<=1}">
						<li><span class="button disabled">Prev</span></li>
						</c:if>
						<c:forEach var="pN" items="${pageNumList}" end="4">
							<c:choose>
								<c:when test="${pN != pageNum}">
									<li><a href="showList?pageNumStr=${pN}" class="page">${pN}</a></li>
								</c:when>
								<c:when test="${pN == pageNum}">
									<li><a href="showList?pageNumStr=${pN}" class="page active">${pN}</a></li>
								</c:when>
							</c:choose>
						</c:forEach>
						<c:if test="${pages>pageNum}">
						<li><a href="showList?pageNumStr=${pageNum+1}" class="button">Next</a></li>
						</c:if>
						<c:if test="${pages<=pageNum}">
						<li><span class="button disabled">Next</span></li>
						</c:if>
					</ul>
				</div>
				<c:if test="${sessionScope.loginMember.id>=1&&sessionScope.loginMember.id<=20}">
					<button class="right_position" id="btnInsert">공지 등록</button>			
				</c:if>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer id="footer">
		<div class="inner">
			<div class="aboutUsSub">
				<strong>단어장<br /></strong>
				<p>단어장을 만들어 사용하세요.</p>
			</div>
			<p class="copyright">&copy; Untitled eunji yoonseon hani. All
				rights reserved.</p>
			<ul class="menu">
				<li><a href="#">이용약관</a></li>
				<li><a href="#">사이트 정책</a></li>
			</ul>
		</div>
	</footer>

	<!-- Scripts -->
	<script src="/js/jquery.min.js"></script>
	<script src="/js/jquery.scrollex.min.js"></script>
	<script src="/js/jquery.dropotron.min.js"></script>
	<script src="/js/browser.min.js"></script>
	<script src="/js/breakpoints.min.js"></script>
	<script src="/js/util.js"></script>
	<script src="/js/main.js"></script>

</body>

</html>