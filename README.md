# Lafayette

Testing Camunda Platform with Spring Boot Java using Eclipse.

# Gmail setup

This exmaple was built using a GMAIL account. To setup acces to your gmail accout folow [these steps](https://stackoverflow.com/questions/26594097/javamail-exception-javax-mail-authenticationfailedexception-534-5-7-9-applicatio/72592946#72592946).


Assuming you have already configured access to your email server. Update the `application.yml` file with your username and password.

```yaml
spring.mail:
  host: smtp.gmail.com
  port: 587
  username: yourGmail
  password: yourAppPassword
  properties.mail.smtp:
    auth: true
    starttls.enable: true
```


