package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentalsService;

/**
 * 貸し出しコントローラー
 */
@Controller
public class RentalBookController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    RentalsService rentalsService;
    @Autowired
    BooksService booksService;
	
	@RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook(@RequestParam("bookId")int bookId,
    		Model model) {
		if(rentalsService.getBookInfo(bookId) == 0) {
			
			rentalsService.rentalBook(bookId);
			return "redirect:/home";
			
		}
		
		model.addAttribute("rentErrorMessage", "貸出し済みです。");
		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		return "details";
	}
}
