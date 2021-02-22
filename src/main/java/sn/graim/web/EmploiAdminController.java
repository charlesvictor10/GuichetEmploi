package sn.graim.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sn.graim.dao.MetiersRepository;
import sn.graim.dao.RegionRepository;
import sn.graim.dao.RoleRepository;
import sn.graim.dao.SecteursRepository;
import sn.graim.entities.Metiers;
import sn.graim.entities.Region;
import sn.graim.entities.Role;
import sn.graim.entities.Secteurs;

@Controller
public class EmploiAdminController {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private SecteursRepository secteurRepository;
	@Autowired
	private MetiersRepository metierRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void init() {
		System.out.println("Initialisation du contrôleur de l'administration");
	}
	
	@RequestMapping(value="/formRegion", method=RequestMethod.GET)
	public String regionForm(Model model) {
		model.addAttribute("regions", new Region());
		return "nouvelleRegion";
	}
	
	@RequestMapping(value="/saveRegion", method=RequestMethod.POST)
	public String saveRegion(@Valid Region region, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "nouvelleRegion";
		}
		
		regionRepository.save(region);
		
		return "redirect:/listeRegions";
	}
	
	@RequestMapping(value="/listeRegions", method=RequestMethod.GET)
	public String listeRegions(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Region> regs = regionRepository.recherche("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeRegions", regs.getContent());
		int[] pages = new int[regs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "regions";
	}
	
	@RequestMapping(value="/editRegion", method=RequestMethod.GET)
	public String editerRegion(Long id, Model model) {
		Region r = regionRepository.getOne(id);
		model.addAttribute("region", r);
		return "editRegion";
	}
	
	@RequestMapping(value="/updateRegion", method=RequestMethod.POST)
	public String modifierRegion(@Valid Region region, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "editRegion";
		}
		
		regionRepository.save(region);
		
		return "redirect:/listeRegions";
	}
	
	@RequestMapping(value="/deleteRegion", method=RequestMethod.DELETE)
	public String supprimerRegion(Long id) {
		regionRepository.deleteById(id);
		return "regions";
	}
	
	@RequestMapping(value="/formSecteur", method=RequestMethod.GET)
	public String secteurForm(Model model) {
		model.addAttribute("secteurs", new Secteurs());
		return "nouveauSecteur";
	}
	
	@RequestMapping(value="/saveSecteur", method=RequestMethod.POST)
	public String saveSecteur(@Valid Secteurs secteurs, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "nouveauSecteur";
		}
		
		secteurRepository.save(secteurs);
		
		return "redirect:/listeSecteurs";
	}
	
	@RequestMapping(value="/listeSecteurs", method=RequestMethod.GET)
	public String listeSecteurs(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Secteurs> secs = secteurRepository.recherche("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeSecteurs", secs.getContent());
		int[] pages = new int[secs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "secteurs";
	}
	
	@RequestMapping(value="/editSecteur", method=RequestMethod.GET)
	public String editerSecteur(Long id, Model model) {
		Secteurs s = secteurRepository.getOne(id);
		model.addAttribute("secteur", s);
		return "editSecteur";
	}
	
	@RequestMapping(value="/updateSecteur", method=RequestMethod.POST)
	public String modifierSecteurs(@Valid Secteurs s, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "editSecteur";
		}
		
		secteurRepository.save(s);
		
		return "redirect:/listeSecteurs";
	}
	
	@RequestMapping(value="/deleteSecteur", method=RequestMethod.DELETE)
	public String supprimerSecteur(Long id) {
		secteurRepository.deleteById(id);
		return "secteurs";
	}
	
	@RequestMapping(value="/formMetier", method=RequestMethod.GET)
	public String metierForm(Model model) {
		List<Secteurs> secteurs = secteurRepository.findAll();
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("metier", new Metiers());
		return "nouveauMetier";
	}
	
	@RequestMapping(value="/saveMetier", method=RequestMethod.POST)
	public String saveMetier(@Valid Metiers m, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "nouveauMetier";
		}
		
		metierRepository.save(m);
		
		return "redirect:/listeMetiers";
	}
	
	@RequestMapping(value="/listeMetiers", method=RequestMethod.GET)
	public String listeMetiers(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Metiers> metiers = metierRepository.recherche("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeMetiers", metiers.getContent());
		int[] pages = new int[metiers.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "metiers";
	}
	
	@RequestMapping(value="/editMetier", method=RequestMethod.GET)
	public String editerMetier(Long id, Model model) {
		Metiers m = metierRepository.getOne(id);
		List<Secteurs> secteurs = secteurRepository.findAll();
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("metier", m);
		return "editMetier";
	}
	
	@RequestMapping(value="/updateMetier", method=RequestMethod.POST)
	public String modifierMetier(@Valid Metiers m, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "editMetier";
		}
		
		metierRepository.save(m);
		
		return "redirect:/listeMetiers";
	}
	
	@RequestMapping(value="/deleteMetier", method=RequestMethod.DELETE)
	public String supprimerMetier(Long id) {
		metierRepository.deleteById(id);
		return "metiers";
	}
	
	@RequestMapping(value="/formRole", method=RequestMethod.GET)
	public String roleForm(Model model) {
		model.addAttribute("role", new Role());
		return "nouveauRole";
	}
	
	@RequestMapping(value="/saveRole", method=RequestMethod.POST)
	public String saveRole(@Valid Role role, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "nouveauRole";
		}
		
		roleRepository.save(role);
		
		return "redirect:/listeRoles";
	}
	
	@RequestMapping(value="/listeRoles", method=RequestMethod.GET)
	public String listeRoles(Model model) {
		model.addAttribute("listeRoles", roleRepository.findAll());
		return "roles";
	}
	
	@RequestMapping(value="/editRole", method=RequestMethod.GET)
	public String editerRole(String nom, Model model) {
		Role role = roleRepository.findByNom(nom);
		model.addAttribute("role", role);
		return "editRole";
	}
	
	@RequestMapping(value="/updateRole", method=RequestMethod.POST)
	public String modifierRole(@Valid Role r, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "editMetier";
		}
		
		roleRepository.save(r);
		
		return "redirect:/listeRoles";
	}
	
	@RequestMapping(value="/deleteRole", method=RequestMethod.DELETE)
	public String supprimerRole(Role r) {
		roleRepository.delete(r);
		return "roles";
	}
		
	@PreDestroy
	public void destroy() {
		System.out.println("Destuction du contrôleur de l'administration");
	}
}
