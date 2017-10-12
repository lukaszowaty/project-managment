package pl.project.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.project.model.Project;
import pl.project.service.UserService;
import pl.project.service.impl.ProjectServiceImpl;

@Controller
@Secured( {"ROLE_ADMIN"} )
@RequestMapping(value = "admin")
public class AdminController {
	
	@Autowired
	private ProjectServiceImpl projectService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/projects")
	public String listProjects(Model model) {
		model.addAttribute("projects", projectService.list());
		return "projectListAdmin";
	}

	@GetMapping("/project/create")
	public String displayAddProjectForm(Model model) {
		model.addAttribute("project", new Project());
		return "addProjectAdmin";
	}
	
	@PostMapping("project/create")
	public String processAddProjectForm(@Valid @ModelAttribute Project project, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "addProjectAdmin";
		} else {
			LocalDate now = LocalDate.now();
			project.setDate(now);
			projectService.save(project);
			return "redirect:/admin/projects";
		}
	}
	
	@GetMapping("/project/{id}")
	public String viewProject(@PathVariable Integer id, Model model) {
		model.addAttribute("project", projectService.get(id));
		return "projectViewAdmin";
	}
	
	@GetMapping("/project/edit/{id}")
	public String editProject(@PathVariable Integer id, Model model) {
		model.addAttribute("project", projectService.get(id));
		return "addProjectAdmin";
	}
	
	@GetMapping("/project/delete/{id}")
	public String deleteProject(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		projectService.delete(id);
		redirectAttributes.addFlashAttribute("message", "The project deleted");
		return "redirect:/admin/projects";
	}
	
	@GetMapping("/employees")
	public String listEmployees(Model model) {
		model.addAttribute("users", userService.getUserList());
		return "employeeListAdmin";
	}
	
	@GetMapping("/employee/delete/{id}")
	public String deleteEmployee(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		userService.delete(id);
		redirectAttributes.addFlashAttribute("message", "The employee deleted");
		return "redirect:/admin/employees";
	}
}
