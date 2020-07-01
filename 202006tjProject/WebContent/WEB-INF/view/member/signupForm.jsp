<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/css/signup.css" />
<script src="/js/signup/signup.js"></script>
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
						<li style="white-space: nowrap;"><a
							href="${pageContext.request.contextPath}/login/form"
							class="button primary">LogIn</a></li>
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
			<div class="signup">회원 가입</div>
			<br>
			<form name="signupForm" action="insert" method="post">
				<div class="row gtr-uniform">
					<div class="col-6 col-12-xsmall">
						<div class="signup_id">
							아이디 <br> <input type="text" id="memberId" name="memberId"
								value="${member.memberId}" min="6" max="15"
								placeholder="띄어쓰기 없이 영/숫자 6-15자">
							<c:if test="${ememberId != null}">
								<p style="color: red">아이디 형식에 맞지 않습니다.</p>
							</c:if>
						</div>
						<span class="error_next_box" id="idMsg" aria-live="assertive"></span>

						<div class="id_check check_box">
							<button type="button" id="id_check" name="memberIdCheck">아이디 중복 확인</button>
						</div>
						<br>

						<div class="signup_pw">
							비밀번호 <br> <input type="password" id="password" name="password" 
							value="" min="6" max="15" placeholder="6~15자의 영문 대소문자, 숫자 및 특수문자 조합">
							<c:if test="${epassword != null}">
								<p style="color: red">비밀번호 형식에 맞지 않습니다.</p>
							</c:if>
						</div>
						<span class="error_next_box" id="pwMsg" aria-live="assertive"></span>
						<br>

						<div class="check_pw">
							비밀번호 확인 <br> <input type="password" id="passwordCheck"
								name="passwordConfirm" value="" placeholder="위의 비밀번호를 다시 입력해주세요.">
						</div>
						<span class="error_next_box" id="pwCheckMsg" aria-live="assertive"></span>
						<br>

						<div class="signup_email">
							이메일 <br> <input type="email" id="email" name="email"
								value="${member.email}">
							<c:if test="${eemail != null}">
								<p style="color: red">이메일 형식에 맞지 않습니다.</p>
							</c:if>
						</div>
						<span class="error_next_box" id="emailMsg" aria-live="assertive"></span>

						<div class="email_check check_box">
							<button type="button" id="email_check" name="emailCheck">이메일 중복 확인</button>
						</div>
						<br>

						<div class="signup_phone">
							휴대폰 번호 <br> <input type="text" id="phone" name="phone" 
								value="${member.phone}" placeholder="- 제외 숫자만 입력해주세요.">
							<c:if test="${ephone != null}">
								<p style="color: red">전화번호 형식에 맞지 않습니다.</p>
							</c:if>
						</div>
						<span class="error_next_box" id="phoneMsg" aria-live="assertive"></span>

						<div class="phone_check check_box">
							<button type="button" id="phone_check" name="phoneCheck">전화번호 중복 확인</button>
						</div>
						<br>

						<br>
						<div class="signup_addr">
							주소 <br> <input type="text" id="address" name="address" class="skip" value="${member.address}"> 
							<input type="text" id="sample4_postcode" placeholder="우편번호"> 
							<div class="check_box">
								<button type="button" onclick="sample4_execDaumPostcode()" value="">우편번호 찾기</button>
							</div>
							<br> 
							<input type="text" id="sample4_roadAddress" placeholder="도로명주소"> 
							<input type="text" id="sample4_jibunAddress" placeholder="지번주소">
							<input type="text" id="sample4_detailAddress" placeholder="상세주소">
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
						
						   <article id="content" class="cols-d">
                  <div>
                     <h1>이용약관</h1>

                     <h4 class="scheme-g">●이용약관 동의</h4>
                     (필수동의)
                     <textarea readonly>
제1조 (목적)
이 약관은 워드북(이하"회사"라 표기합니다)가 제공하는 사이버 회원에 대한 서비스 제공 및 이용조건, 회원가입에 관련된 사항 및 절차 그리고 기타 필요한 사항의 규정을 목적으로 합니다.
제2조 (약관의 효력)
1. 이 약관은 전기통신사업법 제31조, 동법 시행규칙 제21조의 2에 따라 공시절차를 거친 후 서비스 화면에 게시하거나 전자우편, 기타의 방법으로 회원에게 통지함 으로써 효력을 발생합니다.
2. 회사는 이 약관을 변경할 수 있으며, 변경된 약관은 변경된 사항에 대하여 공지 또는 통지함으로써 효력을 발생합니다.
제3조 (약관 이외의 준칙)
이 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신 사업법 및 기타 관련법령에 규정되어있는 경우 그 규정에 따릅니다.
제4조 (용어의 정의)
이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
1. 워드북 사이버회원(이하"회원"이라 표기합니다) 워드북 홈페이지를 통해 사이버 회원에 회원등록 하신 고객
2. 아이디(ID) 회원 식별과 회원의 서비스 이용을 위하여 회원이 선정하고 회사가 승인하는 문자와 숫자의 조합
3. 비밀번호 회원의 비밀보호를 위하여 회원 자신이 설정한 문자와 숫자의 조합
4. 해지 회사 또는 회원이 서비스 이용 후 이용계약을 종료시키는 의사표시
제5조 (회원가입 절차, 회사의 승낙 및 이용계약의 성립)
1. 회원가입 신청은 온라인으로 다음사항(필수 및 선택사항)을 가입신청 양식에 의거, 기록하여 신청합니다.
  - 이름

  - 아이디(ID)

  - 비밀번호

  - 이메일

  - 전화번호

  - 주소 등

2. 회사의 승낙
회사는 회원이 제1항에서 정한 필수사항을 정확히 기재하여 이용신청을 하였을 때 승낙합니다. 회사는 다음 각 호의 1에 해당하는 이용신청에 대하여는 승낙을 유보할 수 있습니다.
  -설비에 여유가 없는 경우

  -기술상 지장이 있는 경우

기타 회사가 필요하다고 인정되는 경우 회사는 다음 각 호의 1에 해당하는 이용신청에 대하여는 이를 승낙하지 않거나 승낙을 취소할 수 있습니다.
  -이름이 실명이 아닌 경우

  -다른 사람의 명의를 사용하여 신청한 경우

  -이용 신청 시 필요내용을 허위로 기재하여 신청한 경우

  -사회의 안녕질서 또는 미풍양속을 저해할 목적으로 신청한 경우

  -기타 회사가 정한 이용신청 요건이 미비 되었을 경우

3. 이용자가 회원가입 절차를 걸쳐 "회원가입" 단추를 누르면 이 약관에 동의하는 것으로 간주됩니다.
제6조 (회원 구분)
워드북 홈페이지에서 구분하는 회원은 다음과 같습니다.
1. 정회원 별솔호텔리조트 콘도 회원.
2. 사이버회원 워드북 홈페이지를 통해 사이버 회원에 회원등록 하신 고객
제7조 (서비스의 내용)
회원에게 워드북의 제반시설 이용 시 이메일을 활용한 정보제공 및 맞춤 서비스 등의 서비스를 제공하며 구체적 서비스내용은 별도로 홈페이지에 게시합니다.
제8조 (회사의 의무)
1. 회사는 서비스제공과 관련해서 알고 있는 회원의 신상정보를 본인의 승낙없이 제 3자에게 누설, 배포하지 않습니다. 단, 전기통신기본법 등 법률의 규정에 의해 국가기관의 요구가 있는 경우, 범죄에 대한 수사상의 목적이 있거나 정보통신윤리위원회의 요청이 있는 경우 또는 기타 관계법령에서 정한 절차에 따른 요청이 있는 경우에는 그러하지 않습니다.
2. 회사는 개인정보와 관련하여 책임자를 임명,운영합니다.
3. 개인이 제공한 개인정보에 대하여, 회사는 제공한 정보를 1년간 보유하는 것을 원칙으로 하며, 개인이 특별히 이의제기를 하지않는 한 자동적으로 1년씩 연장 되는 것으로 합니다.
제9조 (회원 아이디(ID)와 비밀번호 관리에 관한 의무)
아이디(ID)와 비밀번호에 관한 모든 관리책임은 회원에게 있습니다. 회원에게 부여된 아이디(ID)와 비밀번호의 관리소홀, 부정사용에 의하여 발생하는 모든 결과에 대한 책임은 회원에게 있습니다. 자신의 아이디(ID)가 부정하게 사용된 경우, 회원은 반드시 회사에 그 사실을 통보해야 합니다.
제10조 (계약사항의 변경)
회원은 이용 신청 시 기재한 사항이 변경되었을 경우에는 온라인 수정을 해야 합니다.
제11조 (회원의 의무)
1. 회원은 관계법령, 이 약관의 규정, 이용안내 및 주의사항 등 회사가 통지하는 사항을 준수 하여야 하며, 기타 회사의 업무에 방해되는 행위를 해서는 안됩니다.
2. 회원은 회사의 사전 동의 없이 서비스를 이용하여 어떠한 영리 행위도 할 수 없습니다.
3. 회원은 서비스를 이용하여 얻은 정보를 회사의 사전 동의 없이 복사, 복제, 변경, 번역, 출판, 방송, 기타의 방법으로 사용하거나 이를 타인에게 제공할 수 없습니다.
4. 회원은 서비스이용과 관련하여 다음 각 호의 행위를 해서는 안됩니다.
  -다른 회원의 아이디(ID)를 부정 사용하는 경우
  
  -범죄행위를 목적으로 하거나 기타 범죄행위와 관련된 행위

  -타인의 명예를 훼손하거나 모욕하는 행위

  -타인의 지적 재산권 등의 권리를 침해하는 행위

  -해킹행위 또는 컴퓨터 바이러스의 유포행위

  -타인의 의사에 반하여 광고성 정보 등 일정한 내용을 전송하는 행위

  -서비스의 안정적인 운영에 지장을 주거나, 줄 우려가 있는 일체의 행위

  -기타 관계법령에 위배되는 행위

제12조 (정보의 제공 및 광고의 게재)
1. 회사는 서비스를 운용함에 있어 각종 정보를 웹사이트에 게재하는 방법 등으로 회원에게 제공할 수 있습니다.
2. 회사는 서비스 운영과 관련하여 웹사이트, 전자우편 등에 광고를 게재할 수 있습니다.
제13조 (서비스 제공의 중지)
다음 각 항의 1에 해당하는 경우 서비스 제공을 중지할 수 있습니다.
1. 시스템 정비를 위하여 부득이한 경우
2. 전기통신사업법에 규정된 기간통신 사업자가 전기통신 서비스를 중지하는 경우
3. 기타 회사에서 서비스를 제공할 수 없는 사유가 발생할 경우
제14조 (게시물에 대한 권리, 의무, 삭제 등)
게시물에 대한 저작권을 포함한 모든 권리 및 책임은 이를 게시한 회원에게 있으며, 게시판에 제10조 제4항과 관련된 자료가 게재되었을 경우, 운영자의 판단에 의하여 게시물을 삭제할 수 있습니다.
제15조 (계약해지 및 이용제한)
1. 회원이 이용계약을 해지하고자 하는 때에는 본인이 서비스 또는 전자우편을 통하여 해지하고자 하는 날의 1일 전까지 (단, 해지일이 법정공휴일인 경우 공휴일 개시 2일전) 이를 회사에 신청해야 합니다.
2. 회사는 회원이 제10조 기타 이 약관의 내용을 위반하고, 회사 소정의 기간 이내에 이를 해소하지 아니하는 경우 서비스 이용계약을 해지할 수 있습니다.
제16조 (손해배상)
회사는 무료로 제공되는 서비스와 관련하여 회원에게 어떠한 손해가 발생하더라도 동 손해가 회사의 중대한 과실에 의한 경우를 제외하고 이에 대하여 책임을 부담하지 아니합니다.
제17조 (면책, 배상)
1. 회사는 회원이 서비스에 게재한 정보, 자료, 사실의 정확성, 신뢰성 등 그 내용에 관하여는 어떠한 책임을 부담하지 아니하고, 회원은 자기의 책임 아래 서비스를 이용하며, 서비스를 이용하여 게시 또는 전송한 자료등에 관하여 손해가 발생하거나 자료의 취사선택, 기타 서비스 이용과 관련하여 어떠한 불이익이 발생하더라도 이에 대한 모든 책임은 회원에게 있습니다.
2. 회사는 제10조의 규정에 위반하여 회원간 또는 회원과 제3자 간에 서비스를 매개로 하여 물품거래 등과 관련해 어떠한 책임도 부담하지 아니하고, 회원이 서비스의 이용과 관련해 기대하는 이익에 관하여 책임을 부담하지 않습니다.
3. 회원 아이디(ID)와 비밀번호의 관리 및 이용상의 부주의로 인해 발생되는 손해 또는 제3자에 의한 부정사용등에 대한 책임은 모두 회원에게 있습니다.
4. 회원이 제10조, 기타 이 약관의 규정을 위반함으로써 회사가 회원 또는 제3자에 대하여 책임을 부담하게 되고, 이를 통하여 회사가 손해를 입게 되는 경우, 이 약관을 위반한 회원은 회사에 발생된 모든 손해를 배상하여야 하며, 동 손해로부터 회사를 면책시켜야 합니다.
제18조 (분쟁의 해결)
1. 회사와 회원은 서비스와 관련해 발생한 분쟁을 원만하게 해결하기 위하여 필요한 모든 노력을 해야 합니다.
2. 제1항의 규정에도 불구하고, 동 분쟁으로 인해 소송이 제기될 경우, 동 소송은 회사의 본사 소재지를 관할하는 법원의 관할로 합니다.
                  </textarea>
                     <p>
                        <label><input type="checkbox" id="check_1" name="" /> 위의 약관에 동의 합니다.</label><br />
                     </p>
                     <br>
                     <h4 class="scheme-g">●개인정보 수집, 이용 동의</h4>(필수동의)
                     <textarea readonly>
[수집하는 개인정보의 항목 및 수집방법]
이 약관은 별솔호텔리조트(이하"회사"라 표기합니다)가 제공하는 사이버 회원에 대한 서비스 제공 및 이용조건, 회원가입에 관련된 사항 및 절차 그리고 기타 필요한 사항의 규정을 목적으로 합니다.
1.이 약관은 전기통신사업법 제31조, 동 법 시행규칙 제21조의 2에 따라 공시절차를 거친 후 서비스 화면에 게시하거나 전자우편, 기타의 방법으로 회원에게 통지함으로써 효력을 발생합니다.
2.회사는 이 약관을 변경할 수 있으며, 변경된 약관은 변경된 사항에 대하여 공지 또는 통지함으로써 효력을 발생합니다.
* 개인정보의 수집 및 이용목적
1) 별솔호텔리조트 웹사이트는 다음과 같은 목적을 위하여 개인정보를 수집하고 있습니다.
가. 성명, 생년월일, ID, 비밀번호, 이동전화번호: 회원제 서비스 이용에 따른 본인 식별 절차에 이용
나. 주소, 유선전화번호, e-mail 주소 : 고지사항 전달, 본인 의사 확인, 불만 처리 등 원활한 의사소통 경로의 확보, 새로운 서비스/신상품/이벤트 정보의 안내, 경품 등 물품 배송에 대한 정확한 배송의 확보
다. 생년월일, 주소 : 인구통계학적 분석 자료(회원의 연령별, 성별, 지역별 통계분석)
라. 그 외 선택항목 : 개인맞춤 서비스를 제공하기 위한 자료
2) 단, 이용자의 기본적 인권 침해의 우려가 있는 민감한 개인정보(인종 및 민족, 사상 및 신조, 출신지 및 본적지, 정치적 성향 및 범죄기록, 건강상태 및 성생활 등)는 수집하지 않습니다.
* 수집하는 개인정보의 항목
1) 필수항목
- 이름, ID(고유번호), Password(비밀번호), E-mail(전자우편) 주소, 핸드폰번호, 주소, 기타 회사가 필요하다고 인정하는 사항
2) 선택항목
- 회사명, 직업, 취미, 결혼여부, 뉴스레터 수신여부, SMS 수신여부, DM 수령여부
※ 개인정보의 보유 및 이용기간
회원의 개인정보는 다음과 같이 개인정보의 수집 목적 또는 제공받은 목적이 종료되면 파기됩니다. 단, 상법 등 관련법령의 규정에 의하여 다음과 같이 거래 관련 권리 의무 관계의 확인 등을 이유로 일정기간 보유하여야 할 필요가 있을 경우에는 일정기간 보유합니다.
1) 회원가입정보의 경우 회원탈퇴 또는 회원제명시 사전에 보유목적, 기간, 보유하는 개인정보 항목을 명시하고 동의를 구할 경우 보유 가능
2) 계약 또는 청약철회 등에 관한 기록 : 5년
3) 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년
* 상기 보유 기간은 전자상거래 등에서의 소비자보호에 관한법률 시행령 제 6조 1항에 의거합니다.
                           </textarea>
                     <p>
                        <label><input type="checkbox" id="check_2" name="" /> 위의 약관에 동의 합니다.</label><br />
                     </p>
                     <br>
                     <h4 class="scheme-g">●개인정보 취급위탁 동의</h4>(필수동의)
                     <textarea readonly>
[개인정보 취급 위탁 안내]
이 약관은 워드북(이하"회사"라 표기합니다)가 제공하는 사이버 회원에 대한 서비스 제공 및 이용조건, 회원가입에 관련된 사항 및 절차 그리고 기타 필요한 사항의 규정을 목적으로 합니다.
1) 워드북 : 웹사이트 관리
2) 워드북 : 전산시스템 구축 및 유지보수
                  </textarea>
                     <p>
                        <label><input type="checkbox" id="check_3" name="" /> 위의 약관에 동의 합니다.</label><br />
                     </p>
                  </div>
               </article>
						<div class="signup_btn_box actions stacked">
							<input type="submit" style="width: 100%;" value="회원가입"
								class="button primary fit">
						</div>
					</div>
				</div>
			</form>
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