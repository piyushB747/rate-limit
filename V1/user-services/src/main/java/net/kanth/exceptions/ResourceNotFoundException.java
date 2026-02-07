package net.kanth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public String resource;
	public String key;
	public String value;
	
	public ResourceNotFoundException(String resource, String key, String value) {
		super(String.format("%s not found with %s : '%s' ",resource,key,value));
		this.resource = resource;
		this.key = key;
		this.value = value;
	}
	
	

}