# Handy Offers Web Application Guide

### Introduction
1. Based on Spring Boot components:
    - spring-boot-starter-web (web application)
    - spring-boot-starter-data-jpa (hibernate and dao layer)
    - spring-boot-starter-security (security)
    - spring-boot-starter-thymeleaf (template framework as alternative for jsp)
    - spring-boot-devtools (developer tools)
    - spring-boot-starter-actuator (spring rest endpoints for app monitoring)
    - spring-boot-starter-test (testing)
2. Other components:
    - mysql-connector-java (connector to MySQL database)
3. Development database:
    - MySQL database
    - user: root, password: test
    - you can change default user and password in application.properties file
4. Running application
    - Install MySQL database with default user name and password (or edit application.properties)
    - Run HandyOffersWebApplication.main()
    - Spring will fire up embedded Tomcat and Hibernate will create database and fill with data from DatabaseLoader class
5. Application packages:
    - __common__: for common resources like constants variables
    - __config__: custom configuration for application (spring configuration)
    - __controller__: controllers for managing http requests and responses
    - __converter__: converters for translating model into DTO and back
    - __dev__: package for development purposes, like creating sample data for app
    - __dto__: data transfer object (transfers object from view to controller)
    - __exception__: all custom exceptions
    - __model__: application entities (hibernate mappings)
    - __repository__: data access object (for retrieving data from DB - Spring Data)
    - __service__: all services (implements functionality)
    - __util__: utility package for helper classes
6. Application resources:
    - __static__: folder for web static content like javascript files, css files etc
    - __templates__: default folder for html files (THYMELEAF)
    - __application.properties__: properties for spring boot and custom properties if needed
    - __logback-prod.xml__: logging configuration for production
7. Testing:
    - __*Test__: classes with postfix Test are unit tests
    - __*MockIntTest__: classes with postfix MockIntTest are using SpringMockMVC for integration tests
    - __*WebIntTest__: classes with postfix IntTest are real integration tests (with RestTemplate)
    - __*SeleniumIntTest__: classes with postfix SeleniumIntTest are using Selenium testing framework
8. Google maps:
    - __Api key__: create credentials to access google maps APIs.
    - __Java API__: [https://github.com/googlemaps/google-maps-services-java](https://github.com/googlemaps/google-maps-services-java)
9. Dumping database
    ```
    mysqldump -uroot -p handyoffers -r handyoffers.sql
    ```
10. Restoring database
    ```
    mysql -uroot -ptest --default-character-set=utf8 handyoffers
    mysql> SET names 'utf8'
    mysql> SOURCE handyoffers.sql
    ```
11. Dumping only data
    ```
    mysqldump -uroot -p --no-create-info --skip-triggers --extended-insert --lock-tables --quick handyoffers -r dump.sql
    ```
#### Spring Actuator
1. Endpoints
    - [http://localhost:8080/monitoring/autoconfig](http://localhost:8080/monitoring/autoconfig)
    - [http://localhost:8080/monitoring/configprops](http://localhost:8080/monitoring/configprops)
    - [http://localhost:8080/monitoring/beans](http://localhost:8080/monitoring/beans)
    - [http://localhost:8080/monitoring/dump](http://localhost:8080/monitoring/dump)
    - [http://localhost:8080/monitoring/env](http://localhost:8080/monitoring/env)
    - [http://localhost:8080/monitoring/env/{name}](http://localhost:8080/monitoring/env)
    - [http://localhost:8080/monitoring/info](http://localhost:8080/monitoring/info)
    - [http://localhost:8080/monitoring/health](http://localhost:8080/monitoring/health)
    - [http://localhost:8080/monitoring/mappings](http://localhost:8080/monitoring/mappings)
    - [http://localhost:8080/monitoring/metrics](http://localhost:8080/monitoring/metrics)
    - [http://localhost:8080/monitoring/metrics/{name}](http://localhost:8080/monitoring/metrics)
    - [http://localhost:8080/monitoring/trace](http://localhost:8080/monitoring/trace)
    - [http://localhost:8080/monitoring/shutdown](http://localhost:8080/monitoring/shutdown)

#### TECHNOLOGIES AND LIBRARIES
- Spring Boot 1.5.2
    - Spring JPA (hibernate)
    - Spring Data
    - Spring MVC
    - Spring Security 4
    - Spring Test
- Thymeleaf
- MySQL
- jQuery
- jQuery Location Picker
- jQuery Day Schedule Selector plugin
- Google Maps Services
- Bootstrap DateTimePicker
- Bootstrap
