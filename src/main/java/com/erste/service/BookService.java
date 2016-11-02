package com.erste.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.erste.exceptions.BookException;
import com.erste.model.Book;
import com.erste.model.Publisher;

@Service("bookService")
public class BookService {

	private ConcurrentHashMap<Integer, Book> bookMap;

	private static AtomicInteger at = new AtomicInteger(1);

	public List<Book> getBooks() {
		if (null == bookMap) {
			bookMap = createBookList();
			at.set(bookMap.size() + 1);
		}
		return new ArrayList<Book>(bookMap.values());
	}

	public Book addBook() {
		if (null == bookMap) {
			bookMap = new ConcurrentHashMap<>();
		}
		int id = at.getAndIncrement();
		Book b = createBook(id);
		bookMap.put(id, b);
		return b;
	}

	public Book addBook(Book book) {
		if (null == bookMap) {
			bookMap = new ConcurrentHashMap<>();
		}
		int id = at.getAndIncrement();
		book.setId(id);
		bookMap.put(id, book);
		return book;
	}

	private ConcurrentHashMap<Integer, Book> createBookList() {
		ConcurrentHashMap<Integer, Book> bookMap = new ConcurrentHashMap<Integer, Book>(10);

		for (int i = 1; i <= 10; i++) {
			bookMap.put(i, createBook(i));
		}

		return bookMap;
	}

	private Book createBook(int id) {
		Book b1 = new Book();
		b1.setId(id);
		b1.setName("Book" + id);
		b1.setCitation("Citation" + id);

		List<String> categoryList = new ArrayList<String>(3);
		for (int i = 1; i <= 3; i++) {
			categoryList.add("Category" + i * id);
		}
		b1.setCategory(categoryList);

		List<Publisher> publisherList = new ArrayList<Publisher>(2);
		for (int i = 1; i <= 3; i++) {
			publisherList.add(new Publisher("Publisher" + i * id));
		}
		b1.setPublisherList(publisherList);

		Date date = new Date();
		date.setYear(date.getYear() - id%12);
		b1.setDateOfPublish(date);

		Date creationDate = new Date();
		creationDate.setYear(creationDate.getYear() - id%12);
		creationDate.setMonth(creationDate.getMonth() - id%28);
		b1.setCreationDate(creationDate);

		return b1;
	}

	public Book getBookById(int id) {
		if (null != bookMap) {
			return bookMap.get(id);
		}
		return null;
	}

	public List<Book> deleteBook(int id) {
		bookMap.remove(id);
		return new ArrayList<>(bookMap.values());
	}

	public Book updateBook(int id, Book book) throws BookException {
		if (book.getId() != id) {
			throw new BookException("ID MISMATCH");
		} else {
			bookMap.put(id, book);
			return book;
		}
	}

	public void sortBooklist(List<Book> list, String sort) {
		switch (sort) {
		case "id":
			Collections.sort(list, (b1, b2) -> Integer.valueOf(b1.getId()).compareTo(Integer.valueOf(b2.getId())));
			break;
		case "name":
			Collections.sort(list, (b1, b2) -> b1.getName().compareTo(b2.getName()));
			break;
		case "dop":
			Collections.sort(list, (b1, b2) -> {
				try {
					return new SimpleDateFormat("yyyy-MM-dd").parse(b1.getDateOfPublish())
							.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(b2.getDateOfPublish()));
				} catch (ParseException exception) {
					return 1;
				}
			});
			break;
		}
	}

}
