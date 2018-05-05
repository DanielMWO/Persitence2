package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

//Controller
public class StudentsController {

    @RequestMapping(value="/Students")
    public String listSchoolClass(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	
        return "studentsList";    
    }
    
    @RequestMapping(value="/AddStudent")
    public String displayAddSchoolClassForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
       	
        return "studentForm";    
    }

    @RequestMapping(value="/CreateStudent", method=RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value="studentName", required=false) String name,
    		@RequestParam(value="studentSurname", required=false) String surname,
    		@RequestParam(value="studentPesel", required=false) String pesel,
    		@RequestParam(value="studentSchoolClass", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Student student = new Student();
    	schoolClass.setStartYear(Integer.valueOf(startYear));
    	schoolClass.setCurrentYear(Integer.valueOf(currentYear));
    	schoolClass.setProfile(profile);
    	
    	DatabaseConnector.getInstance().addSchoolClass(schoolClass, schoolId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Nowa klasa została dodana");
         	
    	return "studentsList";
    }
    
    @RequestMapping(value="/DeleteStudent", method=RequestMethod.POST)
    public String deleteSchoolClass(@RequestParam(value="schoolClassId", required=false) String schoolClassId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);    	
       	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
    	model.addAttribute("message", "Klasa została usunięta");
         	
    	return "studentsList";
    }


}