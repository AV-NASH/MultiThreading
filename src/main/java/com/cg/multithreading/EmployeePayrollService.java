package com.cg.multithreading;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

public class EmployeePayrollService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();



    public void addEmployeesToPayroll(List<EmployeePayroll> employeePayrollDataList) {
        employeePayrollDataList.forEach(employeePayrollData -> {
            addEmployeeDataWithPayroll(employeePayrollData.getName(), employeePayrollData.getGender(),
                    employeePayrollData.getDate(), employeePayrollData.getSalary());
        });

    }


    public void addEmployeesToPayrollWithThread(List<EmployeePayroll> employeePayrollDataList) {
        TreeMap<String, Boolean> employeeADDStatus = new TreeMap<>();
        employeePayrollDataList.forEach(employeePayrollData -> {
            Runnable task = () -> {
                employeeADDStatus.put(employeePayrollData.getName(), false);
                addEmployeeDataWithPayrollWithThread(employeePayrollData.getName(), employeePayrollData.getGender(),
                        employeePayrollData.getDate(), employeePayrollData.getSalary());
                employeeADDStatus.put(employeePayrollData.getName(), true);
            };
            Thread thread = new Thread(task, employeePayrollData.getName());
            thread.start();
        });

        while (employeeADDStatus.containsValue(false)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void addEmployeeData(String name, Character gender, LocalDate date, double salary) {

        String query1 = String.format("insert into employee_service (id,name,gender,salary,start) " +
                "values ('%s','%s','%s','%s');", name, gender, salary, Date.valueOf(date));

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
        String query = "select*from employee_service;";
        int count = 0;

        Connection connection = databaseConnection.getConnecton();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    public void addEmployeeDataWithPayroll(String name, Character gender, LocalDate date, double salary) {
        int rowaffected1 = 0, rowaffected2 = 0, id = 0;
        String query1 = String.format("insert into employee_service (name,gender,salary,start) " +
                "values ('%s','%s',%s,'%s');", name, gender,salary ,Date.valueOf(date));

       Connection  connection = databaseConnection.getConnecton();
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            rowaffected1 = statement.executeUpdate(query1, statement.RETURN_GENERATED_KEYS);
            connection.commit();
            if (rowaffected1 == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) id = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String query2 = String.format("insert into emp_pay_service  " +
                "values (%s,%s,%s,%s,%s,%s);", id, salary, (0.2 * salary), (salary - (0.2 * salary)), ((salary - (0.2 * salary)) * 0.1), (salary - ((salary - (0.2 * salary)) * 0.1)));
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            rowaffected2 = statement.executeUpdate(query2);
            connection.commit();
            if (rowaffected1 == 1 && rowaffected2 == 1) {
            } else System.out.println("data not added");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        public synchronized void addEmployeeDataWithPayrollWithThread(String name, Character gender, LocalDate date, double salary) {
            int[] rowaffected1 = new int[1];rowaffected1[0]=0;
            int[] rowaffected2 = new int[1];rowaffected2[0]=0;
                  int[]  id = new int[1];id[0]=0;


           Connection  connection = databaseConnection.getConnecton();
                 String query1 = String.format("insert into employee_service (name,gender,salary,start) " +
                         "values ('%s','%s',%s,'%s');", name, gender,salary ,Date.valueOf(date));
                 try {
                     connection.setAutoCommit(false);
                   Statement  statement=connection.createStatement();
                     rowaffected1[0] =statement.executeUpdate(query1,statement.RETURN_GENERATED_KEYS);
                     connection.commit();
                     if (rowaffected1[0] == 1)
                     {
                         ResultSet resultSet=statement.getGeneratedKeys();
                         if(resultSet.next()) id[0]=resultSet.getInt(1);
                     }
                 } catch (SQLException throwables) {
                     throwables.printStackTrace();
                     try {
                         connection.rollback();
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                 }

            String query2 = String.format("insert into emp_pay_service  " +
                    "values (%s,%s,%s,%s,%s,%s);", id[0], salary, (0.2 * salary), (salary - (0.2 * salary)), ((salary - (0.2 * salary)) * 0.1), (salary - ((salary - (0.2 * salary)) * 0.1)));
            try {
                connection.setAutoCommit(false);
              Statement  statement=connection.createStatement();
                rowaffected2[0] = statement.executeUpdate(query2);
                connection.commit();
                if (rowaffected1[0] == 1 && rowaffected2[0] == 1) {
                } else System.out.println("data not added");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

}