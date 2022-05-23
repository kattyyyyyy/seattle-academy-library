package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 貸出情報格納DTO
 *
 */
@Configuration
@Data
public class RentalInfo {

	private int bookId;
	
    private String title;

    private String rentDate;

    private String returnDate;
    
}
