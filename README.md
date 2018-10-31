#Boleto - A Bankslip Application

It is a project based on RESTApi, with control over bankslips.
It's responsible for insert bankslips on database, to pay bankslips, to see details about bankslips, plus simple interest calculation on bankslips out of the payment date.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project.

### Prerequisites

* **Prerequisites:**<br /> <br /> 
    * Install the version 8 of [Java](https://java.com) and [Maven](https://maven.apache.org/download.html).<br /> <br /> 
    * Install [Eclipse](http://www.eclipse.org/downloads/), the [Maven plugin](http://eclipse.org/m2e/), and optionally the [GitHub plugin](http://eclipse.github.com/)<br /> <br /> 

## Installing

* **Steps:**<br /><br />

    * You must get the project in GitHub. [BankslipApplication](https://github.com/gamoedo/contaazul/)
    <br />
    * In Eclipse, select *Import > Existing Maven Projects* and browse the directory where the project was placed
    <br />
    * To run the project, just a right-click on class BankslipApplication, and select *Run As > Java Application*
	<br /><br />
	
* **Addresses:**<br /><br />
    
    * The project will run on address [BankslipApplicationRunning](http://localhost:8080). There is a instance from ApacheTomcat that SpringBoot provides <br /><br />  
    
    * The project uses H2 Database that writes the Create / Insert data temporarily to memory. It was decided not to persist in disk generating files, which is a pilot project. <br />
    For this reason, you can access the database console at the following address: [H2-DatabaseConsole](http://localhost:8080/h2-console/) - Note: The connection data is set to login: "admin", password: "admin", and can be changed at any time in the "application.properties" file<br /><br />
	 * The project uses too other important tool, called Swagger. This one provides all the documentation of the methods to access the applications RestAPI, requests and responses, as well as making it possible to mount (and send) requests for the API, including showing the return as it will be. You can access this one here: [Swagger-UI](http://localhost:8080/swagger-ui.html) <br /><br />
	 


## Running the tests <br />

* To run the unit tests and integration tests, just a right-click on class BankslipApplicationTests, and select *Run As > JUnit Test*
	<br /><br />
* There are tests with controllers, services, entities and exceptions. To know how much of code is covered by unit/integration tests, just a right-click on class BankslipApplicationTests, and select *Coverage As > JUnit Test*



## Built With

* [SpringBoot](https://spring.io/projects/spring-boot) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [GSon](https://github.com/google/gson) - Used to convert Java Objects into JSON and back
* [Swagger](https://swagger.io/) - Used to generate the documentation about the APIRest
* [Lombok](https://projectlombok.org/) - Used to generate getters and setters in the project
* [H2](http://www.h2database.com) - Used to persist data in database during the runtime

## Author

* **Gabriel Amoedo **

