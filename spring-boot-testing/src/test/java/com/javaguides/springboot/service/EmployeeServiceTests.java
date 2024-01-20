package com.javaguides.springboot.service;

import com.javaguides.springboot.exception.ResourceNotFoundException;
import com.javaguides.springboot.model.Employee;
import com.javaguides.springboot.repository.EmployeeRepository;
import com.javaguides.springboot.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private  EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
         employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("Ramesh")
                .email("ramesh@gmail.com")
                .build();
    }

    //Junit test for saveEmployee()
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given-precondition

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or behaviour to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    //Junit test for saveEmployee() method which throws exception
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        //given-precondition

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //when - action or behaviour to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });
        // Then
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    //Junit test for getAllEmployees()
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnAllEmployees(){
        //given-precondition
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tonystark@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
        //when - action or behaviour to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //Junit test for getAllEmployees() - negative scenarios
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        //given-precondition
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tonystark@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when - action or behaviour to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //Junit test for getEmployeeById
    @Test
    public void givenEmployeeId_whenGetEmployeeByID_thenReturnEmployeeObject(){
        //given-precondition
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or behaviour to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    //Junit test for updatedEmployee()
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given-precondition
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmail.com");
        employee.setFirstName("Ram");

        //when - action or behaviour to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");

    }

    //Junit test for deleteById()
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){
        long employeeId =1L;
        //given-precondition
        willDoNothing().given(employeeRepository).deleteById(1L);

        //when - action or behaviour to test
        employeeService.deleteEmployee(employeeId);
        //then - verify the output
        verify(employeeRepository,times(1)).deleteById(employeeId);

    }
}
