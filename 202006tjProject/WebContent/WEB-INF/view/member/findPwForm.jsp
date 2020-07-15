<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>findIdForm</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<script src="/js/footer.js"></script>
<link rel="stylesheet" href="/css/findIdPw.css" />
</head>

<body class="is-preload">

	<!-- Header -->
	<header id="header">
		<nav id="nav">
			<ul>
				<li><a href="${pageContext.request.contextPath}/">홈</a></li>
				<li><a
					href="${pageContext.request.contextPath}/wordbook/showlist">단어장</a></li>
				<li><a
					href="${pageContext.request.contextPath}/notice/showList">공지사항</a>
				</li>
				<c:choose>
					<c:when test="${sessionScope.loginMember == null}">
						<li style="white-space: nowrap;"><a
							href="${pageContext.request.contextPath}/login/form"
							class="button primary">LogIn</a></li>
						<li style="white-space: nowrap;"><a
							href="${pageContext.request.contextPath}/signup/form"
							class="button">SignUp</a></li>
					</c:when>
					<c:when test="${sessionScope.loginMember != null}">
						<li><a
							href="${pageContext.request.contextPath}/account/showInfo">마이페이지</a>
						</li>
						<li style="white-space: nowrap;"><a href="#" class="button">LogOut</a>
						</li>
					</c:when>
				</c:choose>
			</ul>
		</nav>
	</header>

	<!-- container -->
	<section class="wrapper major-pad">
		<div class="inner">
			<div class="findpw">비밀번호 찾기</div>
			<br>
			<form method="post" action="findingpw">
				<div class="row gtr-uniform">
					<div class="col-6 col-12-xsmall">
						<div class="find_sub">
							<input type="text" id="memberId" name="memberId" value=""
								placeholder="ID"> <br> <input type="email"
								id="email" name="email" value="" placeholder="EMAIL">
						</div>
						<br>
						<div class="find_btn_box actions stacked">
							<ul class="actions stacked">
								<li><input type="submit" style="width: 100%;"
									value="비밀번호 찾기" class="button small fit"></li>
								<li><a
									href="${pageContext.request.contextPath}/findid/form"
									class="button small fit">아이디 찾기로 이동</a></li>
								<li><a href="${pageContext.request.contextPath}/login/form"
									class="button primary fit">로그인으로 이동</a></li>
							</ul>
						</div>
						<br> <span id="foundpw" aria-live="assertive"></span>
					</div>
				</div>
			</form>
		</div>
	</section>

	<section class="wrapper style">
		<div class="inner"></div>
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
	<script src="/js/findpw/findpw.js"></script>

</body>

</html>