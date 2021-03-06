package myTest;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import member.controller.SignupController;
import member.dto.MemberDto;
import member.service.MemberService;
import notice.dto.NoticeDto;
import notice.service.NoticeService;
import word.dto.WordbookDto;
import word.service.WordbookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/root-context.xml")
public class MyTest {
	@Autowired
	MemberService memberService;
	@Autowired
	NoticeService noticeService;
	@Autowired
	WordbookService wordbookService;
	@Autowired
	SignupController signupController;
	//멤버 삽입 셀렉 전체셀렉
	@Test @Ignore
	public void testMember01() {
		MemberDto memberDto = new MemberDto("test0005", "1234", "test0005@gmail.com", "010-0000-0005", "test");
		memberService.insertMember(memberDto);
		System.out.println(memberService.selectMemberById(memberDto.getId()));
		System.out.println(memberService.selectMemberAll());
	}
	//멤버 업데이트 델리트
	@Test @Ignore
	public void testMember02() {
		MemberDto member = memberService.selectMemberById(24);
		member.setEmail("updateTest@gmail.com");
		memberService.updateMember(member);
		System.out.println(memberService.selectMemberById(24));
		memberService.deleteMember(24);
		System.out.println(memberService.selectMemberAll());
	}
	//공지 삽입 셀렉 전체셀렉
	@Test @Ignore
	public void testNotice01() {
		NoticeDto notice = new NoticeDto("test", 1, "test");
		try {
			noticeService.insertNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(noticeService.selectNoticeById(notice.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(noticeService.selectNoticeList(1,5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//공지 업데이트 델리트
	@Test @Ignore
	public void testNotice02() {
		NoticeDto notice;
		try {
			notice = noticeService.selectNoticeById(1);
			notice.setTitle("updateTest");
			noticeService.updateNotice(notice);
			System.out.println(noticeService.selectNoticeById(1));
			noticeService.deleteNotice(2);
			System.out.println(noticeService.selectNoticeList(1,5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//단어장 삽입 셀렉 전체셀렉
	@Test @Ignore
	public void testWordbook01() {
		WordbookDto wordbook = new WordbookDto(22, 0, "test_wordbook", "test");
		try {
			wordbookService.insertWordbook(wordbook);
			System.out.println(wordbookService.selectWordbookById(wordbook.getId()));
			System.out.println(wordbookService.selectWordbookAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//단어장  업데이트 델리트
	@Test @Ignore
	public void testWordbook02() {
		WordbookDto wordbook;
		try {
			wordbook = wordbookService.selectWordbookById(1);
			wordbook.setTitle("test_wordbook");
			wordbookService.updateWordbook(wordbook);
			System.out.println(wordbookService.selectWordbookById(1));
			wordbookService.deleteWordbook(2);
			System.out.println(wordbookService.selectWordbookAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//회원가입 아이디, 메일, 번호 중복확인
	@Test @Ignore
	public void testSignup01() {
		System.out.println("phoneX"+signupController.confirmPhone("010-7530-0079"));
		System.out.println("phoneO"+signupController.confirmPhone("test"));
		System.out.println("mailX"+signupController.confirmEmail("test@gmail.com"));
		System.out.println("mailO"+signupController.confirmEmail(""));
		System.out.println("memberIdX"+signupController.confirmMemberId("test0003"));		
		System.out.println("memberIdO"+signupController.confirmMemberId("tsdf"));		
	}
	@Test @Ignore
	public void testSignout01() {
		List<String> addressList = wordbookService.selectAddressGroupByAddressByOwnerId(21);
		System.out.println(addressList);
	}
}
