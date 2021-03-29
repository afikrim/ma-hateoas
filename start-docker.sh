export SPRING_DATASOURCE_URL="jdbc:mysql://127.0.0.1:3306/flop?autoReconnect=true&failOverReadOnly=false&maxReconnects=10"
export SPRING_DATASOURCE_USERNAME=flop 
export SPRING_DATASOURCE_PASSWORD=flipFlop

docker-compose --file docker-compose.test.yml up --build -d
./mvnw -f pom.xml clean install
docker-compose down

docker-compose up --build