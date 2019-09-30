package com.student.Controllers;

import com.student.DAOs.DAONEO4J;
import com.student.DAOs.DAOSQL;
import com.student.Models.*;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.sql.SQLException;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;


@ManagedBean
@SessionScoped

public class StudentController {
	private ArrayList<Student> students;
	private ArrayList<CoursesStudents> fullStudent;
	private DAOSQL dao;

	public StudentController() throws Exception {
		super();
		dao = new DAOSQL();
		students = new ArrayList<>();
	}
	
	//********************LOAD STUDENTS********************
	public void loadStudents() {
		try {
			students = dao.loadStudents();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		System.out.println("Students size = " + students.size());
	} // loadStudents
	
	public ArrayList<Student> getStudents() {
		return students;
	} // getStudents
	
	//********************ADD STUDENT********************
	public String addStudent(Student s) {
		try {
			dao.addStudent(s);
		} catch (CommunicationsException e) {
			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Database Offline");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Duplicate Entry '" + s.getSid() + "' for key 'PRIMARY'");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: Unknown Exception");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Unknown Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		 try{
	            DAONEO4J.addStudent(s);  
	        } catch (ServiceUnavailableException e){
	            FacesMessage message =
	            new FacesMessage("Warning: Student "+ s.getName() +" has not been added to Neo4j DB, as it offline." );
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }
		 return "index.xhtml";
	} // addStudent
	
	//********************DELETE STUDENT********************
	public String deleteStudent(Student s){	
		try {
			dao.deleteStudent(s);
		} catch (SQLException e) {
			FacesMessage message = 	new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		 try{
	            DAONEO4J.deleteStudent(s);   // <-- delete student from neo4j DB
	        } catch (ClientException e){
	            FacesMessage message =
	            new FacesMessage("Error: Student: "+ s.getName() +" has not ben deleted from DB as he/she has relationships in Neo4j DB");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        } catch (ServiceUnavailableException e){
	            FacesMessage message =
	            new FacesMessage("Warning: Student "+ s.getName() +" has not been deleted from Neo4j DB, as it offline." );
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }
		return "list_students.xhtml";
	} // deleteStudent
	
	//********************LOAD FULL STUDENT DETAILS********************
	public ArrayList<CoursesStudents> getFullStudent() {
		return fullStudent;
	} // getFullStudent
	
	public String loadFullStudentDetails(Student s) {
		try {
			fullStudent = dao.loadFullStudentDetails(s);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		return "student_details.xhtml";
	} // loadFullStudentDetails
	
}

