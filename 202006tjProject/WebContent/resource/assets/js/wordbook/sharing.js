console.log("aaaa");
Kakao.init('0e4fcd913762bd2a1016dc2dc2798451');
var showListSharing = $("[class*=showListSharing]");
var getkeySharing = $("[class*=getkeySharing]");
var kakaoSharing = $("[class*=kakaoSharing]");
var deleteSharing = $("[class*=deleteSharing]");
var memberId = $("[class*=postId]");
var title = $("[class*=title]");
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
						$("#tempKey").val(data);
						let tempKey = $("#tempKey");
						console.dir("tempKey : "+tempKey);
						tempKey.select();
						document.execCommand("copy");
						alert("key복사 완료!");
						return false;
					},
					error : function(request, status, error, data) {
						console.log(data)
						console.log("code:" + request.status + "\n" + "message:"
								+ request.responseText + "\n" + "error:"
								+ error);
						alert("error");
						return false;
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
				let memberIdForSend = memberId.eq(i).text();
				let titleForSend = title.eq(i).text();
				var data = $("form").eq(i+1).serialize();
				$.ajax({
					type : "post",
					url : "sharingKey",
					data : data,
					dataType : "json",
					success : function(data) {
						Kakao.Link.sendCustom({
					        templateId: 32095,
					        templateArgs: {
					            'memberId': memberIdForSend,
					            'title' : titleForSend,
					            'sharingKey' : data
					        }
					    });
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
for (let i = 0; i < deleteSharing.length; i++) {
	deleteSharing.eq(i).on(
			"click",
			function() {
				console.log("deleteSharing "+i);
				var data = $("form").eq(i+1).serialize();
				$.ajax({
					url : "deleteSharing",
					type : "post",
					data : data,
					dataType : "json",
					success : function() {
						alert("삭제완료");
						location.reload();
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
