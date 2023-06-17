./gradlew clean build

docker build -t swifticket.jar .
docker build -t swt-postgres .

docker-compose up
docker-compose up -d