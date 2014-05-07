spring-boot demo
================

Little demo to insert and show groovy frameworks. Running with [spring-boot](http://projects.spring.io/spring-boot/), the
server is mainly a REST api to insert and list frameworks. All the logic running in the client. That logic is done
in groovy with spock tests and converted to javascript with [grooscript](http://grooscript.org). Using require.js to
load all javascript stuff.

Create idea project:

    ./gradlew idea

Run project, need Mongo Db 2.4+ installed and running:

    ./gradlew bootRun

Convert logic groovy files to javascript:

    ./gradlew convert

Demo running in http://localhost:8080/