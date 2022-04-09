/**
 * 
 */
package com.termproject.csd4464.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.UtilityBillsModel;

/**
 * @author abhinavmittal
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

	public int insertUtilityBill(UtilityBillsModel utilityBillsModel) {
		System.out.println("insertUtilityBill() begins, utilityBillsModel: " + utilityBillsModel.toString());
		String sql = "INSERT INTO utility_bills (utility_id, balance, status, generated_on, client_id) " + "VALUES "
				+ "('" + utilityBillsModel.getUtilitiesModel().getUtilityId() + "', '" + utilityBillsModel.getBalance()
				+ "', '" + utilityBillsModel.getStatus() + "','" + utilityBillsModel.getGeneratedOn() + "', '"
				+ utilityBillsModel.getClientsModel().getClientId() + "')";

		return jdbcTemplate.update(sql);
	}

	public int updateUtilityBill(String status, Date updateDate, long utilityBillId) {
		System.out.println("updateUtilityBill() begins, status: " + status + ", updateDate: " + updateDate);
		String sql = "UPDATE utility_bills SET status = " + status + ", paid_on = '" + updateDate
				+ "' where utility_bill_id = " + utilityBillId;
		return jdbcTemplate.update(sql);
	}

	public List<UtilityBillsModel> getUtilityBillsByClient(long clientId) {
		System.out.println("getUtilityBillsByClient(): begins: clientId: " + clientId);
		return jdbcTemplate.query("select * from utility_bills", new RowMapper<UtilityBillsModel>() {
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

}
