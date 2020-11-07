package com.cg.multithreading;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class EmployeePayrollServiceTest {
    @Test
    public void given6Employees_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        EmployeePayroll[] arrayOfEmps = {
                new EmployeePayroll(   "Jeff Bezos",  'M',  100000.0, LocalDate.now()),
        new EmployeePayroll(  "Bill Gates",  'M',  200000.0, LocalDate.now()),
        new EmployeePayroll(  "Mark Zuckerberg",  'M',  300000.0, LocalDate.now()),
        new EmployeePayroll( "Sunder",  'M',  600000.0, LocalDate.now()),
        new EmployeePayroll( "Mukesh",  'M', 1000000.0, LocalDate.now()),
        new EmployeePayroll(  "Anil",  'M',  200000.0, LocalDate.now() )
        };
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Instant start = Instant.now();
        employeePayrollService. addEmployeesToPayroll(Arrays.asList(arrayOfEmps) );
        Instant end = Instant.now();
        System.out.println("Duration without Thread: "+ Duration.between(start, end));
        Instant threadStart = Instant.now();
        employeePayrollService. addEmployeesToPayrollWithThread(Arrays.asList(arrayOfEmps) );
        Instant threadEnd = Instant.now();
        System.out.println("Duration with Thread: "+Duration.between(threadStart, threadEnd) );
        Assert.assertEquals(  13, employeePayrollService.readFromDB());

    }

    @Test
    public void updateSalaryOfMultipleEmployeesAndSyncWithDB() {
        EmployeePayroll[] arrayOfEmps = {
                new EmployeePayroll(   "Jeff Bezos",  'M',  100000.0, LocalDate.now()),
                new EmployeePayroll(  "Bill Gates",  'M',  200000.0, LocalDate.now()),
                new EmployeePayroll(  "Mark Zuckerberg",  'M',  300000.0, LocalDate.now()),
                new EmployeePayroll( "Sunder",  'M',  600000.0, LocalDate.now()),
                new EmployeePayroll( "Mukesh",  'M', 1000000.0, LocalDate.now()),
                new EmployeePayroll(  "Anil",  'M',  200000.0, LocalDate.now() )
        };
        TreeMap<String, Double> updateSalaryList=new TreeMap<>();
        updateSalaryList.put("Bill Gates",230000.0);
        updateSalaryList.put("Sundar",430000.0);
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.updateMultipleSalaries(updateSalaryList);
       boolean check= employeePayrollService.checkSalarySyncWithDB(updateSalaryList);
       Assert.assertTrue(check);

    }


}