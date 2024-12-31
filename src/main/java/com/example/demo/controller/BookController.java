package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.exception.BookException;
import com.example.demo.model.dto.BookDto;
import com.example.demo.repository.BookStatusRepository;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;


@Controller
@RequestMapping(value = {"/book","/books"})
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookStatusRepository bookStatusRepository;

	@GetMapping
	public String getBooks(Model model, @ModelAttribute BookDto bookDto) {
		model.addAttribute("bookDto",new BookDto());
		List<BookDto> bookDtos = bookService.getAllBooks();
		model.addAttribute("bookDtos", bookDtos);
		model.addAttribute("bookstatus"	,bookStatusRepository.findAll());
		return "book/book";
	}
	
	@PostMapping
	// @Valid 進行驗證
	// BindingResult 驗證結果
	public String addBook(@ModelAttribute BookDto bookDto,  Model model) {
		bookService.addBook(bookDto);
		return "redirect:/books"; // 重導到 /books 頁面
	}
	
	
	@GetMapping("/delete/{bookId}")
	public String deleteBook(@PathVariable Integer bookId) {
		bookService.deleteBook(bookId);
		return "redirect:/books"; // 重導到 /books 頁面
	}
	
	@GetMapping("/{bookId}")
	public String getBook(@PathVariable Integer bookId, Model model) {
		Optional<BookDto> bookDtoOpt = bookService.getBookById(bookId); 
		if (bookDtoOpt.isPresent()) {
		        model.addAttribute("bookDto", bookDtoOpt.get());  // 解包並傳遞 BookDto
		    } else {
		        model.addAttribute("message", "找不到BookID: " + bookId);
		    }
		    return "book/book_update";  // 返回更新頁面
	}
	
	@PostMapping("/update/{bookId}")
	public String updateBook(@PathVariable Integer bookId, @Valid @ModelAttribute BookDto bookDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) { // 若有錯誤發生
			model.addAttribute("bookDto", bookDto); // 將原本的 bookDto 回傳
			return "book/book_update"; // 會自動將錯誤訊息傳給 jsp
		}
		bookService.updateBook(bookDto);
		return "redirect:/books"; // 重導到 /books 頁面
	}
	
	@ExceptionHandler({BookException.class})
	public String handleBookException(BookException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
	}
}
