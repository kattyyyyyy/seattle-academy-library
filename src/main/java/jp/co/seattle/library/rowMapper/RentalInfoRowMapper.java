package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.RentalInfo;

@Configuration
public class RentalInfoRowMapper implements RowMapper<RentalInfo> {
	
	@Override
	public RentalInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Query結果（ResultSet rs）を、オブジェクトに格納する実装
		RentalInfo rentalInfo = new RentalInfo();

		// bookInfoの項目と、取得した結果(rs)のカラムをマッピングする
		rentalInfo.setBookId(rs.getInt("book_id"));
		rentalInfo.setTitle(rs.getString("title"));
		rentalInfo.setRentDate(rs.getString("rent_date"));
		rentalInfo.setReturnDate(rs.getString("return_date"));
		return rentalInfo;
	}
}
