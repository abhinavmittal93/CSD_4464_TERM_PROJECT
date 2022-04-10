/**
 * 
 */
package com.termproject.csd4464.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.termproject.csd4464.model.UtilitiesAuditModel;

/**
 * @author abhinavmittal
 *
 */
@Service
public class UtilityAuditDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UtilityBillsDao utilityBillsDao;

	public int insertUtilityAuditHistory(UtilitiesAuditModel utilitiesAuditModel) {
		System.out
				.println("insertUtilityAuditHistory() begins:, utilitiesAuditModel: " + utilitiesAuditModel.toString());

		try {
			String sql = "INSERT INTO utilities_audit (utility_bill_id, amount, paid_on, status, reason_code, client_id) "
					+ "VALUES " + "(" + utilitiesAuditModel.getUtilityBillsModel().getUtilityBillId() + ", "
					+ utilitiesAuditModel.getAmount() + ", '" + utilitiesAuditModel.getPaidOn() + "','"
					+ utilitiesAuditModel.getStatus() + "', '" + utilitiesAuditModel.getReasonCode() + "', "
					+ utilitiesAuditModel.getClientId() + ")";
			return jdbcTemplate.update(sql);
		} catch (Exception e) {
			System.err.println("An error occurred while saving utility bill history, " + e.getMessage() + e);
		}
		return 0;
	}

	public List<UtilitiesAuditModel> getUtilityBillsHistoryByClient(long clientId) {
		System.out.println("getUtilityBillsHistoryByClient() begins, clientId:" + clientId);
		return jdbcTemplate.query("select * from utilities_audit where client_id = " + clientId,
				new RowMapper<UtilitiesAuditModel>() {
					public UtilitiesAuditModel mapRow(ResultSet rs, int row) throws SQLException {
						UtilitiesAuditModel utilitiesAuditModel = new UtilitiesAuditModel();
						utilitiesAuditModel.setUtilityAuditId(rs.getLong(1));
						utilitiesAuditModel.setUtilityBillsModel(utilityBillsDao.getUtilityBillById(rs.getLong(2)));
						utilitiesAuditModel.setAmount(rs.getDouble(3));
						utilitiesAuditModel.setPaidOn(rs.getDate(4));
						utilitiesAuditModel.setStatus(rs.getString(5));
						utilitiesAuditModel.setReasonCode(rs.getString(6));
						utilitiesAuditModel.setClientId(rs.getLong(7));
						return utilitiesAuditModel;
					}
				});
	}

}
