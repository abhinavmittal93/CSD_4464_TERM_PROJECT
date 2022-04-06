/**
 * 
 */
package com.termproject.csd4464.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.model.ClientsModel;

/**
 * @author abhinavmittal
 *
 */
@RequestMapping("/admin")
@Controller
public class AdminController {
	
	
	@Autowired
	private ClientDao clientDao;
	
	@GetMapping("/addClient")
	private String addClient(Model m, HttpServletRequest request) {
		System.out.println("addClient:Get begins");
		return "admin/AddClient";
	}
	
	@PostMapping("/addClient")
	private String addClient(Model m, HttpServletRequest request, ClientsModel clientsModel) {
		System.out.println("addClient:Post begins");
		try {
			
			ClientsModel existingClientsModel = clientDao.getClientsDetailByUsername(clientsModel.getUsername());
			if(existingClientsModel != null) {
				System.out.println("A client already exists with username: " + clientsModel.getUsername());
				m.addAttribute("message", "A client already exists with username: " + clientsModel.getUsername() + ". Please try another username.");
			} else {
				clientDao.addClient(clientsModel);
				System.out.println("Client is created successfully.");
				m.addAttribute("message", "Client is created successfully.");
			}
		} catch(Exception e) {
			System.out.println("Exception occurred while adding a new client: " + e.getMessage() + e);
			m.addAttribute("message", "An error occurred. Please contact administrator.");
		}
		
		return "Response";
	}

}