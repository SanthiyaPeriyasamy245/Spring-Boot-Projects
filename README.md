# Docker command to build image :
docker build -t image-name .

# Docker command to run container : 
docker run -d -p host-port:container-port --name container_name image_name

# Docker command to run multiple container
docker -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=local" --name todo-application-1 todo-application

docker -d -p 8081:8080 -e "SPRING_PROFILES_ACTIVE=local" --name todo-application-2 todo-application

docker -d -p 8082:8080 -e "SPRING_PROFILES_ACTIVE=local" --name todo-application-3 todo-application

provide unique host-port and container name container-port listens to springboot port.
