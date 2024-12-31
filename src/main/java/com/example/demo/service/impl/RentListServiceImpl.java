package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.RentListMapper;
import com.example.demo.model.dto.BookDto;
import com.example.demo.model.dto.RentItemDto;
import com.example.demo.model.dto.RentListDto;
import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.BookStatus;
import com.example.demo.model.entity.RentItem;
import com.example.demo.model.entity.RentList;
import com.example.demo.model.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.RentItemRepository;
import com.example.demo.repository.RentListRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RentItemService;
import com.example.demo.service.RentListService;

import jakarta.transaction.Transactional;

@Service
public class RentListServiceImpl implements RentListService {

	@Autowired
	private RentListRepository rentListRepository;

	@Autowired
	RentListMapper rentListMapper;

	@Autowired
	RentItemService rentItemService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	RentItemRepository rentItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<RentListDto> getAllRentLists(Integer userId, String rentStatus) {
		List<RentList> rentLists = rentListRepository.findByUserUserId(userId);

		// 将 RentList 转换为 RentListDto
		return rentLists.stream().map(rentList -> {
			// 映射 RentList 到 RentListDto
			RentListDto rentListDto = modelMapper.map(rentList, RentListDto.class);
			
			// 获取 RentItem 并映射到 RentItemDto
			RentItem rentItem = rentList.getRentItem();
			if(rentItem != null) {
				RentItemDto rentItemDto = modelMapper.map(rentItem, RentItemDto.class);
				rentListDto.setRentItemDto(rentItemDto);
			}
			
			return rentListDto; // 返回映射后的 DTO
		}).collect(Collectors.toList());
	}

	private Double cacullateLatee(RentList rentList) {
		double latefee = rentList.getLatefee();
		String sendday = rentList.getDueDate();
		String returndate = rentList.getReturnDate();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dueDate = LocalDate.parse(sendday, formatter);
		LocalDate returnDate = LocalDate.parse(returndate, formatter);
		long overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
		overdueDays = Math.max(overdueDays, 0); // 確保逾期天數不為負數
		return overdueDays * latefee;
	}

	private Double calculateSubtotal(Book book) {
		return book.getBookPrice();
	}

	@Override
	public void batchUpdateRentListStatus(List<Integer> rentIds, String rentStatus) {// bookStatus
		if (rentIds == null || rentIds.isEmpty()) {
			throw new IllegalArgumentException("The rentIds cannot be null or empty.");
		}
		if (rentStatus == null || rentStatus.isBlank()) {
			throw new IllegalArgumentException("The rentliststatus cannot be null or blank.");
		}

		List<RentList> rentLists = rentListRepository.findAllById(rentIds);
		if (rentLists.isEmpty()) {
			throw new IllegalArgumentException("No RentList records found for the given IDs.");
		}

		rentLists.forEach(rentList -> rentList.setRentStatus("finish"));
		rentListRepository.saveAll(rentLists);
	}

	@Override
	public List<RentListDto> findListByUser(User users) {
		return rentListRepository.findByUserUserId(users.getUserId()).stream()
				.map(rent -> modelMapper.map(rent, RentListDto.class)).collect(Collectors.toList());
	}

	@Override
	public void updateBookToRentList(RentItemDto rentItemDto, RentListDto rentListDto, Boolean statusname) {
		for (BookDto bookDto : rentItemDto.getBooks()) {
			Book book = bookRepository.findById(bookDto.getBookId())
					.orElseThrow(() -> new BookNotFoundException("找不到書籍Id: " + bookDto.getBookId()));
			BookStatus bookStatus = new BookStatus();
			bookStatus.setStatusName(statusname);
			book.setBookStatus(bookStatus);
			bookRepository.save(book);
		}
		RentList rentList = modelMapper.map(rentListDto, RentList.class);
		User user = userRepository.findById(rentList.getUser().getUserId())
				.orElseThrow(() -> new RuntimeException("找不到使用者"));
		rentList.setUser(user);
		RentItem rentItem = rentList.getRentItem();
		rentItem.setRentList(rentList);
		rentListRepository.save(rentList);
	}

	@Override
	public RentListDto saveRentList(Integer userId, List<RentItemDto> rentitems) {
		User user = userRepository.findById(userId).orElseThrow();
		List<Book> books = ((RentItemDto) rentitems).getBooks().stream()
				.map(bookDto -> bookRepository.findById(bookDto.getBookId())
						.orElseThrow(() -> new BookNotFoundException("找不到書籍ID: " + bookDto.getBookId() + "，無法租借")))
				.collect(Collectors.toList());

		for (Book book : books) {
			if (!book.getBookStatus().getStatusName()) {
				throw new IllegalArgumentException("書籍ID: " + book.getBookId() + "無法租借");
			}
		}

		RentItem rentItem = new RentItem();
		rentItem.setBooks(books);

		RentList rentList = new RentList();
		rentList.setUser(user);

		rentList.setRentDate(LocalDate.now().toString());
		rentList.setDueDate(LocalDate.now().plusDays(7).toString());
		rentList.setRentStatus("pending");

		rentList.setUnitPrice(books.stream().mapToDouble(Book::getBookPrice).sum());
		rentList.setSubtotal(books.stream().mapToDouble(this::calculateSubtotal).sum());

		// rentList.setLatefee(cacullateLatee(rentList));

		// rentList.setReturnDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd
		// HH:mm:ss")));
		rentItemRepository.save(rentItem);
		rentListRepository.save(rentList);
		return modelMapper.map(rentList, RentListDto.class);
	}

	@Override
	public List<RentListDto> updateRentListStatus(Integer userId, String oldrentStatus, String newrentStatus) {

		List<RentList> rentlists = rentListRepository.findByUserUserIdAndRentStatus(userId, oldrentStatus);

		if (oldrentStatus == newrentStatus) {
			throw new RuntimeException("沒有符合條件的租書清單");
		}
		rentlists.forEach(rentlist -> {
			rentlist.setRentStatus(newrentStatus);
			rentListRepository.saveAll(rentlists);
		});
		return rentlists.stream().map(rentlist -> modelMapper.map(rentlist, RentListDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public RentListDto addBookToRent(RentItemDto rentItemDto, Integer userId) {
		List<Book> books = rentItemDto.getBooks().stream().map(book -> bookRepository.findById(book.getBookId()).get())
				.collect(Collectors.toList());
		RentList rentList = new RentList();

		User user = userRepository.findById(userId).orElseThrow();
		rentList.setUser(user);
		rentList.setRentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		rentList.setDueDate(LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		// rentList.setReturnDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd
		// HH:mm::ss")));
		// rentList.setLatefee(cacullateLatee(rentList));

		// RentItem rentItem=rentList.getRentItem();

		// if(rentItem==null) {
		// rentItem=new RentItem();
		// rentList.setRentItem(rentItem);
		// }
		// rentItem.setBooks(books);
		RentItem rentItem = rentItemRepository.findById(rentItemDto.getRentItemId()).get();
		rentList.setUnitPrice(books.stream().mapToDouble(Book::getBookPrice).sum());
		rentList.setSubtotal(books.stream().mapToDouble(Book::getBookPrice).sum());
		rentList.setRentStatus("pending");
		rentList.setRentItem(rentItem);

		rentListRepository.save(rentList);

		return modelMapper.map(rentList, RentListDto.class);
	}

	@Override
	public RentListDto getPendingRentListByStatus(String rentStatus) {
		if (rentListRepository.findByRentStatus(rentStatus) == null) {
			return null;
		}
		RentList rentList = rentListRepository.findByRentStatus(rentStatus);
		RentListDto rentListDto = modelMapper.map(rentList, RentListDto.class);
		rentListDto.setRentItemDto(modelMapper.map(rentList.getRentItem(), RentItemDto.class));
		return rentListDto;
	}

	@Transactional
	@Override
	public RentListDto rentBook(RentItemDto rentItemDto, Integer userId) throws UserNotFoundException {// 書籍管理在Item,拿Item不用拿bookID
		List<Book> books = rentItemDto.getBooks().stream()
				.map(bookDto -> bookRepository.findById(bookDto.getBookId())
						.orElseThrow(() -> new BookNotFoundException("找不到書籍ID: " + bookDto.getBookId() + "無法租借")))
				.collect(Collectors.toList());
		for (Book book : books) {
			if (!book.getBookStatus().getStatusName()) {
				throw new IllegalStateException("書籍無法租借");
			}
		}
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("找不到使用者" + userId));
		RentList rentList = new RentList();
		rentList.setUser(user);
		rentList.setRentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		rentList.setDueDate(LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		// rentList.setReturnDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd
		// HH:mm::ss")));
		// rentList.setLatefee(cacullateLatee(rentList));

		rentList.setUnitPrice(books.stream().mapToDouble(Book::getBookPrice).sum());
		rentList.setSubtotal(books.stream().mapToDouble(Book::getBookPrice).sum());
		rentList.setRentStatus("pending");

		books.forEach(book -> {
			book.getBookStatus().setStatusName(false);
			bookRepository.save(book);
		});

		RentItem rentItem = modelMapper.map(rentItemDto, RentItem.class);
		rentList.setRentItem(rentItem);

		rentItemRepository.save(rentItem);
		rentListRepository.save(rentList);

		return modelMapper.map(rentList, RentListDto.class);
	}

	@Override
	public RentListDto returnBook(Integer rentId) {// 按完按鈕才
		RentList rentList = rentListRepository.findById(rentId).orElseThrow(() -> new RuntimeException("找不到租借清單"));

		double latefee = 0.0;
		if (rentList.getReturnDate() != null) {
			latefee = cacullateLatee(rentList);
		}
		rentListRepository.findByRentStatus("finish");
		rentList.setLatefee(latefee);
		rentList.setReturnDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		rentList.getRentItem().getBooks().forEach(book -> {
			BookStatus bookStatus = book.getBookStatus();
			bookStatus.setStatusName(true);
		});
		rentListRepository.save(rentList);
		return modelMapper.map(rentList, RentListDto.class);
	}

	@Override
	public RentListDto checkoutRentList(RentListDto rentListDto) {
		RentList rentList = modelMapper.map(rentListDto, RentList.class);
		rentList.setUser(rentList.getUser());
		rentList.setRentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		rentList.setRentStatus("finish");
		rentList = rentListRepository.save(rentList);

		return modelMapper.map(rentList, RentListDto.class);
	}

}
