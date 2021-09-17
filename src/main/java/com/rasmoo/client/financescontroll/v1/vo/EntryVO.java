package com.rasmoo.client.financescontroll.v1.vo;

import java.util.Date;

import com.rasmoo.client.financescontroll.v1.constant.TypeEnum;

import lombok.Data;

@Data
public class EntryVO {
	
	private Long id;

	private TypeEnum tipo;

	private Date data = new Date();

	private int valor;

	private Long categoryId;
	
}
