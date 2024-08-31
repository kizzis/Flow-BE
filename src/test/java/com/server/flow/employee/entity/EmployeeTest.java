package com.server.flow.employee.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@ActiveProfiles("local")
@TestPropertySource(locations = "classpath:/yaml/application-local.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Transactional
class EmployeeTest {
	@Autowired
	EntityManager em;

	@Test
	@Commit
	public void createEmployee() {
		// given
		// when
		// then
		Department department = Department.builder()
			.departmentName("개발팀")
			.build();

		em.persist(department);

		Position position = Position.builder()
			.positionName("리드")
			.build();

		em.persist(position);

		Employee employee = Employee.builder()
			.name("박보검")
			.employeeNumber("1234340001")
			.password("abc123!!")
			.department(department)
			.position(position)
			.build();

		em.persist(employee);
	}
}
