package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 訂單
+----------+---------+------------+--------------+------------+--------------+--------------+--------------+
| rent_id  | user_id | rent_date  |  book_id     | unit_price | total_amount | rent_status  |
+----------+---------+------------+--------------+------------+--------------+--------------+--------------+
| 1        | 1       | 2024-09-19 | 1            | 30000.00   | 60000.00     | Finished     |
| 2        | 1       | 2024-09-20 | 2            | 15000.00   | 75000.00     | Finished     |
| 3        | 3       | 2024-09-21 | 3            | 3000.00    | 9000.00      | Pending      |
| 4        | 4       | 2024-09-22 | 2            | 15000.00   | 15000.00     | Cancel       |
| 5        | 5       | 2024-09-23 | 5            | 8000.00    | 32000.00     | Pending      |
+----------+---------+------------+--------------+----------+------------+--------------+--------------+
**/
import org.springframework.web.bind.annotation.RequestMapping;
//service 業務邏輯
//repository資料庫
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.dto.RentItemDto;
import com.example.demo.model.dto.RentListDto;
import com.example.demo.model.dto.UserCert;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.BookStatusRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.BookService;
import com.example.demo.service.RentItemService;
import com.example.demo.service.RentListService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/rentlist")
public class RentListController {

	@Autowired
	private RentListService rentListService;

	@Autowired
	private BookService bookService;

	@Autowired
	private RentItemService rentItemService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BookStatusRepository bookStatusRepository;

	@GetMapping("/bookrent")
	public String showBookRentList(@ModelAttribute BookDto bookDto, Model model) {
		model.addAttribute("bookDto", new BookDto());
		List<BookDto> bookDtos = bookService.getAllAvaliableBooks(true);
		model.addAttribute("bookDtos", bookDtos);
		model.addAttribute("bookstatus", bookStatusRepository.findAll());
		return "/book/bookrent";// bookrent.jsp
	}

	@PostMapping("/rent/{bookId}")
	public String rentBook(@PathVariable Integer bookId, HttpSession session, Model model)
			throws UserNotFoundException {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		if (userCert == null) {
			return "redirect:/login";
		}
		try {
			BookDto bookDto = bookService.getBookById(bookId).orElseThrow();

			RentItemDto rentItemDto = rentItemService.createRentItem(bookDto);
			rentItemDto.setBooks(List.of(bookDto));
			RentListDto rentListDtos = rentListService.getPendingRentListByStatus("pending");
			if (rentListDtos == null) {
				rentListDtos = rentListService.addBookToRent(rentItemDto, userCert.getUserId());
			}
			rentListDtos.setRentItemDto(rentItemDto);
			RentItemDto test = rentListDtos.getRentItemDto();
			model.addAttribute("rentListDtos", test);
			return "rentlist/checkout";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/error";
	}

	@GetMapping("/list")
	public ResponseEntity<ApiResponse<List<RentListDto>>> getRentList(Model model, @RequestParam Integer userId,
			@RequestParam(required = false) String rentStatus, HttpSession session) {
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		if (userDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(403, "未登入和session已過期"));
		}
		// 直接抓
		List<RentListDto> rentListDtos = rentListService.getAllRentLists(userId, rentStatus);
		return ResponseEntity.ok(ApiResponse.success("查詢成功", rentListDtos));
	}

	@GetMapping("/checkout")
	public String rentCheckout(HttpSession session, Model model) {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		if (userCert == null) {
			return "redirect:/login";
		}
		RentListDto rentListDto = rentListService.getPendingRentListByStatus("pending");
		if (rentListDto == null) {
			model.addAttribute("errorMessage", "租借清單為空,請選擇書籍租借");
			return "redirect:bookrent";
		}
		model.addAttribute("rentListDto", rentListDto);
		return "rentlist/checkout";
	}

	@PostMapping("/addBook")
	public String addBookToRentList(@RequestBody RentItemDto rentItemDto, @RequestParam Boolean statusName,
			Model model) {
		try {
			RentListDto rentListDto = (RentListDto) model.getAttribute("rentListDto");
			if (rentListDto == null) {
				rentListDto = new RentListDto();
			}
			rentListService.updateBookToRentList(rentItemDto, rentListDto, statusName);

			model.addAttribute("rentListDto", rentListDto);
			return "redirect:/bookrent";

		} catch (Exception e) {
			return "redirect:/error";
		}
	}

	@PostMapping("/rent/finish")
	public String finishCheckout(Model model, HttpSession session) {
		UserCert userCert=(UserCert)session.getAttribute("userCert");
		RentListDto rentListDto=(RentListDto)model.getAttribute("rentListDto");
		if(rentListDto==null) {
			rentListDto=new RentListDto();
		}
		rentListService.updateRentListStatus(userCert.getUserId(), "pending", "finish");
		List<RentListDto>rentListFinishDtos =rentListService.getAllRentLists(userCert.getUserId(),"finish");
		model.addAttribute("rentListFinishDtos",rentListFinishDtos);
		return "rentlist/history";
	}
	@PostMapping("/rent/cancel")
	public String cancelCheckout(Model model,HttpSession session) {
		UserCert userCert=(UserCert)session.getAttribute("userCert");
		RentListDto rentListDto=(RentListDto)model.getAttribute("rentListDto");
		if(rentListDto==null) {
			rentListDto=new RentListDto();
		}
		rentListService.updateRentListStatus(userCert.getUserId(), "pendng", "cancel");
		List<RentListDto>rentListCancelDtos=rentListService.getAllRentLists(userCert.getUserId(), "cancel");
		model.addAttribute("rentListCancelDtos",rentListCancelDtos);
		return"rentlist/history";
	}

	@GetMapping("/finish")
	public String finishCheckoutRent(HttpSession session, Model model) {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		rentListService.updateRentListStatus(userCert.getUserId(), "pending", "finish");
		List<RentListDto>rentListFinishDtos=rentListService.getAllRentLists(userCert.getUserId(), "finish");
		model.addAttribute("rentListFinishDtos",rentListFinishDtos);
		model.addAttribute("message", "結帳完成");
		return "rentlist/history";
	}

	@GetMapping("/cancel")
	public String cancelCheckoutRent(HttpSession session, Model model) {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		rentListService.updateRentListStatus(userCert.getUserId(), "pending", "cancel");
		List<RentListDto>rentListCancelDtos=rentListService.getAllRentLists(userCert.getUserId(), "cancel");
		model.addAttribute("message", "結帳取消");
		return "rentlist/history";
	}
	@GetMapping("/history")
	public String checkoutHistory(HttpSession session, Model model) {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		 List<RentListDto> rentListFinishDtos = rentListService.getAllRentLists(userCert.getUserId(), "finish");
		 List<RentListDto> rentlistCancelDtos = rentListService.getAllRentLists(userCert.getUserId(), "cancel");
		 model.addAttribute("rentListFinishDtos", rentListFinishDtos);
		 model.addAttribute("rentlistCancelDtos", rentlistCancelDtos);
		    return "rentlist/history";
	}
	

}
