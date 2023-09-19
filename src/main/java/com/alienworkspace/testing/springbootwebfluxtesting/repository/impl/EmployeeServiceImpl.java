package com.alienworkspace.testing.springbootwebfluxtesting.repository.impl;

import com.alienworkspace.testing.springbootwebfluxtesting.model.Employee;
import com.alienworkspace.testing.springbootwebfluxtesting.repository.EmployeeRepository;
import com.alienworkspace.testing.springbootwebfluxtesting.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Mono<Employee> create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Mono<Employee> update(Employee employee) {
        return employeeRepository.findById(employee.getId())
                .map(savedEmployee -> {
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());
                    return savedEmployee;
                }).flatMap(employeeRepository::save);


    }

    @Override
    public Mono<Employee> fetch(String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Flux<Employee> fetchAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Mono<Void> delete(String employeeID) {
        return employeeRepository.deleteById(employeeID);
    }
}
