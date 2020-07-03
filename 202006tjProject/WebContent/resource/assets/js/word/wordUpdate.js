window.onload=function(){
	var words = new Array();
	var trans = new Array();
	var input = document.querySelectorAll("input");
	var wInput = document.querySelectorAll("input[id^=word]");
	var tInput = document.querySelectorAll("input[id^=trans]");
	var modify = document.querySelector("#modify");  //수정 버튼
	console.dir(document.querySelectorAll("input").length);
	modify.onclick = function(){
		console.log("ㅇㅅㅇ");
		if(modify.innerText=="수정"){
			modify.innerText="수정완료";
			input.forEach(function(element){
				element.disabled = null;
			})
			console.dir(wInput);
			for(let i=0; i<wInput.length; i++){
				words[i]=wInput.value;
				trans[i]=tInput.value;
				console.log(words[i]);
				console.log(trans[i]);
			}
		}
		else {
			modify.innerText="수정";
			input.forEach(function(element){
				element.disabled = "disabled";
			})
		}
		return false;
	};
};