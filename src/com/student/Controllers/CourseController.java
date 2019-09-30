package com.student.Controllers;

import com.student.DAOs.DAOSQL;
import com.student.Models.*;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.sql.SQLException;

@ManagedBean
@ApplicationScoped
public class CourseController {
	private ArrayList<Course> courses;
	private ArrayList<CoursesStudents> courseStudents;
	private DAOSQL dao;

	public CourseController() throws Exception {
		super();
		dao = new DAOSQL();
		courses = new ArrayList<>();
	}

	//*********************LOAD COURSES********************
	public void loadCourses() {
		try {
			courses = dao.loadCourses();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		System.out.println("Course size = " + courses.size());
	} // loadCourses

	public ArrayList<Course> getCourses() {
		return courses;
	} // getCourses

	//********************ADD COURSE********************
	public String addCourse(Course c) {
		try {
			dao.addCourse(c);
			return "list_courses.xhtml";
		} catch (CommunicationsException e) {
			FacesMessage message = new FacesMessage("Error: Cannot connect to Database");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Database Offline");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			FacesMessage message = new FacesMessage("Error: Course ID " + c.getCid() + " already exists");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Course already exists");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: Unknown Exception");
			FacesContext.getCurrentInstance().addMessage(null, message);
			System.out.println("Unknown Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	} // addCourse
	
	//*********************DELETE COURSE********************
	public String deleteCourse(Course c){	
		try {
			dao.deleteCourse(c);
		} catch (SQLException e) {
			FacesMessage message = 	new FacesMessage("Error: Cannot Delete Course: " + c.getCid() + " as there are associated Students");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return "list_courses.xhtml";
	} // deleteCourse
	
	//********************LOAD COURSES STUDENTS********************
	public ArrayList<CoursesStudents> getCourseStudents() {
		return courseStudents;
	} // getCourseStudent
	
	public String loadCourseStudentDetails(Course c) {
		try {
			courseStudents = dao.loadCourseStudentDetails(c);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Error: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
		}
		return "course_details.xhtml";
	} // loadCourseStudentDetails
	
	
} // CourseController
