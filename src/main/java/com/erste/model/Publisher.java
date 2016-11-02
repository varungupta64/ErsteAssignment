package com.erste.model;

import org.springframework.stereotype.Component;

@Component
public class Publisher {
	String name;

	public Publisher() {

	}

	public Publisher(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
