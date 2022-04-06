/**
 * 
 */
package com.termproject.csd4464.model;

import java.sql.Date;

/**
 * @author abhinavmittal
 *
 */
public class TransactionsAuditModel {

	private Long transactionId;

	private AccountsModel transactionAccountModel;

	private AccountsModel transferToAccountModel;

	private String action;

	private Date transactionDate;

	private Double transactionAmount;

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the transactionAccountModel
	 */
	public AccountsModel getTransactionAccountModel() {
		return transactionAccountModel;
	}

	/**
	 * @param transactionAccountModel the transactionAccountModel to set
	 */
	public void setTransactionAccountModel(AccountsModel transactionAccountModel) {
		this.transactionAccountModel = transactionAccountModel;
	}

	/**
	 * @return the transferToAccountModel
	 */
	public AccountsModel getTransferToAccountModel() {
		return transferToAccountModel;
	}

	/**
	 * @param transferToAccountModel the transferToAccountModel to set
	 */
	public void setTransferToAccountModel(AccountsModel transferToAccountModel) {
		this.transferToAccountModel = transferToAccountModel;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionAmount
	 */
	public Double getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

}
