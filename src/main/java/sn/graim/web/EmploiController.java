package sn.graim.web;

import java.io.File;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sn.graim.dao.CandidatRepository;
import sn.graim.dao.CritereRepository;
import sn.graim.dao.ExperienceRepository;
import sn.graim.dao.FormationRepository;
import sn.graim.dao.LangueRepository;
import sn.graim.dao.MetiersRepository;
import sn.graim.dao.NiveauLangueRepository;
import sn.graim.dao.OffresRepository;
import sn.graim.dao.RecruteurRepository;
import sn.graim.dao.RegionRepository;
import sn.graim.dao.SecteursRepository;
import sn.graim.dao.UtilisateurRepository;
import sn.graim.entities.Candidat;
import sn.graim.entities.Critere;
import sn.graim.entities.Experience;
import sn.graim.entities.Formation;
import sn.graim.entities.Langue;
import sn.graim.entities.Metiers;
import sn.graim.entities.NiveauLangue;
import sn.graim.entities.Offres;
import sn.graim.entities.Recruteur;
import sn.graim.entities.Region;
import sn.graim.entities.Secteurs;
import sn.graim.entities.Utilisateur;

@Controller
public class EmploiController {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private SecteursRepository secteurRepository;
	@Autowired
	private RecruteurRepository recruteurRepository;
	@Autowired
	private CandidatRepository candidatRepository;
	@Autowired
	private OffresRepository offreRepository;
	@Autowired
	private MetiersRepository metierRepository;
	@Autowired
	private LangueRepository langueRepository;
	@Autowired
	private NiveauLangueRepository niveauRepository;
	@Autowired
	private FormationRepository formationRepository;
	@Autowired
	private ExperienceRepository experienceRepository;
	@Autowired
	private CritereRepository critereRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Value("${dir.logo}")
	private String logoDir;
	@Value("${dir.cv}")
	private String cvDir;
	@Value("${dir.photo}")
	private String photoDir;
	
	@PostConstruct
	public void init() {
		System.out.println("Initialisation du contrôleur des emplois");
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model) {
		List<Region> regions = regionRepository.findAll();
		List<Metiers> metiers = metierRepository.findAll();
		List<Offres> offres = offreRepository.findAll();
		List<Secteurs> secteurs = secteurRepository.findAll();
		model.addAttribute("regions", regions);
		model.addAttribute("metiers", metiers);
		model.addAttribute("offres", offres);
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("nombres", offreRepository.nombreOffres());
		model.addAttribute("nbrOffreParSecteur", offreRepository.offresParSecteur());
		return "index";
	}
	
	@RequestMapping(value="/recherche", method=RequestMethod.GET)
	public String rechercheOffre(Model model, @RequestParam(name="titre", defaultValue="") String titre,
			@RequestParam(name="region", defaultValue="") Region region,
			@RequestParam(name="metier", defaultValue="") Metiers metier,
			@RequestParam(name="page", defaultValue="0") int p,
			@RequestParam(name="size", defaultValue="8") int s) {		
		List<Region> regions = regionRepository.findAll();
		List<Metiers> metiers = metierRepository.findAll();
		Page<Offres> moteurRecherche = offreRepository.moteurRecherche("%"+titre+"%", region, metier, PageRequest.of(p,s));
		model.addAttribute("moteurRecherche", moteurRecherche.getContent());
		int[] pages = new int[moteurRecherche.getTotalPages()];
		model.addAttribute("recherche", pages);
		model.addAttribute("regions", regions);
		model.addAttribute("metiers", metiers);
		model.addAttribute("nombres", offreRepository.nombreOffres());
		return "rechercheOffres";
	}
		
	@RequestMapping(value="/formRecruteur", method=RequestMethod.GET)
	public String form(Model model) {
		model.addAttribute("regions", regionRepository.findAll());
		List<Secteurs> secteurs = (List<Secteurs>) secteurRepository.findAll();
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("recruteur", new Recruteur());
		return "recruteur";
	}
	
	@RequestMapping(value="/SaveRecruteur", method=RequestMethod.POST)
	public String Save(@Valid Recruteur r, BindingResult bindingResult, @RequestParam(name="picture") MultipartFile file) throws Exception {
		if(bindingResult.hasErrors()) {
			return "recruteur";
		}
		
		if(!(file.isEmpty())) {
			r.setLogo(file.getOriginalFilename());
		}
		
		recruteurRepository.save(r);
		
		if(!(file.isEmpty())) {
			r.setLogo(file.getOriginalFilename());
			file.transferTo(new File(logoDir+r.getId()));
		}
		
		return "redirect:/entreprises";
	}
	
	@RequestMapping(value="/getLogo", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getLogo(Long id) throws Exception {
		File f = new File(logoDir+id);
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	@RequestMapping(value="/entreprises", method=RequestMethod.GET)
	public String listeEntreprises(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Recruteur> recs = recruteurRepository.chercherRecruteurs("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeRecruteurs", recs.getContent());
		int[] pages = new int[recs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "entreprises";
	}
	
	@RequestMapping(value="/formEmploi", method=RequestMethod.GET)
	public String formOffre(Model model) {
		List<Recruteur> recruteurs = (List<Recruteur>) recruteurRepository.findAll();
		model.addAttribute("recruteurs", recruteurs);
		List<Metiers> metiers = (List<Metiers>) metierRepository.findAll();
		model.addAttribute("metiers", metiers);
		List<Region> regions = (List<Region>) regionRepository.findAll();
		model.addAttribute("regions", regions);
		List<Langue> langues = (List<Langue>) langueRepository.findAll();
		model.addAttribute("langues", langues);
		List<NiveauLangue> niveaux = (List<NiveauLangue>) niveauRepository.findAll();
		model.addAttribute("niveaux", niveaux);
		model.addAttribute("offres", new Offres());
		return "emploi";
	}
	
	@RequestMapping(value="/SaveEmploi", method=RequestMethod.POST)
	public String Save(@Valid Offres o, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "emploi";
		}
				
		offreRepository.save(o);
				
		return "redirect:/listeEmplois";
	}
	
	@RequestMapping(value="/getPhoto", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		File f = new File(photoDir+id);
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	@RequestMapping(value="/candidats", method=RequestMethod.GET)
	public String listeCandidats(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		List<Candidat> candidats = candidatRepository.findAll();
		Page<Candidat> cands = candidatRepository.chercherCandidats("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeCandidats", cands.getContent());
		int[] pages = new int[cands.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		model.addAttribute("candidats", candidats);
		return "candidats";
	}
	
	@RequestMapping(value="/listeEmplois", method=RequestMethod.GET)
	public String listeOffres(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Offres> emps = offreRepository.rechercheParTitre("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeEmplois", emps.getContent());
		int[] pages = new int[emps.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		model.addAttribute("nombres", offreRepository.nombreOffres());
		return "listeEmploi";
	}
		
	@RequestMapping(value="/detailPoste", method=RequestMethod.GET)
	public String detail(Long idOffre, Model model) {
		Offres o = offreRepository.getOne(idOffre);
		model.addAttribute("offre", o);
		return "detailOffre";
	}
	
	@RequestMapping(value="/profil", method=RequestMethod.GET)
	public String profil(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		Formation f = formationRepository.findByUtilisateur(u);
		Experience e = experienceRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		model.addAttribute("formation", f);
		model.addAttribute("experience", e);
		return "profil";
	}
	
	@RequestMapping(value="/complement", method=RequestMethod.GET)
	public String complement(Principal principal, Model model) {
		List<Langue> langues = langueRepository.findAll();
		List<NiveauLangue> niveaux = (List<NiveauLangue>) niveauRepository.findAll();
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		model.addAttribute("langues", langues);
		model.addAttribute("niveaux", niveaux);
		model.addAttribute("candidat", new Candidat());
		return "complement";
	}
	
	@RequestMapping(value="/saveComplement", method=RequestMethod.POST)
	public String saveCandidat(@Valid Candidat c, Principal principal, BindingResult bindingResult, @RequestParam(name="file") MultipartFile file, @RequestParam(name="picture") MultipartFile files) throws Exception {
		if(bindingResult.hasErrors()) {
			return "complement";
		}
		
		if(!(file.isEmpty())) {
			c.setCv(file.getOriginalFilename());
		}
		
		if(!(files.isEmpty())) {
			c.setPhoto(files.getOriginalFilename());
		}
		
		Utilisateur u = new Utilisateur();
		u.setUsername(principal.getName());
		c.setUtilisateur(u);
		candidatRepository.save(c);
		
		if(!(file.isEmpty())) {
			c.setCv(file.getOriginalFilename());
			file.transferTo(new File(cvDir+c.getId()));
		}
		
		if(!(files.isEmpty())) {
			c.setPhoto(files.getOriginalFilename());
			files.transferTo(new File(photoDir+c.getId()));
		}
		
		return "redirect:/formation";
	}
	
	@RequestMapping(value="/critere", method=RequestMethod.GET)
	public String criteres(Principal principal, Model model) {
		List<Region> regions = (List<Region>) regionRepository.findAll();
		List<Metiers> metiers = (List<Metiers>) metierRepository.findAll();
		List<Secteurs> secteurs = (List<Secteurs>) secteurRepository.findAll();
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		model.addAttribute("regions", regions);
		model.addAttribute("metiers", metiers);
		model.addAttribute("secteurs", secteurs);
		model.addAttribute("critere", new Critere());
		return "critere";
	}
	
	@RequestMapping(value="/saveCritere", method=RequestMethod.POST)
	public String saveCritere(@Valid Critere c, Principal principal, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "critere";
		}
		
		Utilisateur u = new Utilisateur();
		u.setUsername(principal.getName());
		c.setUtilisateur(u);
		critereRepository.save(c);
		
		return "redirect:/profil";
	}
	
	@RequestMapping(value="/formation", method=RequestMethod.GET)
	public String competences(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		model.addAttribute("formation", new Formation());
		return "formation";
	}
	
	@RequestMapping(value="/saveFormation", method=RequestMethod.POST)
	public String saveFormation(@Valid Formation f, Principal principal, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "formation";
		}
		
		Utilisateur u = new Utilisateur();
		u.setUsername(principal.getName());
		f.setUtilisateur(u);
		formationRepository.save(f);
		
		return "redirect:/experience";
	}
	
	@RequestMapping(value="/experience", method=RequestMethod.GET)
	public String experience(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		model.addAttribute("experience", new Experience());
		return "experience";
	}
	
	@RequestMapping(value="/saveExperience", method=RequestMethod.POST)
	public String saveExperience(@Valid Experience e, Principal principal, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "experience";
		}
		
		Utilisateur u = new Utilisateur();
		u.setUsername(principal.getName());
		e.setUtilisateur(u);
		experienceRepository.save(e);
		
		return "redirect:/critere";
	}
		
	@RequestMapping(value="/suivi", method=RequestMethod.GET)
	public String suivi(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		return "suivi";
	}
	
	@RequestMapping(value="/alerte", method=RequestMethod.GET)
	public String alerte(Principal principal, Model model) {
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		model.addAttribute("candidat", c);
		return "alerte";
	}
	
	@RequestMapping(value="/listeCategorie", method=RequestMethod.GET)
	public String listeCategorie(Model model, @RequestParam(name="motCle", defaultValue="") String mc, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="size", defaultValue="8") int s) {
		Page<Secteurs> secs = secteurRepository.recherche("%"+mc+"%", PageRequest.of(p,s));
		model.addAttribute("listeCategorie", secs.getContent());
		int[] pages = new int[secs.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		model.addAttribute("nbrOffreParSecteur", offreRepository.offresParSecteur());
		return "listeCategorie";
	}
	
	@RequestMapping(value="/detailSecteur", method=RequestMethod.GET)
	public String detailSecteur(Long idSecteur, Model model) {
		Secteurs s = secteurRepository.getOne(idSecteur);
		model.addAttribute("secteur", s);
		return "detailSecteur";
	}
	
	@RequestMapping(value="/postuler", method=RequestMethod.GET)
	public String postuler(Long idOffre, Principal principal, Model model) {
		Offres o = offreRepository.getOne(idOffre);
		String username = principal.getName();
		Utilisateur u = utilisateurRepository.findByUsername(username);
		Candidat c = candidatRepository.findByUtilisateur(u);
		Formation f = formationRepository.findByUtilisateur(u);
		Experience e = experienceRepository.findByUtilisateur(u);
		model.addAttribute("offre", o);
		model.addAttribute("candidat", c);
		model.addAttribute("formation", f);
		model.addAttribute("experience", e);
		return "postuler";
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Destuction du contrôleur des emplois");
	}
}
