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
* MySQL can only use the leftmost prefix of the index
    * (a, b, c) can be used for (a), (a, b), (a, b, c)
    * but cannot be used for (b), (c)

* SELECT * FROM u_phonebook WHERE last_name LIKE ‘G%’; **VS** SELECT * FROM u_phonebook WHERE last_name LIKE ‘%arisson’;
    * The first query can use the index on (last_name), while the second cannot.
        * Because the index is sorted by the beginning of the string. If the wildcard is at the start, the database can't use the sorted order to narrow down the search - it still needs to check every entry.
        * We can create a reverse index for the last_name column to make the second query faster.
 
 ### When Index may not help
 