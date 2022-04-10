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
 * This Controller handles the requests related to admin's tasks such as Login/Logout,
 * creating or updating a client
 * 
 * And this is only accessible to admin users.
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
	
	
	/**
	 * 
	 * It displays a form to login for the admin users.
	 * 
	 * It first checks if the user is already logged in, and if the user is already logged in
	 * then takes the user to Clients' Details page.
	 * 
	 * @param m
	 * @param request
	 * @return AdminLogin.jsp
	 */
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

	/**
	 * 
	 * It receives the request to log in the admin user, 
	 * and gets the credentials in AdminUsersModel attribute
	 * 
	 * And if the credentials are correct then, a session is created,
	 * and will be redirected to the Clients' Details page.
	 * 
	 * @param m
	 * @param request
	 * @param adminUsersModel
	 * @return Redirects to the Clients' Details Page
	 */
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
	
	/**
	 * It is used to log out the admin user and delete the session.
	 * 
	 * @param request
	 * @return Admin Login Page
	 */
	@GetMapping("/logout")
	public String adminLogout(HttpServletRequest request) {
		System.out.println("adminLogout() begins");
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/admin/login";
	}
	
	
	/**
	 * It displays all the clients in the database.
	 * Here "clientsModels" is a reserved request attribute which is used to display clients' data in the table
	 * 
	 * @param m
	 * @param request
	 * @return Clients.jsp
	 */
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
	

	/**
	 * 
	 * It displays a form to input data to create a new client.
	 * 
	 * @param m
	 * @param request
	 * @return AddClient.jsp
	 */
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

	/**
	 * 
	 * It displays a form to input data to update a client.
	 * 
	 * Here "clientsModels" is a reserved request attribute which is used to display clients' data in the dropdown
	 * 
	 * @param m
	 * @param request
	 * @return UpdateClient.jsp
	 */
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

	/**
	 * 
	 * It saves object in database. The "ClientsModel" object receives the entered information
	 * and then it is stored in the database.
	 * 
	 * @param m
	 * @param request
	 * @param clientsModel
	 * @return Redirects to the Clients' Details Page
	 */
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

	/**
	 * It updates object in database. The "ClientsModel" object receives the entered information
	 * and then it is stored in the database.
	 * 
	 * 
	 * @param m
	 * @param request
	 * @param clientsModel
	 * @return Redirects to the Clients' Details Page
	 */
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
	
	
	/**
	 * 
	 * It gets the Client Details page where all the information related to the client is displayed,
	 * such as, all the accounts and utility bills.
	 * 
	 * It takes in a path variable named "clientId", for which the all the information has to be displayed.
	 * 
	 * Here "clientsModels" is a reserved request attribute which is used to display client's data.
	 * 
	 * Here "accountsModels" is a reserved request attribute which is used to display clients' accounts data.
	 * 
	 * Here "utilityBillsModels" is a reserved request attribute which is used to display clients' utility bills data.
	 * 
	 * @param m
	 * @param request
	 * @param clientId
	 * @return ClientDetails.jsp
	 */
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
