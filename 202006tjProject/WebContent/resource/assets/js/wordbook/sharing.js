var sharing = $("[class*=sharing]");
for (let i = 0; i < sharing.length; i++) {
	sharing.eq(i).on(
			"click",
			function() {
				var data = $("form").eq(i).serialize();
				console.log(data);
				$.ajax({
					type : "post",
					url : "sharing",
					data : data,
					dataType : "json",
					success : function(data) {
						console.log(data);
						window.location.reload(true);
						alert("ㅇㅅㅇ");
					},
					error : function(request, status, error) {
						alert("code:" + request.status + "\n" + "message:"
								+ request.responseText + "\n" + "error:"
								+ error);
					}
				});
			});
};
