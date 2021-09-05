package webProgrammingProject.app.validator;


import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import webProgrammingProject.app.model.Order;

@Component
public class ProperDateValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Order.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Order order = (Order) target;
		if(order.getPreferredDate().equals("")) {
			errors.rejectValue("preferredDate", "emptyDate");
		}
		else if(order.get_preferredDate().compareTo(LocalDate.now())<0) {
			errors.rejectValue("preferredDate", "prefferredate.past");
		}
	}

}
