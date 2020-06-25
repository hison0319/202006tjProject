package word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("word")
public class WordController {
	//단어 목록 조회 기능
	public String wordListShow() {
		return "";
	}
	//단어 전체 수정 기능
	public String wordInsert() {
		//핵심기능!
		return "";
	}
}
