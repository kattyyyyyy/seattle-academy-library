package jp.co.seattle.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentalsService;

/**
 * 削除コントローラー
 */
@Controller //APIの入り口
public class DeleteBookController {
    
    @Autowired
    private BooksService booksService;
    
    @Autowired
    private RentalsService rentalsService;

    /**
     * 対象書籍を削除する
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public String deleteBook(
            @RequestParam("bookId") Integer bookId,
            Model model) {
        
		if(rentalsService.getRentInfo(bookId) == 0) {
			booksService.deleteBook(bookId);
		} else {
			model.addAttribute("rentErrorMessage", "貸出しされているため削除できません。");
			model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
			return "details";
		}
		
        model.addAttribute("bookList", booksService.getBookList());
        return "home";

    }

}
