var showSharingList = $("[class*=showSharingList]");
var tr = $("[class*=tr]");
var toggle = true;
for (let i = 0; i < showSharingList.length; i++) {
	showSharingList.eq(i).on(
			"click",
			function() {
				console.log("showSharingList "+i);
				console.dir(this.innerText);
				var data = $.parseJSON( '{ "title": "'+this.innerText+'" }' );
				$.ajax({
					url : "showSharingMemberList",
					type : "post",
					data : data,
					dataType : "json",
					success : function(data) {
						if (toggle) {
							for (let j=0; j<data.length; j++) {
								tr.eq(i).after("<tr class='memberList'><td></td><td>"+data[(data.length-1)-j].memberId+
										"</td><td><button class='deleteSharing' style='box-shadow: none; border: none;'>공유 취소</button></td></tr>")							
							}
							var deleteSharing = $("[class*=deleteSharing]");
							for (let j = 0; j<deleteSharing.length; j++) {
								deleteSharing.eq(j).on(
										"click",
										function() {
											if(!confirm("정말 공유를 삭제하시겠습니까?")) {
												return false;
											}
											console.log("deleteSharing "+j);
											console.log("wordbookId : "+data[j].id);
											var idData = $.parseJSON( '{ "id": "'+data[j].id+'" }' );
											$.ajax({
												url : "deleteSharingMember",
												type : "post",
												data : idData,
												dataType : "json",
												success : function(idData) {
													alert("삭제되었습니다.")
													location.reload();
												},
												error : function(request, status, error, idData) {
													console.log(idData)
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
							toggle = false;
						} else {
							$(".memberList").detach();
							//없어짐
							toggle = true;
						}
					},
				});
				return false;
			});
};
