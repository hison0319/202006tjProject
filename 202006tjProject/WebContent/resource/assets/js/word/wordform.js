//파일 업로드, 장문 직접 입력으로 단어장에 단어를 추가하는 기능

let button = document.querySelector("#submit");  //전송버튼
let file = document.querySelector("#file");  //파일 업로드
let text = document.querySelector("#text");  //직접 입력
let clicked = false;  //버튼 클릭 전
file.onchange = function() {  //파일 변경 시
	if (file.files.length != 0) {  //업로드 하려는 파일이 존재
		let name = file.files[0].name;  //파일 이름
		if (name.substring(name.lastIndexOf(".") + 1, name.length).search("txt") == -1) {  //파일의 확장자가 txt가 아니면
			alert("txt 파일만 가능합니다");
			text.disabled = null;  //직접 입력 가능
			button.disabled = "disabled";  //전송버튼 비활성화
		} else {  //txt가 맞으면
			text.disabled = "disabled";  //직접 입력 비활성화
			button.disabled = null;  //버튼 활성화
		}
	} else {  //업로드 하려는 파일이 존재하지 않을 경우
		text.disabled = null;  //직접 입력 가능
		if (text.value.trim() != "") {  //직접 입력 칸에 입력값이 있을 경우
			button.disabled = null;  //버튼 활성화
		} else {  //직접 입력 칸에 입력값이 없을 경우
			button.disabled = "disabled";  //버튼 비활성화
		}
	}
};
text.onkeyup = function() {  //직접 입력 시
	if (file.files.length != 0) {  //파일이 존재 시
		let name = file.files[0].name;  //파일 이름을 받아옴
	}
	if (text.value.trim() != "") {  //직접 입력 칸에 입력값이 있을 경우
		file.disabled = "disabled";  //파일 업로드 비활성화
		button.disabled = null;  //버튼 활성화
	} else {  //직접 입력 칸에 입력값이 없을 경우
		file.disabled = null;  //파일 업로드 활성화
		if (file.files.length == 0 || (file.files.length != 0 && name.substring(name.lastIndexOf(".") + 1, name.length).search("txt") == -1)) {  //파일이 없거나 파일의 확장자가 txt가 아닌 경우
			button.disabled = "disabled";  //버튼 비활성화
		} else {  //txt 파일이 맞을 경우
			button.disabled = null;  //버튼 활성화
		}
	}
};
button.onclick = function(){  //버튼 클릭 시
	if(clicked){  //이미 클릭했다면
		alert("이미 처리중입니다.");
		return false;  //리턴으로 중복 막음
	}
	clicked=true;
}
