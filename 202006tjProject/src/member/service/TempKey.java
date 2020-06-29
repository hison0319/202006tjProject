package member.service;

import java.util.Random;

public class TempKey {
	private int size;
	
	public String getKey(int size) {
		this.size = size;
		return init();
	}
	
	private String init() {
		Random ran = new Random();
		int num = 0;
		String serial ="";
		do {
			num = ran.nextInt(9);
			serial += num;
		} while(serial.length() < size);

		return serial;
	}
}
