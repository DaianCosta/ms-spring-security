package com.rasmoo.client.financescontroll.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rasmoo.client.financescontroll.v1.constant.TypeEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "ENTRY")
public class Entry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5367268877193247709L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "type")
	private TypeEnum tipo;
	
	@Column(name = "date")
	private Date data = new Date();
	
	@Column(name = "value")
	private int valor;
	
	@OneToOne(cascade = CascadeType.REFRESH)
	private Category categoria = new Category();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
}
