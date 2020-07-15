Kakao.init('0e4fcd913762bd2a1016dc2dc2798451');
var showListSharing = $("[class*=showListSharing]");
var getkeySharing = $("[class*=getkeySharing]");
var kakaoSharing = $("[class*=kakaoSharing]");
var deleteSharing = $("[class*=deleteSharing]");
var memberId = $("[class*=postId]");
var title = $("[class*=title]");
for (let i = 0; i < showListSharing.length; i++) {
	showListSharing.eq(i).on(
			"click",
			function() {
				location.href = "/account/showSharingList";
				return false;
			});
};
for (let i = 0; i < getkeySharing.length; i++) {
	getkeySharing.eq(i).on(	//공유키를 누를 시 비동기식 ajax로 공유키를 받고 자동으로 클립보드에 복사
			"click",
			function() {
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
	kakaoSharing.eq(i).on(	//공유키 카카오톡 전송
			"click",
			function() {
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
	deleteSharing.eq(i).on(	//공유 취소
			"click",
			function() {
				if(!confirm("정말 공유를 취소하시겠습니까?")) {
					return false;
				}
				var data = $("form").eq(i+1).serialize();
				$.ajax({
					url : "deleteSharing",
					type : "post",
					data : data,
					dataType : "json",
					success : function() {
						alert("공유 취소 완료.");
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
