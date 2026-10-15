# performance-test

### Prerequisites
1. [java](../README.md#Java)
2. [docker](../README.md#Docker)
3. [docker compose](../README.md#Docker-Compose)
4. [curl](../README.md#Curl)
5. k6
   Make sure you have k6 installed using this command:
```bash script
k6 --version
```
or install it: https://grafana.com/docs/k6/latest/set-up/install-k6/


## Running k6 performance-test
1. [Build the code](../README.md#Build-the-code)
2. [Create docker image of the kotlin app](../README.md#Create-docker-image-of-the-kotlin-app)
3. [Run all the needed services and the application](../README.md#Run-all-the-needed-services-and-the-application)
4. Then you can run the performance test
```bash script
cd performance-test
k6 run -e BASE_URL=http://localhost:8080 --vus 1 --duration 3s script.js
```