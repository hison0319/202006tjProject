var selected;
console.log("test");
function sumbmitCheck() {
    if (document.noticeForm.title.value == "") {
        alert("제목을 입력해주세요");
        return false;
    } else
        document.noticeForm.submit();
}
function selected() {
	selected = document.getElementsByName("contents")[0].onselect.value;
	console.dir(selected);
}