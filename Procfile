# Web-Runner command to start web application
web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war

# To run liquibase, using maven wrapper
release: sh ./mvnw liquibase:update