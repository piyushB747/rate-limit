package net.kanth.service.demo;

import org.springframework.stereotype.Service;

@Service("cardpayment")
public class CardPayment implements InterfacePayment{
	@Override
	public String doPayment() {
		System.out.println("Going to do Card Payment");
		return """
				Going to do Card Payment
				""";
	}
}
