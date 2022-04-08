/**
 * 
 */
package com.termproject.csd4464.model;

import java.sql.Date;

/**
 * @author abhinavmittal
 *
 */
public class AccountsModel {

	private Long accountId;
	
	private String accountNo;

	private ClientsModel clientsModel;

	private BankTypesModel bankTypesModel;

	private Double balance;

	private Date createdDate;

	private Date updatedDate;

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

	@Override
	public String toString() {
		return "AccountsModel [accountId=" + accountId + ", accountNo=" + accountNo + ", clientsModel=" + clientsModel
				+ ", bankTypesModel=" + bankTypesModel + ", balance=" + balance + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + "]";
	}

}
