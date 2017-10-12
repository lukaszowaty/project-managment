package pl.project.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.project.dao.RoleDao;
import pl.project.domain.security.UserRole;
import pl.project.model.Mail;
import pl.project.model.User;
import pl.project.service.MailService;
import pl.project.service.ProjectService;
import pl.project.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping
	public String home() {
		return "index";
	}
	
	@RequestMapping("loginUser")
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "signup")
	public String signup(Model model) {
		
		User user = new User();
		
		model.addAttribute("user", user);
		
		return "signup";
	}
	
	@PostMapping(value = "signup")
	public String processSignup(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() || userService.checkUserExists(user.getUsername(), user.getEmail())) {
			
			if(userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}
			
			if(userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}
			
			return "signup";
		} else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
			
			userService.createUser(user, userRoles);
			
			return "redirect:/";
			
		}
	}
	
	@GetMapping(value = "projectList")
	public String projectList(Model model) {
		model.addAttribute("projects", projectService.list());
		return "projectList";
	}
	
	@GetMapping("project/{id}")
	public String viewProject(@PathVariable Integer id, Model model) {
		model.addAttribute("project", projectService.get(id));
		return "projectView";
	}

	
	@GetMapping("contact") 
	public String contact(Model model) {
		model.addAttribute("mail", new Mail());
		return "contact";
	}
	
	@PostMapping("contact") 
	public String processContact(@Valid Mail mail, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "contact";
		}
		if(mailService.sendMail(mail)) {
			redirectAttributes.addFlashAttribute("classCss", "alert alert-success");
			redirectAttributes.addFlashAttribute("message", "Your message has been sent");
			
		} else {
			redirectAttributes.addFlashAttribute("classCss", "alert alert-warning");
			redirectAttributes.addFlashAttribute("message", "Cannot send message");
			
		}
		
		return "redirect:contact";
	}
}
