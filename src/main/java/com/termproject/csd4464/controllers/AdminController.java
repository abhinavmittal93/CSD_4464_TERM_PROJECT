/**
 * 
 */
package com.termproject.csd4464.controllers;

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
import com.termproject.csd4464.dao.AdminDao;
import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.dao.UtilityBillsDao;
import com.termproject.csd4464.model.AccountsModel;
import com.termproject.csd4464.model.AdminUsersModel;
import com.termproject.csd4464.model.ClientsModel;
import com.termproject.csd4464.model.UtilityBillsModel;

/**
 * @author abhinavmittal
 *
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private AccountsDao accountsDao;
	
	@Autowired
	private UtilityBillsDao utilityBillsDao;
	
	@GetMapping("/login")
	public String getAdminLoginPage(Model m, HttpServletRequest request) {
		System.out.println("getAdminLoginPage() begins");
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("adminId") != null) {
			return "redirect:/admin/clients";
		}
		
		if (session != null && session.getAttribute("adminId") == null) {
			session.invalidate();
		}
		
		return "admin/AdminLogin";
	}

	@PostMapping("/login")
	public String adminLogin(Model m, HttpServletRequest request, AdminUsersModel adminUsersModel) {
		System.out.println("adminLogin() begins");

		HttpSession session = request.getSession(true);

		boolean isValidAdminUser = adminDao.isValidAdminUser(adminUsersModel.getUsername(), adminUsersModel.getPassword());
		if (!isValidAdminUser) {
			System.err.println("Invalid Credentials.");
			m.addAttribute("message", "Invalid Credentials.");
			return "redirect:/admin/login";
		}
		adminUsersModel = adminDao.getAdminUserDetail(adminUsersModel.getUsername());
		
		session.setAttribute("adminUserName", adminUsersModel.getUsername());
		session.setAttribute("adminId", adminUsersModel.getAdminId());
		session.setAttribute("adminUsersModel", adminUsersModel);

		return "redirect:/admin/clients";
	}
	
	@GetMapping("/logout")
	public String adminLogout(HttpServletRequest request) {
		System.out.println("adminLogout() begins");
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/admin/login";
	}
	
	
	@GetMapping("/clients")
	private String getAllClients(Model m, HttpServletRequest request) {
		System.out.println("addClient:Get begins");
		
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
		
		List<ClientsModel> clientsModels = clientDao.getAllClients();
		m.addAttribute("clientsModels", clientsModels);
		return "admin/Clients";
	}
	

	@GetMapping("/addClient")
	private String addClient(Model m, HttpServletRequest request) {
		System.out.println("addClient:Get begins");
		
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
		
		return "admin/AddClient";
	}

	@GetMapping("/updateClient")
	private String updateClient(Model m, HttpServletRequest request) {
		System.out.println("updateClient:Get begins");
		
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
		
		List<ClientsModel> clientsModels = clientDao.getAllClients();
		m.addAttribute("clientsModels", clientsModels);
		return "admin/UpdateClient";
	}

	@PostMapping("/addClient")
	private String addClient(Model m, HttpServletRequest request, ClientsModel clientsModel) {
		System.out.println("addClient:Post begins, clientsModel: " + clientsModel.toString());
		try {

			ClientsModel existingClientsModel = clientDao.getClientsDetailByUsername(clientsModel.getUsername());
			if (existingClientsModel != null) {
				System.out.println("A client already exists with username: " + clientsModel.getUsername());
				m.addAttribute("message", "A client already exists with username: " + clientsModel.getUsername()
						+ ". Please try another username.");
			} else {
				clientDao.addClient(clientsModel);
				System.out.println("Client is created successfully.");
				m.addAttribute("message", "Client is created successfully.");
			}
		} catch (Exception e) {
			System.err.println("Exception occurred while adding a new client: " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}

		return "redirect:/admin/clients";
	}

	@PostMapping("/updateClient")
	private String updateClient(Model m, HttpServletRequest request, ClientsModel clientsModel) {
		System.out.println("updateClient:Post begins, clientsModel: " + clientsModel.toString());
		try {

			ClientsModel existingClientsModel = clientDao.getClientsDetailByUsername(clientsModel.getUsername());
			if (existingClientsModel == null) {
				System.out.println("A client does not exists with username: " + clientsModel.getUsername());
				m.addAttribute("message", "A client does not exists with username: " + clientsModel.getUsername());
			} else {
				clientDao.updateClient(clientsModel);
				System.out.println("Client is updated successfully.");
				m.addAttribute("message", "Client is updated successfully.");
			}
		} catch (Exception e) {
			System.err.println("Exception occurred while updating a client: " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}
		return "redirect:/admin/clients";
	}
	
	@GetMapping("/client/details/{clientId}")
	public String getClientDetailsPage(Model m, HttpServletRequest request, @PathVariable Long clientId) {
		System.out.println("getAccountsPageForClient(): begins, clientId: " + clientId);
		
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
		
		try {
			List<AccountsModel> accountsModels = accountsDao.getAccountByClientId(clientId);
			ClientsModel clientsModel = clientDao.getClientsDetailById(clientId);
			List<UtilityBillsModel> utilityBillsModels = utilityBillsDao.getUtilityBillsByClient(clientId);
			m.addAttribute("accountsModels", accountsModels);
			m.addAttribute("clientsModel", clientsModel);
			m.addAttribute("utilityBillsModels", utilityBillsModels);
		} catch (Exception e) {
			System.err.println("Exception occurred in getClientDetailsPage(): " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
			return "redirect:/admin/clients";
		}
		return "admin/ClientDetails";
	}

}
