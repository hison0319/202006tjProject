<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE HTML>

<html>

<head>
<title>certification</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/css/main.css" />
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="/js/account/certificationForm.js"></script>
<script src="/js/footer.js"></script>
</head>

<body class="is-preload">

	<!-- Header -->
	<header id="header">
        <nav id="nav">
            <ul>
				<li><a href="${pageContext.request.contextPath}/">홈</a></li>
				<li><a href="${pageContext.request.contextPath}/wordbook/showlist">단어장</a></li>
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
			<div class="certify">certification</div>
			<br>
			<h3 class="message">인증번호를 전송해주세요!</h3>
			<form>
				<div class="row gtr-uniform">
					<div class="col-6 col-12-xsmall">
						<div class="certify">
							<input type="text" name="email" value="${sessionScope.loginMember.email}" readonly="readonly">
						</div>
						<br>
						<div class="certify_sub">
							<input type="submit" value="인증번호 전송" class="primary">
						</div>
						<br>
					</div>
				</div>
			</form>
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
				<li><button type="button" class="snslogo kakao" onclick="kakaoBtn()">kakao</button></li>
				<li><button type="button" class="snslogo twitter" onclick="twitterBtn()">twitter</button></li>
				<li><button type="button" class="snslogo facebook" onclick="facebookBtn()">facebook</button></li><!-- 계정이 없어 미확인 -->
				<li><button type="button" class="snslogo naver" onclick="naverBtn()">naver</button></li>
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