package member.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
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
	//인증 메일 서비스 메소드
	public void certifySendMail(HttpSession session, MemberDto member) throws Exception{
		String certifyKey = new TempKey().getKey(5); //인증키 생성
		MailHandler sendMail = new MailHandler(mailSender);
		session.setAttribute("key", certifyKey); //세션에 인증키 저장
		//메일 제목 설정
		sendMail.setSubject("단어장 인증 메일입니다.");
		//메일 내용 설정
		sendMail.setText(
				"<h2>메일인증</h2>"+
				"<a href='http://localhost:8080/certify/confirm?certifyKey="+certifyKey+
				"' target='_blenk'>이메일 인증 확인</a>"+"<br>"+
				"<p>인증 번호는 "+certifyKey+" 입니다.</p>");//인증키를 추가하여 보냄
		//메일 보내는 이 설정
		sendMail.setFrom("hison0319test@gmail.com","단어장");
		//메일 받는 이 설정
		sendMail.setTo(member.getEmail());
		//메일 보냄
		sendMail.send();		
	}
	
	//동일 세션 내에서만 인증이 가능함.
	public boolean memberCertify(HttpSession session, String certifyKey, String memberKey) throws Exception {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		//세션에 저장된 인증키확인
		if(memberKey.equals(certifyKey)) {
			member.setCertified(1);	//맞을 시 회원 인증
			memberService.updateMemberCertify(member);
			return true;			
		} else {
			return false;
		}
	}
	//패스워드 메일 보내기 기능
	public void findPwSendMail(String memberId, String email) throws Exception{
		String newPw = new TempKey().getKey(5); //인증키 생성
		MemberDto member = memberService.selectMemberInfoByIDEmail(memberId, email);
		member.setPassword(newPw);	//새로운 패스워드를 저장
		memberService.updateMember(member);	//멤버 새로운 패스워드 업데이트
		MailHandler sendMail = new MailHandler(mailSender);
		sendMail.setSubject("단어장 패스워드 메일입니다.");
		sendMail.setText(
				"<h2>메일인증</h2>"+
				"<a href='http://localhost:8080/login/form"+
				"' target='_blenk'>로그인 하러 가기</a>"+"<br>"+
				"<p>귀하의 비밀 번호는 "+newPw+" 입니다.</p>");	//새로운 패스워드를 메일에 넣어 보냄
		sendMail.setFrom("hison0319test@gmail.com","단어장");
		sendMail.setTo(member.getEmail());
		sendMail.send();		
	}
	//서버 에러 시 개발자에게 에러 메일을 보내는 기능
	public void sendErorrMail(String e){	//에러 e 매개변수로 받음
		MailHandler sendMail;
		try {
			sendMail = new MailHandler(mailSender);
			sendMail.setSubject("단어장에서 보낸 메일입니다.");
			sendMail.setText(
					"<h2>사용자 에러 메세지 입니다.</h2>"+
							"<p>에러는 <br>"+e+"<br> 입니다.</p>");	//e를 메일에 넣어서 보냄.
			try {
				sendMail.setFrom("hison0319test@gmail.com","단어장");
				sendMail.setTo("hison0319test@gmail.com");
				sendMail.send();	
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
	}
}
