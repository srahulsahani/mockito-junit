package com.javaguides.springboot.repository;

import com.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup(){
         employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();
    }

    //Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup
        /* Commented employee object to execute @BeforeEach annotation */
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();*/

        //when - action or behaviour to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //Junit test for get all employees
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        //given-precondition
        Employee employee1 = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("johncena@gmail.com")
                .build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //when - action or behaviour to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //Junit test for get employee by id
    @Test
    public void givenEmployee_whenFindById_thenReturnEmployeeObject(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        //when - action or behaviour to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for get Employee by email
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when - action or behaviour to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for update employee operation
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("ram@gmail.com");
        savedEmployee.setFirstName("Ram");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    //Junit test for delete Employee operation
    @Test
    public void givenEmployeeObject_whenDelete_theRemoveEmployeeObject(){
        //given-precondition
       /* Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //when - action or behaviour to test
        employeeRepository.delete(employee);
        Optional <Employee> employeeOptional = employeeRepository.findById(employee.getId());
        //then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    //Junit test for custom query using JPQL with index
    @Test
    public void givenFirstNameAndLastName_whenfindByJPQL_thenReturnEmployeeObject(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        String firstName= "Ramesh";
        String lastName = "Fadatare";
        //when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName,lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using JPQL named index
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        String firstName= "Ramesh";
        String lastName = "Fadatare";
        //when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName,lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using native SQL query
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        String firstName= "Ramesh";
        String lastName = "Fadatare";

        //when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),employee.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for custom query using native SQL Named Param query
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamed_thenReturnEmployeeObject(){
        //given-precondition
        /*Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        String firstName= "Ramesh";
        String lastName = "Fadatare";

        //when - action or behaviour to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(),employee.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}
