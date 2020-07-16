let button = document.querySelector("#submit");
let title = document.querySelector("#title");
let file = document.querySelector("#file");
let text = document.querySelector("#text");
let sharingInput = document.querySelector("#sharingKey");
let sharing = document.querySelector("#submitSharing");
let click = true;

//버튼 중복클리 방지 기능
button.onclick = function() {
	if(click) {
		click = !click;
	} else {
		return false;
	}
}

sharing.onclick = function() {
	if(click) {
		click = !click;
	} else {
		return false;
	}
}


title.onkeyup = function(){
	if(title.value.trim()==""){
		button.disabled="disabled";
	}
	else{
		if (file.files.length != 0) {
			let name = file.files[0].name;
			if (name.substring(name.lastIndexOf(".") + 1, name.length)
					.search("txt") != -1) {
				button.disabled=null;
			}
		}
		else if(text.value.trim() != ""){
			button.disabled=null;
		}
	}
}
//각 form안의 input내용이 변경 시 submit버튼 disabled 토글
file.onchange = function() {
	if (file.files.length != 0) {
		let name = file.files[0].name;
		if (name.substring(name.lastIndexOf(".") + 1, name.length)
				.search("txt") == -1) {
			alert("txt 파일만 가능합니다");
			text.disabled = null;
			button.disabled = "disabled";
		} else {
			text.disabled = "disabled";
			button.disabled = null;
		} 
	} else {
		text.disabled = null;
		if (text.value.trim() != "") {
			button.disabled = null;
		} else {
			button.disabled = "disabled";
		}
	}
	if(title.value.trim()==""){
		button.disabled="disabled";
	}
}
text.onkeyup = function() {
	if (file.files.length != 0) {
		let name = file.files[0].name;
	}
	if (text.value.trim() != "") {
		file.disabled = "disabled";
		button.disabled = null;
	} else {
		file.disabled = null;
		if (file.files.length == 0
				|| (file.files.length != 0 && name.substring(
						name.lastIndexOf(".") + 1, name.length).search("txt") == -1)) {
			button.disabled = "disabled";
		} else {
			button.disabled = null;
		}
	}
	if(title.value.trim()==""){
		button.disabled="disabled";
	}
}
sharingInput.onkeyup = function(){
	if(this.value.trim() == ""){
		sharing.disabled = "disabled";
	} else {
		sharing.disabled = null;
	}
}
if(sharingInput.value != null) {
	sharing.disabled = null;
} else {
	sharing.disabled = "disabled";
}
