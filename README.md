# spring-boot-dynamic-database

Features
---
liquibase prepare multi database

spring dynamic Routing DataSource


Start
----

prepare database
===
```
gradlew gold update
gradlew silver update
gradlew bronze update

```

test and build
 ===
```
gradlew build
```

start service
===
```
java -jar build/libs/spring-boot-dynamic-database-0.1.0.jar
```

verify result
===
visit to different database:
 http://localhost:8080/greeting?name=gold
 http://localhost:8080/greeting?name=silver
 http://localhost:8080/greeting?name=bronze
