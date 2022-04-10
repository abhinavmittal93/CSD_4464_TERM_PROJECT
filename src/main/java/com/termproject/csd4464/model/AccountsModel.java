/**
 * 
 */
package com.termproject.csd4464.model;

import java.sql.Date;

/**
 * @author abhinavmittal
 * 
 *  Model class for table "accounts" with accountId, accountNo, clientId, bankTypeId,
 *  clientsModel, bankTypesModel, balance, createdDate, updatedDate.
 *
 */
public class AccountsModel {

	private Long accountId;

	private String accountNo;

	private Long clientId;

	private Long bankTypeId;

	private ClientsModel clientsModel;

	private BankTypesModel bankTypesModel;

	private Double balance;

	private Date createdDate;

	private Date updatedDate;

	public AccountsModel() {
	}

	public AccountsModel(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the clientsModel
	 */
	public ClientsModel getClientsModel() {
		return clientsModel;
	}

	/**
	 * @param clientsModel the clientsModel to set
	 */
	public void setClientsModel(ClientsModel clientsModel) {
		this.clientsModel = clientsModel;
	}

	/**
	 * @return the bankTypesModel
	 */
	public BankTypesModel getBankTypesModel() {
		return bankTypesModel;
	}

	/**
	 * @param bankTypesModel the bankTypesModel to set
	 */
	public void setBankTypesModel(BankTypesModel bankTypesModel) {
		this.bankTypesModel = bankTypesModel;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the bankTypeId
	 */
	public Long getBankTypeId() {
		return bankTypeId;
	}

	/**
	 * @param bankTypeId the bankTypeId to set
	 */
	public void setBankTypeId(Long bankTypeId) {
		this.bankTypeId = bankTypeId;
	}

	@Override
	public String toString() {
		return "AccountsModel [accountId=" + accountId + ", accountNo=" + accountNo + ", clientId=" + clientId
				+ ", bankTypeId=" + bankTypeId + ", clientsModel=" + clientsModel + ", bankTypesModel=" + bankTypesModel
				+ ", balance=" + balance + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

}
