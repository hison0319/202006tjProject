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
				<li><a href="index.html">홈</a></li>
				<li><a href="encyclopedia.html" Tabindex="2">단어장</a></li>
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
						<li><span class="button disabled">Prev</span></li>
						<li><a href="#" class="page active">1</a></li>
						<li><a href="#" class="page">2</a></li>
						<li><a href="#" class="page">3</a></li>
						<li><span>&hellip;</span></li>
						<li><a href="#" class="page">8</a></li>
						<li><a href="#" class="page">9</a></li>
						<li><a href="#" class="page">10</a></li>
						<li><a href="#" class="button">Next</a></li>
					</ul>
				</div>
				<button class="right_position" id="btnInsert">공지 등록</button>
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