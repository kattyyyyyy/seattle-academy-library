package jp.co.seattle.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

/**
 * 検索コントローラー
 */
@Controller
public class SearchBookController {
	
    @Autowired
    BooksService booksService;
	
	@RequestMapping(value = "/searchBook", method = RequestMethod.POST)
	 public String searchBook(@RequestParam("word")String word,
			 @RequestParam("search")String search,
			 Model model) {
		
		model.addAttribute("bookList", booksService.getSearchBookInfo(word, search));
		return "home";
		 
	 }

}
