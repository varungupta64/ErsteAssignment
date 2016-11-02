package com.erste.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Book {
	private int id;
	private String name;
	private List<Publisher> publisherList;
	private Date dateOfPublish;
	private String citation;
	private List<String> category;
	private Date creationDate;

	public Book() {

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

	public List<Publisher> getPublisherList() {
		return publisherList;
	}

	public void setPublisherList(List<Publisher> publisherList) {
		this.publisherList = publisherList;
	}

	public String getDateOfPublish() {
		return new SimpleDateFormat("yyyy-MM-dd").format(dateOfPublish);
	}

	public void setDateOfPublish(Date dateOfPublish) {
		this.dateOfPublish = dateOfPublish;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public String getCreationDate() {
		return  new SimpleDateFormat("yyyy-MM-dd").format(creationDate);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
