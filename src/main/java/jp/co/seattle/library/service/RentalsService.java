package jp.co.seattle.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.RentalInfo;
import jp.co.seattle.library.rowMapper.RentalInfoRowMapper;

/**
 * 書籍サービス
 * 
 * rentalsテーブルに関する処理を実装する
 */
@Service
public class RentalsService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 書籍をrentalsテーブルの情報を取得する
	 *
	 * @param bookId 書籍ID
	 * 
	 * @return rentalInfo 貸出情報
	 */
	public RentalInfo getRentInfo(int bookId) {

		String sql = "SELECT book_id, title, rent_date, return_date FROM rentals where book_id = ?";
		try {
			RentalInfo rentalInfo = jdbcTemplate.queryForObject(sql, new RentalInfoRowMapper(), bookId);
			return rentalInfo;
		} catch (Exception e) {
			
			return null;
		}
	}
	
	/**
	 * rentalsテーブルの一覧を取得
	 * 
	 * @return rentList 貸出リスト
	 */
	public List<RentalInfo> getRentList(){
		String sql = "SELECT book_id, title, rent_date, return_date FROM rentals ORDER BY title";
		List<RentalInfo> rentList = jdbcTemplate.query(sql, new RentalInfoRowMapper());
		
		return rentList;
	}
	
	/**
	 * 書籍をrentalsテーブルに追加する
	 *
	 * @param bookId 書籍ID
	 * @param title タイトル
	 */
	public void rentalBook(int bookId, String title) {
		
		String sql = "INSERT INTO rentals (book_id, rent_date, title) VALUES (?, now(), ?)";
		jdbcTemplate.update(sql, bookId, title);
	}
	
	/**
	 * 書籍を貸出状態にする
	 * 
	 * @param bookId 書籍ID
	 */
	public void updateRentalBook(int bookId) {
		String sql = "UPDATE rentals SET rent_date = now(), return_date = null WHERE book_id = ?";
		jdbcTemplate.update(sql, bookId);
	}
	
	/**
	 * 書籍を返却状態にする
	 *
	 * @param bookId 書籍ID
	 */
	public void updateReturnBook(int bookId) {
	
		String sql = "UPDATE rentals SET rent_date = null, return_date = now() WHERE book_id = ?";
		jdbcTemplate.update(sql, bookId);
	}
	
	/**
	 * rentalsテーブルの書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteRent(int bookId) {
		String sql = "DELETE FROM rentals WHERE book_id = ?;";
    	jdbcTemplate.update(sql, bookId);
	}
}
