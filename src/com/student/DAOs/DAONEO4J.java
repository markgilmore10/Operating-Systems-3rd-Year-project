package com.student.DAOs;

import com.student.Models.Student;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class DAONEO4J {
	 private static final String user = "admin1";
	 private static final String pass = "admin1";

	 private DAONEO4J(){}

	//********************ADD STUDENT********************
	 public static void addStudent(Student student){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic(user, pass));
        Session session = driver.session();

        session.writeTransaction(tx ->
            tx.run("CREATE(:STUDENT{name: $name, address: $address})", parameters("name", student.getName(), "address", student.getAddress()))
        );

        session.close();
        driver.close();
	 }

	//********************DELETE STUDENT********************
	 public static void deleteStudent(Student student){
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic(user, pass));
        Session session = driver.session();

        session.run("MATCH(s:STUDENT{name: $name}) delete s;", parameters("name",student.getName()) );

        session.close();
        driver.close();
    }
	 
} // DAONEO4J