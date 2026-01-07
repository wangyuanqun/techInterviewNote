### SQL Practice

#### Group By (Having)
* If a table has columns 'emp_id', 'department', 'salary', and I want to list all the departments that have the total department's salary more than 1000000. how to write the query?
    ```
        SELECT department, SUM(salary) AS total_salary
        FROM employees
        GROUP BY department
        HAVING SUM(salary) > 1000000;
    ``` 
    * **Key Differences: WHERE vs HAVING**
    1. **WHERE**: Filters rows **before grouping** (cannot use aggregate functions)
    2. **HAVING**: Filters groups **after grouping** (can use aggregate functions)

#### Join

* If a table has columns 'emp_id', 'department', 'manager_emp_id', 'salary', and I want to list all the employee that has more salary than their manager, how to write the SQL?
    ```
        SELECT 
            e.emp_id,
            e.department,
            e.salary AS employee_salary,
            e.manager_emp_id,
            m.salary AS manager_salary
        FROM employees e
        JOIN employees m ON e.manager_emp_id = m.emp_id
        WHERE e.salary > m.salary;
    ```
* Handling NULL Managers (Top-level executives)
    ```
        -- Only employees with managers
        SELECT e.emp_id, e.salary
        FROM employees e
        INNER JOIN employees m ON e.manager_emp_id = m.emp_id
        WHERE e.salary > m.salary;

        -- Include employees without managers (will show NULL for manager salary)
        SELECT 
            e.emp_id, 
            e.salary AS employee_salary,
            m.salary AS manager_salary
        FROM employees e
        LEFT JOIN employees m ON e.manager_emp_id = m.emp_id
        WHERE e.salary > m.salary OR m.emp_id IS NULL;
    ```
