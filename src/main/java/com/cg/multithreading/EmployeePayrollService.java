package com.cg.multithreading;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EmployeePayrollService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    ArrayList<EmployeePayroll> employeePayrollArrayList = new ArrayList<EmployeePayroll>();
    int count=0;

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
    public void readDataFromDB() {
        ResultSet resultSet1 = null, resultSet2;


        Connection connection = databaseConnection.getConnecton();
        String query1 = "select*from employee service;";
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            resultSet1 = statement.executeQuery(query1);
            connection.commit();
            while (resultSet1.next()){
                int id = resultSet1.getInt(1);
                String nameEmp = resultSet1.getString(2);
                char gender = resultSet1.getString(3).charAt(0);
                LocalDate date = resultSet1.getDate(5).toLocalDate();
                double salary = resultSet1.getDouble(4);
                employeePayrollArrayList.add(new EmployeePayroll(id, nameEmp, gender, date, salary));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkSalarySyncWithDB(TreeMap<String, Double> updateList) {
        while(count<updateList.size()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<EmployeePayroll> employeePayrollArrayListMemory = new ArrayList<EmployeePayroll>(employeePayrollArrayList.stream().
                filter(p -> updateList.containsKey(p.getName())).collect(Collectors.toList()));
        ArrayList<EmployeePayroll> employeePayrollArrayListDB = new ArrayList<>();
        updateList.entrySet().stream().forEach(p->{checkSalaryRecordInDB(p.getKey()).stream().forEach(e->employeePayrollArrayListDB.add(e));
        });
        return employeePayrollArrayListMemory.toString().equals(employeePayrollArrayListDB.toString());
    }

    private ArrayList<EmployeePayroll> checkSalaryRecordInDB(String name) {
        ResultSet resultSet1 = null;
        ArrayList<EmployeePayroll> employeePayrollUpdatedList = new ArrayList<EmployeePayroll>();
        Connection connection = databaseConnection.getConnecton();
        String query1 = "select*from employee service where name='" + name + "';";

        try {
            connection.setAutoCommit(false);
           Statement statement = connection.createStatement();
            resultSet1 = statement.executeQuery(query1);
            connection.commit();
          while (resultSet1.next()){
              int id = resultSet1.getInt(1);
              String nameEmp = resultSet1.getString(2);
              char gender = resultSet1.getString(3).charAt(0);
              LocalDate date = resultSet1.getDate(5).toLocalDate();
              double salary = resultSet1.getDouble(4);
              employeePayrollUpdatedList.add(new EmployeePayroll(id, nameEmp, gender, date, salary));
          }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  employeePayrollUpdatedList;
    }


    public synchronized int updateEmployeeSalaryInDB(String name, double salary) {
        int result1 = 0,result2=0;
        String query1 = " update salary from employee_service where name='"+name+"';";

        Connection connection = databaseConnection.getConnecton();
        try {connection.setAutoCommit(false);
            Statement  statement = connection.createStatement();
            result1 = statement.executeUpdate(query1);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String query2 = " update emp_pay_service inner join employee_service on employee_details.emp_id=payroll_details.emp_id" +
                " set payroll_details.basic_pay=" + salary + ",payroll_details.deductions=" + (0.2 * salary) +
                ",payroll_details.taxable_pay=" + (salary - (0.2 * salary)) + ",payroll_details.income_tax=" + ((salary - (0.2 * salary)) * 0.1) +
                ",payroll_details.net_pay=" +(salary - ((salary - (0.2 * salary)) * 0.1)) +
                " where employee_details.name='" + name + "';";

        try {connection.setAutoCommit(false);
          Statement  statement = connection.createStatement();
            result2 = statement.executeUpdate(query2);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(result1==1&&result2==1) return 1;
      else return  0;
    }


    public void updateMultipleSalaries(TreeMap<String, Double> updateSalaryList) {
        readDataFromDB();
        int[] count=new int[1]; count[0]=0;
        updateSalaryList.entrySet().stream().forEach(p->{
            Runnable runnable=()->{
            updateEmployeeSalaryInDB(p.getKey(),p.getValue());
            count[0]++;
            };
            Thread thread=new Thread(runnable);
            thread.start();
        });
        updateSalaryList.entrySet().stream().forEach(p->{
            Runnable runnable=()->{
            updateSalaryInMemory(p.getKey(),p.getValue());
            count[0]++;
            };
            Thread thread=new Thread(runnable);
            thread.start();
        });
        this.count=count[0];

    }

    private synchronized void updateSalaryInMemory(String name, Double salary) {
        employeePayrollArrayList.stream()
                .filter(p -> p.getName().equals(name))
                .forEach(p -> p.setSalary(salary));
    }

}