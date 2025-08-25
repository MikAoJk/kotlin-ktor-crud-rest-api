# kotlin-ktor-crud-rest-api
Simple application to showcase a CRUD Rest API application in Kotlin

## Technologies used
* JDK 21
* Kotlin
* Gradle
* Docker
* Ktor
* Postgres
* Curl

### Prerequisites
Make sure you have the Java JDK 21 installed
You can check which version you have installed using this command:
``` bash script
java -version
```

#### Docker
Make sure you have docker installed using this command:
```bash script
docker --version
```

#### Curl
Make sure you have curl installed using this command:
```bash script
curl --version
```

### Running the application locally

### Build code
Build the code without running it
```bash script
./gradlew installDist
```

### Test code
Run all the tests
```bash script
./gradlew test
```

#### Running the application locally

##### Create docker image of the kotlin app
Creating a docker image should be as simple as
``` bash
docker build -t kotlinktorapp .
```

##### 👟 Run all the needed services and the application
```bash script
docker compose up
```

##### 🧪 Test the applications endpoints

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

Request to create a new user
```bash script
curl --location --request POST 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--data-raw '{"name": "aaa","email": "aaa@mail"}'
```

Request to get one specific user:
```bash script
curl --location --request GET 'http://localhost:8080/user/2'
```
Example of a response:
`{
"id": 1,
"name": "new",
"email": "new@mail"
}`

Request to update a user
```bash script
curl --location --request PUT 'http://localhost:8080/user/2' \
--header 'Content-Type: application/json' \
--data-raw '{"id":2,"name": "new","email": "new@mail"}'
```

Request to delete a user
```bash script
curl --location --request DELETE 'http://localhost:8080/user/3'
```

#### Api documentation
The api documentation is available here
> **_NOTE:_** The application has to be running

http://localhost:8080/openapi

### Upgrading the gradle wrapper
Find the newest version of gradle here: https://gradle.org/releases/ Then run this command:
> **_NOTE:_** Remember to replace $gradleVersjon with the newest version of gradle
```shell script
./gradlew wrapper --gradle-version $gradleVersjon
```

### Contribute
Want to add a feature or just some other improvements? see [CONTRIBUTING](CONTRIBUTING.md)

### Contact

This project is maintained by [CODEOWNERS](CODEOWNERS)

Questions? please create an
[issue](https://github.com/MikAoJk/kotlin-ktor-crud-rest-api/issues)
