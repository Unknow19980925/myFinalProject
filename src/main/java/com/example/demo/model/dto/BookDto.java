package com.example.demo.model.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
//思考一下price的範圍,再輸入至message.proparties
	//rentId
	@NotNull(message = "{bookDto.bookId.notNull}")
	private Integer bookId;
	
	@NotNull(message = "{bookDto.bookName.notNull}")
	private String bookName;
	
	@NotNull(message = "{bookDto.author.notNull}")
	private String author;
	
	@NotNull(message = "{bookDto.publisher.notNull}")
	private String publisher;
	
	@NotNull(message = "{bookDto.bookPrice.notNull}")
	@Positive
	private Double bookPrice;

	@NotNull(message = "{bookDto.statusName.notNull}")
	private Boolean statusName;
	
	
}
