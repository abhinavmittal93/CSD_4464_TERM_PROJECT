/**
 * 
 */
package com.termproject.csd4464.model;

import java.sql.Date;

/**
 * @author abhinavmittal
 * 
 * Model class for table "transaction_audit" with transactionId, transactionAccountModel, transferToAccountModel, 
 * action, transactionDate, transactionAmount, status, reasonCode.
 *
 */
public class TransactionsAuditModel {

	private Long transactionId;

	private AccountsModel transactionAccountModel;

	private AccountsModel transferToAccountModel;

	private String action;

	private Date transactionDate;

	private Double transactionAmount;

	private String status;

	private String reasonCode;

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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	@Override
	public String toString() {
		return "TransactionsAuditModel [transactionId=" + transactionId + ", transactionAccountModel="
				+ transactionAccountModel + ", transferToAccountModel=" + transferToAccountModel + ", action=" + action
				+ ", transactionDate=" + transactionDate + ", transactionAmount=" + transactionAmount + ", status="
				+ status + ", reasonCode=" + reasonCode + "]";
	}

}
