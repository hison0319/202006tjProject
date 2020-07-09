<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>

<head>
<title>accountInfo</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<script src="/js/account/info.js"></script>
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
	<section class="wrapper style">
		<div class="inner">
			<div class="col-6 col-12-medium">
				<h5 class="alt">Alternate</h5>
				<ul class="alt">
					<li><a href="${pageContext.request.contextPath}/account/showInfo"><b>내 정보 보기</b></a></li>
					<li><a href="${pageContext.request.contextPath}/account/showSharingList">공유목록 보기</a></li>
					<li><button type="button" class="button" onclick="btnModify('${sessionScope.loginMember.memberId}')">내 정보
            수정하기</button></li>

				</ul>
			</div>
		</div>
	</section>

	<section class="wrapper major-pad">
		<div class="inner">
			<ul class="bulleted-icons">
				<li><span class="icon-wrapper"><span
						class="icon solid fa-user"></span></span>
					<h3>ID</h3>
					<p>${sessionScope.loginMember.memberId}</p></li>
				<li><span class="icon-wrapper"><span
						class="icon solid fa-envelope"></span></span>
					<h3>EMAIL</h3>
					<p>${sessionScope.loginMember.email}</p></li>
				<li><span class="icon-wrapper"><span
						class="icon solid fa-phone"></span></span>
					<h3>Phone</h3>
					<p>${sessionScope.loginMember.phone}</p></li>
				<li><span class="icon-wrapper"><span
						class="icon solid fa-home"></span></span>
					<h3>ADDRESS</h3>
					<p>${sessionScope.loginMember.address}</p></li>
				<li><span class="icon-wrapper"><span
						class="icon solid fa-calendar-check"></span></span>
					<h3>REGDATE</h3>
					<p>${sessionScope.loginMember.regDate}</p></li>
				<li><span class="icon-wrapper"><span
						class="icon solid fa-id-card"></span></span>
					<h3>CERTIFIED</h3>
					<p>
						<c:choose>
							<c:when test="${sessionScope.loginMember.certified == 1}">CERTIFIED</c:when>
							<c:when test="${sessionScope.loginMember.certified == 0}">
						NOT CERTIFIED
						<button type="button" class="button" onclick="btnCertified()">Email
									인증하기</button>
							</c:when>
						</c:choose>
					</p></li>
			</ul>
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