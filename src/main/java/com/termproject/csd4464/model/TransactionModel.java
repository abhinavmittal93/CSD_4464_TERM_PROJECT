/**
 * 
 */
package com.termproject.csd4464.model;

/**
 * @author abhinavmittal
 *
 */
public class TransactionModel {

	private Long transferFromAccountId;

	private Long transferToAccount;

	private Double balance;

	private String action;

	/**
	 * @return the transferFromAccountId
	 */
	public Long getTransferFromAccountId() {
		return transferFromAccountId;
	}

	/**
	 * @param transferFromAccountId the transferFromAccountId to set
	 */
	public void setTransferFromAccountId(Long transferFromAccountId) {
		this.transferFromAccountId = transferFromAccountId;
	}

	/**
	 * @return the transferToAccount
	 */
	public Long getTransferToAccount() {
		return transferToAccount;
	}

	/**
	 * @param transferToAccount the transferToAccount to set
	 */
	public void setTransferToAccount(Long transferToAccount) {
		this.transferToAccount = transferToAccount;
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

	@Override
	public String toString() {
		return "TransactionModel [transferFromAccountId=" + transferFromAccountId + ", transferToAccount="
				+ transferToAccount + ", balance=" + balance + ", action=" + action + "]";
	}
	
	

}
