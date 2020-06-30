package member.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import member.dto.MemberDto;

@Transactional
@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	MemberService memberService;
	public void create(HttpSession session, MemberDto member) throws Exception{
		String certifyKey = new TempKey().getKey(5); //인증키 생성
		MailHandler sendMail = new MailHandler(mailSender);
		session.setAttribute("key", certifyKey); //세션에 인증키 저장
		sendMail.setSubject("단어장 인증 메일입니다.");
		sendMail.setText(
				"<h2>메일인증</h2>"+
				"<a href='http://localhost:8080/certify/confirm?certifyKey="+certifyKey+
				"' target='_blenk'>이메일 인증 확인</a>"+"<br>"+
				"<p>인증 번호는 "+certifyKey+" 입니다.</p>");
		sendMail.setFrom("hison0319test@gmail.com","단어장");
		sendMail.setTo(member.getEmail());
		sendMail.send();		
	}
	
	//동일 세션 내에서만 인증이 가능함.
	public boolean memberCertify(HttpSession session, String certifyKey, String memberKey) throws Exception {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if(memberKey.equals(certifyKey)) {
			member.setCertified(1);
			memberService.updateMemberCertify(member);
			return true;			
		} else {
			return false;
		}
	}
}