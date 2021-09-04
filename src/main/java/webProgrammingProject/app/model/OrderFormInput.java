package webProgrammingProject.app.model;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrderFormInput {
	
	@NotEmpty
	@Size(min=5 ,max=50)
	private String customerName;
	
	@NotEmpty
	@Size(min=6)
	private String neighbourhood;
	
	@NotEmpty
	private String district;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String remainingAdress;
	
	@NotEmpty
	private String customerPhone;
	
	@NotEmpty
	private LocalDate prefferredDate;
	
	@NotEmpty
	@NotNull
	private List<Long> itemIdList;
	
	@NotEmpty
	@NotNull
	private List <Integer> itemQuantities;
	
		
}
