package EmployeeStreamApiDemo;

import java.util.*;
import java.util.stream.*;

class Employee {
    String name;
    int age;
    String gender;
    double salary;
    String designation;
    String department;

    public Employee(String name, int age, String gender, double salary,
                    String designation, String department) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.designation = designation;
        this.department = department;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getSalary() { return salary; }
    public String getDesignation() { return designation; }
    public String getDepartment() { return department; }

    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return name + " | " + age + " | " + gender + " | " +
               salary + " | " + designation + " | " + department;
    }
}

public class EmployeeStreamDemo {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
            new Employee("Amit", 45, "Male", 90000, "Manager", "IT"),
            new Employee("Priya", 30, "Female", 60000, "Developer", "IT"),
            new Employee("Rahul", 50, "Male", 120000, "Director", "HR"),
            new Employee("Sneha", 28, "Female", 55000, "Developer", "IT"),
            new Employee("Vikram", 40, "Male", 80000, "Manager", "Finance"),
            new Employee("Neha", 35, "Female", 75000, "Analyst", "Finance"),
            new Employee("Arjun", 38, "Male", 70000, "Developer", "IT"),
            new Employee("Kavya", 42, "Female", 95000, "Manager", "HR")
        );

        // 1. Highest salary employee
        employees.stream()
            .max(Comparator.comparing(Employee::getSalary))
            .ifPresent(e -> System.out.println("Highest salary employee: " + e));

        // 2. Count male & female employees
        Map<String, Long> genderCount =
            employees.stream()
                .collect(Collectors.groupingBy(
                    Employee::getGender,
                    Collectors.counting()
                ));
        System.out.println("Gender count: " + genderCount);

        // 3. Total expense department wise
        Map<String, Double> deptExpense =
            employees.stream()
                .collect(Collectors.groupingBy(
                    Employee::getDepartment,
                    Collectors.summingDouble(Employee::getSalary)
                ));
        System.out.println("Department expense: " + deptExpense);  // use partitioningBy if you want to separate managers and non-managers in each department

        // 4. Top 5 senior employees (by age)
        System.out.println("Top 5 senior employees:");
        employees.stream()
            .sorted(Comparator.comparing(Employee::getAge).reversed())
            .limit(5)
            .forEach(System.out::println);

        // 5. Names of all managers
        List<String> managers =
            employees.stream()
                .filter(e -> e.getDesignation().equalsIgnoreCase("Manager"))
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("Managers: " + managers);

        // 6. Hike salary by 20% for everyone except managers
        employees.stream()
            .filter(e -> !e.getDesignation().equalsIgnoreCase("Manager"))
            .forEach(e -> e.setSalary(e.getSalary() * 1.20));

        System.out.println("Employees after salary hike:");
        employees.forEach(System.out::println);

        // 7. Total number of employees
        long totalEmployees = employees.stream().count();
        System.out.println("Total employees: " + totalEmployees);
        // list.size() can also be used since we have a List, but using stream().count() is more consistent with the rest of the operations.
    //colloctions can also be used to perform some of the above operations, but using streams allows for more complex and flexible data processing.
    // this are the operations that can be performed using collections

    

    }
}

