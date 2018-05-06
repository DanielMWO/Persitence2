package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;

public class DatabaseConnector {
	
	protected static DatabaseConnector instance = null;
	
	public static DatabaseConnector getInstance() {
		if (instance == null) {
			instance = new DatabaseConnector();
		}
		return instance;
	}
	
	Session session;

	protected DatabaseConnector() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public void teardown() {
		session.close();
		HibernateUtil.shutdown();
		instance = null;
	}
	
	public Iterable<School> getSchools() {
		String hql = "FROM School";
		Query query = session.createQuery(hql);
		List schools = query.list();
		
		return schools;
	}
	
	public void addSchool(School school) {
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
	}
	
	public void deleteSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (School s : results) {
			session.delete(s);
		}
		transaction.commit();
	}

	public Iterable<SchoolClass> getSchoolClasses() {
		String hql = "FROM SchoolClass";
		Query query = session.createQuery(hql);
		List schoolClasses = query.list();
		
		return schoolClasses;
	}
	
	public void addSchoolClass(SchoolClass schoolClass, String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(schoolClass);
		} else {
			School school = results.get(0);
			school.addClass(schoolClass);
			session.save(school);
		}
		transaction.commit();
	}
	
	public void deleteSchoolClass(String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (SchoolClass s : results) {
			session.delete(s);
		}
		transaction.commit();
	}

	public Iterable<Student> getStudents() {
		String hql = "FROM Student";
		Query query = session.createQuery(hql);
		List students = query.list();
		return students;
	}

	public void addStudent(Student student, String schoolClassId) {
		String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list();
		Transaction transaction = session.beginTransaction();
		if (results.size() == 0) {
			session.save(student);
		} else {
			SchoolClass schoolClass = results.get(0);
			schoolClass.addStudent(student);
			session.save(schoolClass);
		}
		transaction.commit();
	
		
	}

	public void deleteStudent(String studentId) {
		String hql = "FROM Student S WHERE S.id=" + studentId;
		Query query = session.createQuery(hql);
		List<Student> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (Student s : results) {
			session.delete(s);
		}
		transaction.commit();
	}

	public Iterable<School> getSchool(String schoolId) {
		String hql = "FROM School S WHERE S.id=" + schoolId;
		Query query = session.createQuery(hql);
		List<School> schools = query.list();
		return schools;
	}

	public void updateSchool(School school) {
		String hql ="From School s Where s.id=" +school.getId();
		Query query = session.createQuery(hql);
		List<School> results = query.list(); 
		Transaction transaction = session.beginTransaction();
		School tmpSchool = results.get(0);
		tmpSchool.setAddress(school.getAddress());
		tmpSchool.setName(school.getName());
		transaction.commit();
		
	}

	public School getSchooltoUpdate(String schoolId) {
			List<School> tmpSchools = (List<School>) getSchool(schoolId);
			School school = tmpSchools.get(0);
		return school;
	}

	public SchoolClass getSchoolClassToUpdate(String schoolClassId) {
		String hql = "FROM SchoolClass s WHERE s.id=" +schoolClassId;
		Query query =session.createQuery(hql);
		SchoolClass schollClass = (SchoolClass) query.list().get(0);
		return schollClass;
	}

	public void updateSchoolClass(SchoolClass schoolClass) {
		String hql ="From SchoolClass s Where s.id=" +schoolClass.getId();
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list(); 
		Transaction transaction = session.beginTransaction();
		SchoolClass tmpSchoolClass = results.get(0);
		tmpSchoolClass.setCurrentYear(schoolClass.getCurrentYear());
		tmpSchoolClass.setStartYear(schoolClass.getStartYear());
		tmpSchoolClass.setProfile(schoolClass.getProfile());
		transaction.commit();
		
	}

	public Student getStudnetToUpdate(String studentId) {
		String hql = "FROM Student s WHERE s.id=" +studentId;
		Query query =session.createQuery(hql);
		Student student = (Student) query.list().get(0);
		// TODO Auto-generated method stub
		return student;
	}

	public void updateStudent(Student student) {
		String hql = "FROM Student s WHERE s.id=" +student.getId();
		Query query =session.createQuery(hql);
		List<Student> results =query.list();
		Transaction transaction = session.beginTransaction();
		Student tmpStudent = results.get(0);
		tmpStudent.setName(student.getName());
		tmpStudent.setSurname(student.getSurname());
		tmpStudent.setPesel(student.getPesel());
		transaction.commit();
	}
	
	
	
	
}
