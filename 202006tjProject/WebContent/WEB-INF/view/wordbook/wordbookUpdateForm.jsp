<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>

<html>

<head>
    <title>wordbookUpdateForm</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="robots" content="noindex, nofollow" />
    <meta name="keywords" content="단어장" />
    <meta name="description" content="basic" />
    <meta name="author" content="HaniSon" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
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
							<a href="#" class="button">LogOut</a>
						</li>
					</c:when>
				</c:choose>
			</ul>
        </nav>
    </header>
    
    <!-- container -->
    <section class="wrapper major-pad">
        <div class="inner">
			<div>단어장 생성 창</div>
			<div>핵심기능 구현</div>
        </div>
    </section>

    <section class="wrapper style">
        <div class="inner"></div>
    </section>


    <!-- Footer -->
    <footer id="footer">
        <div class="inner">
            <div class="aboutUsSub"><strong>단어장<br /></strong>
                <p>단어장을 만들어 사용하세요.</p>
            </div>
            <p class="copyright">&copy; Untitled eunji yoonseon hani. All rights reserved. </p>
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