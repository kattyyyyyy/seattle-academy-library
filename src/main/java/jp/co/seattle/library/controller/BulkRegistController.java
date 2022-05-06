package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * 一括登録コントローラー
 */
@Controller
public class BulkRegistController {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Autowired
    private BooksService booksService;

    @RequestMapping(value = "/bulkRegist", method = RequestMethod.GET)
    public String bulkBook(Locale locale) {
        // デバッグ用ログ
        logger.info("Welcome EditControler.java! The client locale is {}.", locale);
        return "bulkRegist";
    }
    
    @RequestMapping(value = "/bulkRegist", method = RequestMethod.POST)
    public String bulkRegist(
    		@RequestParam("upload_file")MultipartFile uploadFile,
    		Model model) {
        
        String line = null;
        List<String[]> booksList = new ArrayList<String[]>();
        List<String> errorLine = new ArrayList<String>();
        int count = 0;
        
        try {
            InputStream stream = uploadFile.getInputStream();           
            Reader reader = new InputStreamReader(stream);
            BufferedReader buf = new BufferedReader(reader);
            
            if(!buf.ready()) {
            	model.addAttribute("bulkErrorMessage", "CSVに書籍情報がありません");
            	return "bulkRegist";
            }
            
            while((line = buf.readLine()) != null) {
            	// 行数のインクリメント
            	count++;
                String split[] = line.split(",", -1);
                
                List<String> errorList = booksService.validationCheck(split[0], split[1], split[2], split[3], split[4]);

                if(errorList.size() > 0) {
                	errorLine.add("<p>" + count + "行目の書籍登録でエラーが起きました。</p>");
                } else {
                	booksList.add(split);
                }
            }

        } catch (IOException e) {
        	model.addAttribute("bulkErrorMessage", "CSVファイル読み込み時にエラーが発生しました。");
        	return "bulkRegist";
        }
        
        if(errorLine.size() > 0) {
        	model.addAttribute("bulkErrorMessage", errorLine);
        	return "bulkRegist";
        }
        
        String[] bookList;
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        
        for(int i = 0; i < booksList.size(); i++){
        	bookList = booksList.get(i);        		
    		bookInfo.setTitle(bookList[0]);
    		bookInfo.setAuthor(bookList[1]);
    		bookInfo.setPublisher(bookList[2]);
    		bookInfo.setPublishDate(bookList[3]);
    		bookInfo.setIsbn(bookList[4]);
    		bookInfo.setText(bookList[5]);
        	booksService.registBook(bookInfo);
        }
        
        model.addAttribute("bookList", booksService.getBookList());
    	return "home";
    }
}
