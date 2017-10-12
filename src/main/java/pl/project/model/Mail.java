package pl.project.model;

import org.hibernate.validator.constraints.NotBlank;

import pl.project.validation.ValidEmail;

public class Mail {

	@NotBlank
	private String name;
	@NotBlank
	private String subject;
	@ValidEmail
	private String email;
	@NotBlank
	private String message;
	
	public Mail(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
