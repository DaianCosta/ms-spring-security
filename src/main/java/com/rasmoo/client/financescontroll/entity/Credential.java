package com.rasmoo.client.financescontroll.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Credential implements Serializable{
	
	private String email;

	private String senha;
	
}
