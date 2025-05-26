package org.example.currencycenter.repository;

import org.example.currencycenter.dto.ResponseEmployeeDTO;
import org.example.currencycenter.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByUsername(String username);

    @Query("SELECT new org.example.currencycenter.dto.ResponseEmployeeDTO(e.id, e.username)FROM Employee e")
    Optional<List<ResponseEmployeeDTO>> findAllEmployeesIdAndUsername();



}
