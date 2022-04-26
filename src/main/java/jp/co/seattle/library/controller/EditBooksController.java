package jp.co.seattle.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * 詳細表示コントローラー
 */
@Controller
public class EditBooksController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService booksService;
    
    @Autowired
    private ThumbnailService thumbnailService;

    @Transactional
    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
    public String editBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
        logger.info("Welcome EditControler.java! The client locale is {}.", locale);

        model.addAttribute("bookEditInfo", booksService.getBookInfo(bookId));

        return "editBook";
    }
    

    /**
     * 編集画面に遷移する
     * @param locale
     * @param bookId
     * @param model
     * @return
     */
    @RequestMapping(value = "/correntBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String correctBook(Locale locale,
    		@RequestParam("bookId") int bookId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("publish_date") String publishDate,
            @RequestParam("isbn") String isbnCode,
            @RequestParam("detail_text") String detailText,
            @RequestParam("thumbnail") MultipartFile file,
            Model model) {
    	logger.info("Welcome editBooks.java! The client locale is {}.", locale);
    	
    	
    	BookDetailsInfo bookInfo = new BookDetailsInfo();
    	bookInfo.setBookId(bookId);
    	bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setIsbn(isbnCode);
        bookInfo.setText(detailText);
        
        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookEditInfo", bookInfo);
                return "editBook";
            }
        }
    	
        List<String> list = new ArrayList<String>();
        list = booksService.validationCheck(title, author, publisher, publishDate, isbnCode);
        
        if(list.size() > 0) {
        	model.addAttribute("editErrorMessage", list);
        	model.addAttribute("bookEditInfo", bookInfo);
        	return "editBook";
        }
        
        booksService.updateBook(bookInfo);
        
        model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookInfo.getBookId()));
    	return "details";
    }
}
