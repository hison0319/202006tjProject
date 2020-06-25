package notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("notice")
public class NoticeController {
	//공지사항 조회 기능
	@GetMapping("show")
	public String noticeListShow() {
		return "notice/notice";
	}
	//운영자 아이디 확인 기능 구현
	@GetMapping("admin")
	public String noticeAdmin() {
		return "";
	}
	//공지사항 삽입 기능
	@GetMapping("insert")
	public String noticeInsertForm() {
		return "notice/noticeInsertForm";
	}
	@PostMapping("insert")
	public String noticeInsert() {
		return "notice/noticeInsertComplete";
	}
	//공지사항 수정 기능
	@GetMapping("update")
	public String noticeUpdateForm() {
		return "notice/noticeUpdateForm";
	}
	@PostMapping("update")
	public String noticeUpdate() {
		return "notice/noticeUpdateComplete";
	}
	//공지사항 삭제 기능
	@GetMapping("delete")
	public String noticeDelete() {
		return "notice/notice";
	}
}
