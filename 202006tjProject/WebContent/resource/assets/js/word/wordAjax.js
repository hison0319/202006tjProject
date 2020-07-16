/*
var student = [
    { name : "재석", age : 21},
    { name : "광희", age : 25},
    { name : "형돈", age : 13},
    { name : "명수", age : 44}
];
student.sort(function(a, b) { // 오름차순
    return a.name < b.name ? -1 : a.name > b.name ? 1 : 0;
});
*/
var insertBtn;
var updateBtn;
var windowWidth;
var iInputVal;
var wInputVal;
var tInputVal;
var fInputVal;
var tempWidth;
function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}  //parameter 받아오기

function getData(callback){  //단어장 id로 단어장 json 파일 읽어서 가져옴
	$.ajax({
		type:"post",
		url:"getwords?wordbookid="+getParameterByName("wordbookid"),
		dataType:"json",
		success:function(data){
			if(data.nope=="notAllowed") {
				alert("권한이 없습니다.");
				location.replace("/wordbook/showlist");
			}
			else if(data.nope=="notExist") {
				alert("존재하지 않는 페이지입니다.");
				location.replace("/wordbook/showlist");
			}
			else if(data.nope=="loginPlease") {
				alert("로그인이 필요한 서비스입니다.");
				location.replace("/login/form");
			}
			else if(data.nope == "wrongAccess" ){
				alert("잘못 된 접근입니다.");
				location.replace("/");
			}
			else {
				if(callback){
					callback(data);  //다음 함수에 data를 넘겨줌
				}
				else{
					return data;
				}
			}
		}
	});
}

function setList(){
	getData(function(data){  //getData 실행이 끝난 후 data를 받아와 실행. sort의 값이 양수면 순서 변경
		if(data.length > 1 && arrange.value == 1){  //중요단어 - 입력순서
			data.sort(function(a,b){  //중요단어 정렬
				return b.favorite-a.favorite;
			})
			data.sort(function(a,b){  //중요도가 다르면 순서 변경 X, 같은 중요도에서 index 정렬
				return (a.favorite != b.favorite) ? 0 : a.index-b.index;
			})
		}
		else if(data.length > 1 && arrange.value == 2) {  //중요단어 - 오름차순
			data.sort(function(a,b){  //중요단어 정렬
				return b.favorite-a.favorite;
			})
			data.sort(function(a,b){  //중요도가 다르면 순서 변경 X, 같은 중요도에서 오름차순 정렬
				return (a.favorite != b.favorite) ? 0 : a.word < b.word ? -1 : a.word > b.word ? 1 : 0;
			})
		}
		else if(data.length > 1 && arrange.value == 3) {
			data = data.sort(function(a, b) {  //입력순서
				return a.index-b.index;
			});
		}
		else if(data.length > 1 && arrange.value==4){
			data = data.sort(function(a, b) {  //오름차순
				return a.word < b.word ? -1 : a.word > b.word ? 1 : 0;
			});
		}
		iInputVal = new Array();  //index 배열
		wInputVal = new Array();  //word 배열
		tInputVal = new Array();  //trans 배열
		fInputVal = new Array();  //favorite 배열
		for(let i=0; i<data.length; i++){  //각 배열에 data의 값을 담음
			iInputVal[i]=data[i].index;
			wInputVal[i]=data[i].word;
			tInputVal[i]=data[i].trans;
			fInputVal[i]=data[i].favorite;
		}
		showList(data, iInputVal, wInputVal, tInputVal, fInputVal);  //단어를 펼침
		windowWidth = $(window).width();  //창 너비
		if(windowWidth>=480){  //480px 이상
			tempWidth=1;  //480px 기준, 현재 창 너비 480px 이상
		}
		else{
			tempWidth=0;  //480px 미만
		}
		$(window).resize(function(){  //창 크기 변경 시
			windowWidth = $(window).width();  //현재 창 너비 실시간 탐색
			if(tempWidth==1 && windowWidth<480) {  //창 너비가 480px 이상에서 480px 미만으로 변했을 경우
				for(let i=0; i<document.querySelectorAll("input[name=index]").length;i++){  //배열 재 정의
					iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
					wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
					tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
					fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
				}
				getData(function(data){  //data를 다시 받아옴
					showList(data, iInputVal, wInputVal, tInputVal, fInputVal);  //단어를 펼침
				});
				tempWidth=0;  //현재 창 상태 변경
			}
			else if(tempWidth==0 && windowWidth>=480){  //창 너비가 480px 미만에서 480px 이상으로 변했을 경우
				for(let i=0; i<document.querySelectorAll("input[name=index]").length;i++){  //배열 재 정의
					iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
					wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
					tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
					fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
				}
				getData(function(data){  //data를 다시 받아옴
					showList(data, iInputVal, wInputVal, tInputVal, fInputVal);  //단어를 펼침
				});
				tempWidth=1;  //현재 창 상태 변경
			}
		})
	});
}

function checkFavorite(){  //중요단어 색상 변경
	$("input[name=favorite][value=1]").parent().prev().children("input").css({  //중요단어의 word 칸
		color:'white',
		background:'rgba(0, 0, 255, 0.56)'
	});
	$("input[name=favorite][value=1]").prev().css({  //중요단어의 trans 칸
		color:'white',
		background:'rgba(0, 0, 255, 0.56)'
	});
	$("input[name=favorite][value=0]").parent().prev().children("input").css({  //그 외 단어의 word 칸 초기화
		color:'',
		background:''
	});
	$("input[name=favorite][value=0]").prev().css({  //그 외 단어의 trans 칸 초기화
		color:'',
		background:''
	});
}

function checkDuplicates(){  //중복 검사
	wInput=document.querySelectorAll("input[name=word]");
	wInput.forEach(function(element){  //각각의 word 칸
		if(element.style.background=="rgba(0, 0, 255, 0.56)"){  //중요단어일 경우
			element.style.color="white";  //글자 색 흰 색으로 초기화
		}
		else{  //그 외 단어일 경우
			element.style.color="";  //글자 색 초기화
		}
	});
	wInput.forEach(function(element, index){  //각각의 word 칸
		for(let i=index+1; i<wInput.length; i++){  //현재 단어의 다음 칸들과 대조
			if(element.value == wInput[i].value){  //같은 단어가 있다면
				element.style.color="red";  //현재 word 칸 글자 색 빨강으로 변경
				wInput[i].style.color="red";  //같은 단어를 가진 word 칸 글자 색 빨강으로 변경
			}
		}
	});
}

function showList(data, iInputVal, wInputVal, tInputVal, fInputVal){  //단어를 펼치는 함수
	$("table").find("tr:not(:last)").remove();  //새로 펼치기 전 현재 창에 있는 단어 모두 제거
	windowWidth = $(window).width();  //현재 창 너비 확인
	insertBtn = document.querySelector("#insert");  //추가버튼
	updateBtn = document.querySelector("#update");  //수정버튼
	if(windowWidth >= 480){
		for(let i = iInputVal.length-1; i >= 0 ; i--){  //가장 index가 큰 단어부터 prepend => 아래로 점점 밀려나는 배치
			if(iInputVal.length%2==0){  //칸 수가 짝수일 경우
				if(insertBtn.innerText == "추가" && updateBtn.innerText == "수정"){  //평상시
					if((iInputVal.length-1-i)%2==0){  //큼, 전체짝수칸, 평상시, 홀수칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else{  //큼, 전체짝수칸, 평상시, 짝수칸
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
				}
				else if(updateBtn.innerText == "수정완료"){
					if((iInputVal.length-1-i)%2==0){  //큼, 전체짝수칸, 수정시, 홀수칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' style='width:80%'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' style='width:80%'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else{  //큼, 전체짝수칸, 수정시, 짝수칸
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' style='width:80%'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' style='width:80%'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
					if(i==0){
						modifying();
					}
				}
				else {  //큼, 전체짝수칸, 추가시
					if((iInputVal.length-1-i)%2==0){  //큼, 전체짝수칸, 추가시, 홀수칸
						if(i<data.length){  //큼, 전체짝수칸, 추가시, 홀수칸, 기존데이터
								$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
								+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
								+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else{  //큼, 전체짝수칸, 추가시, 홀수칸, 신규데이터
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
						}
					}
					else {   //큼, 전체짝수칸, 추가시, 짝수칸
						if(i<data.length){   //큼, 전체짝수칸, 추가시, 짝수칸, 기존데이터
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td>");
						}
						else{   //큼, 전체짝수칸, 추가시, 짝수칸, 신규데이터
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
						}
					}
				}
			}  //전체짝수칸
			else {  //전체홀수칸
				if(insertBtn.innerText == "추가" && updateBtn.innerText == "수정"){  //평상시
					if(i==iInputVal.length-1){  //전체홀수칸, 평상시, 첫 칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else if ((iInputVal.length-1-i)%2==1){  //전체홀수칸, 평상시, 짝수칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else {  //전체홀수칸, 평상시, 홀수칸
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
				}
				else if(updateBtn.innerText == "수정완료"){  //전체홀수칸, 수정시
					if(i==iInputVal.length-1){  //전체홀수칸, 수정시, 첫 칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' style='width:80%'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' style='width:80%'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else if ((iInputVal.length-1-i)%2==1){  //전체홀수칸, 수정시, 짝수칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' style='width:80%'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' style='width:80%'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else {  //전체홀수칸, 수정시, 홀수칸
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' style='width:80%'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' style='width:80%'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
					if(i==0){
						modifying();
					}
				}
				else{  //전체홀수칸, 추가시
					if(i==iInputVal.length-1){  //전체홀수칸, 추가시, 첫 칸
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else if((iInputVal.length-1-i)%2==1){  //전체홀수칸, 추가시, 짝수칸
						if(i<data.length){  //전체홀수칸, 추가시, 짝수칸, 기존데이터
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else{  //전체홀수칸, 추가시, 짝수칸, 신규데이터
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
						}
					}
					else{  //전체홀수칸, 추가시, 홀수칸
						if(i<data.length){  //전체홀수칸, 추가시, 홀수칸, 기존데이터
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td>");
						}
						else{  //전체홀수칸, 추가시, 홀수칸, 신규데이터
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
						}
					}
				}
			}
		}
	}  //창 너비가 480px 이상인 경우
	else {  //창 너비가 480px 미만인 경우
		for(let i=iInputVal.length-1; i>=0; i--){  //가장 index가 큰 단어부터 prepend => 아래로 점점 밀려나는 배치
			if(insertBtn.innerText == "추가" && updateBtn.innerText == "수정"){  //평상시
				$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
			}
			else if(updateBtn.innerText == "수정완료"){  //수정시
				$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' style='width:80%'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' style='width:80%'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
				if(i==0){
					modifying();
				}
			}
			else{  //추가시
				if(i<data.length){  //기존 데이터
					$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' disabled='disabled'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' disabled='disabled'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
				}
				else{  //신규 데이터
					$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
				}
			}
		}
	}
	checkFavorite();  //중요단어 강조
	checkDuplicates();  //중복 검사
};


document.querySelector("#form").onclick=function(){  //장문추가버튼 클릭 시
	location.href="/word/form?wordbookid="+getParameterByName("wordbookid");  //폼으로 이동
}

document.querySelector("#deleting").onclick=function(){  //단어장 삭제버튼 클릭 시
	let isOK = prompt("삭제하시려면 [단어장 삭제 확인]을 입력하세요.");  //입력창을 띄움
	if(isOK == null){};  //취소 버튼
	if(isOK != "단어장 삭제 확인"){  //입력이 틀릴 시
		alert("입력이 잘못되었습니다.");
	}
	else{  //입력 성공 시
		location.replace("/wordbook/delete?wordbookid="+getParameterByName("wordbookid"));  //삭제
	}
}

setList();  //단어를 펼침

var arrange = document.querySelector("select#arrange");  //단어 배치 방식
arrange.onchange=function(){  //선택지 변경 시
	setList();  //단어를 새로 펼침
};

$.getScript("/js/word/wordInsert.js?v=<%=System.currentTimeMillis()%>");  //js 파일 불러오기
$.getScript("/js/word/wordUpdate.js?v=<%=System.currentTimeMillis()%>");
