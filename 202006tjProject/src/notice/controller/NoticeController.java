package notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import notice.dto.NoticeDto;
import notice.service.NoticeService;

@Controller
@RequestMapping("notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	//공지사항 조회 기능
	@GetMapping("show")
	public String noticeShow(Model m, int id) {
		System.out.println(id);
		NoticeDto notice = noticeService.selectNoticeById(id);
		m.addAttribute("notice",notice);
		return "notice/notice";
	}
	@GetMapping("showList")
	public String noticeListShow(Model m) {
		List<NoticeDto> noticeList = noticeService.selectNoticeAll();
		m.addAttribute("noticeList", noticeList);
		return "notice/noticeList";
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
	public String noticeInsert(NoticeDto notice) {
		//유효성검증필요
		noticeService.insertNotice(notice);
		return "notice/noticeInsertComplete";
	}
	//공지사항 수정 기능
	@GetMapping("update")
	public String noticeUpdateForm(Model m, int id) {
		NoticeDto notice = noticeService.selectNoticeById(id);
		m.addAttribute("notice", notice);
		return "notice/noticeUpdateForm";
	}
	@PostMapping("update")
	public String noticeUpdate(Model m, NoticeDto notice) {
		//유효성검증필요
		System.out.println(notice);
		noticeService.updateNotice(notice);
		System.out.println(notice.getId());
		m.addAttribute("id",notice.getId());
		return "notice/noticeUpdateComplete";
	}
	//공지사항 삭제 기능
	@GetMapping("delete")
	public String noticeDelete( int id) {
		noticeService.deleteNotice(id);
		return "notice/noticeDeleteComplete";
	}
}
