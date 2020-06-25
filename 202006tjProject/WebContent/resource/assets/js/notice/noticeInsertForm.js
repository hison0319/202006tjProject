function sumbmitCheck() {
    if (document.noticeForm.title.value == "") {
        alert("제목을 입력해주세요");
        return false;
    } else
        document.noticeForm.submit();
}