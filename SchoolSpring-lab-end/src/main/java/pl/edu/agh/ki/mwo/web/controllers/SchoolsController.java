package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class SchoolsController {

    @RequestMapping(value="/Schools")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	
        return "schoolsList";    
    }
    
    @RequestMapping(value="/AddSchool")
    public String displayAddSchoolForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "schoolForm";    
    }

   
    @RequestMapping(value="/UpdateSchool")
    public String ShowSchool (@RequestParam(value="schoolId", required=true) String schoolId, Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schoolId", DatabaseConnector.getInstance().getSchool(schoolId));
    	System.out.println("\n----------------\n This is schoolid:" +schoolId);
    	System.out.println("");
        return "schoolUpdate";    
    }
    
    
    @RequestMapping(value="/UpdatedSchool")
    public String Updayedschool (
    		@RequestParam(value="schoolId", required=true) String schoolId, 
    		@RequestParam(value="schoolAddress", required=true) String schoolAddress,
    		@RequestParam(value="schoolName", required=true) String schoolName,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	System.out.println("\nschooll id: "+ schoolId );
    	System.out.println("\nschooll name: "+ schoolName ); 
    	System.out.println("\nschooll Address: "+ schoolAddress ); 
    	
    	School school = DatabaseConnector.getInstance().getSchooltoUpdate(schoolId);
    	school.setAddress(schoolAddress);
    	school.setName(schoolName);
    	DatabaseConnector.getInstance().updateSchool(school);
    	  	
    	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	
    	return "schoolsList";    
    }
    
    
    
    @RequestMapping(value="/CreateSchool", method=RequestMethod.POST)
    public String createSchool(@RequestParam(value="schoolName", required=false) String name,
    		@RequestParam(value="schoolAddress", required=false) String address,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	School school = new School();
    	school.setName(name);
    	school.setAddress(address);
    	
    	DatabaseConnector.getInstance().addSchool(school);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Nowa szkoła została dodana");
         	
    	return "schoolsList";
    }
    
    @RequestMapping(value="/DeleteSchool", method=RequestMethod.POST)
    public String deleteSchool(@RequestParam(value="schoolId", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchool(schoolId);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Szkoła została usunięta");
         	
    	return "schoolsList";
    }


}