import entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();
        System.out.print("Enter salary: ");
        Double value = sc.nextDouble();

        List<Employee> employees = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                String name = fields[0];
                String email = fields[1];
                Double salary = Double.parseDouble(fields[2]);

                Employee employee = new Employee(name, email, salary);
                employees.add(employee);

                line = br.readLine();
            }

            List<String> emails = employees.stream()
                    .filter(e -> e.getSalary() > value)
                    .map(e -> e.getEmail())
                    .sorted((e1, e2) -> e1.compareTo(e2))
                    .collect(Collectors.toList());

            System.out.printf("Email of people whose salary is more than %.2f:%n", value);
            for (String email : emails){
                System.out.println(email);
            }

            double totalSalaries = employees.stream()
                    .filter(e -> e.getName()
                            .startsWith("M"))
                            .mapToDouble(e -> e.getSalary())
                            .sum();

            System.out.printf("Sum of salary of people whose name starts with 'M': %.2f", totalSalaries);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}