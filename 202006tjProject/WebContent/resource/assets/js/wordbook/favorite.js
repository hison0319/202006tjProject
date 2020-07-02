window.onload = function(){
    var favorite = $("[class*=favorite]");
    for(let i = 0; i < favorite.length; i++){
		$("form").eq(i).on("submit",function(){
			var data = $("form").eq(i).serialize();
			console.log(data);
	    		$.ajax({
	    			type:"post",
	    			url:"favorite",
	    			data:data,
					dataType:"json",
	    			success:function(data){
	    				console.log(data);
	    			},
	    			error:function(request,status,error){
	    	             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	   		        }
			});
	  		if(favorite[i].innerText == "ㅡㅅㅡ"){
	   			favorite[i].innerText = "ㅇㅅㅇ";
	   		}
	   		else {
	    		favorite[i].innerText = "ㅡㅅㅡ";
	   		}
		 	return false;
		});
	};
}