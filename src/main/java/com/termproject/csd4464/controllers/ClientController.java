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
 */

@Controller
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private AccountsDao accountsDao;
	
	
	@GetMapping("/login")
	public String getClientLoginPage(Model m) {
		System.out.println("getClientLoginPage() begins");
		return "client/ClientLogin";
	}
	
	
	@PostMapping("/login")
	public String clientLogin(Model m, HttpServletRequest request, ClientsModel clientsModel) {
		System.out.println("clientLogin() begins");
		
		HttpSession session = request.getSession(false);
		String sessionClientUserName = (String) session.getAttribute("clientUserName");
		
		if(sessionClientUserName!= null && !sessionClientUserName.isBlank()) {
			return "redirect:/client/home";
		}
		
		session = request.getSession(true);
		
		boolean isValidClient = clientDao.isValidClient(clientsModel.getUsername(), clientsModel.getPassword());
		if(!isValidClient) {
			System.out.println("Invalid Credentials.");
			m.addAttribute("message", "Invalid Credentials.");
			return "redirect:/client/login";
		}
		session.setAttribute("clientUserName", clientsModel.getUsername());
		
		return "redirect:/client/home";
	}
	
	
	
	
	@GetMapping("/home")
	public String getClientHomePage(Model m, HttpServletRequest request) {
		System.out.println("getClientHomePage() begins");
		
		try {
			HttpSession session = request.getSession(false);
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
			System.out.println("Exception occurred in getClientHomePage(): " + e.getMessage()+ e);
		}
		return "redirect:/client/login";
	}
	
	

}
