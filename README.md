# Secret Santa Game (Java)

## Description

The **Secret Santa Game** is a Java-based application that automates the process of assigning Secret Santa pairs while ensuring no one is assigned to themselves or to someone they have gifted to in previous years (if previous assignments are provided). The application uses employee data from `employees.csv` and optionally checks against `previous_assignments.csv` to generate new pairings. The results are output to `assignments.csv` for easy distribution.

---

## Table of Contents

1. [Installation](#installation)  
2. [Usage](#usage)  
3. [Testing](#testing)  
4. [File Structure](#file-structure)  
5. [Sample Input Files](#sample-input-files)  
6. [Output](#output)  

---

## Installation

### 1. Clone the Repository:

```bash
git clone https://github.com/your-username/secret-santa-game.git
cd secret-santa-game
```

### 2. Ensure Java and Maven are Installed:
- Ensure Java (JDK 17 or later) and Maven are installed on your system.

- Installation Guides:

    - [How to Install Java](https://www.java.com/en/download/help/download_options.html)
    - [How to Install Maven](https://www.geeksforgeeks.org/how-to-install-apache-maven-on-windows-macos-and-linux/)

- Verfiy Installation:
    ```bash
    java -version
    mvn -version
    ```

## Usage

### 1. Place Input Files:
Place the following input files in the `src/main/resources/` directory:

- `employees.csv`: Contains the list of employees participating in the Secret Santa.
- `previous_assignments.csv` (Optional): Contains past assignments to avoid repeating pairs.

#### Example Directory Structure:    
    secret-santa-game/
    ├── src/
    │   ├── main/
    │   │   ├── resources/
    │   │   │   ├── employees.csv
    │   │   │   ├── previous_assignments.csv

### 2. Compile and Run the Program:
Use the following commands:
```bash   
    mvn compile 
    mvn exec:java -Dexec.mainClass="com.acme.Main"
```

### 3. Check the Output:
The output file assignments.csv will be generated in the `/result/` directory.
#### Example Directory Structure:
    secret-santa-game/
    ├── result/
    │   ├── assignments.csv

## Testing
#### Run the unit tests using the following command:
    mvn test

## File Structure
    secret-santa-game/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   ├── com/
    │   │   │   │   ├── acme/
    │   │   │   │   │   ├── Employee.java
    │   │   │   │   │   ├── FileHandler.java
    │   │   │   │   │   ├── Validator.java
    │   │   │   │   │   ├── SecretSanta.java
    │   │   │   │   │   ├── Main.java
    │   │   ├── resources/
    │   │   │   ├── employees.csv
    │   │   │   ├── previous_assignments.csv
    │   ├── test/
    │   │   ├── java/
    │   │   │   ├── com/
    │   │   │   │   ├── acme/
    │   │   │   │   │   ├── SecretSantaTest.java
    ├── result/
    │   ├── assignments.csv
    ├── pom.xml
    ├── README.md

## Sample Input Files
### 1. `employees.csv`
This is the input file that contains the list of employee names and employee email ids.

Place this file in `src/main/resources/employees.csv`

This is a sample CSV File:
```csv
    Employee_Name,Employee_EmailID
    Hamish Murray,hamish.murray@acme.com
    Layla Graham,layla.graham@acme.com
    Matthew King,matthew.king@acme.com
    Benjamin Collins,benjamin.collins@acme.com 
```

### 2. `previous_assignments.csv` (Optional)
This is the input file that contains the list of Secret-Santa-Secret-Child Relationship last time, in order to prevent repeating.

Place this file in `src/main/resources/employees.csv`

This is a sample CSV File:
```csv
    Employee_Name,Employee_EmailID,Secret_Child_Name,Secret_Child_EmailID
    Hamish Murray,hamish.murray@acme.com,Charlie Ross,charlie.ross@acme.com
    Layla Graham,layla.graham@acme.com,Matthew King,matthew.king@acme.com
```

## Output
The program will generate the `assignments.csv` file in the /result/ directory.

#### Example Output (`assignments.csv`):
```csv
    Employee_Name,Employee_EmailID,Secret_Child_Name,Secret_Child_EmailID
    Hamish Murray,hamish.murray@acme.com,Charlie Wright,charlie.wright@acme.com
    Layla Graham,layla.graham@acme.com,Matthew King Jr,matthew.king.jr@acme.com
    Matthew King,matthew.king@acme.com,Piper Stewart,piper.stewart@acme.com
    Benjamin Collins,benjamin.collins@acme.com,Hamish Murray Jr,hamish.murray.jr@acme.com
```