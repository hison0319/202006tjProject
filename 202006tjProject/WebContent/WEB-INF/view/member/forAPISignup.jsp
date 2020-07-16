<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>

<html>

<head>
<title>ForApiSignup</title>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="robots" content="noindex, nofollow" />
<meta name="keywords" content="단어장" />
<meta name="description" content="basic" />
<meta name="author" content="HaniSon" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="/js/footer.js"></script>
<link rel="stylesheet" href="/css/signup.css" />
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
	<section class="wrapper major-pad">
		<div class="inner">
			<div class="signup">카카오로 회원 가입</div>
			<br>
			<form name="kakaoSignupForm" action="forAPISignup" method="post">
				<div class="row gtr-uniform">
					<div class="col-6 col-12-xsmall">
						<input type="hidden" name="realId" value="${realId}" class="skip"
							readonly="readonly" /> 이름 <br> <input type="text"
							name="nickName" value="${nickName}" readonly="readonly" /> <input
							type="hidden" class="skip" name="memberId" value="tempId1234"
							readonly="readonly" /> <input type="hidden" class="skip"
							name="forAPIPassword" value="${forAPIPassword}" maxlength="15">
						<br>

						<div class="signup_email">
							이메일 <br> <input type="email" id="email" name="email"
								value="${member.email}" required>
							<c:if test="${eemail != null}">
								<p style="color: red">이메일 형식에 맞지 않습니다.</p>
							</c:if>
						</div>
						<span class="error_next_box" id="emailMsg" aria-live="assertive"></span>

						<div class="email_check check_box">
							<button type="button" id="email_check" name="emailCheck">이메일
								중복 확인</button>
						</div>
						<br>

						<div class="signup_phone">
							휴대폰 번호 <br> <input type="text" id="phone" name="phone"
								value="${member.phone}" placeholder="- 제외 숫자만 입력해주세요." required>
							<c:if test="${ephone != null}">
								<p style="color: red">전화번호 형식에 맞지 않습니다.</p>
							</c:if>
						</div>
						<span class="error_next_box" id="phoneMsg" aria-live="assertive"></span>

						<div class="phone_check check_box">
							<button type="button" id="phone_check" name="phoneCheck">전화번호
								중복 확인</button>
						</div>
						<br>

						<div class="signup_addr">
							주소 <br> <input type="hidden" id="address" name="address"
								class="skip" value="${member.address}"> <input
								type="text" id="sample4_postcode" placeholder="우편번호">
							<div class="check_box">
								<button type="button" onclick="sample4_execDaumPostcode()"
									value="">우편번호 찾기</button>
							</div>
							<br> <input type="text" id="sample4_roadAddress"
								placeholder="도로명주소"> <input type="text"
								id="sample4_jibunAddress" placeholder="지번주소"> <input
								type="text" id="sample4_detailAddress" placeholder="상세주소">
							<input type="text" id="sample4_extraAddress" placeholder="참고항목">
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

						<div class="checkbox_group">
							<ul class="join_box">
								<li class="checkBox">
									<ul class="clearfix">
										<li>이용약관, 개인정보 수집 및 이용에 모두 동의합니다.</li>
										<li class="checkAllBtn"><input type="checkbox"
											id="check_all"></li>
									</ul>
								</li>

								<li class="checkBox"><textarea name="" id="">
제 1 조 (목적)

이 약관은 네이버 주식회사 ("회사" 또는 "네이버")가 제공하는 네이버 및 네이버 관련 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.


제 2 조 (정의)

이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
①"서비스"라 함은 구현되는 단말기(PC, TV, 휴대형단말기 등의 각종 유무선 장치를 포함)와 상관없이 "회원"이 이용할 수 있는 네이버 및 네이버 관련 제반 서비스를 의미합니다.
②"회원"이라 함은 회사의 "서비스"에 접속하여 이 약관에 따라 "회사"와 이용계약을 체결하고 "회사"가 제공하는 "서비스"를 이용하는 고객을 말합니다.
③"아이디(ID)"라 함은 "회원"의 식별과 "서비스" 이용을 위하여 "회원"이 정하고 "회사"가 승인하는 문자와 숫자의 조합을 의미합니다.
④"비밀번호"라 함은 "회원"이 부여 받은 "아이디와 일치되는 "회원"임을 확인하고 비밀보호를 위해 "회원" 자신이 정한 문자 또는 숫자의 조합을 의미합니다.
⑤"유료서비스"라 함은 "회사"가 유료로 제공하는 각종 온라인디지털콘텐츠(각종 정보콘텐츠, VOD, 아이템 기타 유료콘텐츠를 포함) 및 제반 서비스를 의미합니다.
⑥"포인트"라 함은 서비스의 효율적 이용을 위해 회사가 임의로 책정 또는 지급, 조정할 수 있는 재산적 가치가 없는 "서비스" 상의 가상 데이터를 의미합니다.
⑦"게시물"이라 함은 "회원"이 "서비스"를 이용함에 있어 "서비스상"에 게시한 부호ㆍ문자ㆍ음성ㆍ음향ㆍ화상ㆍ동영상 등의 정보 형태의 글, 사진, 동영상 및 각종 파일과 링크 등을 의미합니다. 
      			 </textarea>
									<ul class="clearfix">
										<li>워드북 이용 약관 동의(필수)</li>
										<li class="checkBtn"><input type="checkbox" id="check_1"
											class="normal"></li>
									</ul></li>

								<li class="checkBox"><textarea name="" id="">
정보통신망법 규정에 따라 네이버에 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.


1. 수집하는 개인정보

이용자는 회원가입을 하지 않아도 정보 검색, 뉴스 보기 등 대부분의 네이버 서비스를 회원과 동일하게 이용할 수 있습니다. 이용자가 메일, 캘린더, 카페, 블로그 등과 같이 개인화 혹은 회원제 서비스를 이용하기 위해 회원가입을 할 경우, 네이버는 서비스 이용을 위해 필요한 최소한의 개인정보를 수집합니다.
       </textarea>
									<ul class="clearfix">
										<li>개인정보 수집 및 이용에 대한 안내(필수)</li>
										<li class="checkBtn"><input type="checkbox" id="check_2"
											class="normal"></li>
									</ul></li>
							</ul>
							<div class="signup_btn_box actions stacked">
								<input type="submit" style="width: 100%;" value="회원가입"
									class="button primary fit">
							</div>
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
				<li><button type="button" class="snslogo kakao"
						onclick="kakaoBtn()">kakao</button></li>
				<li><button type="button" class="snslogo twitter"
						onclick="twitterBtn()">twitter</button></li>
				<li><button type="button" class="snslogo facebook"
						onclick="facebookBtn()">facebook</button></li>
				<!-- 계정이 없어 미확인 -->
				<li><button type="button" class="snslogo naver"
						onclick="naverBtn()">naver</button></li>
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
	<script src="/js/signup/forAPISignup.js"></script>

</body>

</html>