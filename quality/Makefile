build:
	@echo "Building the application"
	@mvn compile

unit-test:
	@echo "Running unit tests"
	@mvn test

integration-test:
	@echo "Running integration tests"
	@mvn test -P integration-test

system-test:
	@echo "Running system tests"
	@make docker-start-detached
	@mvn test -P system-test
	@make docker-stop

performance-test:
	@echo "Running performance tests"
	@mvn gatling:test -P performance-test

test: unit-test integration-test

package:
	@echo "Packaging the application"
	@mvn package

start-app:
	@echo "Starting the application"
	@mvn spring-boot:start

stop-app:
	@echo "Stopping the application"
	@mvn spring-boot:stop

docker-build:
	@echo "Building the docker image"
	@docker build -t backend:dev -f ./Dockerfile .

docker-start:
	@echo "Starting the application in docker"
	docker compose -f ./docker-compose.yaml up --build

docker-stop:
	@echo "Stopping the application in docker"
	@docker compose -f ./docker-compose.yaml down

docker-start-detached:
	@echo "Starting the application in docker"
	docker compose -f ./docker-compose.yaml up --build -d