package member.service;

import java.util.Random;

public class TempCharKey {
	private boolean lowerCheck;
	private int size;

	public String getKey(int size, boolean lowerCheck) {
		this.size = size;
		this.lowerCheck = lowerCheck;
		return init();
	}
	//랜덤의 키 생성
	private String init() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		int num = 0;
		do {
			num = ran.nextInt(75) + 48;	//a~z, A~Z
			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char) num);
			} else {
				continue;
			}
		} while (sb.length() < size);
		if (lowerCheck) {	//모든 키를 소문자형태로 반환
			return sb.toString().toLowerCase();
		}	//대소문자 형태로 반환
		return sb.toString();
	}
}
