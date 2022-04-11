/**
 * 
 */
package com.termproject.csd4464.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.termproject.csd4464.dao.AccountsDao;
import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.dao.TransactionAuditDao;
import com.termproject.csd4464.dao.UtilityAuditDao;
import com.termproject.csd4464.dao.UtilityBillsDao;
import com.termproject.csd4464.dao.UtilityDao;
import com.termproject.csd4464.model.AccountsModel;
import com.termproject.csd4464.model.ClientsModel;
import com.termproject.csd4464.model.TransactionModel;
import com.termproject.csd4464.model.TransactionsAuditModel;
import com.termproject.csd4464.model.UtilitiesAuditModel;
import com.termproject.csd4464.model.UtilitiesModel;
import com.termproject.csd4464.model.UtilityBillsModel;
import com.termproject.csd4464.utils.Constants;

/**
 * @author abhinavmittal, aarti
 * 
 * This controller handles all the requests related to the Utility Bills of a client,
 * such as displaying all the bills, paying a bill
 * and displaying the history of bills.
 * 
 * And this is accessible only to clients.
 *
 */

@RequestMapping("/client/utilities")
@Controller
public class ClientUtilityController {

	@Autowired
	private UtilityBillsDao utilityBillsDao;

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private TransactionAuditDao transactionAuditDao;

	@Autowired
	private UtilityDao utilityDao;

	@Autowired
	private AccountsDao accountsDao;

	@Autowired
	private UtilityAuditDao utilityAuditDao;

	/**
	 * 
	 * It displays a form to input the data to pay a bill.
	 * 
	 * It displays the bills for the logged in client.
	 * 
	 * It gets the clientId from the session and get all data on basis of that.
	 * 
	 * Here "clientsModels" is a reserved request attribute which is used to display client's data.
	 * 
	 * Here "utilityBillsModels" is a reserved request attribute which is used to display client's utility bills data.
	 * 
	 * Here "clientAccounts" is a reserved request attribute which is used to display client's accounts.
	 * 
	 * @param m
	 * @param request
	 * @return PayBills.jsp
	 */
	@GetMapping("/bills")
	public String getClientBills(Model m, HttpServletRequest request) {
		System.out.println("getClientBills() begins:");

		HttpSession session = request.getSession(false);
		if (session == null) {
			m.addAttribute("message", "Please Login first!!!");
			return "redirect:/client/login";
		}

		Long sessionClientId = (Long) session.getAttribute("clientId");
		if (sessionClientId == null) {
			m.addAttribute("message", "Please Login first!!!");
			return "redirect:/client/login";
		}

		List<UtilityBillsModel> utilityBillsModels = utilityBillsDao.getUnpaidUtilityBillsByClient(sessionClientId);
		m.addAttribute("utilityBillsModels", utilityBillsModels);

		List<AccountsModel> clientAccounts = accountsDao.getAccountByClientId(sessionClientId);
		m.addAttribute("clientAccounts", clientAccounts);

		ClientsModel clientsModel = clientDao.getClientsDetailById(sessionClientId);
		m.addAttribute("clientsModel", clientsModel);

		return "client/PayBills";
	}

	/**
	 * 
	 * It saves and updates the objects in the database.
	 * 
	 * The "UtilityBillsModel" object receives the entered information
	 * and then it is updated in the database.
	 * 
	 * The "TransactionModel" receives the information related the account,
	 * through which the bill has to be paid.
	 * 
	 * It has validations, such as the accounts has sufficient funds or not.
	 * 
	 * It updates the status of bill to paid.
	 * It inserts a transaction history for the related account.
	 * Also, it inserts a record in utilities_audit table, which has all the paid bills history. 
	 * 
	 * @param m
	 * @param request
	 * @param utilityBillsModel
	 * @param transactionModel
	 * @return Redirects to the Bill History page
	 */
	@PostMapping("/bills/pay")
	public String payUtilityBillsForAclient(Model m, HttpServletRequest request, UtilityBillsModel utilityBillsModel,
			TransactionModel transactionModel) {
		System.out.println("payUtilityBillsForAclient() begins: utilityBillsModel: " + utilityBillsModel.toString()
				+ ", transactionModel: " + transactionModel.toString());
		TransactionsAuditModel transactionsAuditModel = new TransactionsAuditModel();
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/client/login";
			}

			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/client/login";
			}

			utilityBillsModel = utilityBillsDao.getUtilityBillById(utilityBillsModel.getUtilityBillId());

			transactionsAuditModel.setAction(Constants.TRANSACTION_ACTION_UTILITY_BILL_PAYMENT);
			transactionsAuditModel.setTransactionAmount(utilityBillsModel.getBalance());

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			transactionsAuditModel.setTransactionDate(sqlDate);

			// get the details of the account which is being used to pay the bill
			AccountsModel accountsModel = accountsDao
					.getAccountByAccountId(transactionModel.getTransferFromAccountId());

			transactionsAuditModel.setTransactionAccountModel(accountsModel);

			// check if the account has sufficient funds
			if (accountsModel.getBalance() < utilityBillsModel.getBalance()) {
				transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
				transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_INSUFF_FUNDS);
				System.err.println("The selected account has insufficient funds.");
				m.addAttribute("message", "The selected account has insufficient funds.");
				return "redirect:/transaction/history";
			}

			UtilitiesModel utilitiesModel = utilityDao.getUtilityById(utilityBillsModel.getUtilityId());
			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_SUCCESS);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_UTILITY_BILL_PAYMENT
					+ utilitiesModel.getUtilityType() + " - " + utilitiesModel.getUtilityName());

			// adding a record for bill payment in the audit table
			UtilitiesAuditModel utilitiesAuditModel = new UtilitiesAuditModel();
			utilitiesAuditModel.setAmount(utilityBillsModel.getBalance());
			utilitiesAuditModel.setClientId(sessionClientId);
			utilitiesAuditModel.setPaidOn(sqlDate);
			utilitiesAuditModel.setStatus(Constants.UTILITIES_BILLS_PAYMENT_STATUS_PAID);
			utilitiesAuditModel.setReasonCode("Bill is paid successfully.");
			utilitiesAuditModel.setUtilityBillsModel(utilityBillsModel);

			
			utilityBillsDao.updateUtilityBill(Constants.UTILITIES_BILLS_PAYMENT_STATUS_PAID, sqlDate,
					utilityBillsModel.getUtilityBillId());

			accountsDao.updateAccountBalance(accountsModel.getAccountId(),
					accountsModel.getBalance() - utilityBillsModel.getBalance(), sqlDate);
			utilityAuditDao.insertUtilityAuditHistory(utilitiesAuditModel);

			m.addAttribute("status", "success");
			m.addAttribute("message", "Bill is paid successfully.");
		} catch (Exception e) {
			System.err.println("Exception in payUtilityBillsForAclient(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
			transactionsAuditModel.setStatus(Constants.TRANSACTION_STATUS_FAILED);
			transactionsAuditModel.setReasonCode(Constants.TRANSACTION_REASON_CODE_ERROR);
			return "redirect:/transaction/history";
		} finally {
			transactionAuditDao.insertTransactionHistory(transactionsAuditModel);
		}
		return "redirect:/client/utilities/bills/history";
	}

	/**
	 * 
	 * It displays all the history of paid bills for the logged in client.
	 * 
	 * Here "utilitiesAuditModels" is a reserved request attribute,
	 * which is used to display client's paid bill history in the table
	 * 
	 * @param m
	 * @param request
	 * @return UtilityBillsHistory.jsp
	 */
	@GetMapping("/bills/history")
	public String getPaidBillsHistory(Model m, HttpServletRequest request) {
		System.out.println("getPaidBillsHistory() begins:");
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/client/login";
			}
			Long sessionClientId = (Long) session.getAttribute("clientId");
			if (sessionClientId == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/client/login";
			}
			List<UtilitiesAuditModel> utilitiesAuditModels = utilityAuditDao
					.getUtilityBillsHistoryByClient(sessionClientId);
			m.addAttribute("utilitiesAuditModels", utilitiesAuditModels);
		} catch (Exception e) {
			System.err.println("Exception in getPaidBillsHistory(), " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact Administrator");
		}
		return "client/UtilityBillsHistory";
	}

}
