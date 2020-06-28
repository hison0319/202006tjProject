<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>

<head>
<title>accountModify</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/css/signup.css" />

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
        	<div class="update_form">회원 정보 수정</div>
        	<br>
			<form name="updateForm" action="update" method="post">
				<div class="row gtr-uniform">
						<input type="text" name="id" value="${sessionScope.loginMember.id}" class="skip" readonly="readonly"/>
						<input type="text" name="memberId" value="${sessionScope.loginMember.memberId}" class="skip" readonly="readonly"/>					
						<div class="signup_pw">비밀번호
						<br>
							<input type="password" id="password" name="password" value="" maxlength="15" placeholder="6~15자의 영문 대소문자, 숫자 및 특수문자 조합">
							<c:if test="${epassword != null}"><p style="color:red">비밀번호 형식에 맞지 않습니다.</p></c:if>
						</div>
						<br>
						
						<div class="check_pw">비밀번호 확인
						<br>
							<input type="password" id="passwordCheck" name="passwordConfirm" value="" placeholder="위의 비밀번호를 다시 입력해주세요.">
						</div>
						<br>
						
						<div class="signup_email">이메일
						<br>
							<input type="email" id="email" name="email" value="${sessionScope.loginMember.email}" placeholder="">
							<c:if test="${eemail != null}"><p style="color:red">이메일 형식에 맞지 않습니다.</p></c:if>
						</div>
						<br>
						
						<div class="email_check">
						<br>
							<button type="button" id="email_check" name="emailCheck">이메일 가능 확인</button>
						</div>
						<br>
						
						<div class="signup_phone">휴대폰 번호
						<br>
							<input type="text" id="phone" name="phone" value="${sessionScope.loginMember.phone}" placeholder="숫자만 입력해주세요.">
							<c:if test="${ephone != null}"><p style="color:red">전화번호 형식에 맞지 않습니다.</p></c:if>
						</div>
						<br>
						
						<div class="phone_check">
						<br>
							<button type="button" id="phone_check" name="phoneCheck">전화번호 가능 확인</button>
						</div>
						<br>
						
						<div class="signup_addr">주소
						<br>
							<input type="text" id="address" name="address" value="${sessionScope.loginMember.address}" placeholder="">
						</div>
						<br>
						
						<div class="signup_sub">
							<input type="submit" value="회원정보 수정" class="primary">
						</div>
						
						<div class="signup_reset">
							<input type="reset" value="처음으로">
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
	<script src="/js/account/modify.js"></script>

</body>

</html>