Kakao.init('0e4fcd913762bd2a1016dc2dc2798451');
//카카오, 트위터, 페이스북, 네이버 공유버튼 기능
function kakaoBtn() {
	Kakao.Link.sendCustom({
		templateId : 32279
	});
}
function twitterBtn() {
	let text = "단어장입니다";
	let hashtags = "단어장,영단어,열공"
	console.log("t");
	let link = "https://twitter.com/share?"+
  "url=http://localhost:8080/&"+
  "via=twitterdev&"+
  "hashtags="+hashtags+"&"+
  "text="+text;
	window.open(link);
}
function facebookBtn() {
	let url = "http://localhost:8080/";
	let encodeUrl = encodeURIComponent(url);
	let facebook = "https://www.facebook.com/sharer/sharer.php?u=";
	let link = facebook + encodeUrl;
	window.open(link);
}
function naverBtn() {
	let url = encodeURI(encodeURIComponent("http://localhost:8080/"));
	let title = encodeURI("단어장을 사용해보자!");
	let link = "https://share.naver.com/web/shareView.nhn?url=" + url + "&title=" + title;
	window.open(link);
}