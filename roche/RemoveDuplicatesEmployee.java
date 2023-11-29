package roche;

import java.util.List;
import java.util.Objects;

public class RemoveDuplicatesEmployee {

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(1, "Silas"),
                new Employee(2, "Joao"),
                new Employee(1, "Silas")
        );

        List<Employee> collect = removeDuplicates(employees);

        System.out.println(collect.size());
    }

    public static List<Employee> removeDuplicates(List<Employee> employees) {
        return employees.stream().distinct().toList();
    }

    public static class Employee {
        private Integer id;
        private String name;

        public Employee(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return Objects.equals(id, employee.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
