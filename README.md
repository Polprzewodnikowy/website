## website

My personal website code based on Spring Boot. I started coding it as a reason to learn basics of Spring and Hibernate frameworks.

Used libraries / frameworks:
 - Spring Framework
 - Spring Boot
 - Spring Security
 - Spring MVC
 - Spring Data
 - Hibernate/JPA
 - Thymeleaf template engine with layout dialect
 - flexmark-java Markdown parser
 - Bootstrap
 - jQuery

## Build and run

Before running this application make sure you have a development SSL certificate named keystore.p12 in config directory. To make one just type this command in project root directory:

```
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore config/keystore.p12 -validity 3650
```

You will also need a running MySQL or MariaDB database server with table `website` and user `websiteUser:websitePassword`.

-----

To build and run this project simply install gradle and type in terminal:

```
gradle bootRun
```

After some time you can open your favourite browser and go to `https://localhost:8443` to see application in action.

Default credentials are:
 - login: admin
 - password: admin

Hope you enjoy it :)
