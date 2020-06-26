package word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wordbook")
public class WordbookController {
	//단어장 목록 조회 기능
	@GetMapping("showlist")
	public String wordbookListShow() {
		return "wordbook/wordbookList";
	}
	
	//단어장 생성 페이지
	@GetMapping("form")
	public String wordbookForm() {
		return "wordbook/wordbookUpdateForm";
	}
	
	@PostMapping("update")
	public String wordbookInsert() {
		//핵심기능!
		return "";
	}
}
