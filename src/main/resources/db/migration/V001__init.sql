-- Genders
DROP TABLE IF EXISTS genders;
CREATE TABLE genders (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL
);
INSERT INTO genders (name) values ('Femenino'), ('Masculino');

-- Jobs
DROP TABLE IF EXISTS jobs;
CREATE TABLE jobs (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
salary DECIMAL(12,2) NOT NULL
);
INSERT INTO jobs (name, salary) values ('Vendedor', 8000), ('Diseñador', 10000), ('Scrum Master', 20000), ('Supervisor', 30000);

-- Employees
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
gender_id BIGINT NOT NULL,
job_id BIGINT NOT NULL,
name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
birthdate DATE NOT NULL,
created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
CONSTRAINT unique_name_lastname UNIQUE (name, last_name),
CONSTRAINT fk_employees_genders FOREIGN KEY (gender_id) REFERENCES genders (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT fk_employees_jobs FOREIGN KEY (job_id) REFERENCES jobs (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Employee Worked Hours
DROP TABLE IF EXISTS employee_worked_hours;
CREATE TABLE employee_worked_hours (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
employee_id BIGINT NOT NULL,
worked_hours INT NOT NULL,
worked_date DATE NOT NULL,
CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);