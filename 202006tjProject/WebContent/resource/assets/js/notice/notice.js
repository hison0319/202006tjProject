function deleteCheck(id) {
	var conf = confirm("정말 삭제하시겠습니까?");
	console.log(id);
	if(conf) {
		location.replace("delete?id="+id);
	}
}

function updateForm(id) {
	location.replace("update?id="+id);
}

function showList() {
	location.replace("showList");
}