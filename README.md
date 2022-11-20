# Testing in Quarkus

This project applies a complete test strategy to a Quarkus application.

## Dev Mode

In dev mode, database container is started automatically, so no manual setup is required.

```shell script
mvn compile quarkus:dev
```

## Packaging & running

### Preparation

For all packages, DB settings must be specified as environment variables before starting the application.

```shell script
export QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://postgres:5432/todo
export QUARKUS_DATASOURCE_USERNAME=postgres
export QUARKUS_DATASOURCE_PASSWORD=postgres
```

To start a local database server, the following can be used:

```shell script
cd src/main/docker/local-setup
docker compose up postgres
```

### Package & run as JAR

```shell script
mvn package

java -jar target/quarkus-app/quarkus-run.jar
```

### Package & run as über-JAR

```shell script
mvn package -Dquarkus.package.type=uber-jar

java -jar target/*-runner.jar
```

### Package & run as native image

```shell script
mvn package -Pnative -Dquarkus.native.container-build=true

./target/quarkus-maven-kotlin-1.0.0-SNAPSHOT-runner 
```

### Package & run native build in Docker container

In `src/main/docker/local-setup/docker-compose.yml` a Docker Compose file is provided to run the native build in a
container.

This also starts a Postgres container. The native image must be build beforehand via Maven.

```shell script
mvn package -Pnative -Dquarkus.native.container-build=true

docker compose -f src/main/docker/local-setup/docker-compose.yml build
docker compose -f src/main/docker/local-setup/docker-compose.yml up
``` 

## Important endpoints

* http://localhost:8080/todos/ - API base
* http://localhost:8080/q/dev/ - Dev UI (dev mode only)
* http://localhost:8080/q/metrics/ - Metrics
* http://localhost:8080/q/swagger-ui/ - Swagger UI
