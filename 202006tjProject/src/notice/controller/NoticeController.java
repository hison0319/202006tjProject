package notice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import member.service.MailService;
import notice.dto.NoticeDto;
import notice.service.NoticeService;

@Controller
@RequestMapping("notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	@Autowired
	MailService mailService;

	// 운영자 아이디 확인 기능 구현
	@GetMapping("admin")
	public String noticeAdmin() {
		return "";
	}

	// 공지사항 조회 기능
	@GetMapping("show")
	public String noticeShow(Model m, int id, String memberId) {
		NoticeDto notice;
		try {
			notice = noticeService.selectNoticeById(id);	//공지사항 아이디로 공지사항 단일 조회
			m.addAttribute("notice", notice);	//공지사항 Model에 저장
			m.addAttribute("memberId", memberId);	//운영자 아이디 확인 위해 memberId를 저장
			return "notice/notice";
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());	//에러 시 개발자에게 메일 전송
			return "error/wrongAccess";
		}
	}

	@GetMapping("showList")
	public String noticeListShow(Model m, String pageNumStr) {
		int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
		int ea = 5;// 페이지에 띄울 갯수 정의(정책)
		// 공지 총 갯수
		int totalCnt;
		try {
			totalCnt = noticeService.selectNoticeCount();
			// 페이지 리스트
			int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
			List<Integer> pageNumList = getPageList(pageNum, ea, pages);
			m.addAttribute("pageNumList", pageNumList);
			m.addAttribute("pageNum", pageNum);
			m.addAttribute("pages", pages);
			List<NoticeDto> noticeList = noticeService.selectNoticeListJoin((pageNum - 1) * 5, ea);
			//regDate 날짜까지만 나오도록 변경
			for(int i=0; i<noticeList.size(); i++) {
				if(noticeList.get(i).getRegDate()!=null)noticeList.get(i).setRegDateStr(noticeList.get(i).getRegDate().toString().substring(0, 10));
			}
			m.addAttribute("noticeList", noticeList);
			return "notice/noticeList";
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
	}

	// 페이지 네이션 구현 기능
	public List<Integer> getPageList(int pageNum, int ea, int pages) {
		List<Integer> pageNumList = new ArrayList<Integer>();
		int begin;
		if (pageNum % 5 == 1) {
			begin = pageNum;
		} else if(pageNum % 5 == 0){
			begin = pageNum-4;
		} else {
			begin = pageNum-(pageNum%5-1);
		}
		for (int i=begin; i<=pages; i++) {
			pageNumList.add(i);
		}
		return pageNumList;
	}

	// 공지사항 삽입 기능
	@GetMapping("insert")
	public String noticeInsertForm() {
		return "notice/noticeInsertForm";
	}
	
	// 공지사항 생성 기능
	@PostMapping("insert")
	public String noticeInsert(NoticeDto notice) {
		try {
			noticeService.insertNotice(notice);
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
		return "notice/noticeInsertComplete";
	}

	// 공지사항 수정 폼 이동
	@GetMapping("update")
	public String noticeUpdateForm(Model m, int id) {
		NoticeDto notice;
		try {
			notice = noticeService.selectNoticeById(id);
			m.addAttribute("notice", notice);
			return "notice/noticeUpdateForm";
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
	}
	
	// 공지사항 수정 기능
	@PostMapping("update")
	public String noticeUpdate(Model m, NoticeDto notice) {
		try {
			noticeService.updateNotice(notice);
			m.addAttribute("id", notice.getId());
			return "notice/noticeUpdateComplete";
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
	}

	// 공지사항 삭제 기능
	@GetMapping("delete")
	public String noticeDelete(int id) {
		try {
			noticeService.deleteNotice(id);
			return "notice/noticeDeleteComplete";
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
	}
}
