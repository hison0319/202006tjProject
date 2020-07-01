window.onload = function(){
	document.querySelectorAll(".favorite").forEach(function(element){
		element.onclick = function(){
				$.ajax({
					type:"post",
					url:"favorite",
					data:{},
					success:function(){
						console.log("ㅇㅁㅇ");
					}
				});
			if(element.innerText == "ㅡㅅㅡ"){
				element.innerText = "ㅇㅅㅇ";
			}
			else {
				element.innerText = "ㅡㅅㅡ";
			}
		};
	});
}