package net.kanth.exceptions;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionalHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND.value() + "",
				LocalDateTime.now(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails("An internal server error occurred",
				HttpStatus.INTERNAL_SERVER_ERROR.value() + "", LocalDateTime.now(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleArguementError(MethodArgumentNotValidException exception,
			HttpHeaders headers,HttpStatusCode code,WebRequest request){
		
		Map<String, String> errorDetails = new HashMap<>();
		List<ObjectError> lstObj =  exception.getBindingResult().getAllErrors();
		
		lstObj.forEach(p -> {
			String fieldName = ((FieldError) p).getField();
			String fieldValue = p.getDefaultMessage();
			errorDetails.put(fieldName, fieldValue);

		});
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

	}
	
	 @ExceptionHandler(TransactionSystemException.class)
	    public ResponseEntity<ErrorDetails> handleTransactionException(TransactionSystemException ex,WebRequest request) {

	        Throwable root = ex.getRootCause();

	        // Constraint violations during commit
	        if (root instanceof jakarta.validation.ConstraintViolationException cve) {
	            return ResponseEntity.badRequest().body(
	                    new ErrorDetails(
	                            "VALIDATION_FAILED",
	                            cve.getConstraintViolations()
	                               .stream()
	                               .map(v -> v.getMessage())
	                               .findFirst()
	                               .orElse("Validation failed"),
	                            LocalDateTime.now(),
	                            request.getDescription(false)
	                    )
	            );
	        }

	        // Fallback
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                new ErrorDetails(
	                        "TX_COMMIT_FAILED",
	                        "Transaction failed while saving data",
	                        LocalDateTime.now(),
	                        request.getDescription(false)
	                )
	        );
	    }
	 
	 @ExceptionHandler(BadRequestException.class)
	 public ResponseEntity<?> badRequest(WebRequest request,BadRequestException exception){		 
		 ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(),HttpStatus.BAD_REQUEST+"",LocalDateTime.now(),request.getDescription(false));		 
		 return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
	 }
	 
	 @ExceptionHandler(DataIntegrityViolationException.class)
	 public ResponseEntity<?> dataIntegrityException(DataIntegrityViolationException ex,WebRequest request){
		 
		 String message = "Duplicate value";
		   Throwable root = ex.getRootCause();
	        if (root != null && root.getMessage() != null) {
	            if (root.getMessage().contains("username")) {
	                message = "Username already exists";
	            }else if (root.getMessage().contains("phoneNo")) {
	            	 message = "PhoneNo already exists";
	            }
	        }
		ErrorDetails errorDetails = new ErrorDetails(message, HttpStatus.CONFLICT+"", LocalDateTime.now(), request.getDescription(false));
		 return new ResponseEntity<>(errorDetails,HttpStatus.CONFLICT);
	 }
}
