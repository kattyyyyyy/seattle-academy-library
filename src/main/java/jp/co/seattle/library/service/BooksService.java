package jp.co.seattle.library.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
        		"select id, title, author, publisher, publish_date, thumbnail_url from books order by title",
                new BookInfoRowMapper());
        return getedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報 + 貸出状況
     */
    public BookDetailsInfo getBookInfo(int bookId) {
        // JSPに渡すデータを設定する
    	String sql = "select b.*, case when r.book_id is null then '貸し出し可' else '貸出中' end as status from books b left join rentals r on b.id = r.book_id where b.id = ?;";

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

        return bookDetailsInfo;
    }

    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public int registBook(BookDetailsInfo bookInfo) {
    	
    	String sql = "";
    	int bookId;
    	
    	if(bookInfo.getThumbnailUrl() != null) {
    		sql = "INSERT INTO books (title, author, publisher, publish_date, thumbnail_name, thumbnail_url,reg_date, upd_date, text, isbn) VALUES (?, ?, ?, ?, ?, ?, now(),now(), ?, ?) RETURNING id;";
    		
    		bookId = jdbcTemplate.queryForObject(sql, Integer.class, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
    				bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(), bookInfo.getText(), bookInfo.getIsbn());
    	} else {
    		sql = "INSERT INTO books (title, author, publisher, publish_date, reg_date, upd_date, text, isbn) VALUES (?, ?, ?, ?, now(),now(), ?, ?) RETURNING id;";
    		
    		bookId = jdbcTemplate.queryForObject(sql, Integer.class, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
            		bookInfo.getPublishDate(), bookInfo.getText(), bookInfo.getIsbn());
    	}
        
        return bookId;
    }
    
    /**
     * 書籍を削除する
     *
     * @param bookId 書籍ID
     */
    public void deleteBook(int bookId) {
    	String sql = "DELETE FROM books WHERE id = " + bookId + ";";
    	jdbcTemplate.update(sql);
    }
    
    /**
     * 書籍を更新する
     *
     * @param bookInfo 書籍情報
     */
    public void updateBook(BookDetailsInfo bookInfo) {
    	
    	String sql = "";
    	if(bookInfo.getThumbnailUrl() != null) {
    		sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, thumbnail_name = ?, thumbnail_url = ?, reg_date = now(), upd_date = now(), text = ?, isbn = ? WHERE id = ? ;";
    		
    		jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
    				bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(), bookInfo.getText(), bookInfo.getIsbn(), bookInfo.getBookId());
    		
    	} else {
    		sql = "UPDATE books SET title = ?, author = ?, publisher = ?, publish_date = ?, reg_date = now(), upd_date = now(), text = ?, isbn = ? WHERE id = ? ;";
    		
    		jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
            		bookInfo.getPublishDate(), bookInfo.getText(), bookInfo.getIsbn(), bookInfo.getBookId());
    	}
    }
    
    /**
     * バリデーションチェック
     *
     * @param title タイトル
     * @param author 著者名
     * @param publisher 出版社
     * @param publishDate 出版日
     * @param isbnCode ISBN
     */
    public List<String> validationCheck(String title, String author, String publisher, String publishDate, String isbnCode){
    	List<String> list = new ArrayList<String>();
        if(title.equals("") || author.equals("") || publisher.equals("") || publishDate.length() == 0){
        	list.add("<p>必須項目を入力してください。</p>");
        }
        
        if(!(publishDate.matches("^[0-9]{8}"))) {
        	list.add("<p>出版日は半角数字のYYYYMMDD形式で入力してください。</p>");
        	
        }
        
        if(isbnCode.length() != 0 && !(isbnCode.matches("^[0-9]{10}|[0-9]{13}"))) {
        	list.add("<p>ISBNの桁数または半角数字が正しくありません。</p>");
        }
        
		return list;
    }
}
