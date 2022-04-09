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

import com.termproject.csd4464.dao.ClientDao;
import com.termproject.csd4464.dao.UtilityBillsDao;
import com.termproject.csd4464.model.ClientsModel;
import com.termproject.csd4464.model.UtilityBillsModel;
import com.termproject.csd4464.utils.Constants;

/**
 * @author abhinavmittal
 *
 */

@RequestMapping("/client/utilities")
@Controller
public class ClientUtilityController {

	@Autowired
	private UtilityBillsDao utilityBillsDao;

	@Autowired
	private ClientDao clientDao;

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

		List<UtilityBillsModel> utilityBillsModels = utilityBillsDao.getUtilityBillsByClient(sessionClientId);
		m.addAttribute("utilityBillsModels", utilityBillsModels);

		ClientsModel clientsModel = clientDao.getClientsDetailById(sessionClientId);
		m.addAttribute("clientsModel", clientsModel);

		return "client/PayBills";
	}

	@PostMapping("/bills/pay")
	public String payUtilityBillsForAAclient(Model m, HttpServletRequest request, UtilityBillsModel utilityBillsModel) {
		System.out.println("payUtilityBillsForAAclient() begins: utilityBillsModel: " + utilityBillsModel.toString());

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

			// check if the amount of bill is greater than zero or not
			if (utilityBillsModel.getBalance() <= 0) {
				System.err.println("Bill Amount is zero or less than zero.");
				m.addAttribute("message", "Amount should be greater than zero.");
				return "redirect:/client/utilities/bills";
			}

			java.util.Date date = new java.util.Date();
			java.sql.Date sqlDate = new Date(date.getTime());
			utilityBillsModel.setPaidOn(sqlDate);
			utilityBillsModel.setStatus(Constants.UTILITIES_BILLS_PAYMENT_STATUS_PAID);
			utilityBillsDao.insertUtilityBill(utilityBillsModel);
			m.addAttribute("status", "success");
			m.addAttribute("message", "Bill is paid successfully.");
		} catch (Exception e) {
			System.err.println("Exception in payUtilityBillsForAAclient(), " + e.getMessage() + e);
		}
		return "redirect:/client/utilities/bill/historty";
	}

}
