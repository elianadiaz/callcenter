package com.almundo.callcenter.repositories;

import java.util.SortedSet;
import com.almundo.callcenter.entities.Employee;

public interface IEmployeeRepository {

	SortedSet<Employee> getAvailableEmployees();
}
