<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE HTML>

<html>

<head>
    <title>noticeForm</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="robots" content="noindex, nofollow" />
    <meta name="keywords" content="단어장" />
    <meta name="description" content="basic" />
    <meta name="author" content="HaniSon" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <script src="/js/notice/noticeInsertForm.js"></script>    
    <!-- include libraries(jQuery, bootstrap) -->
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script src="/js/footer.js"></script>
	<!-- include summernote css/js -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
	
    <link rel="stylesheet" href="/css/main_posting.css" />
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
							<a href="${pageContext.request.contextPath}/login/form" class="button primary">LogIn</a>
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
            <div class="posting">
                <div class="textWindow">
                    <form action="insert" name="noticeForm" method="POST">
                        <div class="col-6 col-12-xsmall">
                            <input type="hidden" name="writerId" value="${sessionScope.loginMember.id }"  readonly/>
                            <input type="text" name="memberId" value="${sessionScope.loginMember.memberId }"  readonly/>
                            <input type="text" name="title" placeholder="+ 제목 (단어)" maxlength="100" />
                        </div>
                        <div class="col-12">
                        	<textarea id="summernote" name="contents"></textarea>
                        </div>
                    </form>
                </div>
                <div class="conclusion">
                	<button class="button primary" aria-label="게시하기" data-tip="게시하기" onclick="sumbmitCheck()">게시하기</button>
                	<button class="button" aria-label="전부삭제" data-tip="전부삭제">내용 삭제</button>
                </div>
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
				<li><button type="button" class="snslogo kakao" onclick="kakaoBtn()">kakao</button></li>
				<li><button type="button" class="snslogo twitter" onclick="twitterBtn()">twitter</button></li>
				<li><button type="button" class="snslogo facebook" onclick="facebookBtn()">facebook</button></li><!-- 계정이 없어 미확인 -->
				<li><button type="button" class="snslogo naver" onclick="naverBtn()">naver</button></li>
			</ul>
		</div>
	</footer>

    <!-- Scripts -->
	<!-- <script src="/js/jquery.min.js"></script> -->
    <script src="/js/jquery.scrollex.min.js"></script>
    <script src="/js/jquery.dropotron.min.js"></script>
    <script src="/js/browser.min.js"></script>
    <script src="/js/breakpoints.min.js"></script>
    <script src="/js/util.js"></script>
    <script src="/js/main.js"></script>
	  <script>
	    $(document).ready(function() {
	        $('#summernote').summernote();
	    });
	  </script>
</body>

</html>