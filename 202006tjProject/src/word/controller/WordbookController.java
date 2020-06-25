package word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wordbook")
public class WordbookController {
	//단어장 목록 조회 기능
	public String wordbookListShow() {
		return "";
	}
	//단어장 생성 기능
	public String wordbookInsert() {
		//핵심기능!
		return "";
	}
}
