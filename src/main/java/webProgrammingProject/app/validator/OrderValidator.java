package webProgrammingProject.app.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import webProgrammingProject.app.model.Order;

@Component
public class OrderValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Order.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Order order = (Order) target;
		if(!validatePattern(order.getEmail())) {
			errors.rejectValue("email", "email.regex");
		}
	}
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static boolean validatePattern(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
	
}
