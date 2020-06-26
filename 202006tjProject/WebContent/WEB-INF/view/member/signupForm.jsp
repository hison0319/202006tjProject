<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>

<head>
    <title>signupForm</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="robots" content="noindex, nofollow" />
    <meta name="keywords" content="단어장" />
    <meta name="description" content="basic" />
    <meta name="author" content="HaniSon" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="/css/signup.css" />
</head>

<body class="is-preload">

    <!-- Header -->
    <header id="header">
        <nav id="nav">
            <ul>
                <li><a href="index.html">홈</a></li>
                <li>
                    <a href="encyclopedia.html" Tabindex="2">단어장</a>
                </li>
            </ul>
        </nav>
    </header>
    
    <!-- container -->
    <section class="wrapper major-pad">
        <div class="inner">
        	<div class="signup">회원 가입</div>
        	<br>
			<form method="post" action="#">
				<div class="row gtr-uniform">
					<div class="col-6 col-12-xsmall">
						<div class="signup_id">아이디
						<br>
							<input type="text" name="demo-id" id="demo-id" value="" maxlength="15" placeholder="띄어쓰기 없이 영/숫자 6-10자">
							<!-- 중복확인 -->
						</div>
						<br>
						
						<div class="signup_pw">비밀번호
						<br>
							<input type="password" name="demo-pw" id="demo-pw" value="" maxlength="15" placeholder="6~15자의 영문 대소문자, 숫자 및 특수문자 조합">
						</div>
						<br>
						
						<div class="check_pw">비밀번호 확인
						<br>
							<input type="password" name="demo-pw" id="demo-pw" value="" placeholder="위의 비밀번호를 다시 입력해주세요.">
						</div>
						<br>
						
						<div class="signup_email">이메일
						<br>
							<input type="email" name="demo-email" id="demo-email" value="" placeholder="">@
							<!-- 직접입력 -->
							<!-- 보기(네이버...)-->
						</div>
						<br>
						
						<div class="signup_phone">휴대폰 번호
						<br>
							<input type="text" name="demo-phone" id="demo-phone" value="" placeholder="-없이 숫자만 입력해주세요.">
						</div>
						<br>
						
						<div class="signup_addr">주소
						<br>
							<input type="text" name="demo-addr" id="demo-addr" value="" placeholder="">
						</div>
						<br>
						
						<div class="signup_sub">
							<input type="submit" value="회원가입" class="primary">
						</div>
						
						<div class="signup_reset">
							<input type="reset" value="처음으로">
						</div>
					</div>
				</div>
			</form>
            <div>유효성 검사</div>
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