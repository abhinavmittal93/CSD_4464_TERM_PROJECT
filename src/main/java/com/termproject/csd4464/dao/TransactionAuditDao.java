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

import com.termproject.csd4464.model.TransactionsAuditModel;

/**
 * @author abhinavmittal
 * 
 * This is a DAO class which handles the Database queries related to "transaction_audit" table 
 * and transform the retrieved data and return to TransactionController.
 *
 */
@Service
public class TransactionAuditDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AccountsDao accountsDao;

	/**
	 * It adds a new record in transaction_audit table whenever a transaction is made.
	 * 
	 * @param transactionsAuditModel
	 * @return the number of rows affected
	 */
	public int insertTransactionHistory(TransactionsAuditModel transactionsAuditModel) {
		System.out.println("insertTransactionHistory() begins:, " + transactionsAuditModel.toString());
		
		// in case of deposit or withdrawal this column will be null
		Long transferTOAccount = transactionsAuditModel.getTransferToAccountModel() != null ? transactionsAuditModel.getTransferToAccountModel().getAccountId() : null;
		try {
			String sql = "INSERT INTO transaction_audit (transaction_account, transfer_to, action, transaction_date, transaction_amount, status, reason_code) "
					+ "VALUES " + "(" + transactionsAuditModel.getTransactionAccountModel().getAccountId() + ", "
					+ transferTOAccount + ", '"
					+ transactionsAuditModel.getAction() + "','" + transactionsAuditModel.getTransactionDate() + "', "
					+ transactionsAuditModel.getTransactionAmount() + ", '" + transactionsAuditModel.getStatus()
					+ "', '" + transactionsAuditModel.getReasonCode() + "')";
			return jdbcTemplate.update(sql);
		} catch (Exception e) {
			System.err.println("An error occurred while saving transaction history, " + e.getMessage() + e);
		}
		return 0;
	}

	/**
	 * 
	 * It retrieves all the records from transaction_audit table by accountIds
	 * and set it in the Model 
	 * and return the List. 
	 * 
	 * Also, it retrieves the foreign key table details as well.
	 * 
	 * @param accountIds
	 * @return List<TransactionsAuditModel>
	 */
	public List<TransactionsAuditModel> getAllTransactionsByAccountId(List<Long> accountIds) {
		System.out.println("getAllTransactionsByAccountId() begins, accountId:" + accountIds);
		String accountIdsCommaSeparated = accountIds.toString();
		accountIdsCommaSeparated = accountIdsCommaSeparated.replace("[", "(");
		accountIdsCommaSeparated = accountIdsCommaSeparated.replace("]", ")");
		return jdbcTemplate.query("select * from transaction_audit where transaction_account IN " + accountIdsCommaSeparated
				+ " OR transfer_to IN " + accountIdsCommaSeparated, new RowMapper<TransactionsAuditModel>() {
					public TransactionsAuditModel mapRow(ResultSet rs, int row) throws SQLException {
						TransactionsAuditModel transactionsAuditModel = new TransactionsAuditModel();
						transactionsAuditModel.setTransactionId(rs.getLong(1));
						transactionsAuditModel
								.setTransactionAccountModel(accountsDao.getAccountByAccountId(rs.getLong(2)));
						transactionsAuditModel
								.setTransferToAccountModel(accountsDao.getAccountByAccountId(rs.getLong(3)));
						transactionsAuditModel.setAction(rs.getString(4));
						transactionsAuditModel.setTransactionDate(rs.getDate(5));
						transactionsAuditModel.setTransactionAmount(rs.getDouble(6));
						transactionsAuditModel.setStatus(rs.getString(7));
						transactionsAuditModel.setReasonCode(rs.getString(8));
						return transactionsAuditModel;
					}
				});
	}

}
