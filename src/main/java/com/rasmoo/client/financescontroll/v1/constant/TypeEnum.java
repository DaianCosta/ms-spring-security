package com.rasmoo.client.financescontroll.v1.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {
	
	DESPESA(0),
	RECEITA(1);
	
	@JsonValue
	private int valor;
}
