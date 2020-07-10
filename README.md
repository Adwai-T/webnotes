# Programming Blog and Quiz BackEnd

## Notes

## Using Environment Variables

In the application.properties file we could use environment variables.

Environment variables without default value : `api.key=${API_KEY}`

Environment variables with default value : `api.key=${API_KEY:123abc}`

`@Value("${message.default.welcome}")` 
Can be used in our java program to get the values of the set environment variable.

It also helps us to set a default value if this variable was not found.
`@Value("${message.default.welcome:SomeDefaultValue}")`

If our properties have some common context like the same prefix, we can use the `@ConfigurationProperties` annotation which will map these properties to Java objects:

```java

/*
* @Configuration will tell Spring to create a bean of this class.
* @ConfigurationProperties will initialize the fields with corresponding property names.
*/
@Configuration
@ConfigurationProperties(prefix = "message.default")
public class MessageProperties {

    private String welcome;
    private String goodbye;

    // Getters and Setters
}
```
```java
//We can now use this bean anywhere in our project
@Autowired
private MessageProperties messageProperties;

```

## Using Spring Profiles

Following files can be defined for different environments:

1. application-dev.properties
2. application-qa.properties
3. application-production.properties

To change between these environments we have to set the environment variable: `--spring.profiles.active="dev"` while we run the program like so : `java -jar app.jar --name="Spring"`.

> Note : application.properties is always loaded, irrespective of the spring.profiles.active value. If there is the same key-value present both in application.properties and application-<environment>.properties, the latter will override the former.

> While Using IntelliJ IDE we can set the environment variables in Run -> Edit Configuration -> EnvironmentVariables while we run the program with IntelliJ.

