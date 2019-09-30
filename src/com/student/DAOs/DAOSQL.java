package com.student.DAOs;

import java.sql.*;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.*;
import com.student.Models.*;


@ManagedBean
@ApplicationScoped
public class DAOSQL {
	private DataSource mysqlDS;
	private Connection conn; 
	
	public DAOSQL() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/studentdb";
		mysqlDS = (DataSource) context.lookup(jndiName);
	}
	//********************LOAD COURSES********************
	public ArrayList<Course> loadCourses() throws Exception {
		 ArrayList<Course> courses = new ArrayList<Course>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select * from course");
		 
		 while (rs.next()) {
			 Course course = new Course();
			 course.setCid(rs.getString("cID"));
			 course.setcName(rs.getString("cName"));
			 course.setDuration(rs.getInt("duration"));
			 courses.add(course);
		 }
		 conn.close();
		 return courses;
	}
	
	//********************ADD COURSE********************
	public void addCourse(Course c) throws Exception{
		System.out.print("Adding Course: '" + c.getCid() + "', '" + c.getcName() + "', '" + c.getDuration()+"' ");
		
		conn = mysqlDS.getConnection();
		Statement stmt = conn.createStatement();
		
		String query = "insert into course (cID, cName, duration) values ('" + c.getCid() + "', '" + c.getcName() + "', '" + c.getDuration() + "');";
		stmt.executeUpdate(query);
		
		ArrayList<Course> courses = new ArrayList<Course>();
		System.out.println("Size: " + courses.size());
		conn.close();
	}
	
	//********************DELETE COURSE********************
	public void deleteCourse(Course c) throws SQLException {
		System.out.print("Deleting Course: " + c.getCid() + "', '" + c.getcName() + "', '" + c.getDuration());
		
		conn = mysqlDS.getConnection();
		
		Statement stmt = conn.createStatement();
		String query = "delete from course where cid in('" + c.getCid() +"');";
		
		stmt.executeUpdate(query);
		
		ArrayList<Course> courses = new ArrayList<Course>();
		System.out.println("Size: " + courses.size());
		conn.close();
	}
	
	//********************LOAD COURSES STUDENTS********************
	public ArrayList<CoursesStudents> loadCourseStudentDetails(Course c) throws Exception {
		System.out.print("Courses Student Details: '" + c.getCid() + "', '" + c.getcName() + "', '" + c.getDuration());

		 ArrayList<CoursesStudents> courseStudent = new ArrayList<CoursesStudents>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select c.cid, c.cname, c.duration, s.name, s.address from course c join student s on c.cid = s.cid and c.cid='"+ c.getCid() + "';");
		 
		 while (rs.next()) {
			 CoursesStudents courseStudents = new CoursesStudents();
			 courseStudents.setCid(rs.getString("cID"));
			 courseStudents.setcName(rs.getString("cName"));
			 courseStudents.setDuration(rs.getInt("duration"));
			 courseStudents.setsName(rs.getString("name"));
			 courseStudents.setAddress(rs.getString("address"));
			 courseStudent.add(courseStudents);
		 }
		 System.out.print(courseStudent);
		 conn.close();
		 return courseStudent;
	}
	
	//********************LOAD STUDENTS********************
	public ArrayList<Student> loadStudents() throws Exception {
		 ArrayList<Student> students = new ArrayList<Student>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select * from student");
		 
		 while (rs.next()) {
			 Student student = new Student();
			 student.setSid(rs.getString("sid"));
			 student.setCid(rs.getString("cID"));
			 student.setName(rs.getString("name"));
			 student.setAddress(rs.getString("address"));
			 students.add(student);
		 }
		 conn.close();
		 return students;
	}
	
	//********************ADD STUDENT********************
	public void addStudent(Student s) throws Exception{
		System.out.print("Adding Student: " + s.getSid() + ", " + s.getCid() + ", " + s.getName() + ", " + s.getAddress() );
		
		conn = mysqlDS.getConnection();
		Statement stmt = conn.createStatement();
		
		String query = "insert into student (sid ,cID, name, address) values ('" + s.getSid() + "', '" + s.getCid() + "', '" + s.getName() + "', '" + s.getAddress() + "');";
		stmt.executeUpdate(query);
		
		ArrayList<Student> students = new ArrayList<Student>();
		System.out.println("Size: " + students.size());
		conn.close();
	}
	
	//********************DELETE STUDENT********************
	public void deleteStudent(Student s) throws SQLException {
		System.out.print("Deleting Student: " + s.getSid() + ", " + s.getCid() + ", " + s.getName() + ", " + s.getAddress() );
		
		conn = mysqlDS.getConnection();
		
		Statement stmt = conn.createStatement();
		String query = "delete from student where sid in('" + s.getSid() +"');";
		
		stmt.executeUpdate(query);
		
		ArrayList<Student> students = new ArrayList<Student>();
		System.out.println("Size: " + students.size());
		conn.close();
	}
	
	//********************LOAD FULL STUDENT DETAILS********************
	public ArrayList<CoursesStudents> loadFullStudentDetails(Student s) throws Exception {
		System.out.print("Full Student Details: " + s.getSid() + ", " + s.getCid() + ", " + s.getName() + ", " + s.getAddress() );

		 ArrayList<CoursesStudents> fullStudent = new ArrayList<CoursesStudents>();
		 conn = mysqlDS.getConnection();
		 
		 Statement stmt = conn.createStatement();		
		 ResultSet rs = stmt.executeQuery("select s.sid, s.name, s.cid, c.cname, c.duration from student s join course c on s.cid = c.cid and s.sid='"+ s.getSid()+ "';");
		 
		 while (rs.next()) {
			 CoursesStudents fullStudents = new CoursesStudents();
			 fullStudents.setSid(rs.getString("sid"));
			 fullStudents.setsName(rs.getString("name"));
			 fullStudents.setCid(rs.getString("cID"));
			 fullStudents.setcName(rs.getString("cName"));
			 fullStudents.setDuration(rs.getInt("duration"));
			 fullStudent.add(fullStudents);
		 }
		 conn.close();
		 return fullStudent;
	}
	

}
