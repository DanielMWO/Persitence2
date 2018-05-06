package pl.edu.agh.ki.mwo.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class SchoolClassesController {

    @RequestMapping(value="/SchoolClasses")
    public String listSchoolClass(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());

    	Iterable<School> schoolList = DatabaseConnector.getInstance().getSchools();
    	Iterable<SchoolClass> classList = DatabaseConnector.getInstance().getSchoolClasses();
    	List<String> schoolNames =  new ArrayList<>();
    	HashMap<SchoolClass, School> classesAndSchools = new HashMap<>();
    	
    	for (SchoolClass schollClass : classList) {
    		for (School school : schoolList ) {
    			if (school.getClasses().contains(schollClass)) {
    				classesAndSchools.put(schollClass, school);
    				System.out.println("Klasa: " +  schollClass + " Szkoła: " + school.getName());
    			}
    		}
    	}
    	
    	model.addAttribute("classesAndSchools", classesAndSchools);
    	
        return "schoolClassesList";    
    }
    
    @RequestMapping(value="/AddSchoolClass")
    public String displayAddSchoolClassForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
       	
        return "schoolClassForm";    
    }

    @RequestMapping(value="/CreateSchoolClass", method=RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value="schoolClassStartYear", required=false) String startYear,
    		@RequestParam(value="schoolClassCurrentYear", required=false) String currentYear,
    		@RequestParam(value="schoolClassProfile", required=false) String profile,
    		@RequestParam(value="schoolClassSchool", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	SchoolClass schoolClass = new SchoolClass();
    	schoolClass.setStartYear(Integer.valueOf(startYear));
    	schoolClass.setCurrentYear(Integer.valueOf(currentYear));
    	schoolClass.setProfile(profile);
    	
    	DatabaseConnector.getInstance().addSchoolClass(schoolClass, schoolId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Nowa klasa została dodana");
         	
    	return "schoolClassesList";
    }
    
    @RequestMapping(value="/SchoolClassUpdate", method=RequestMethod.POST)
    public String showSchollClasstoUpdate(
    		@RequestParam(value="schoolClassId", required=true) String schoolClassId, Model model, HttpSession session
    		) {	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("schoolClassToUpdate", DatabaseConnector.getInstance().getSchoolClassToUpdate(schoolClassId));
    	SchoolClass tmpSC = DatabaseConnector.getInstance().getSchoolClassToUpdate(schoolClassId);
    	System.out.println(tmpSC);
    	return "schoolClassUpdate";
    }
    
    
    @RequestMapping(value="/SchoolClassUpdated", method=RequestMethod.POST)
    public String updateSchollClass(
    		@RequestParam(value="schoolClassId", required=true) String schoolClassId,
    		@RequestParam(value="schoolClassCurrentYear", required=true) String schoolClassCurentYear,
    		@RequestParam(value="schoolClassStartYear", required=true) String schoolClassStartYear,
    		@RequestParam(value="schoolClassProfile", required=true) String schoolClassProfile,
    		Model model, HttpSession session) {
    		
    		if (session.getAttribute("userLogin") == null)
    			return "redirect:/Login";
    	
    		SchoolClass schoolClass =  DatabaseConnector.getInstance().getSchoolClassToUpdate(schoolClassId);
    		schoolClass.setCurrentYear(Integer.valueOf(schoolClassCurentYear));
    		schoolClass.setStartYear(Integer.valueOf(schoolClassStartYear));
    		schoolClass.setProfile(schoolClassProfile);
    		
    		DatabaseConnector.getInstance().updateSchoolClass(schoolClass);
    		
    		
    		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
        	model.addAttribute("message", "Klasa Została zmieniona");
    		
    		
    		return "schoolClassesList";
    }
    
    
    
    @RequestMapping(value="/DeleteSchoolClass", method=RequestMethod.POST)
    public String deleteSchoolClass(@RequestParam(value="schoolClassId", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Klasa została usunięta");
         	
    	return "schoolClassesList";
    }


}