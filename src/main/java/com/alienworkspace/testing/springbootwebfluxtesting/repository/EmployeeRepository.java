package com.alienworkspace.testing.springbootwebfluxtesting.repository;

import com.alienworkspace.testing.springbootwebfluxtesting.model.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {
}
