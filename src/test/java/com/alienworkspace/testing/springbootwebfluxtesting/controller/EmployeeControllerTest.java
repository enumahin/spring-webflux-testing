package com.alienworkspace.testing.springbootwebfluxtesting.controller;

import com.alienworkspace.testing.springbootwebfluxtesting.model.Employee;
import com.alienworkspace.testing.springbootwebfluxtesting.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("Test Create Employee Operation")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){

        // given -
        Employee employee = Employee.builder()
                .firstName("Michael")
                .lastName("Konja")
                .email("konja_emma@gmail.com")
                .build();
        BDDMockito.given(employeeService.create(ArgumentMatchers.any(Employee.class)))
                .willReturn(Mono.just(employee));
        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(employee), Employee.class)
                .exchange();

        responseEntity.expectStatus().isCreated()
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

        BDDMockito.given(employeeService.fetch("123"))
                .willReturn(Mono.just(employee));
        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.get()
                .uri("/api/employees/{id}", Collections.singletonMap("id", "123"))
                .exchange();

        responseEntity.expectStatus().isOk()
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

        BDDMockito.given(employeeService.fetchAll())
                .willReturn(Flux.just(employee, employee2));
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
//        Employee employee = Employee.builder()
//                .firstName("Michael")
//                .lastName("Konja")
//                .email("konja_emma@gmail.com")
//                .build();
//        BDDMockito.given(employeeService.fetch("123"))
//                .willReturn(Mono.just(employee));
        Employee updatedEmployee = Employee.builder()
                .firstName("Ike")
                .lastName("Obodo")
                .email("ike@gmail.com")
                .build();
        BDDMockito.given(employeeService.update(ArgumentMatchers.any(Employee.class)))
                .willReturn(Mono.just(updatedEmployee));
        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", "123"))
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
        BDDMockito.given(employeeService.delete("123")).willReturn(Mono.empty());

        // when -
        WebTestClient.ResponseSpec responseEntity = webTestClient.delete()
                .uri("/api/employees/{id}", Collections.singletonMap("id", "123"))
                .exchange();

        responseEntity.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);


        // then -



    }


}
