/**
 * 
 */
package com.termproject.csd4464.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.UtilityBillsModel;
import com.termproject.csd4464.utils.Constants;

/**
 * @author abhinavmittal, aarti
 * 
 * This is a DAO class which handles the Database queries related to "utility_bills" table 
 * and transform the retrieved data and return to AdminUtilityController.
 *
 */

@Service
public class UtilityBillsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UtilityDao utilityDao;

	@Autowired
	private ClientDao clientDao;

	/**
	 * 
	 * It adds a new record in utility_bills table.
	 * 
	 * @param utilityBillsModel
	 * @return the number of rows affected
	 */
	public int insertUtilityBill(UtilityBillsModel utilityBillsModel) {
		System.out.println("insertUtilityBill() begins, utilityBillsModel: " + utilityBillsModel.toString());
		String sql = "INSERT INTO utility_bills (utility_id, balance, status, generated_on, client_id) " + "VALUES "
				+ "('" + utilityBillsModel.getUtilitiesModel().getUtilityId() + "', '" + utilityBillsModel.getBalance()
				+ "', '" + utilityBillsModel.getStatus() + "','" + utilityBillsModel.getGeneratedOn() + "', '"
				+ utilityBillsModel.getClientsModel().getClientId() + "')";

		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 * It updates a record in utility_bills table with the details provided by the user.
	 * 
	 * @param status
	 * @param updateDate
	 * @param utilityBillId
	 * @return the number of rows affected
	 */
	public int updateUtilityBill(String status, Date updateDate, long utilityBillId) {
		System.out.println("updateUtilityBill() begins, status: " + status + ", updateDate: " + updateDate);
		String sql = "UPDATE utility_bills SET status = '" + status + "', paid_on = '" + updateDate
				+ "' where utility_bill_id = " + utilityBillId;
		return jdbcTemplate.update(sql);
	}

	/**
	 * It retrieves all the records from utility_bills table by clientId
	 * and set it in the Model 
	 * and return the List. 
	 * 
	 * Also, it retrieves the foreign key table details as well.
	 * 
	 * @param clientId
	 * @return List<UtilityBillsModel>
	 */
	public List<UtilityBillsModel> getUtilityBillsByClient(long clientId) {
		System.out.println("getUtilityBillsByClient(): begins: clientId: " + clientId);
		return jdbcTemplate.query("select * from utility_bills where client_id = " + clientId, new RowMapper<UtilityBillsModel>() {
			public UtilityBillsModel mapRow(ResultSet rs, int row) throws SQLException {
				UtilityBillsModel utilityBillsModel = new UtilityBillsModel();
				utilityBillsModel.setUtilityBillId(rs.getLong(1));
				utilityBillsModel.setUtilitiesModel(utilityDao.getUtilityById(rs.getLong(2)));
				utilityBillsModel.setBalance(rs.getDouble(3));
				utilityBillsModel.setStatus(rs.getString(4));
				utilityBillsModel.setGeneratedOn(rs.getDate(5));
				utilityBillsModel.setPaidOn(rs.getDate(6));
				utilityBillsModel.setClientsModel(clientDao.getClientsDetailById(rs.getLong(7)));
				return utilityBillsModel;
			}
		});
	}

	/**
	 * 
	 * It retrieves all the records from utility_bills table by clientId, which does not have status = 'paid'
	 * and set it in the Model 
	 * and return the List. 
	 * 
	 * Also, it retrieves the foreign key table details as well.
	 * 
	 * @param clientId
	 * @return List<UtilityBillsModel>
	 */
	public List<UtilityBillsModel> getUnpaidUtilityBillsByClient(long clientId) {
		System.out.println("getUnpaidUtilityBillsByClient(): begins: clientId: " + clientId);
		return jdbcTemplate.query(
				"select * from utility_bills where status <> '" + Constants.UTILITIES_BILLS_PAYMENT_STATUS_PAID + "' and client_id = " + clientId,
				new RowMapper<UtilityBillsModel>() {
					public UtilityBillsModel mapRow(ResultSet rs, int row) throws SQLException {
						UtilityBillsModel utilityBillsModel = new UtilityBillsModel();
						utilityBillsModel.setUtilityBillId(rs.getLong(1));
						utilityBillsModel.setUtilitiesModel(utilityDao.getUtilityById(rs.getLong(2)));
						utilityBillsModel.setBalance(rs.getDouble(3));
						utilityBillsModel.setStatus(rs.getString(4));
						utilityBillsModel.setGeneratedOn(rs.getDate(5));
						utilityBillsModel.setPaidOn(rs.getDate(6));
						utilityBillsModel.setClientsModel(clientDao.getClientsDetailById(rs.getLong(7)));
						return utilityBillsModel;
					}
				});
	}

	/**
	 * 
	 * It retrieves the details of an utility_bills by utilityBillId.
	 * 
	 * @param utilityBillId
	 * @return UtilityBillsModel
	 */
	public UtilityBillsModel getUtilityBillById(long utilityBillId) {
		System.out.println("getUtilityBillById() begins, utilityBillId: " + utilityBillId);
		String sql = "select * from utility_bills where utility_bill_id=?";

		try {
			UtilityBillsModel utilityBillsModel = jdbcTemplate.queryForObject(sql,
					new BeanPropertyRowMapper<UtilityBillsModel>(UtilityBillsModel.class),
					new Object[] { utilityBillId });
			utilityBillsModel.setUtilitiesModel(utilityDao.getUtilityById(utilityBillsModel.getUtilityId()));
			
			return utilityBillsModel;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
