package com.perscholas.model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Author {
	private int id;
	private String name;
	public Author() {
		
	}
	public Author(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + "]";
	}
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
