package member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;

import member.dto.MemberDto;
import member.dto.MemberVO;
import member.dto.MemberVOForAPI;
import member.service.MemberService;
import word.dto.WordbookDto;
import word.service.WordbookService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	MemberService memberService;
	@Autowired
	WordbookService wordbookService;

	// 회원정보 리스트 조회 기능
	@GetMapping("/showInfo")
	public String memberInfoListShow() {
		return "/account/showInfo";
	}

	// 회원정보 수정 기능-비동기?
	@GetMapping("/confirmP")
	public String memberConfirmPasswordForm() {
		return "/account/confirmPassword";
	}

	@ResponseBody
	@PostMapping("/confirmP")
	public String memberConfirmPassword(HttpSession session, String passwordC) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if (member.getPassword().equals(passwordC)) {
			return "t";
		} else {
			return "f";
		}
	}

	// 회원 정보 수정
	@GetMapping("/update")
	public String memberModifyForm() {
		return "/account/modify";
	}

	@PostMapping("/update")
	public String memberModify(HttpSession session, @ModelAttribute("MemberVo") @Valid MemberVO memberVo,
			BindingResult result, Model m) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		MemberDto changeMember = new MemberDto(memberVo.getMemberId(), memberVo.getPassword(), memberVo.getEmail(),
				memberVo.getPhone(), memberVo.getAddress());
		// 생성자에 없는 id값을 넣어줌
		changeMember.setId(memberVo.getId());
		// 만약 메일을 바꾸면 인증을 취소함.
		if (loginMember.getEmail().equals(changeMember.getEmail())) {
			changeMember.setCertified(loginMember.getCertified());
		} else {
			changeMember.setCertified(0);
		}
		// 유효성 검사
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fe : errors) {
				m.addAttribute("e" + fe.getField(), fe.getField());
			}
			return "account/modify";
		} else {
			try {
				memberService.updateMember(changeMember);
			} catch (Exception e) {
				return "account/modify";
			}
		}
		// 마지막 값(regDate)을 채워줌
		changeMember.setRegDate(loginMember.getRegDate());
		// 로그인 된 맴버의 정보를 최종 변경
		session.setAttribute("loginMember", changeMember);
		return "account/modifyComplete";
	}

	// 비동기 이메일, 폰번호 검사
	@ResponseBody
	@PostMapping("/confirmEmail")
	public String confirmEmail(HttpSession session, String email) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if (member.getEmail().equals(email)) {
			return "s";
		} else {
			if (memberService.selectMemberByEmail(email) == null) {
				return "t";
			} else {
				return "f";
			}
		}
	}

	@ResponseBody
	@PostMapping("/confirmPhone")
	public String confirmPhone(HttpSession session, String phone) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if (member.getPhone().equals(phone)) {
			return "s";
		} else {
			if (memberService.selectMemberByPhone(phone) == null) {
				return "t";
			} else {
				return "f";
			}
		}
	}

	@GetMapping("/forAPIupdate")
	public String APIModifyForm() {
		return "/account/modifyAPI";
	}

	@PostMapping("forAPIupdate")
	public String APIModify(@ModelAttribute("MemberVo") @Valid MemberVOForAPI memberVo, BindingResult result, Model m,
			HttpSession session) {
		MemberDto member = new MemberDto();
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		MemberDto changeMember = new MemberDto(memberVo.getMemberId(), memberVo.getPassword(), memberVo.getEmail(),
				memberVo.getPhone(), memberVo.getAddress());
		changeMember.setId(memberVo.getId());
		if (loginMember.getEmail().equals(changeMember.getEmail())) {
			changeMember.setCertified(loginMember.getCertified());
		} else {
			changeMember.setCertified(0);
		}
		;
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			System.out.println(result.toString());
			for (FieldError fe : errors) {
				m.addAttribute("e" + fe.getField(), fe.getField());
				System.out.println(fe.getField());
			}
			m.addAttribute("member", member);
			return "account/modifyAPI";
		} else {
			try {
				memberService.updateMember(changeMember);
			} catch (Exception e) {
				m.addAttribute("member", member);
				return "account/modifyAPI";
			}
		}
		changeMember.setRegDate(loginMember.getRegDate());
		session.setAttribute("loginMember", changeMember);
		return "account/modifyComplete";
	}

	// 공유한 단어장 조회기능
	@GetMapping("showSharingList")
	public String wordbookListShow(HttpSession session, Model m, String pageNumStr) { // 세션 모델
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		int pageNum = pageNumStr == null || pageNumStr == "" ? 1 : Integer.parseInt(pageNumStr);
		int ea = 5;// 페이지에 띄울 갯수 정의(정책)
		int loginId = loginMember.getId();
		// 단어장 총 갯수
		int totalCnt = wordbookService.selectWordbookCountSharing(loginId);
		// 페이지 리스트
		int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
		List<Integer> pageNumList = getPageList(pageNum, ea, pages);
		m.addAttribute("pageNumList", pageNumList);
		m.addAttribute("pageNum", pageNum);
		m.addAttribute("pages", pages);
		// 공유 인원 리스트
		List<Integer> sharingNumlist = wordbookService.selectSharingCheckGroupByTitle(loginId, (pageNum - 1) * 5, ea);
		// 단어장 리스트
		List<WordbookDto> wordbooklist = wordbookService.selectWordbookSharingJoin(loginId, (pageNum - 1) * 5, ea);
		// 등록일을 날짜만 표현
		for (int i = 0; i < wordbooklist.size(); i++) {
			if (wordbooklist.get(i).getuDate() != null)
				wordbooklist.get(i).setuDateStr(wordbooklist.get(i).getuDate().toString().substring(0, 10));
		}
		if (wordbooklist.size() == 0) {
			m.addAttribute("listNull", "단어장을 공유해보세요!");
		} else {
			m.addAttribute("listNull", "");
		}
		m.addAttribute("sharingNumlist", sharingNumlist);
		m.addAttribute("wordbooklist", wordbooklist);
		return "account/shareList";
	}

	// 페이지 네이션 구현 기능
	public List<Integer> getPageList(int pageNum, int ea, int pages) {
		List<Integer> pageNumList = new ArrayList<Integer>();
		int begin;
		if (pageNum % 5 == 1) {
			begin = pageNum;
		} else if (pageNum % 5 == 0) {
			begin = pageNum - 4;
		} else {
			begin = pageNum - (pageNum % 5 - 1);
		}
		for (int i = begin; i <= pages; i++) {
			pageNumList.add(i);
		}
		return pageNumList;
	}
	
	// 공유한 회원목록 조회기능
	@PostMapping("showSharingMemberList")
	@ResponseBody
	public List<WordbookDto> sharingMemberListShow(HttpSession session, String title) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		List<WordbookDto> sharingWordbookMemberList = wordbookService.selectSharingMemberCheckByTitle(loginMember.getId(), title);
		System.out.println(sharingWordbookMemberList);
		return sharingWordbookMemberList;
	}
	
	//공유한 회원 삭제
	@PostMapping("deleteSharingMember")
	@ResponseBody
	public String deleteSharingMember(String id) {
		//exception처리
		System.out.println(id);
		int wordbookId = Integer.parseInt(id);
		wordbookService.deleteWordbook(wordbookId);
		return '"'+"t"+'"';
	}
	
	//회원 탈퇴
	@GetMapping("/delete")
	public String memberDelete(int id, HttpSession session) {
		memberService.deleteMember(id);
		session.removeAttribute("loginMember");  //세션에서 로그인 정보 삭제
		if(session.getAttribute("access_token") != null) {
			JsonNode accessToken = (JsonNode) session.getAttribute("access_token");
			session.removeAttribute("access_token");
		}
		return "/account/memberDeleteComplete";
	}
}
