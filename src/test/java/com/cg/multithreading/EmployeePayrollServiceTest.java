package com.cg.multithreading;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class EmployeePayrollServiceTest {
    @Test
    public void given6Employees_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        EmployeePayroll[] arrayOfEmps = {
                new EmployeePayroll(  2, "Jeff Bezos",  'M',  100000.0, LocalDate.now()),
        new EmployeePayroll( 3,  "Bill Gates",  'M',  200000.0, LocalDate.now()),
        new EmployeePayroll( 4, "Mark Zuckerberg",  'M',  300000.0, LocalDate.now()),
        new EmployeePayroll(5, "Sunder",  'M',  600000.0, LocalDate.now()),
        new EmployeePayroll( 6,"Mukesh",  'M', 1000000.0, LocalDate.now()),
        new EmployeePayroll( 7, "Anil",  'M',  200000.0, LocalDate.now() )
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
        Assert.assertEquals(  12, employeePayrollService.readFromDB());

    }

}