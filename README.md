# Human Resources Management

A Java Swing desktop application created to practice desktop UI development and database connectivity. The repository includes an employee-management interface backed by Oracle Database, along with small image-loading and recipe-finder exercises.

## Technology

- Java
- Java Swing
- JDBC
- Oracle Database
- SQL and BLOB storage for employee photos

## Included components

- Employee management UI in `src/HumanResMgt/`
- Oracle table, sequence, and trigger setup in `src/HumanResMgt/DB.sql`
- Image loading example in `src/ImageFromHardDIsk/`
- Recipe finder example in `src/RecipeFinder.java`

## Database setup

The SQL script defines an `employees` table with employee details and a BLOB column for a photo. It also creates a sequence and trigger for generating employee IDs.

To run the employee application, configure an Oracle database and update the JDBC connection settings locally. **Do not commit database usernames, passwords, or real employee information.** Use synthetic sample records only.

## Running

Open the Java source project in an IDE, add the Oracle JDBC driver to the classpath, configure the database connection, and run the relevant application class.

The repository does not currently include a build tool configuration, so dependency setup is manual.

## Learning objectives

- Build desktop interfaces with Swing
- Connect Java applications to relational databases with JDBC
- Execute SQL statements and work with database-generated IDs
- Store and retrieve image data using BLOB fields

## Security and privacy

This is an educational project, not a production HR system. Employee identifiers and personal information are sensitive. Use dummy data and externalize credentials before sharing or deploying the application.

## Author

**Manish Rauniyar** · [GitHub](https://github.com/Manishr767)
