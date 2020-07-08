let button = document.querySelector("#submit");
let file = document.querySelector("#file");
let text = document.querySelector("#text");
let sharingInput = document.querySelector("#sharingKey");
let sharing = document.querySelector("#submitSharing");
console.log(sharing);
console.log(file);
console.log("ㅇㅅㅇ");
console.log(file.files);
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
}
sharingInput.onkeyup = function(){
	if(this.value.trim() == ""){
		sharing.disabled = "disabled";
	} else {
		sharing.disabled = null;
	}
}
