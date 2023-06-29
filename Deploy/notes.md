### Build spring boot app jar 
./gradlew clean build

### Create images for spring and postgresql
docker build -t swifticket.jar .
docker build -t swt-postgres .

### Launch container
docker-compose up
docker-compose up -d

## List containers and images
docker ps
docker images
docker volume ls

## Stop and delete containers

### Stop the container(s) using the following command
docker-compose down
docker stop $(docker ps -aq)

### Delete all containers using the following command
docker rm -f $(docker ps -a -q)

### Remove one image
docker rmi swifticket.jar

### Remove all images
docker rmi $(docker images -q)

### Delete all volumes using the following command
docker volume rm $(docker volume ls -q)
