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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.termproject.csd4464.dao.AccountsDao;
import com.termproject.csd4464.dao.BankTypesDao;
import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.model.AccountsModel;
import com.termproject.csd4464.model.BankTypesModel;
import com.termproject.csd4464.model.ClientsModel;

/**
 * @author abhinavmittal
 * 
 * This controller handles the request related to the accounts of a client,
 * such as creating a new account.
 * 
 * And this is only accessible to admin users.
 *
 */
@Controller
@RequestMapping("/admin/accounts")
public class AccountController {

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private BankTypesDao bankTypesDao;

	@Autowired
	private AccountsDao accountsDao;

	/**
	 * 
	 * It displays a form to input data for creating a new account.
	 * 
	 * It takes in a path variable named "clientId", for which the account has to be created.
	 * It first checks if the user is logged in and if the user is admin.
	 * 
	 * Here we are passing client's details in "clientsModel", and
	 * different types of accounts in "bankTypesModels"
	 * 
	 * @param m
	 * @param request
	 * @param clientId
	 * @return CreateAccount.jsp
	 */
	@GetMapping("/create/{clientId}")
	private String createAccountPage(Model m, HttpServletRequest request, @PathVariable Long clientId) {
		System.out.println("createAccount:Get begins, clientId: " + clientId);
		try {
			
			HttpSession session = request.getSession(false);
			if(session == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/admin/login";
			}
			
			Long sessionAdminUserId = (Long) session.getAttribute("adminId");
			if (sessionAdminUserId == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/admin/login";
			}
			
			ClientsModel clientsModel = clientDao.getClientsDetailById(clientId);
			List<BankTypesModel> bankTypesModels = bankTypesDao.getBankTypes();

			m.addAttribute("clientsModel", clientsModel);
			m.addAttribute("bankTypesModels", bankTypesModels);
		} catch (Exception e) {
			System.err.println("Exception occurred in createAccountPage(): " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}
		return "admin/CreateAccount";
	}

	/**
	 * 
	 * It saves the object in the database. The "AccountsModel" will receive the inputs entered by the user.
	 * 
	 * Here we have added validations, if the client has already an account with same account type,
	 * 
	 * And we are generating a unique Account Number as well for the new account.
	 * 
	 * @param m
	 * @param request
	 * @param accountsModel
	 * @return Redirects to the Clients' Details Page
	 */
	@PostMapping("/create")
	private String createAccount(Model m, HttpServletRequest request, AccountsModel accountsModel) {
		System.out.println("createAccount:Post begins, accountsModel: " + accountsModel.toString());
		try {

			AccountsModel existingAccountModel = accountsDao.getAccountByClientIdAndBankTypeId(
					accountsModel.getClientsModel().getClientId(), accountsModel.getBankTypesModel().getBankTypeId());
			
			BankTypesModel bankTypesModel = bankTypesDao.getBankTypeById(accountsModel.getBankTypesModel().getBankTypeId());

			if (existingAccountModel != null) {
				m.addAttribute("message", "This client has already an account with Account Type: "
						+ bankTypesModel.getBankType());
				return "redirect:/admin/client/details/" + accountsModel.getClientsModel().getClientId();
			}

			Long clientId = accountsModel.getClientsModel().getClientId();
			Long bankTypeId = accountsModel.getBankTypesModel().getBankTypeId();

			int min = 1000000;
			int max = 9999999;

			String accountNumber = "ACC" + clientId + bankTypeId + Math.round(Math.random() * (max - min + 1) + min);

			accountsModel.setAccountNo(accountNumber);
			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			accountsModel.setCreatedDate(sqlDate);
			accountsModel.setUpdatedDate(sqlDate);
			accountsDao.addAccount(accountsModel);
			m.addAttribute("message", "Account created successfully.");
			m.addAttribute("status", "success");
		} catch (Exception e) {
			System.err.println("Exception occurred in createAccount(): " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}
		return "redirect:/admin/client/details/" + accountsModel.getClientsModel().getClientId();
	}

}
