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
        	<div class="signup">회원 정보 수정</div>
        	<br>
			<form name="updateForm" action="update" method="post">
				<div class="row gtr-uniform">
					<div class="col-6 col-12-xsmall">
						<input type="hidden" name="id" value="${sessionScope.loginMember.id}" readonly="readonly"/>
						아이디<br>
						<input type="text" name="memberId" value="${sessionScope.loginMember.memberId}" readonly="readonly"/>					
						<br>
						
						<div class="signup_pw">비밀번호
						<br>
							<input type="password" id="password" name="password" value="" min="6" max="15" placeholder="6~15자의 영문 대소문자, 숫자 및 특수문자 조합">
							<i class="fa fa-eye fa-lg"></i>
							<c:if test="${epassword != null}"><p style="color:red">비밀번호 형식에 맞지 않습니다.</p></c:if>
						</div>
						<span class="error_next_box" id="pwMsg" aria-live="assertive"></span>
						<br>
						
						<div class="check_pw">비밀번호 확인
						<br>
							<input type="password" id="passwordCheck" name="passwordConfirm" value="" placeholder="위의 비밀번호를 다시 입력해주세요.">
							<c:if test="${epasswordCheck != null}"><p style="color:red">비밀번호 형식에 맞지 않습니다.</p></c:if>
						</div>
						<span class="error_next_box" id="pwCheckMsg" aria-live="assertive"></span>
						<br>
						
						<div class="signup_email">이메일
						<br>
							<input type="email" id="email" name="email" value="${sessionScope.loginMember.email}" placeholder="">
							<c:if test="${eemail != null}"><p style="color:red">이메일 형식에 맞지 않습니다.</p></c:if>
						</div>
						<span class="error_next_box" id="emailMsg" aria-live="assertive"></span>
						
						<div class="email_check check_box">
							<button type="button" id="email_check" name="emailCheck">이메일 증복 확인</button>
						</div>
						<br>
						
						<div class="signup_phone">휴대폰 번호
						<br>
							<input type="text" id="phone" name="phone" value="${sessionScope.loginMember.phone}" placeholder="숫자만 입력해주세요.">
							<c:if test="${ephone != null}"><p style="color:red">전화번호 형식에 맞지 않습니다.</p></c:if>
						</div>
						<span class="error_next_box" id="phoneMsg" aria-live="assertive"></span>
						
						<div class="phone_check check_box">
							<button type="button" id="phone_check" name="phoneCheck">전화번호 중복 확인</button>
						</div>
						<br>
						
						<div class="signup_addr">
							주소 <br> <input type="hidden" id="address" name="address" class="skip" value="${sessionScope.loginMember.address}"> 
							<input type="text" id="sample4_postcode" placeholder="우편번호"> 
							<div class="address_check check_box">
								<button type="button" id="address_check" onclick="sample4_execDaumPostcode()" value="">우편번호 찾기</button>
							</div>
							<br> 
							<input type="text" id="sample4_roadAddress" placeholder="도로명주소" readonly> 
							<input type="text" id="sample4_jibunAddress" placeholder="지번주소" readonly>
							<input type="text" id="sample4_detailAddress" placeholder="상세주소">
							<input type="text" id="sample4_extraAddress" placeholder="참고항목" readonly>
						</div>
						<script
							src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
						<script>
							//본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
							function sample4_execDaumPostcode() {
								new daum.Postcode(
										{
											oncomplete : function(data) {
												// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

												// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
												// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
												var roadAddr = data.roadAddress; // 도로명 주소 변수
												var extraRoadAddr = ''; // 참고 항목 변수

												// 법정동명이 있을 경우 추가한다. (법정리는 제외)
												// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
												if (data.bname !== ''
														&& /[동|로|가]$/g
																.test(data.bname)) {
													extraRoadAddr += data.bname;
												}
												// 건물명이 있고, 공동주택일 경우 추가한다.
												if (data.buildingName !== ''
														&& data.apartment === 'Y') {
													extraRoadAddr += (extraRoadAddr !== '' ? ', '
															+ data.buildingName
															: data.buildingName);
												}
												// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
												if (extraRoadAddr !== '') {
													extraRoadAddr = ' ('
															+ extraRoadAddr
															+ ')';
												}

												// 우편번호와 주소 정보를 해당 필드에 넣는다.
												document
														.getElementById('sample4_postcode').value = data.zonecode;
												document
														.getElementById("sample4_roadAddress").value = roadAddr;
												document
														.getElementById("sample4_jibunAddress").value = data.jibunAddress;

												// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
												if (roadAddr !== '') {
													document
															.getElementById("sample4_extraAddress").value = extraRoadAddr;
												} else {
													document
															.getElementById("sample4_extraAddress").value = '';
												}

												var guideTextBox = document
														.getElementById("guide");
												// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
												if (data.autoRoadAddress) {
													var expRoadAddr = data.autoRoadAddress
															+ extraRoadAddr;
													guideTextBox.innerHTML = '(예상 도로명 주소 : '
															+ expRoadAddr + ')';
													guideTextBox.style.display = 'block';

												} else if (data.autoJibunAddress) {
													var expJibunAddr = data.autoJibunAddress;
													guideTextBox.innerHTML = '(예상 지번 주소 : '
															+ expJibunAddr
															+ ')';
													guideTextBox.style.display = 'block';
												} else {
													guideTextBox.innerHTML = '';
													guideTextBox.style.display = 'none';
												}
											}
										}).open();
							}
						</script>
						<br>
						
						<div class="signup_sub center_position">
							<ul class="actions stacked">
								<li><input type="submit" value="회원정보 수정" class="button primary"></li>
								<li><button class="button" type="button" onclick="deleteCheck(${sessionScope.loginMember.id})">회원 탈퇴하기</button></li>
							</ul>
						</div>
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