### SQL Practice
* if a table has columns 'emp_id', 'department', 'salary', and I want to list all the departments that have the total department's salary more than 1000000. how to write the query?
    ```
        SELECT department, SUM(salary) AS total_salary
        FROM employees
        GROUP BY department
        HAVING SUM(salary) > 1000000;
    ``` 
    * **Key Differences: WHERE vs HAVING**
    1. **WHERE**: Filters rows **before grouping** (cannot use aggregate functions)
    2. **HAVING**: Filters groups **after grouping** (can use aggregate functions)