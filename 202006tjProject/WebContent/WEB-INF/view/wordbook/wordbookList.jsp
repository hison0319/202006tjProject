<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>

<head>
<title>wordbookList</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<link rel="stylesheet"
	href="/css/main_wordbookList.css?v=<%=System.currentTimeMillis()%>" />
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
			<a href="form">
				<button class="postWirtingBtn icon fas fa-pencil-alt"
					id="addWordbook" aria-label="단어장 만들기" data-tip="단어장 만들기">
					단어장 만들기</button>
			</a>
			<div class="postTool">

				<form method="get" action="showlistSearch" name="searchfrm"
					class="search">
					<input type="text" class="inputVal" id="searchKeyword"
						name="keyword" placeholder="검색어를 입력" />
					<button class="searchBtn fas fa-search" aria-label="검색"
						data-tip="검색"></button>
				</form>

				<div class="array tool">
					<select name="arraySelect" id="arraySelect">
						<c:choose>
							<c:when test="${method ==''}">
								<option value="1" selected="selected">최근수정</option>
							</c:when>
							<c:otherwise>
								<option value="1">최근수정</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${method =='Favorite'}">
								<option value="2" selected="selected">중요단어장</option>
							</c:when>
							<c:otherwise>
								<option value="2">중요단어장</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${method =='Owner'}">
								<option value="3" selected="selected">소유단어장</option>
							</c:when>
							<c:otherwise>
								<option value="3">소유단어장</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${method =='Guest'}">
								<option value="4" selected="selected">공유단어장</option>
							</c:when>
							<c:otherwise>
								<option value="4">공유단어장</option>
							</c:otherwise>
						</c:choose>
						<!-- 
						<option value="1" selected="selected">최근수정</option>
						<option value="2" >중요단어장</option>
						<option value="3" >소유단어장</option>
						<option value="4" >공유단어장</option> -->
					</select>
				</div>
			</div>
			<c:choose>
				<c:when test="${loginPlease != null }">
					<div>${loginPlease }</div>
				</c:when>
				<c:when test="${certifyPlease != null }">
					<div>${certifyPlease }</div>
				</c:when>
				<c:when test="${listNull != '' }">
					<h2>${listNull }</h2>
				</c:when>
				<c:otherwise>
					<div class="posts">
						<c:forEach items="${list }" var="l" varStatus="i">
							<section class="post">
								<div class="content">
									<form
										action="showlist${method }?pageNumStr=${pageNum-1}&keyword=${keyword}"
										method="post">
										<div style="height: 50px;">
											<a href="#">소유자 :&nbsp;<span class="postId">${l.memberId }</span></a>
											<div class="postDate" style="height: 50px;">
												&nbsp;&nbsp;&nbsp;수정일&nbsp;:&nbsp;${l.uDateStr }</div>
											<input type="hidden" name="id" value="${l.id }" />
										</div>
										<ul>
											<li
												style="font-size: 1.2em; font-weight: bold; height: 50px;">
												<a href="../word/showlist?wordbookid=${l.id }" class="title">${l.title }</a>
											</li>
											<li style="height: 50px;"><c:choose>
													<c:when test="${l.favorite==0 }">
														중요&nbsp;<button class="favorite"
															style="box-shadow: none; border: none;">
															<span class="icon fas fa-star"
																style="font-size: 1.5em; color: #ccc"></span>
														</button>
													</c:when>
													<c:otherwise>
														중요&nbsp;<button class="favorite"
															style="box-shadow: none; border: none;">
															<span class="icon fas fa-star"
																style="font-size: 1.5em; color: #cc0"></span>
														</button>
													</c:otherwise>
												</c:choose></li>
											<li style="height: 80px;"><c:choose>
													<c:when test="${l.ownerId == sessionScope.loginMember.id }">
														<button class="showListSharing"
															style="box-shadow: none; border: none;">
															공유목록 <span class="icon fas fa-clipboard-list"
																style="font-size: 1.8em;"></span>
														</button>
														<button class="getkeySharing"
															style="box-shadow: none; border: none;">
															키복사 <span class="icon fas fa-key"
																style="font-size: 1.5em;"></span>
														</button>
														<button class="kakaoSharing"
															style="box-shadow: none; border: none;">
															카카오톡으로 키전송 <span class="icon far fa-share-alt"
																style="font-size: 1.5em;"></span>
														</button>
														<!-- form에 동일 한 index를 주기위해 skip으로 elements만 생성 -->
														<button class="deleteSharing skip"
															style="box-shadow: none; border: none;"></button>
													</c:when>
													<c:otherwise>
														<!-- form에 동일 한 index를 주기위해 skip으로 elements만 생성 -->
														<button class="showListSharing skip"
															style="box-shadow: none; border: none;"></button>
														<button class="getkeySharing skip"
															style="box-shadow: none; border: none;"></button>
														<button class="kakaoSharing skip"
															style="box-shadow: none; border: none;"></button>
														<button class="deleteSharing"
															style="box-shadow: none; border: none;">
															공유 취소 <span class="icon fas fa-trash-alt"
																style="font-size: 1.5em;"></span>
														</button>
													</c:otherwise>
												</c:choose></li>
										</ul>
									</form>
								</div>
							</section>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
			<input type="text" id="tempKey"
				style="position: absolute; top: 0; left: 0; width: 1px; height: 1px; margin: 0; padding: 0; border: 0;" />
		</div>
		<div class="center_position">
			<ul class="pagination">
				<c:if test="${pageNum>1}">
					<li><a
						href="showlist${method }?pageNumStr=${pageNum-1}&keyword=${keyword}"
						class="button">Prev</a></li>
				</c:if>
				<c:if test="${pageNum<=1}">
					<li><span class="button disabled">Prev</span></li>
				</c:if>
				<c:forEach var="pN" items="${pageNumList}" end="4">
					<c:choose>
						<c:when test="${pN != pageNum}">
							<li><a
								href="showlist${method }?pageNumStr=${pN}&keyword=${keyword}"
								class="page">${pN}</a></li>
						</c:when>
						<c:when test="${pN == pageNum}">
							<li><a
								href="showlist${method }?pageNumStr=${pN}&keyword=${keyword}"
								class="page active">${pN}</a></li>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:if test="${pages>pageNum}">
					<li><a
						href="showlist${method }?pageNumStr=${pageNum+1}&keyword=${keyword}"
						class="button">Next</a></li>
				</c:if>
				<c:if test="${pages<=pageNum}">
					<li><span class="button disabled">Next</span></li>
				</c:if>
			</ul>
		</div>
	</section>

	<section class="wrapper major-pad">
		<div class="inner">
			<div>혹시 문제 생길까봐 나둡니다!</div>
			<c:choose>
				<c:when test="${loginPlease != null }">
					<div>${loginPlease }</div>
				</c:when>
				<c:when test="${certifyPlease != null }">
					<div>${certifyPlease }</div>
				</c:when>
				<c:otherwise>
					<ol>
						<c:forEach items="${list }" var="l" varStatus="i">
							<li>
								<form action="showlist" method="post">
									<input type="hidden" id="favorite${i.index }" name="id"
										value="${l.id }" />
									<c:choose>
										<c:when test="${l.favorite==0 }">
											<button class="favorite${i.index }">ㅡㅅㅡ</button>
										</c:when>
										<c:otherwise>
											<button class="favorite${i.index }">ㅇㅅㅇ</button>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${l.guestId==0 }">
											<button class="sharing${i.index }">공유하기</button>
										</c:when>
										<c:otherwise>
											<button class="getkey${i.index }">키 복사</button>
											<button class="sharing${i.index }">공유 끝</button>
										</c:otherwise>
									</c:choose>
									<a href="../word/showlist?wordbookid=${l.id }">${l.title }</a>
								</form>
							</li>
						</c:forEach>
					</ol>
				</c:otherwise>
			</c:choose>
		</div>
		<hr />

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
	<script src="/js/wordbook/favorite.js"></script>
	<script src="/js/wordbook/sharing.js?v=<%=System.currentTimeMillis()%>"></script>
	<script src="/js/wordbook/wordbookList.js"></script>

</body>

</html>