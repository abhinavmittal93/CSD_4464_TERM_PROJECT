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

import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.dao.UtilityBillsDao;
import com.termproject.csd4464.dao.UtilityDao;
import com.termproject.csd4464.model.ClientsModel;
import com.termproject.csd4464.model.UtilitiesModel;
import com.termproject.csd4464.model.UtilityBillsModel;
import com.termproject.csd4464.utils.Constants;

/**
 * @author abhinavmittal, aarti
 * 
 * This controller handles the requests related to the Utility Bills for a client,
 * such as, creating a new utility bill.
 *
 */

@RequestMapping("/admin/utilities")
@Controller
public class AdminUtilityController {

	@Autowired
	private UtilityDao utilityDao;

	@Autowired
	private UtilityBillsDao utilityBillsDao;

	@Autowired
	private ClientDao clientDao;

	/**
	 * 
	 * It displays a form to input data for creating a new utility bill for a client.
	 * 
	 * It takes in a path variable named "clientId", for which the bill has to be created.
	 * 
	 * It first checks if the user is logged in and if the user is admin.
	 * 
	 * Here we are passing client's details in "clientsModel", and
	 * different types of utilities in "utilitiesModels"
	 * 
	 * @param m
	 * @param request
	 * @param clientId
	 * @return CreateUtilityBills.jsp
	 */
	@GetMapping("/bills/create/{clientId}")
	public String getCreateUtilitiesBillPage(Model m, HttpServletRequest request, @PathVariable Long clientId) {
		System.out.println("getCreateUtilitiesBillPage() begins: clientId: " + clientId);

		HttpSession session = request.getSession(false);
		if (session == null) {
			m.addAttribute("message", "Please Login first!!!");
			return "redirect:/admin/login";
		}

		Long sessionAdminUserId = (Long) session.getAttribute("adminId");
		if (sessionAdminUserId == null) {
			m.addAttribute("message", "Please Login first!!!");
			return "redirect:/admin/login";
		}

		List<UtilitiesModel> utilitiesModels = utilityDao.getUtilities();
		m.addAttribute("utilitiesModels", utilitiesModels);

		ClientsModel clientsModel = clientDao.getClientsDetailById(clientId);
		m.addAttribute("clientsModel", clientsModel);

		return "admin/CreateUtilityBills";
	}

	/**
	 * 
	 * It saves the object in the database.
	 * The "UtilityBillsModel" will receive the inputs entered by the user.
	 * 
	 * Here we have added validation, if the bill amount is greater than zero.
	 * 
	 * @param m
	 * @param request
	 * @param utilityBillsModel
	 * @return Redirects to the Clients' Details Page
	 */
	@PostMapping("/bills/create")
	public String createUtilitiesBillForAClient(Model m, HttpServletRequest request,
			UtilityBillsModel utilityBillsModel) {
		System.out
				.println("createUtilitiesBillForAClient() begins: utilityBillsModel: " + utilityBillsModel.toString());

		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/admin/login";
			}
			Long sessionAdminUserId = (Long) session.getAttribute("adminId");
			if (sessionAdminUserId == null) {
				m.addAttribute("message", "Please Login first!!!");
				return "redirect:/admin/login";
			}

			// check if the amount of bill is greater than zero or not
			if (utilityBillsModel.getBalance() <= 0) {
				System.err.println("Bill Amount is zero or less than zero.");
				m.addAttribute("message", "Amount should be greater than zero.");
				return "redirect:/admin/utilities/bills/create/" + utilityBillsModel.getClientsModel().getClientId();
			}

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			utilityBillsModel.setGeneratedOn(sqlDate);
			utilityBillsModel.setStatus(Constants.UTILITIES_BILLS_PAYMENT_STATUS_PENDING);
			utilityBillsDao.insertUtilityBill(utilityBillsModel);
			m.addAttribute("status", "success");
			m.addAttribute("message", "Bill is created successfully.");
		} catch (Exception e) {
			System.err.println("Exception in createUtilitiesBillForAClient(), " + e.getMessage() + e);
		}
		return "redirect:/admin/client/details/" + utilityBillsModel.getClientsModel().getClientId();
	}

}
