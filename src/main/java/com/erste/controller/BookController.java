package com.erste.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erste.exceptions.BookException;
import com.erste.model.Book;
import com.erste.model.ErrorResponse;
import com.erste.service.BookService;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@RequestMapping("/")
	public String home() {
		return "Erste Assignment!";
	}

	@RequestMapping("/books")
	public List<Book> getBooks(@RequestParam(required = false) String sort,
			@RequestParam(required = false) String order) {
		List<Book> list = bookService.getBooks();

		if (sort != null) {
			bookService.sortBooklist(list,sort);
		}
		
		if(order != null && order.equals("DESC")){
			Collections.reverse(list);
		}

		return list;
	}

	@RequestMapping("/books/{id}")
	public Book getBookById(@PathVariable int id) throws BookException {
		Book b = null;
		b = bookService.getBookById(id);

		if (b != null) {
			return b;
		} else {
			throw new BookException("ID NOT FOUND");
		}
	}

	@RequestMapping(value = "/books", method = RequestMethod.POST)
	public Book addBook(@RequestBody(required = false) Book book) throws BookException {
		if (book != null) {
			return bookService.addBook(book);
		} else {
			return bookService.addBook();
		}
	}

	@RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
	public List<Book> deleteBook(@PathVariable int id) throws BookException {
		if (null != bookService.getBookById(id)) {
			return bookService.deleteBook(id);
		} else {
			throw new BookException("ID NOT FOUND");
		}
	}

	@RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
	public Book updateBook(@PathVariable int id, @RequestBody Book book) throws BookException {
		if (null != bookService.getBookById(id)) {
			return bookService.updateBook(id, book);
		} else {
			throw new BookException("ID NOT FOUND");
		}
	}

	@ExceptionHandler(BookException.class)
	public ErrorResponse exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return error;
	}
}
