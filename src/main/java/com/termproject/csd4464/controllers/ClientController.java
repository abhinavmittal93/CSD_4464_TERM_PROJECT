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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.termproject.csd4464.dao.AccountsDao;
import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.model.AccountsModel;
import com.termproject.csd4464.model.ClientsModel;

/**
 * @author abhinavmittal
 * 
 * This controller handles all the requests related to a client, 
 * such as login, logout and client's home page.
 * And this is accessible only to clients.
 *
 */

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private AccountsDao accountsDao;

	/**
	 * 
	 * It displays a form to login for the clients.
	 * 
	 * It first checks if the user is already logged in, and if the user is already logged in
	 * then takes the user to Client's Home page.
	 * 
	 * @param m
	 * @param request
	 * @return ClientLogin.jsp
	 */
	@GetMapping("/login")
	public String getClientLoginPage(Model m, HttpServletRequest request) {
		System.out.println("getClientLoginPage() begins");
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("clientId") != null) {
			return "redirect:/client/home";
		}
		if (session != null && session.getAttribute("clientId") == null) {
			session.invalidate();
		}
		return "client/ClientLogin";
	}

	/**
	 * It receives the request to log in the client, 
	 * and gets the credentials in ClientsModel attribute.
	 * 
	 * And if the credentials are correct then, a session is created,
	 * and will be redirected to the Client's Home page.
	 * 
	 * @param m
	 * @param request
	 * @param clientsModel
	 * @return Redirect to Client's Home page
	 */
	@PostMapping("/login")
	public String clientLogin(Model m, HttpServletRequest request, ClientsModel clientsModel) {
		System.out.println("clientLogin() begins");

		HttpSession session = request.getSession(true);

		boolean isValidClient = clientDao.isValidClient(clientsModel.getUsername(), clientsModel.getPassword());
		if (!isValidClient) {
			System.err.println("Invalid Credentials.");
			m.addAttribute("message", "Invalid Credentials.");
			return "redirect:/client/login";
		}
		clientsModel = clientDao.getClientsDetailByUsername(clientsModel.getUsername());
		
		session.setAttribute("clientUserName", clientsModel.getUsername());
		session.setAttribute("clientId", clientsModel.getClientId());
		session.setAttribute("clientsModel", clientsModel);

		return "redirect:/client/home";
	}
	
	/**
	 * 
	 * It is used to log out the client and delete the session.
	 * 
	 * @param request
	 * @return Client Login Page
	 */
	@GetMapping("/logout")
	public String clientLogout(HttpServletRequest request) {
		System.out.println("clientLogout() begins");

		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/client/login";
	}

	/**
	 * 
	 * It gets the client's information and displays on a page.
	 * 
	 * It displays information such as personal information and
	 * the accounts related information.
	 * 
	 * @param m
	 * @param request
	 * @return ClientHome.jsp
	 */
	@GetMapping("/home")
	public String getClientHomePage(Model m, HttpServletRequest request) {
		System.out.println("getClientHomePage() begins");

		try {
			HttpSession session = request.getSession(false);
			
			if(session == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/client/login";
			}
			
			String sessionClientUserName = (String) session.getAttribute("clientUserName");
			if (sessionClientUserName == null || sessionClientUserName.isBlank()) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/client/login";
			}
			ClientsModel clientsModel = clientDao.getClientsDetailByUsername(sessionClientUserName);
			m.addAttribute("clientsModel", clientsModel);

			List<AccountsModel> accountsModels = accountsDao.getAccountByClientId(clientsModel.getClientId());
			m.addAttribute("accountsModels", accountsModels);
			return "client/ClientHome";
		} catch (Exception e) {
			System.err.println("Exception occurred in getClientHomePage(): " + e.getMessage() + e);
		}
		return "redirect:/client/login";
	}

}
