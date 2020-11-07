package com.cg.multithreading;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeePayroll {
    private int empID;
    private  String name;
    private char gender;
    private LocalDate date;
    private double salary;




    public EmployeePayroll( String name,char gender,  double salary,LocalDate date) {
        this.empID = 0;
        this.name = name;
        this.gender=gender;

        this.date = date;
        this.salary = salary;
    }

    public EmployeePayroll(int empID, String name, char gender, LocalDate date, double salary) {
        this.empID = empID;
        this.name = name;
        this.gender = gender;
        this.date = date;
        this.salary = salary;
    }


    public int getEmpID() {
        return empID;
    }

    public String getName() {
        return name;
    }


    public LocalDate getDate() {
        return date;
    }

    public double getSalary() {
        return salary;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "EmployeePayroll{" +
                "empID=" + empID +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", date=" + date +
                ", salary=" + salary +
                '}';
    }
}

