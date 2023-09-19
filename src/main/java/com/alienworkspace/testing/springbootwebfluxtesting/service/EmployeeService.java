package com.alienworkspace.testing.springbootwebfluxtesting.service;

import com.alienworkspace.testing.springbootwebfluxtesting.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<Employee> create(Employee employee);

    Mono<Employee> update(Employee employee);

    Mono<Employee> fetch(String employeeId);

    Flux<Employee> fetchAll();

    Mono<Void> delete(String employeeID);
}
