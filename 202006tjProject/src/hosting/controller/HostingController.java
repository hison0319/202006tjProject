package hosting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class HostingController {
	
	@GetMapping("show")
	public String showIndex() {
		return "helloProject";
	}
}
