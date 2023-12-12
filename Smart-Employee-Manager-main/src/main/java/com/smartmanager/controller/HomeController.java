package com.smartmanager.controller;

import com.smartmanager.entities.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.smartmanager.dao.Repository;
import com.smartmanager.entities.User;
@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private Repository repository;
    @GetMapping("/")
	public String home(Model model)
	{
    	model.addAttribute("tittle", "Home page");
		return "home.html";
	}
    @GetMapping("/about")
	public String about(Model model)
	{
    	model.addAttribute("tittle", "About page");
		return "about.html";
	}
    @GetMapping("/contact")
   	public String contact(Model model)
   	{
       	model.addAttribute("tittle", "contact page");
   		return "ContactUs.html";
   	}
    @GetMapping("/login")
   	public String login(Model model)
   	{
		model.addAttribute("tittle", "LogIn page");
   		return "login.html";
   	}
	@PostMapping("/do_register")
	public String registeruser(@Valid @ModelAttribute("user") User user, BindingResult binding, @RequestParam(name="agreement",defaultValue = "false")  boolean agreement, Model model, HttpSession session)
	{

		try {
//		   System.out.println(binding);
//    	if(!agreement)
//    	{
//    		session.setAttribute("message",new Msgstr("Please check the agreement ","alert-danger"));
//
//    		return "signup.html";
//    	}

			if(binding.hasErrors())
			{
				model.addAttribute("user", user);
				return "signup";
			}
//		   user.setRole("ROLE_ADMIN");
			user.setEnabled("true");
			user.setImageUrl("default.png");
			user.setPassword(encoder.encode(user.getPassword()));

			this.repository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message",new Message("Sucessfully Registered","alert-success"));
			return "signup";
		}catch (Exception e){
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("not Registered","alert-danger"));
			return "signup";
		}
//		return "signup.html";
	}

}
