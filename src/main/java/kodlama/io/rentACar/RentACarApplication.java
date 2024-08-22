package kodlama.io.rentACar;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kodlama.io.rentACar.core.utilities.exceptions.BusinessException;
import kodlama.io.rentACar.core.utilities.exceptions.ProblemDetailes;
import kodlama.io.rentACar.core.utilities.exceptions.ValidationProblemDetailes;

@SpringBootApplication
@RestControllerAdvice
public class RentACarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentACarApplication.class, args);
	}
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetailes handleBusinessException(BusinessException businessException) {
		ProblemDetailes problemDetailes = new ProblemDetailes();
		problemDetailes.setMessage(businessException.getMessage());
		return problemDetailes;
	}
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ProblemDetailes handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
		ValidationProblemDetailes validationProblemDetailes = new ValidationProblemDetailes();
		validationProblemDetailes.setMessage("validation exception");
		
		for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
			validationProblemDetailes.getValidationErrors().put(fieldError.getField(), fieldError.getDefaultMessage() );
		}
		validationProblemDetailes.setValidationErrors(new HashMap<String, String>());
		return validationProblemDetailes;
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	} 
	
}
