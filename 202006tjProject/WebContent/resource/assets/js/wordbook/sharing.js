console.log("aaaa");
var showListSharing = $("[class*=showListSharing]");
var getkeySharing = $("[class*=getkeySharing]");
var kakaoSharing = $("[class*=kakaoSharing]");
var deleteSharing = $("[class*=deleteSharing]");
for (let i = 0; i < showListSharing.length; i++) {
	console.log("bbb");
	showListSharing.eq(i).on(
			"click",
			function() {
				console.log("showList "+i);
				return false;
			});
};
for (let i = 0; i < getkeySharing.length; i++) {
	getkeySharing.eq(i).on(
			"click",
			function() {
				console.log("getkeySharing "+i);
				var data = $("form").eq(i+1).serialize();
				console.log(data);
				$.ajax({
					type : "post",
					url : "sharingKey",
					data : data,
					dataType : "json",
					success : function(data) {
						console.log("key : "+data);
					},
					error : function(request, status, error) {
						console.log("code:" + request.status + "\n" + "message:"
								+ request.responseText + "\n" + "error:"
								+ error);
						alert("error");
					}
				});
				return false;
			});
};
for (let i = 0; i < kakaoSharing.length; i++) {
	kakaoSharing.eq(i).on(
			"click",
			function() {
				console.log("kakaoSharing "+i);
				return false;
			});
};
for (let i = 0; i < deleteSharing.length; i++) {
	deleteSharing.eq(i).on(
			"click",
			function() {
				console.log("deleteSharing "+i);
				return false;
			});
};
