package com.alienworkspace.testing.springbootwebfluxtesting.controller;

import com.alienworkspace.testing.springbootwebfluxtesting.config.AbstractTestContainerBase;
import com.alienworkspace.testing.springbootwebfluxtesting.model.Employee;
import com.alienworkspace.testing.springbootwebfluxtesting.repository.EmployeeRepository;
import com.alienworkspace.testing.springbootwebfluxtesting.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerITest extends AbstractTestContainerBase {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    @DisplayName("Test Create Employee Operation")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){

        // given -
        Employee employee = Employee.builder()
                .firstName("Michael")
                .lastName("Konja")
                .email("konja_emma@gmail.com")
                .build();
        // when -
        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employee), Employee.class)
                .exchange().expectStatus().isCreated()
                .expectBody().consumeWith(System.out::print)
                .jsonPath("$.firstName").isEqualTo(employee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employee.getLastName())
                .jsonPath("$.email").isEqualTo(employee.getEmail());


        // then -



    }

    @Test
    @DisplayName("Test Get Employee Operation")
    public void givenEmployeeId_whenGetEmployee_thenReturnEmployeeObject(){

        // given -
        Employee employee = Employee.builder()
                .firstName("Michael")
                .lastName("Konja")
                .email("konja_emma@gmail.com")
                .build();
        employeeService.create(employee).block();

     webTestClient.get()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employee.getId()))
                .exchange().expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employee.getLastName())
                .jsonPath("$.email").isEqualTo(employee.getEmail());
        // then -



    }


    @Test
    @DisplayName("Test Get All Employees Operation")
    public void givenFluxOfEmployees_whenGetEmployees_thenReturnFluxEmployeeObject(){

        // given -
        Employee employee = Employee.builder()
                .firstName("Michael")
                .lastName("Konja")
                .email("konja_emma@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Ibeh")
                .lastName("Agbam")
                .email("agbam@gmail.com")
                .build();

        employeeService.create(employee).block();
        employeeService.create(employee2).block();
        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.get()
                .uri("/api/employees")
                .exchange();

        // then -

        responseEntity.expectStatus().isOk()
                .expectBodyList(Employee.class)
                .consumeWith(System.out::println);


    }


    @Test
    @DisplayName("Test Update Employee Operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject(){

        // given -
        Employee employee = Employee.builder()
                .firstName("Zim")
                .lastName("Uzo")
                .email("zim_emma@gmail.com")
                .build();
        employeeService.create(employee).block();
        System.out.println(employee);
        Employee updatedEmployee = Employee.builder()
                .firstName("Ike")
                .lastName("Obodo")
                .email("ike@gmail.com")
                .build();
        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee), Employee.class)
                .exchange();

        responseEntity.expectStatus().isOk()
                .expectBody().consumeWith(System.out::print)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());


        // then -



    }


    @Test
    @DisplayName("Test Delete Employee Operation")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){

        // given -
        Employee employee = Employee.builder()
                .firstName("Zim")
                .lastName("Uzo")
                .email("zim_emma@gmail.com")
                .build();
        employeeService.create(employee).block();

        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.delete()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employee.getId()))
                .exchange();

        responseEntity.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);


        // then -



    }


}
