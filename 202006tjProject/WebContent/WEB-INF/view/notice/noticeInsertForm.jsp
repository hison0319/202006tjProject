<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="/css/main_posting.css" />
</head>

<body class="is-preload">

    <!-- Header -->
    <header id="header">
        <nav id="nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">홈</a></li>
                <li>
                    <a href="#">단어장</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/notice/showList">공지사항</a>
                </li>
                <li>
                	<a href="${pageContext.request.contextPath}/account/showMemberInfo">마이페이지</a>
                </li>
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
                            <input type="text" name="writerId" value="1" placeholder="아이디(셰션연동 자동업로드)" readonly/>
                            <input type="text" name="title" placeholder="+ 제목 (단어)" maxlength="100" />
                        </div>
                        <div class="col-12">
                            <textarea name="contents"placeholder="내용을 입력해주세요." maxlength="1000" rows="6"></textarea>
                        </div>
                    </form>
                </div>

                <div class="writeOption">
                    <button class="icon fas fa-undo" aria-label="실행취소 (Ctrl-Z)" data-tip="실행취소 ‪(Ctrl-Z)‬"></button>
                    <button class="icon fas fa-redo" aria-label="재실행 (Ctrl-Y)" data-tip="재실행 ‪(Ctrl-Y)‬"></button>
                    <select name="textSize" class="textSize" aria-label="크기 ‪(Ctrl-Shift--, Ctrl-Shift-+)‬" data-tip="크기 ‪(Ctrl-Shift--, Ctrl-Shift-+)‬">
                        <option value="">- 글자크기 -</option>
                        <option value="16">16</option>
                        <option value="14">14</option>
                        <option value="12">12</option>
                        <option value="12">10</option>
                        <option value="12">8</option>
                    </select>
                    <button class="icon fas fa-bold" aria-label="굵게 ‪(Ctrl-B)‬" data-tip="굵게 ‪(Ctrl-B)‬"></button>
                    <button class="icon fas fa-italic" aria-label="기울임꼴 ‪(Ctrl-I)‬‬" data-tip="기울임꼴 ‪(Ctrl-I)‬‬"></button>
                    <button class="icon fas fa-underline" aria-label="밑줄 ‪(Ctrl-U)‬‬" data-tip="밑줄 ‪(Ctrl-U)‬"></button>
                    <button name="textArray" class="textArray" aria-label="정렬‬" data-tip="정렬‬">
                        <div class="fas fa-align-left"><span class="skip">왼쪽정렬</span></div>
                        <div class="fas fa-align-center displayNone"><span class="skip">가운데정렬</span></div>
                        <div class="fas fa-align-right displayNone"><span class="skip">오른쪽정렬</span></div>
                    </button>
                </div>
            </div>
            <div class="conclusion">
                <button class="upLoad" aria-label="게시하기" data-tip="게시하기" onclick="sumbmitCheck()">게시하기</button>
                <button class="cancel" aria-label="전부삭제" data-tip="전부삭제">내용 삭제</button>
            </div>
        </div>
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