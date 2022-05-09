package jp.co.seattle.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
	 * 書籍をrentalsテーブルに追加する
	 *
	 * @param bookId 書籍ID
	 */
	public void rentalBook(int bookId) {
		String sql = "INSERT INTO rentals (book_id) VALUES (?)";
		jdbcTemplate.update(sql, bookId);
	}

	/**
	 * 書籍をrentalsテーブルの情報を取得する
	 *
	 * @param bookId 書籍ID
	 */
	public int getRentInfo(int bookId) {

		String sql = "SELECT book_id FROM rentals where book_id = ?";
		try {
			int rentId = jdbcTemplate.queryForObject(sql, Integer.class, bookId);
			return rentId;
		} catch (Exception e) {
			return 0;
		}
	}
}
