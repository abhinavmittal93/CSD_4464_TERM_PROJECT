/**
 * 
 */
package com.termproject.csd4464.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	@GetMapping("/{clientId}")
	public String getAccountsPageForClient(Model m, HttpServletRequest request, @PathVariable Long clientId) {
		System.out.println("getAccountsPageForClient(): begins, clientId: " + clientId);
		List<AccountsModel> accountsModels = accountsDao.getAccountByClientId(clientId);
		ClientsModel clientsModel = clientDao.getClientsDetailById(clientId);
		m.addAttribute("accountsModels", accountsModels);
		m.addAttribute("clientsModel", clientsModel);
		return "admin/Accounts";
	}

	@GetMapping("/create/{clientId}")
	private String createAccountPage(Model m, HttpServletRequest request, @PathVariable Long clientId) {
		System.out.println("createAccount:Get begins, clientId: " + clientId);
		try {
			ClientsModel clientsModel = clientDao.getClientsDetailById(clientId);
			List<BankTypesModel> bankTypesModels = bankTypesDao.getBankTypes();

			m.addAttribute("clientsModel", clientsModel);
			m.addAttribute("bankTypesModels", bankTypesModels);
		} catch (Exception e) {
			System.out.println("Exception occurred in createAccountPage(): " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}
		return "admin/CreateAccount";
	}

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
				return "redirect:/admin/accounts/" + accountsModel.getClientsModel().getClientId();
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
		} catch (Exception e) {
			System.out.println("Exception occurred in createAccount(): " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}
		return "redirect:/admin/accounts/" + accountsModel.getClientsModel().getClientId();
	}

}
