package net.kanth.service.demo;

import org.springframework.stereotype.Service;

@Service("upipayment")
public class UpiPayment implements InterfacePayment{

	@Override
	public String doPayment() {
		System.out.println("Going to do UPI Payment");
		return "Going to do UPI Payment";
	}

}
