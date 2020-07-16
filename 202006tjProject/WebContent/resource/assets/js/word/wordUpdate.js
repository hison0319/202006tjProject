var words;
var trans;
var input = document.querySelectorAll("input");
var iInput;
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var offset = input.length;
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼
var formBtn = document.querySelector("#form");  //장문 추가 버튼
var trs;
var selectBar = document.querySelector("#arrange");
var windowWidth;

function modifying(){  //각 단어에 중요도, 칸 비우기 버튼 넣는 함수
	if(windowWidth>=480){  //480px 이상
		trs=$("tr:not(:last)");  //마지막 버튼 줄 외 모든 줄
		for(let i=0; i<trs.length; i++){
			trs.eq(i).children(":odd").each(function(){  //각 줄의 trans 칸
				if($(this).children(":last").val()==0){  //중요도가 0일 경우 
					$(this).prev().prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="fas fa-star fa-2x n"></i></button>');  //빈 별 추가
				}
				else if($(this).children(":last").val()==1){  //중요도가 1일 경우
					$(this).prev().prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="fas fa-star fa-2x y"></i></button>');  //채워진 별 추가
				}
				$(this).append('<button type="button" class="clear" onclick="clearText(this)"><i class="far fa-trash-alt fa-2x"></i></button>');  //삭제 버튼
			})
		}
	}  //창 크기가 480px 이상
	else{
		trs=$("tr:not(:last)");
		for(let i=0; i<trs.length; i++){
			if(trs.eq(i).children(":last").children(":last").val()==0){  //중요도가 0일 경우
				trs.eq(i).children(":first").prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="fas fa-star fa-2x n"></i></button>');  //빈 별 추가
			}
			else if(trs.eq(i).children(":last").children(":last").val()==1){  //중요도가 1일 경우
				trs.eq(i).children(":first").prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="fas fa-star fa-2x y"></i></button>');  //채워진 별 추가
			}
			trs.eq(i).children(":last").append('<button type="button" class="clear" onclick="clearText(this)"><i class="far fa-trash-alt fa-2x"></i></button>')  //삭제 버튼
		}
	}
	document.querySelectorAll(".fa-star.n").forEach(function(element){  //중요도 0인 별 아이콘
		element.style.color="#ccc";
	});
	document.querySelectorAll(".fa-star.y").forEach(function(element){  //중요도 1인 별 아이콘
		element.style.color="#03A9F4";
	});
}


function complete(){  //수정 완료 후 정리
	trs = $("tr");  //모든 줄
	if(windowWidth>=480){
		for(let i = 0; i<trs.length-1;i++){  //빈 줄 삭제
			if(trs.eq(i).find("td").length==0){
				trs.eq(i).remove();
			}
		}
		trs = $("tr");
		for(let i=0; i<trs.length-2; i++){  //마지막 tr은 버튼
			if(trs.eq(i).find("td").length==2 && trs.eq(i+1).find("td")!=null){  //단어가 하나인 줄이 중간에 끼어있을 때 당기기
				trs.eq(i+1).find("td").eq(0).appendTo(trs.eq(i));
				trs.eq(i+1).find("td").eq(0).appendTo(trs.eq(i));
			}
		}
		trs = $("tr");
		for(let i = 0; i<trs.length-1;i++){  //빈 줄 삭제
			if(trs.eq(i).find("td").length==0){
				trs.eq(i).remove();
			}
		}
	}  //창 크기 480px 이상
	else{
		for(let i = 0; i<trs.length-1;i++){  //빈 줄 삭제
			if(trs.eq(i).find("td").length==0){
				trs.eq(i).remove();
			}
		}
	}
}

function toggleFavorite(i){  //중요단어 강조 함수
	if($(i).parent().next().find("input[name=favorite]").eq(0).val()==0){
		$(i).parent().next().find("input[name=favorite]").eq(0).val(1);
		$(i).find("i").eq(0).attr("class","fas fa-star fa-2x y").css("color","#03A9F4");
	}
	else{
		$(i).parent().next().find("input[name=favorite]").eq(0).val(0);
		$(i).find("i").eq(0).attr("class","fas fa-star fa-2x n").css("color","#ccc");
	}
}

function clearText(i){  //지우기버튼
	$(i).prev().prev().val("");
	$(i).parent().prev().children("input[name=word]").val("");
}

function sendUpdateData(callback){  //수정 데이터 전송
	let formData = $("form").eq(0).serialize();
	$.ajax({
		type:"post",
		url:"update?wordbookid="+getParameterByName("wordbookid"),
		dataType:"json",
		data:formData,
		success:function(data){
			if(data.nope == "loginPlease"){
				alert("로그인이 필요한 페이지입니다.");
				location.replace("/login/form");
			}
			else if(data.nope == "certifyPlease"){
				alert("인증이 필요한 페이지입니다.");
				location.replace("/certify/form");
			}
			else if(data.nope == "wrongAccess"){
				alert("잘못 된 접근입니다.");
				location.replace("/");
			}
			else if(data.nope == "notExist"){
				alert("존재하지 않는 페이지입니다.");
				location.replace("/");
			}
			else{
				alert("수정 완료");
				if(callback){
					callback();
				}
			}
		},
		error:function(request,status,error){
           	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}


$("#update").eq(0).on("click",function(){
	 input = document.querySelectorAll("input");
	 wInput = document.querySelectorAll("input[name=word]");
	 tInput = document.querySelectorAll("input[name=trans]");
	 offset = input.length;
	if(updateBtn.innerText=="수정"){  //수정버튼 클릭 시
		insertBtn.disabled = "disabled";  //추가버튼 비활성화
		formBtn.disabled = "disabled";  //장문추가버튼 비활성화
		selectBar.disabled = "disabled";  //정렬방식 비활성화
		wInput.forEach(function(element){  //단어 칸 너비조절
			element.style.width="80%";
		})
		tInput.forEach(function(element){  //뜻 칸 너비조절
			element.style.width="80%";
		})
		updateBtn.innerText="수정완료";  //수정버튼 변경
		input.forEach(function(element){  //모든 input 활성화
			element.disabled = null;
		})
		modifying();  //각 단어에 중요도, 칸 비우기 버튼 넣는 함수
	}
	else {  //수정완료버튼 클릭 시
		insertBtn.disabled = null;  //추가버튼 활성화
		formBtn.disabled = null;  //장문추가버튼 활성화
		selectBar.disabled = null;  //정렬방식 활성화
		updateBtn.innerText="수정";  //수정완료버튼 변경
		wInput = $("input[name=word]");
		tInput = $("input[name=trans]");
		var deleted=0;  //정리된 칸 수
		var standard;  //기준점(현재 삭제되는 index 값을 찾기 위한 변수)
		trs=$("tr:not(:last)");  //마지막 버튼 줄 외 모든 줄
		trs.children().children().remove("button");  //추가된 버튼들(중요단어, 지우기) 삭제
		for(let i = 0; i<wInput.length;i++){
			if($.trim(wInput.eq(i).val()) == "" && $.trim(tInput.eq(i).val()) == ""){  //빈 칸이라면
				iInput = document.querySelectorAll("input[name=index]");  //모든 인덱스 선택
				standard = iInput[i-deleted].value;  //삭제 될 인덱스 선택
				for (let j = 0; j<iInput.length;j++){
					if(Number(iInput[j].value)>Number(standard)){  //삭제 될 인덱스보다 큰 인덱스
						iInput[j].value--;  //1씩 감소
					}
				}
				deleted++;
				wInput.eq(i).parent().remove();  //td 삭제
				tInput.eq(i).parent().remove();  //td 삭제
			}
		}
		sendUpdateData(function(){  //수정 데이터 전송 후 다음 진행
			complete();  //빈 칸, 줄 정리
			$("input").each(function(){  //모든 input
				$(this).css("width", "");  //너비 초기화
				$(this).attr("disabled", "disabled");  //비활성화
			})
			checkFavorite();  //중요단어 검사
			checkDuplicates();  //중복 검사
		});
	}
});
