package com.cg.multithreading;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollService {
    public void addEmployeesToPayroll(List<EmployeePayroll> employeePayrollDataList) {
        employeePayrollDataList.forEach(employeePayrollData -> {
            addEmployeeData(employeePayrollData.getEmpID(), employeePayrollData.getName(), employeePayrollData.getGender(),
                    employeePayrollData.getDate(), employeePayrollData.getSalary());
        });

    }

    public void addEmployeeData(int id, String name, Character gender, LocalDate date, double salary) {

        String query1 = String.format("insert into employee_service (id,name,gender,salary,start) " +
                "values (%s,'%s','%s','%s','%s');", id, name, gender, salary, Date.valueOf(date));
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnecton();
        try {
            Statement statement = connection.createStatement();
            int rowaffected = statement.executeUpdate(query1);
            if ((rowaffected == 1)) System.out.println(name + "added");

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
//    public void addEmployeesToPayrollwithThreads(List<EmployeePayrollData> employeePayrollDataList) {
//        Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
//        emp loyeePayrollDataList.forEach(employeePayrollData —> {
//                Runnable task = () -> {
//. emp LoyeeAdditionStatus.put(employeePayrollData.hashCode(), false); OC
//            System. out.println("Employee Being Added: "+Thread.currentThread().getName());
//            this. addEmp loyeeToPayroll(employeePayrollData.name, employeePayrollData.salary,
//                    emp LoyeePayrollData.startDate, employeePayrollData.gender) ;
//            emp LoyeeAdditionStatus.put(employeePayrollData.hashCode(), true);
//            System.out.println("Employee Added: '+Thread.currentThread().getName());
//        };
//        Thread thread = new Thread(task, employeePayrollData. name) ;
//        thread.start();
//        Hi; [1
//        while (employeeAdditionStatus.containsValue(false)) {
//            try { Thread.sleep( millis: 18);
//            } catch (InterruptedException e) { }
//        }
//        System.out.println(this.employeePayrollList) ;
//}
//}
