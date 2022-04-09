/**
 * 
 */
package com.termproject.csd4464.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.UtilitiesModel;

/**
 * @author abhinavmittal
 *
 */
@Service
public class UtilityDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<UtilitiesModel> getUtilities() {
		System.out.println("getUtilities(): begins:");
		return jdbcTemplate.query("select * from utilities", new RowMapper<UtilitiesModel>() {
			public UtilitiesModel mapRow(ResultSet rs, int row) throws SQLException {
				UtilitiesModel utilitiesModel = new UtilitiesModel();
				utilitiesModel.setUtilityId(rs.getLong(1));
				utilitiesModel.setUtilityType(rs.getString(2));
				utilitiesModel.setUtilityName(rs.getString(3));
				return utilitiesModel;
			}
		});
	}

	public UtilitiesModel getUtilityById(Long utilityId) {
		System.out.println("getUtilityById(): begins:, utilityId: " + utilityId);
		String sql = "select * from utilities where utility_id=?";
		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<UtilitiesModel>(UtilitiesModel.class),
					new Object[] { utilityId });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
