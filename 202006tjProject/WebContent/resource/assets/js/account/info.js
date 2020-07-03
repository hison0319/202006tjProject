function btnCertified() {
   location.replace("/certify/form");
}

function btnModify(memberId) {
   console.log("a");
   console.log(memberId);
   var memberIdArr = memberId.split("(");
   if (memberIdArr.length === 1) {
      location.replace("/account/confirmP");
   } else {
      location.replace("/account/forAPIupdate");
   }
}