# kotlin-ktor-crud-rest-api
Simple application to showcase a CRUD Rest API application in Kotlin

### Prerequisites
#### Java
Make sure you have the Java JDK 25 installed
You can check which version you have installed using this command:
``` bash script
java -version
```
or install it: https://sdkman.io/

#### Docker
Make sure you have docker installed using this command:
```bash script
docker --version
```
or install it: https://docs.docker.com/engine/install/

#### Docker Compose
Make sure you have docker compose installed using this command:
```bash script
docker compose version
```
or install it: https://docs.docker.com/compose/install/

#### Curl
Make sure you have curl installed using this command:
```bash script
curl --version
```
or install it: https://curl.se/docs/install.html

### Running the application locally

### Build the code
Build the code without running it
```bash script
./gradlew installDist
```

### Test the code
Run all the tests
```bash script
./gradlew test
```

### Run the code
Run the application
> **Note**
> This will not work, you need a database to run the application locally se [Run all the needed services and the application](#Run-all-the-needed-services-and-the-application)
```bash script
./gradlew run
```

#### Running the application locally

##### Create docker image of the kotlin app
Creating a docker image should be as simple as
``` bash
docker build -t kotlinktorapp .
```

##### Run all the needed services and the application
```bash script
docker compose up -d
```
remember to take it down after use:
```bash script
docker compose down
```

##### 🧪 Test the applications endpoints

Request to create a new user
```bash script
curl --location --request POST 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{"name": "aaa","email": "aaa@mail"}'
```

Request to get the all the users:
```bash script
curl --location --request GET 'http://localhost:8080/users'
```
Example of a response:
`[ 
    {
    "id": 1,
    "name": "aaa",
    "email": "aaa@mail"
    },
    {
    "id": 2,
    "name": "bbb",
    "email": "bbb@mail"
    }
]`

Request to get one specific user:
```bash script
curl --location --request GET 'http://localhost:8080/users/1'
```
Example of a response:
`{
"id": 1,
"name": "new",
"email": "new@mail"
}`

Request to update a user
```bash script
curl --location --request PUT 'http://localhost:8080/users/2' \
--header 'Content-Type: application/json' \
--data-raw '{"id":2,"name": "new","email": "new@mail"}'
```

Request to delete a user
```bash script
curl --location --request DELETE 'http://localhost:8080/users/1'
```

#### Api documentation
The api documentation is available here
> **_NOTE:_** The application has to be running

http://localhost:8080/openapi

### Upgrading the Gradle wrapper
Update to the latest version of Gradle 
```shell script
./gradlew wrapper --gradle-version latest
```

### Perfomance test
How to run Performance test see [performance-test/README.md](performance-test/README.md)

### Contact

This project is maintained by [CODEOWNERS](CODEOWNERS)

Questions? please create an
[issue](https://github.com/MikAoJk/kotlin-ktor-crud-rest-api/issues)
