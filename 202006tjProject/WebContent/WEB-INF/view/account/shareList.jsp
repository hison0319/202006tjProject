<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>

<html>

<head>
<title>shareList</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<script src="/js/account/sharingList.js"></script>
<script src="/js/footer.js"></script>
<link rel="stylesheet" href="/css/main.css" />
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
							class="button">LogIn</a></li>
						<li style="white-space: nowrap;"><a
							href="${pageContext.request.contextPath}/signup/form"
							class="button">SignUp</a></li>
					</c:when>
					<c:when test="${sessionScope.loginMember != null}">
						<li><a
							href="${pageContext.request.contextPath}/account/showInfo">마이페이지</a>
						</li>
						<li style="white-space: nowrap;"><a
							href="${pageContext.request.contextPath}/login/logout"
							class="button">LogOut</a></li>
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
					<li><a
						href="${pageContext.request.contextPath}/account/showInfo">내
							정보 보기</a></li>
					<li><a
						href="${pageContext.request.contextPath}/account/showSharingList"><b>공유목록
								보기</b></a></li>
					<li><button type="button" class="button"
							onclick="btnModify('${sessionScope.loginMember.memberId}')">내
							정보 수정하기</button></li>

				</ul>
			</div>
		</div>
	</section>

	<section class="wrapper style">
		<div class="inner">
			<table>
				<thead>
					<tr>
						<th>수정일</th>
						<th>단어장</th>
						<th>공유인원</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="sL" value="${sharingNumlist }"></c:set>
					<c:forEach var="wL" items="${wordbooklist}" varStatus="i">
						<tr class="tr">
							<td>${wL.uDateStr}</td>
							<td><button class="showSharingList"
									style="box-shadow: none; border: none;">${wL.title}</button></td>
							<td>${sL[i.index] }명</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="center_position">
			<ul class="pagination">
				<c:if test="${pageNum>1}">
					<li><a href="showSharingList?pageNumStr=${pageNum-1}"
						class="button">Prev</a></li>
				</c:if>
				<c:if test="${pageNum<=1}">
					<li><span class="button disabled">Prev</span></li>
				</c:if>
				<c:forEach var="pN" items="${pageNumList}" end="4">
					<c:choose>
						<c:when test="${pN != pageNum}">
							<li><a href="showSharingList?pageNumStr=${pN}" class="page">${pN}</a></li>
						</c:when>
						<c:when test="${pN == pageNum}">
							<li><a href="showSharingList?pageNumStr=${pN}"
								class="page active">${pN}</a></li>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:if test="${pages>pageNum}">
					<li><a href="showSharingList?pageNumStr=${pageNum+1}"
						class="button">Next</a></li>
				</c:if>
				<c:if test="${pages<=pageNum}">
					<li><span class="button disabled">Next</span></li>
				</c:if>
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
	<script src="/js/account/showSharingMembers.js"></script>

</body>

</html>