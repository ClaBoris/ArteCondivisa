//package it.uniroma3.siw.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//
//@Controller
//public class ErrController {
//	@GetMapping("/error")
//	public String error(HttpServletRequest request, Model model){
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		if(status != null){
//			model.addAttribute("statuscode", Integer.valueOf(status.toString()));
//			return "/error/error.html";
//		}
//		return "/error/error.html";
//	}
//}
