package com.online.platform.learning.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Chapter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String coursename;
	private String chapter1name;
	private String chapter1id;
	private String chapter2name;
	private String chapter2id;
	private String chapter3name;
	private String chapter3id;
	private String chapter4name;
	private String chapter4id;
	private String chapter5name;
	private String chapter5id;
	private String chapter6name;
	private String chapter6id;
	private String chapter7name;
	private String chapter7id;
	private String chapter8name;
	private String chapter8id;
	
	public Chapter() 
	{
		super();
	}

	public Chapter(int id, String coursename, String chapter1name, String chapter1id, String chapter2name, String chapter2id, String chapter3name, String chapter3id, String chapter4name, String chapter4id, String chapter5name, String chapter5id, String chapter6name, String chapter6id, String chapter7name, String chapter7id, String chapter8name, String chapter8id) {
		super();
		this.id = id;
		this.coursename = coursename;
		this.chapter1name = chapter1name;
		this.chapter1id = chapter1id;
		this.chapter2name = chapter2name;
		this.chapter2id = chapter2id;
		this.chapter3name = chapter3name;
		this.chapter3id = chapter3id;
		this.chapter4name = chapter4name;
		this.chapter4id = chapter4id;
		this.chapter5name = chapter5name;
		this.chapter5id = chapter5id;
		this.chapter6name = chapter6name;
		this.chapter6id = chapter6id;
		this.chapter7name = chapter7name;
		this.chapter7id = chapter7id;
		this.chapter8name = chapter8name;
		this.chapter8id = chapter8id;
	}


}