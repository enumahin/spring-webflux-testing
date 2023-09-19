package com.alienworkspace.testing.springbootwebfluxtesting.controller;

import com.alienworkspace.testing.springbootwebfluxtesting.model.Employee;
import com.alienworkspace.testing.springbootwebfluxtesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Flux<Employee> fetchAll(){
        return employeeService.fetchAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> fetchById(@PathVariable("id") String employeeId){
        return employeeService.fetch(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Employee> create(@RequestBody Employee employee){
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Mono<Employee> update(@RequestBody Employee employee, @PathVariable("id") String employeeId){
        employee.setId(employeeId);
        return employeeService.update(employee);
    }

    @DeleteMapping("/{id}")
    public Mono<?> delete(@PathVariable("id") String employeeId){
        return employeeService.delete(employeeId);
    }
}
