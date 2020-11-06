package com.cg.multithreading;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class EmployeePayrollService {

    public void addEmployeesToPayroll(List<EmployeePayroll> employeePayrollDataList) {
        employeePayrollDataList.forEach(employeePayrollData -> {
            addEmployeeData(employeePayrollData.getEmpID(), employeePayrollData.getName(), employeePayrollData.getGender(),
                    employeePayrollData.getDate(), employeePayrollData.getSalary());
        });

    }


    public void addEmployeesToPayrollWithThread(List<EmployeePayroll> employeePayrollDataList) {
        TreeMap<String, Boolean> employeeADDStatus=new TreeMap<>();
        employeePayrollDataList.forEach(employeePayrollData -> {
            Runnable task=()->{
                employeeADDStatus.put(employeePayrollData.getName(),false);
            addEmployeeData(employeePayrollData.getEmpID(), employeePayrollData.getName(), employeePayrollData.getGender(),
                    employeePayrollData.getDate(), employeePayrollData.getSalary());
                employeeADDStatus.put(employeePayrollData.getName(),true);
            };
            Thread thread=new Thread(task,employeePayrollData.getName());
                    thread.start();
        });

        while(employeeADDStatus.containsValue(false)){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void addEmployeeData(int id, String name, Character gender, LocalDate date, double salary) {

        String query1 = String.format("insert into employee_service (id,name,gender,salary,start) " +
                "values (%s,'%s','%s','%s','%s');", id, name, gender, salary, Date.valueOf(date));
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnecton();
        try {
            Statement statement = connection.createStatement();
            int rowaffected = statement.executeUpdate(query1);
            if ((rowaffected == 1)) System.out.println(name + " added");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public int readFromDB() {
        String query="select*from employee_service;";
        int count=0;
        DatabaseConnection databaseConnection=new DatabaseConnection();
        Connection connection=databaseConnection.getConnecton();
        try {
          Statement  statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
           while (resultSet.next()){count++;}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  count;
    }


}
