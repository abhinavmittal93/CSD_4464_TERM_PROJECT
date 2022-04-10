/**
 * 
 */
package com.termproject.csd4464.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.termproject.csd4464.dao.AccountsDao;
import com.termproject.csd4464.dao.TransactionAuditDao;
import com.termproject.csd4464.model.AccountsModel;
import com.termproject.csd4464.model.ClientsModel;
import com.termproject.csd4464.model.TransactionModel;
import com.termproject.csd4464.model.TransactionsAuditModel;
import com.termproject.csd4464.utils.Constants;

/**
 * @author abhinavmittal
 * 
 * This controller handles all the requests related to the transactions,
 * such as Self Transfer, Transfer to other client, Deposit Money, Withdrawing money.
 * 
 * And this is accessible only to clients.
 *
 */

@Controller
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private AccountsDao accountsDao;

	@Autowired
	private TransactionAuditDao transactionAuditDao;

	
	/**
	 * 
	 * It displays multiple forms for the transactions such as: Self Transfer, Transfer to other client, Deposit Money, Withdrawing money.
	 * 
	 * Here "clientsModels" is a reserved request attribute which is used to display client's data.
	 *
	 * Here "clientAccounts" is a reserved request attribute which is used to display client's accounts.
	 * 
	 * Here "accountsModelList" is a reserved request attribute which is used to display other client's accounts,
	 * the logged in client's accounts is removed from this list.
	 * 
	 * @param m
	 * @param request
	 * @return Transactions.jsp
	 */
	@RequestMapping("")
	public String getTransactionPage(Model m, HttpServletRequest request) {
		System.out.println("getTransactionPage(): begins");
		try {
			HttpSession session = request.getSession(false);
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "You are not logged in. Please Log in first.");
				return "client/ClientLogin";
			}

			ClientsModel clientsModel = (ClientsModel) session.getAttribute("clientsModel");
			List<AccountsModel> clientAccounts = accountsDao.getAccountByClientId(sessionClientId);
			List<AccountsModel> accountsModelList = accountsDao.getAllAccounts();
			List<AccountsModel> otherClientsAccounts = new ArrayList<AccountsModel>();
			
			// removing the accounts related to the logged in client
			for(AccountsModel accountsModel : accountsModelList) {
				if(accountsModel.getClientId() != clientsModel.getClientId()) {
					otherClientsAccounts.add(accountsModel);
				}
			}

			m.addAttribute("clientsModel", clientsModel);
			m.addAttribute("clientAccounts", clientAccounts);
			m.addAttribute("accountsModelList", otherClientsAccounts);
		} catch (Exception e) {
			System.err.println("Exception occurred in getTransactionPage(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			return "redirect:/client/home";
		}
		return "client/Transactions";
	}

	/**
	 * 
	 * It displays all the transactions made by the logged in client or for the logged in client.
	 * 
	 * Here "clientsModels" is a reserved request attribute which is used to display client's data.
	 * 
	 * Here "transactionsAuditModels" is a reserved request attribute which is used to display client's transaction history.
	 * 
	 * @param m
	 * @param request
	 * @return TransactionsHistory.jsp
	 */
	@RequestMapping("/history")
	public String getTransactionHistoryPage(Model m, HttpServletRequest request) {
		System.out.println("getTransactionHistoryPage(): begins");
		try {
			HttpSession session = request.getSession(false);
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "You are not logged in. Please Log in first.");
				return "client/ClientLogin";
			}

			ClientsModel clientsModel = (ClientsModel) session.getAttribute("clientModel");
			List<AccountsModel> clientAccounts = accountsDao.getAccountByClientId(sessionClientId);

			List<Long> clientAccountIds = new ArrayList<Long>();
			for (AccountsModel accountsModel : clientAccounts) {
				clientAccountIds.add(accountsModel.getAccountId());
			}

			List<TransactionsAuditModel> transactionsAuditModels = transactionAuditDao
					.getAllTransactionsByAccountId(clientAccountIds);

			m.addAttribute("clientsModel", clientsModel);
			//m.addAttribute("clientAccounts", clientAccounts);
			m.addAttribute("transactionsAuditModels", transactionsAuditModels);
		} catch (Exception e) {
			System.err.println("Exception occurred in getTransactionHistoryPage(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			return "redirect:/client/home";
		}
		return "client/TransactionsHistory";
	}

	/**
	 * 
	 * It stores the object in the database.
	 * 
	 * It transfers the money to the same client's another account.
	 * 
	 * The parameter "TransactionModel" receives all the information entered by the user.
	 * 
	 * It has all the validation such as:
	 * i) the amount being transferred is greater than zero or not.
	 * ii) the account has sufficient funds or not
	 * iii) the account in which the money is being transferred is not the same account from which the transfer is being made.
	 * 
	 * It also inserts a transaction history.
	 * 
	 * @param m
	 * @param request
	 * @param transactionModel
	 * @return Redirects to transaction history.
	 */
	@PostMapping("/self")
	public String selfTransfer(Model m, HttpServletRequest request, TransactionModel transactionModel) {
		System.out.println("selfTransfer(): begins, " + transactionModel.toString());
		TransactionsAuditModel transactionsAuditModel = new TransactionsAuditModel();
		try {
			HttpSession session = request.getSession(false);
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "You are not logged in. Please Log in first.");
				return "client/ClientLogin";
			}

			transactionsAuditModel.setAction(Constants.TRANSACTION_ACTION_SELF_TRANSFER);
			transactionsAuditModel.setTransactionAmount(transactionModel.getBalance());

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			transactionsAuditModel.setTransactionDate(sqlDate);
			transactionsAuditModel
					.setTransactionAccountModel(new AccountsModel(transactionModel.getTransferFromAccountId()));
			transactionsAuditModel
					.setTransferToAccountModel(new AccountsModel(transactionModel.getTransferToAccount()));
			
			
			if(transactionModel.getTransferFromAccountId() == transactionModel.getTransferToAccount()) {
				System.err.println("Amount is being transferred within same account.");
				m.addAttribute("message", "You can't do transactions within the same Account. Please select a different account.");
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INVALID_INPUT);
				return "redirect:/transaction/history";
			}

			// check if the balance which is being transferred is greater than zero
			if (transactionModel.getBalance() <= 0) {
				System.err.println("Transfer Amount is zero or less than zero.");
				m.addAttribute("message", "Amount should be greater than zero.");
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INVALID_INPUT);
				return "redirect:/transaction/history";
			}

			AccountsModel transferFromAccountsModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferFromAccountId());

			// check if the account has sufficient funds
			if (transferFromAccountsModel.getBalance() < transactionModel.getBalance()) {
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INSUFF_FUNDS);
				System.err.println("The selected account has insufficient funds.");
				m.addAttribute("message", "The selected account has insufficient funds.");
				return "redirect:/transaction/history";
			}

			AccountsModel transferToAccountsModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferToAccount());

			// Add balance to the account to which transfer is made
			accountsDao.updateAccountBalance(transferToAccountsModel.getAccountId(),
					transferToAccountsModel.getBalance() + transactionModel.getBalance(), sqlDate);

			// Deduct balance from the account from which transfer is made
			accountsDao.updateAccountBalance(transferFromAccountsModel.getAccountId(),
					transferFromAccountsModel.getBalance() - transactionModel.getBalance(), sqlDate);

			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_SUCCESS);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_TRANSFER);

		} catch (Exception e) {
			System.err.println("Exception occurred in selfTransfer(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_ERROR);
			return "redirect:/transaction/history";
		} finally {
			transactionAuditDao.insertTransactionHistory(transactionsAuditModel);
		}
		return "redirect:/transaction/history";
	}

	/**
	 * 
	 * It stores the object in the database.
	 * 
	 * It transfers the money to other client's account.
	 * 
	 * It deducts the money from the logged in client's account and add to the other client's account.
	 * 
	 * The parameter "TransactionModel" receives all the information entered by the user.
	 * 
	 * It has all the validation such as:
	 * i) the amount being transferred is greater than zero or not.
	 * ii) the account has sufficient funds or not.
	 * 
	 * It also inserts a transaction history.
	 * 
	 * @param m
	 * @param request
	 * @param transactionModel
	 * @return Redirects to transaction history.
	 */
	@PostMapping("/other")
	public String otherAccountTransfer(Model m, HttpServletRequest request, TransactionModel transactionModel) {
		System.out.println("otherAccountTransfer(): begins, " + transactionModel.toString());
		TransactionsAuditModel transactionsAuditModel = new TransactionsAuditModel();
		try {
			HttpSession session = request.getSession(false);
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "You are not logged in. Please Log in first.");
				return "client/ClientLogin";
			}

			transactionsAuditModel.setAction(Constants.TRANSACTION_ACTION_OTHER_TRANSFER);
			transactionsAuditModel.setTransactionAmount(transactionModel.getBalance());

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			transactionsAuditModel.setTransactionDate(sqlDate);

			transactionsAuditModel
					.setTransactionAccountModel(new AccountsModel(transactionModel.getTransferFromAccountId()));
			transactionsAuditModel
					.setTransferToAccountModel(new AccountsModel(transactionModel.getTransferToAccount()));

			// check if the balance which is being transferred is greater than zero
			if (transactionModel.getBalance() <= 0) {
				System.err.println("Transfer Amount is zero or less than zero.");
				m.addAttribute("message", "Amount should be greater than zero.");
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INVALID_INPUT);
				return "redirect:/transaction/history";
			}

			AccountsModel transferFromAccountsModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferFromAccountId());

			// check if the account has sufficient funds
			if (transferFromAccountsModel.getBalance() < transactionModel.getBalance()) {
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INSUFF_FUNDS);
				System.err.println("The selected account has insufficient funds.");
				m.addAttribute("message", "The selected account has insufficient funds.");
				return "redirect:/transaction/history";
			}

			AccountsModel transferToAccountsModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferToAccount());

			// Add balance to the account to which transfer is made
			accountsDao.updateAccountBalance(transferToAccountsModel.getAccountId(),
					transferToAccountsModel.getBalance() + transactionModel.getBalance(), sqlDate);

			// Deduct balance from the account from which transfer is made
			accountsDao.updateAccountBalance(transferFromAccountsModel.getAccountId(),
					transferFromAccountsModel.getBalance() - transactionModel.getBalance(), sqlDate);

			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_SUCCESS);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_TRANSFER);

		} catch (Exception e) {
			System.err.println("Exception occurred in otherAccountTransfer(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_ERROR);
			return "redirect:/transaction/history";
		} finally {
			transactionAuditDao.insertTransactionHistory(transactionsAuditModel);
		}
		return "redirect:/transaction/history";
	}
	
	/**
	 * 
	 * It stores the object in the database.
	 * 
	 * It deposits the money in the logged in client's selected account.
	 * 
	 * The parameter "TransactionModel" receives all the information entered by the user.
	 * 
	 * It has the validation such as:
	 * i) the amount being transferred is greater than zero or not.
	 * 
	 * It also inserts a transaction history.
	 * 
	 * @param m
	 * @param request
	 * @param transactionModel
	 * @return Redirects to transaction history.
	 */
	@PostMapping("/deposit")
	public String depositMoney(Model m, HttpServletRequest request, TransactionModel transactionModel) {
		System.out.println("depositMoney(): begins, " + transactionModel.toString());
		TransactionsAuditModel transactionsAuditModel = new TransactionsAuditModel();
		try {
			HttpSession session = request.getSession(false);
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "You are not logged in. Please Log in first.");
				return "client/ClientLogin";
			}

			transactionsAuditModel.setAction(Constants.TRANSACTION_ACTION_DEPOSIT);
			transactionsAuditModel.setTransactionAmount(transactionModel.getBalance());

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			transactionsAuditModel.setTransactionDate(sqlDate);

			transactionsAuditModel
					.setTransactionAccountModel(new AccountsModel(transactionModel.getTransferFromAccountId()));

			// check if the balance which is being deposited is greater than zero
			if (transactionModel.getBalance() <= 0) {
				System.err.println("Transfer Amount is zero or less than zero.");
				m.addAttribute("message", "Amount should be greater than zero.");
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INVALID_INPUT);
				return "redirect:/transaction/history";
			}

			AccountsModel depositToAccountModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferFromAccountId());

			// Add balance to the account to which deposit is made
			accountsDao.updateAccountBalance(depositToAccountModel.getAccountId(),
					depositToAccountModel.getBalance() + transactionModel.getBalance(), sqlDate);

			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_SUCCESS);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_DEPOSIT);
			m.addAttribute("message", "Amount is deposited successfully.");
			m.addAttribute("status", "success");

		} catch (Exception e) {
			System.err.println("Exception occurred in depositMoney(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_ERROR);
			return "redirect:/transaction/history";
		} finally {
			transactionAuditDao.insertTransactionHistory(transactionsAuditModel);
		}
		return "redirect:/transaction/history";
	}
	
	/**
	 * 
	 * It stores the object in the database.
	 * 
	 * It withdraws the money from the logged in client's selected account.
	 * 
	 * The parameter "TransactionModel" receives all the information entered by the user.
	 * 
	 * It has the validation such as:
	 * i) the amount being withdrawn is greater than zero or not.
	 * ii) the account has sufficient funds or not.
	 * 
	 * It also inserts a transaction history.
	 * 
	 * @param m
	 * @param request
	 * @param transactionModel
	 * @return Redirects to transaction history.
	 */
	@PostMapping("/withdraw")
	public String withdrawMoney(Model m, HttpServletRequest request, TransactionModel transactionModel) {
		System.out.println("withdrawMoney(): begins, " + transactionModel.toString());
		TransactionsAuditModel transactionsAuditModel = new TransactionsAuditModel();
		try {
			HttpSession session = request.getSession(false);
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "You are not logged in. Please Log in first.");
				return "client/ClientLogin";
			}

			transactionsAuditModel.setAction(Constants.TRANSACTION_ACTION_WITHDRAW);
			transactionsAuditModel.setTransactionAmount(transactionModel.getBalance());

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			transactionsAuditModel.setTransactionDate(sqlDate);

			transactionsAuditModel
					.setTransactionAccountModel(new AccountsModel(transactionModel.getTransferFromAccountId()));

			// check if the balance which is being deposited is greater than zero
			if (transactionModel.getBalance() <= 0) {
				System.err.println("Transfer Amount is zero or less than zero.");
				m.addAttribute("message", "Amount should be greater than zero.");
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INVALID_INPUT);
				return "redirect:/transaction/history";
			}

			AccountsModel withdrawAccountModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferFromAccountId());
			
			// check if the account has sufficient funds
			if (withdrawAccountModel.getBalance() < transactionModel.getBalance()) {
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INSUFF_FUNDS);
				System.err.println("The selected account has insufficient funds.");
				m.addAttribute("message", "The selected account has insufficient funds.");
				return "redirect:/transaction/history";
			}

			// Deduct balance from the account from which money is being withdrawn
			accountsDao.updateAccountBalance(withdrawAccountModel.getAccountId(),
					withdrawAccountModel.getBalance() - transactionModel.getBalance(), sqlDate);

			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_SUCCESS);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_WITHDRAWAL);
			m.addAttribute("message", "Amount is withdrawn successfully.");
			m.addAttribute("status", "success");

		} catch (Exception e) {
			System.err.println("Exception occurred in withdrawMoney(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_ERROR);
			return "redirect:/transaction/history";
		} finally {
			transactionAuditDao.insertTransactionHistory(transactionsAuditModel);
		}
		return "redirect:/transaction/history";
	}

}
