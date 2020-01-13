package com.etud.web;

import com.etud.entities.Etudiant;
import com.etud.repository.EtudiantRepository;

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

import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;


@Controller
@RequestMapping(value = "/Etudiant")
public class EtudiantController {
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Value("${dir.images}")
    private String imageDir;

    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/Index")
    public String Index(Model model,
           @RequestParam(name = "page", defaultValue = "0") int p,
           @RequestParam(name = "motCle", defaultValue = "") String mc) {
        Page<Etudiant> pageEtudiants = etudiantRepository
                .chercherEtudiants("%"+mc+"%", new PageRequest(p, 5));
        int pagesCount = pageEtudiants.getTotalPages();
        int[] pages = new int[pagesCount];
        for (int i=0; i<pagesCount; i++)
            pages[i]=i;
        model.addAttribute("pages", pages);
        model.addAttribute("pageEtudiants", pageEtudiants);
        model.addAttribute("pageCourante", p);
        model.addAttribute("motCle", mc);
        return "etudiants";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String formEtudiant(Model model) {
        model.addAttribute("etudiant", new Etudiant());
        return "FormEtudiant";
    }

    @RequestMapping(value = "/saveEtudiant", method = RequestMethod.POST)
    public String save(@Valid Etudiant et, BindingResult bindingResult,
                       @RequestParam(name = "picture") MultipartFile file) throws Exception {
        if (bindingResult.hasErrors()) {
            return "FormEtudiant";
        }
        if (!(file.isEmpty())) {
            et.setPhoto(file.getOriginalFilename()); }
            etudiantRepository.save(et);
            if(!(file.isEmpty())) {
            	et.setPhoto(file.getOriginalFilename());
            	file.transferTo(new File(imageDir+et.getIdEtudiant()));
            
            }
        
        return "redirect:Index";
    }
    // methode pour recupérer une photo
	 @RequestMapping(value = "/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE) 
	@ResponseBody
    public byte[] getPhoto(Long id) throws Exception {
    	File f = new File(imageDir+id);
    	return IOUtils.toByteArray(new FileInputStream(f));
    }
}
