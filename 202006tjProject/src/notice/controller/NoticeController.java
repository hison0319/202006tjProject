package notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("notice")
public class NoticeController {
	//공지사항 조회 기능
	public String noticeListShow() {
		return "notice";
	}
	//운영자 아이디 확인 기능 구현
	public String noticeAdmin() {
		return "";
	}
	//공지사항 삽입 기능
	public String noticeInsertForm() {
		return "noticeInsertForm";
	}
	public String noticeInsert() {
		return "noticeInsertComplete";
	}
	//공지사항 수정 기능
	public String noticeUpdateForm() {
		return "noticeUpdateForm";
	}
	public String noticeUpdate() {
		return "noticeUpdateComplete";
	}
	//공지사항 삭제 기능
	public String noticeDelete() {
		return "notice";
	}
}
