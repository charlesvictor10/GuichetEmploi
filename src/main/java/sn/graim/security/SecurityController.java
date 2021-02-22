package sn.graim.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sn.graim.dao.RoleRepository;
import sn.graim.dao.UtilisateurRepository;
import sn.graim.entities.Role;
import sn.graim.entities.Utilisateur;

@Controller
public class SecurityController {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private RoleRepository roleRepository;
		
	@PostConstruct
	public void init() {
		System.out.println("Initialisation du contrôleur d'authentification");
	}
	
	@RequestMapping(value={"/", "/index/**"}, method = RequestMethod.GET)
	public String index(Model model) {
		return "redirect:/index";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String formInscription(Model model) {
		String x = "RECRUTEUR";
		String y = "CANDIDAT";
		List<Role> roles = roleRepository.listeRole(x, y);
		model.addAttribute("roles", roles);
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}
	
	@RequestMapping(value="/SaveUtilisateur", method=RequestMethod.POST)
	public String Save(@Valid Utilisateur u, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "inscription";
		}
		
		u.setActive(true);
		u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
		utilisateurRepository.save(u);
				
		return "redirect:/connexion";
	}
		
	@RequestMapping(value = "/connexion", method=RequestMethod.GET)
	public String login(Model model) {
		return "connexion";
	}
	
	@RequestMapping(value="/logoutSuccessful", method=RequestMethod.GET)
	public String logoutSuccessful(Model model) {
		return "redirect:/connexion";
	}
	
	@RequestMapping(value = "/reinitialisation", method=RequestMethod.GET)
	public String reinitialiser(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "reinitialiser";
	}
	
	@RequestMapping(value="/userInfo", method=RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("User name:" +userName);
		User loginUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginUser);
		model.addAttribute("userInfo", userInfo);
		return "";
	}
		
	@RequestMapping(value="/getLogedUser", method=RequestMethod.GET)
	public Map<String, Object> getLogedUser(HttpSession session){
		SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username = context.getAuthentication().getName();
		List<String> roles = new ArrayList<String>();
		for(GrantedAuthority ga: context.getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("roles", roles);
		return params;
	}
	
	@RequestMapping(value="/editProfil", method=RequestMethod.GET)
	public String editerUtilisateur(String username, Model model) {
		Utilisateur u = utilisateurRepository.findByUsername(username);
		model.addAttribute("utilisateur", u);
		return "editUtilisateur";
	}
			
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesDenied(Model model, Principal principal) {
		if(principal!=null) {
			User loginUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = WebUtils.toString(loginUser);
			model.addAttribute("userInfo", userInfo);
			String message = "Désolé, "+principal.getName()+" vous n'avez pas le droit d'accéder à cette page";
			model.addAttribute("message", message);
		}
		return "403";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String errorPage(Model model) {
		return "error";
	}
	
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String notFoundPage(Model model) {
		return "404";
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Destuction du contrôleur d'authentification");
	}
}
