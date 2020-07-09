$("#copyBtn").click(function() {
	$("#sharingKey").select();
	document.execCommand("copy");
	alert("복사완료");
});