package com.bookstore.books;

import com.bookstore.books.models.Books;
import com.bookstore.books.repositories.BookRepository;

import org.springframework.http.MediaType;

import java.text.ParseException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin(maxAge = 3600)
public class BooksApplication implements CommandLineRunner {

	private final BookRepository bookRepository;
	
    @Autowired
    public BooksApplication(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	public void run(String... args) throws Exception {
		
    }
	
	@PostMapping(path = "/add-book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addBook(@RequestBody Map<String, Map<String, String>> data) throws Exception, ParseException {
		bookRepository.save(new Books(
					data.get("book-data").get("title"),
					data.get("book-data").get("author"),
					Float.parseFloat(data.get("book-data").get("price")),
					data.get("book-data").get("date")
				));
		
		return "Book Added Successfully";
	}
	
	@RequestMapping(value = "/get-books")
	public HashMap<Integer, Object> getBooks() 
	{
	    HashMap<Integer, Object> map = new HashMap<>();
	    int i=0;
	    for(Books book: bookRepository.findAll())
	    {
            map.put(i++, book);
        }
	    return map;
	}
	
}
