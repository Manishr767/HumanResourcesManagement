-- Step 1: Create the employees table
CREATE TABLE employees (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    ssn VARCHAR2(50) NOT NULL,
    address VARCHAR2(255) NOT NULL,
    nationality VARCHAR2(50) NOT NULL,
    position VARCHAR2(50) NOT NULL,
    photo BLOB
);

-- Step 2: Create a sequence for the id column
CREATE SEQUENCE employees_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

-- Step 3: Create a trigger to auto-generate id values
CREATE OR REPLACE TRIGGER employees_trigger
BEFORE INSERT ON employees
FOR EACH ROW
BEGIN
    IF :NEW.id IS NULL THEN
        SELECT employees_seq.NEXTVAL INTO :NEW.id FROM DUAL;
    END IF;
END;
/

INSERT INTO employees (name, ssn, address, nationality, position, photo)
VALUES ('John Doe', '123-45-6789', '123 Main St', 'American', 'Developer', NULL);

select * from employees;