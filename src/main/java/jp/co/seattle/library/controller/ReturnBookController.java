package jp.co.seattle.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.RentalInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentalsService;

/**
 * 返却コントローラー
 */
@Controller
public class ReturnBookController {

	@Autowired
    RentalsService rentalsService;
    @Autowired
    BooksService booksService;
	
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public String returnBook(@RequestParam("bookId")int bookId,
    		Model model) {
		
		RentalInfo rentalInfo = rentalsService.getRentInfo(bookId);
		if(rentalInfo != null && rentalInfo.getRentDate() != null) {
			rentalsService.updateReturnBook(bookId);
		} else {
			model.addAttribute("rentErrorMessage", "貸出しされていません。");
		}
		
		
		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		return "details";
	}
	
}
