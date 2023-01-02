run: 
	- mvn clean package
	- java -jar api/target/ratings-microservice-api-1.0.0-SNAPSHOT.jar

up:
	- docker-compose up -d

up-build:
	- docker-compose up --build -d

down:
	- docker-compose down

logs:
	- docker-compose logs -f