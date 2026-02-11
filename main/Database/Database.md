# Database Optimization

## What is Index?
* Index is a file that stores a sorted version of a table based on a certain column or a set of columns.

* Index is a data structure that improves the speed of data retrieval operations on a database table at the cost of additional writes and storage space. 

### Trade-off
* Prons: Faster query performance, especially for large tables.
* Cons: Additional storage space, slower write operations (insert, update, delete).

* The Query Optimizer considers index during every query - more indexes makes this more difficult.

## The right Index
* When to Create an Index
    * what tables and columns you need to query
    * what JOINS you perform
    * What GROUP BY and ORDER BY clauses you are using

* When NOT to Create an Index
    * Small tables (full table scan is faster)
    * Columns with low cardinality (few unique values)
    * Tables with frequent INSERT/UPDATE/DELETE operations
    * Columns rarely used in queries

### Index Candidates
* The query appears in the Slow Query Log
    * available inside the applicaiton
    * parsing applicaiton logs

* The query consistently forces a full table scan
    * Identified by running the MySQL EXPLAIN command

* Query stands out as ‘long running’ in the MySQL Process List

### Index Order is important
* MySQL can only use the **leftmost** prefix of the index
    * (a, b, c) can be used for (a), (a, b), (a, b, c)
    * but cannot be used for (b), (c)

* SELECT * FROM u_phonebook WHERE last_name LIKE ‘G%’; **VS** SELECT * FROM u_phonebook WHERE last_name LIKE ‘%arisson’;
    * The first query can use the index on (last_name), while the second cannot.
        * Because the index is sorted by the beginning of the string. If the wildcard is at the start, the database can't use the sorted order to narrow down the search - it still needs to check every entry.
        * We can create a reverse index for the last_name column to make the second query faster.
 
 ### When Index may not help

#### 1. Small Tables
* Full table scan is faster than index lookup for tables with < 1,000 rows
* The overhead of traversing the index structure + fetching data is slower than just reading all rows sequentially

#### 2. Low Selectivity / High Cardinality Queries
* When the query returns a large percentage of rows (>15-20%), full table scan is faster
* Example: `SELECT * FROM employees WHERE gender = 'M';` (returns ~50% of rows)
* Rule of Thumb: If your query returns more than 15-20% of the table, the optimizer will likely choose a full table scan

#### 3. Columns with Low Cardinality
* Few distinct values mean index isn't selective
* Examples: Boolean fields (true/false), Status fields (active/inactive), Gender (M/F)
* Better: Use partial index (PostgreSQL): `CREATE INDEX idx_active_users ON users(email) WHERE is_active = true;`

#### 4. Functions or Expressions on Indexed Columns
* The index stores raw values, not computed results
* Examples that won't use index:
    * `WHERE UPPER(email) = 'JOHN@EXAMPLE.COM'`
    * `WHERE YEAR(created_date) = 2024`
    * `WHERE price * quantity > 1000`
* Solution: Create function-based/computed column indexes

#### 5. Leading Wildcard in LIKE Queries
* Index is sorted by prefix, can't use it for suffix searches
* Won't use index: `WHERE last_name LIKE '%son'` or `WHERE last_name LIKE '%mit%'`
* Will use index: `WHERE last_name LIKE 'John%'`

#### 6. OR Conditions Across Different Columns
* Database can't efficiently use multiple indexes simultaneously
* Example: `WHERE first_name = 'John' OR last_name = 'Smith'`
* Better: Use UNION to leverage both indexes

#### 7. Inequality Operators with Low Selectivity
* Range scans become expensive if they return many rows
* Example: `WHERE salary > 50000` (if most employees earn > 50000)

#### 8. NOT, !=, <> Operators
* Hard to optimize; often results in full table scan
* Example: `WHERE status != 'active'`
* Better: Rewrite as positive condition: `WHERE status IN ('inactive', 'pending', 'deleted')`

#### 9. Data Type Mismatches
* Implicit type conversion prevents index usage
* Example: `WHERE user_id = '12345'` (if user_id is INT)
* Fix: Use correct data type: `WHERE user_id = 12345`

#### 10. SELECT * with Index-Only Scan Opportunity Lost
* Index can't satisfy all columns, must fetch from table anyway
* Covering index can satisfy query entirely from index alone (faster)

#### 11. Outdated Statistics
* Query optimizer makes decisions based on statistics
* Solution: Regularly run `ANALYZE TABLE users;` (MySQL) or `ANALYZE users;` (PostgreSQL)

#### 12. Index Fragmentation
* Over time, indexes become fragmented and less efficient
* Solution: Rebuild indexes - `OPTIMIZE TABLE users;` (MySQL) or `REINDEX TABLE users;` (PostgreSQL)

#### 13. Too Many Indexes
* Query optimizer gets confused; write operations slow down
* Every INSERT/UPDATE/DELETE must update all indexes
* Rule of Thumb: 5-10 indexes per table maximum

#### 14. Composite Index Wrong Order
* Can only use leftmost prefix
* Index on (last_name, first_name, age):
    * Won't use: `WHERE first_name = 'John'` or `WHERE age = 30`
    * Will use: `WHERE last_name = 'Smith'` or `WHERE last_name = 'Smith' AND first_name = 'John'`

#### 15. Implicit Conversions in Joins
* Type mismatch prevents index usage
* Example: If users.id is INT and orders.user_id is VARCHAR, join won't use index efficiently
```
-- If users.id is INT and orders.user_id is VARCHAR
SELECT * FROM users u
JOIN orders o ON u.id = o.user_id;  -- Index on users.id won't be used efficiently
```
* Fix: Use explicit type cast: `WHERE users.id = CAST(orders.user_id AS INT)`