var words;
var trans;
var input = document.querySelectorAll("input");
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var offset = input.length;
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼
var formBtn = document.querySelector("#form");  //장문 추가 버튼
var trs;
var selectBar = document.querySelector("#arrange");
var windowWidth;

function modifying(){
	if(windowWidth>=480){
		console.log(windowWidth);
		trs=$("tr:not(:last)");
		for(let i=0; i<trs.length; i++){
			trs.eq(i).children(":odd").each(function(){
				if($(this).children(":last").val()==0 && !$(this).prev().children().is("a")){
					$(this).prev().prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="far fa-smile fa-2x"></i></button>');  //
				}
				else if($(this).children(":last").val()==1 && !$(this).prev().children().is("a")){
					$(this).prev().prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="fas fa-grin-squint fa-2x"></i></button>');  //
				}
				$(this).append('<button type="button" class="clear" onclick="clearText(this)">지우기</button>')
			})
		}
	}  //창 크기가 480px 이상
	else{
		trs=$("tr:not(:last)");
		for(let i=0; i<trs.length; i++){
			if(trs.eq(i).children(":last").children(":last").val()==0 && !trs.eq(i).children(":first").children().is("a")){
				trs.eq(i).children(":first").prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="far fa-smile fa-2x"></i></button>');
			}
			else if(trs.eq(i).children(":last").children(":last").val()==1 && !trs.eq(i).children(":first").children().is("a")){
				console.log("이거?" +i);
				trs.eq(i).children(":first").prepend('<button type="button" class="favorite" onclick="toggleFavorite(this)"><i class="fas fa-grin-squint fa-2x"></i></button>');
			}
			trs.eq(i).children(":last").append('<button type="button" class="clear" onclick="clearText(this)">지우기</button>')
		}
	}
}


function complete(){
	trs = $("tr");
	windowWidth=$(window).width();
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
		for(let i = 0; i<trs.length-1;i++){
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


function wordModified(i){
	if(words[i]==undefined || words[i]==null){
		words[i]=wInput[i].value;
		console.log(words[i]);
	}
}
function transModified(i){
	if(trans[i]==undefined || trans[i]==null){
		trans[i]=tInput[i].value;
	}
}


function toggleFavorite(i){
	if($(i).parent().next().find("input[name=favorite]").eq(0).val()==0){
		$(i).parent().next().find("input[name=favorite]").eq(0).val(1);
		$(i).find("i").eq(0).attr("class","fas fa-grin-squint fa-2x");
	}
	else{
		$(i).parent().next().find("input[name=favorite]").eq(0).val(0);
		$(i).find("i").eq(0).attr("class","far fa-smile fa-2x");
	}
}

function clearText(i){
	$(i).prev().prev().val("");
	$(i).parent().prev().children("input[name=word]").val("");
}

function sendData(callback){
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
				alert("존재하지 않는 페이지입니다.")
				location.replace("/");
			}
			else{
				alert("수정 완료");
				wInput = document.querySelectorAll("input[name=word]");
				var dupWords = new Array();
				var x=0;
				wInput.forEach(function(element, index){
				if(element.style.background=="rgba(0, 0, 255, 0.56)"){
					element.style.color="white";
				}
				else{
					element.style.color="black";
				}
				if(index<wInput.length-1){
					for(let i=index+1; i<wInput.length; i++){
						if(element.value == wInput[i].value){
							dupWords[x]=element.value;
							x++;
							break;
						}
					}
				}
			});
		}
		if(callback){
			callback(dupWords, x, data);
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
	if(updateBtn.innerText=="수정"){
		insertBtn.disabled = "disabled";
		formBtn.disabled = "disabled";
		selectBar.disabled = "disabled";
		wInput.forEach(function(element){
			element.style.width="80%";
		})
		tInput.forEach(function(element){
			element.style.width="80%";
		})
		words = new Array();
		trans = new Array();
		updateBtn.innerText="수정완료";
		input.forEach(function(element){
			element.disabled = null;
		})
		modifying();
		wInput.forEach(function(element, index){
			element.onfocus=wordModified(index);
		})
		tInput.forEach(function(element, index){
			element.onfocus=transModified(index);
		})
	}
	else {
		insertBtn.disabled = null;
		formBtn.disabled = null;
		selectBar.disabled = null;
		updateBtn.innerText="수정";
		var iInput;
		var deleted=0;
		var standard;
		windowWidth = $(window).width();
		trs=$("tr:not(:last)");
		trs.children().children().remove("button");
			wInput = $("input[name=word]");
			tInput = $("input[name=trans]");
			for(let i = 0; i<wInput.length;i++){
				if($.trim(wInput.eq(i).val()) == "" && $.trim(tInput.eq(i).val()) == ""){  //빈 칸들 삭제
				iInput = document.querySelectorAll("input[name=index]");
				standard = iInput[i-deleted].value;
				for (let j = 0; j<iInput.length;j++){
					if(Number(iInput[j].value)>Number(standard)){
						iInput[j].value--;
					}
				}
				deleted++;
				wInput.eq(i).parent().remove();
				tInput.eq(i).parent().remove();
			}
		}
		complete();
		sendData(function(dupWords, x, data){
			if(x!=0){
				alert("중복 단어가 있습니다.");
			}
			iInputVal = new Array();
			wInputVal = new Array();
			tInputVal = new Array();
			fInputVal = new Array();
			for(let i=0; i<document.querySelectorAll("input[name=index]").length; i++){
				iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
				wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
				tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
				fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
			}
	
			for(let i=0; i<x; i++){
				document.querySelectorAll("input[value="+dupWords[i]+"]").forEach(function(element){
				element.style.color="red";
				})
			}
		
			$("input").each(function(){
				$(this).removeAttr("onfocus");
				$(this).css("width", "");
				$(this).attr("disabled", "disabled");
			})
			
			checkFavorite();
			checkDuplicates();
		})
	}
});
