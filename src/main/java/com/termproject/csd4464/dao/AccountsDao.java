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

import com.termproject.csd4464.model.AccountsModel;
import com.termproject.csd4464.model.BankTypesModel;
import com.termproject.csd4464.model.ClientsModel;

/**
 * @author abhinavmittal
 *
 */
@Service
public class AccountsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BankTypesDao bankTypesDao;

	@Autowired
	private ClientDao clientDao;

	public int addAccount(AccountsModel accountsModel) {
		System.out.println("addAccount() begins, accountsModel: " + accountsModel.toString());
		String sql = "INSERT INTO accounts (account_no, client_id, bank_type_id, balance, created_date, updated_date) "
				+ "VALUES " + "('" + accountsModel.getAccountNo() + "', '"
				+ accountsModel.getClientsModel().getClientId() + "', '"
				+ accountsModel.getBankTypesModel().getBankTypeId() + "','" + accountsModel.getBalance() + "', '"
				+ accountsModel.getCreatedDate() + "', '" + accountsModel.getUpdatedDate() + "')";

		return jdbcTemplate.update(sql);
	}

	public List<AccountsModel> getAccountByClientId(long clientId) {
		System.out.println("getAccountByClientId() begins, client_id: " + clientId);
		return jdbcTemplate.query("select * from accounts where client_id = " + clientId,
				new RowMapper<AccountsModel>() {
					public AccountsModel mapRow(ResultSet rs, int row) throws SQLException {
						AccountsModel accountsModel = new AccountsModel();
						accountsModel.setAccountId(rs.getLong(1));
						accountsModel.setAccountNo(rs.getString(2));
						accountsModel.setClientsModel(clientDao.getClientsDetailById(rs.getLong(3)));
						accountsModel.setBankTypesModel(bankTypesDao.getBankTypeById(rs.getLong(4)));
						accountsModel.setBalance(rs.getDouble(5));
						accountsModel.setCreatedDate(rs.getDate(6));
						accountsModel.setUpdatedDate(rs.getDate(7));
						return accountsModel;
					}
				});
	}

	public AccountsModel getAccountByClientIdAndBankTypeId(long clientId, long bankTypeId) {
		System.out.println(
				"getAccountByClientIdAndBankTypeId() begins, client_id: " + clientId + ", bankTypeId: " + bankTypeId);
		String sql = "select * from accounts where client_id=? and bank_type_id=?";

		try {
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AccountsModel>(AccountsModel.class),
					new Object[] { clientId, bankTypeId });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<AccountsModel> getAllAccounts() {
		System.out.println("getAllAccounts() begins");
		return jdbcTemplate.query("select * from accounts", new RowMapper<AccountsModel>() {
			public AccountsModel mapRow(ResultSet rs, int row) throws SQLException {
				AccountsModel accountsModel = new AccountsModel();
				accountsModel.setAccountId(rs.getLong(1));
				accountsModel.setAccountNo(rs.getString(2));
				accountsModel.setClientsModel(clientDao.getClientsDetailById(rs.getLong(3)));
				accountsModel.setBankTypesModel(bankTypesDao.getBankTypeById(rs.getLong(4)));
				accountsModel.setBalance(rs.getDouble(5));
				accountsModel.setCreatedDate(rs.getDate(6));
				accountsModel.setUpdatedDate(rs.getDate(7));
				return accountsModel;
			}
		});
	}

	public AccountsModel getAccountByAccountId(Long accountId) {
		System.out.println("getAccountByAccountId() begins, accountId: " + accountId);
		
		if(accountId == null) {
			System.out.println("accountId is null, so returning null.");
			return null;
		}
		
		String sql = "select * from accounts where account_id=?";

		try {
			
			AccountsModel accountsModel = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AccountsModel>(AccountsModel.class),
					new Object[] { accountId });
			
			ClientsModel clientsModel = clientDao.getClientsDetailById(accountsModel.getClientId());
			accountsModel.setClientsModel(clientsModel);
			
			BankTypesModel bankTypesModel = bankTypesDao.getBankTypeById(accountsModel.getBankTypeId());
			accountsModel.setBankTypesModel(bankTypesModel);
			return accountsModel;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int updateAccountBalance(long accountId, double balance, Date updateDate) {
		System.out.println("updateAccountBalance() begins, accountId: " + accountId + ", balance: " + balance);
		String sql = "UPDATE accounts SET balance = " + balance + ", updated_date = '" + updateDate + "' where account_id = " + accountId;
		return jdbcTemplate.update(sql);
	}

}
