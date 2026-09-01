# Lol-demo-server
## About the project
### Description
This repository hosts the code for the backend of a personal project for storing data about
League of legends. The goal is to create a greenfield pet project from local development to cloud hosting
for production like system management.

### Scope
Core features of the project are:

* Browsing champions (skins, stats, spells) and objects (stats, price)
* Saving owned skins for a given champion
* Saving spell and  build order for a given champion like MOBA fire does
* See how many xp the user have on a given champion
* Consult Lol server state in real time

### Architecture

### Technical stack
#### Database
Project uses a PostgresSQL for storing data. Databases run in a docker container for portability matters
and is accessible through adminer interface. There are two databases:
* Postgres: default database that will serve for storing business data
* Keycloak: stores Keycloak data such realm, clients, users and roles. Those data are backed up by an exported json file.
#### Authentication/authorization
The project uses Keycloak for authentication and authorization support in combination of Spring security.
For now, it is not established wether Keycloak built-in screens or Angular will be used to login/logout users, therefore clients are subjected to change.
#### Security layer
Backend uses Spring security for CORS management, xsrf attack mitigation. Rate limiting is forseen with Redis database to track suspicious request abuse rate for a given IP.
#### Backend
The project is built with Spring Boot 4.x and Java 26. it is a REST API server that gather data from RIOT API and add additional data logic upon it before sending json response to Angular client.
#### Frontend
Client is made with Angular 19 (will change over time) to display data to the user. there is a minimal security logic since most of the matter is held by the server side.
#### CI/CD
The CI/CD process is made with Github actions for its ease of use and built-in workflows.
Steps for the backend are the following:
* Nice to have: code review with coderabbit
* Built the project when branch is pushed.
* Runs unit tests and produces a test coverage with jacoco
* Ensures code quality matches expectation and contains no security threat with SonarQube
* Pushes code to Azure environment

### Setup project
#### Requirements
Requirements are minimal since most of the stack runs on Docker
* IntellijIdea so you can run Java 26 in the project structure tab and maven.
* Docker installed. You may want to download Docker Desktop on Windows machine.
* NodeJs for the client.
#### Cloning the project
Make sure you have permissions and create a custom token to clone and push on the repository.
{steps to create token here}
#### Setting secrets files
Two secrets file have to be created for PostgresSQL database:
* postgres_password.txt which contains the password to the database
* postgres_user.txt which contains the username you may want to use.
WARNING: don't commit or push these files for security matters

### Using environment variables for secrets

Alternatively, you may want to use environment variables instead of secrets files

A solution is to export environment variables and cleaning them up after use using Powershell files.
However, you may be prevented from running scripts files. You can disable prevents methods with the following command
in Powershell:

```shell
Set-ExecutionPolicy RemoteSigned -Scope Process
```

#### Getting Riot API key
You need a Riot API key to run the project otherwise the server won't be able to laod data from Lol api's.
You need to loggin to your Riot account in the developer portal and get your API token.
WARNING: API token has a limited duration up to 24 hours, you need to refresh it after.
#### Using the API key
Once you get the api KEY, you can pass it to the backend as an environment variable as as shell argument or as a variable environment int eh Intellij run window.
#### Running Docker containers
Launch containers all at once with the following command:

```bash
docker compose up -d
```

You can check containers health with the following command:
```bash
docker ps
```

#### Stopping Docker containers
There are 2 commands you can use depending wether you want to drop data at shutdown or not.
The first one stops containers but does not remove them:
```bash
docker compose stop
```
While the other one will remove saved data:
```bash
docker compose down
```

#### Compile project and run unit tests

You can build the project with maven if it is installed on your computer or by running it with Intellij directly
```bash
mvn clean install
```
Unit tests will be automatically run.
Additionally, you can run them anytime:
```bash
mvn clean test
```

#### accessing Keycloak server
Once baeldung-keycloak.openidprovider container is correctly launched, you can access the Keycloak server at the given
[url](http://localhost:9090/admin)
You have to use credentials stored in the compose.yml file to log in as admin.
#### Exporting data
You may want to save keycloak data such realms, clients or users data for backup or migration purpose.
To do so, run the given command:
```bash
docker exec -it baeldung-keycloak.openid-provider /opt/keycloak/bin/kc.sh export --dir /tmp/export --realm lol-demo-server-realm --users realm_file
```
A json file will be created inside the Docker container instance. You can copy this file inside Intellij project folder with the command (stop the container before):
```bash
docker cp baeldung-keycloak.openid-provider:/tmp/export ./keycloak-export
```

### Importing Realm
NOTE: place your json realm inside a keycloak directory at the root of the project
```bash
 docker exec -it baeldung-keycloak.openid-provider /opt/keycloak/bin/kc.sh import --dir /opt/keycloak/data/import
```
### API
#### Accessing the API
Api can be tested with a classic curl command or with an API testing tool as Postman.

#### Testing keycloak
Since client is not meant to be secret in order to be accessible for Angular client, make sure client authentication is off.
Then create a validRedirect url for the client (no need to be a real one at first).
Then paste the link with redirectUrl and client id in the browser, for example:
[http://localhost:9090/realms/lol-demo-server-realm/protocol/openid-connect/auth?client_id=lol-demo-server-client&response_type=code&redirect_uri=http://localhost:8081/login/oauth2/code/keycloak&scope=openid](http://localhost:9090/realms/lol-demo-server-realm/protocol/openid-connect/auth?client_id=lol-demo-server-client&response_type=code&redirect_uri=http://localhost:8081/login/oauth2/code/keycloak&scope=openid)
A keycloak login page should pop up. Log in using Keycloak user credentials.
You will be redirected to a blank page. Copy the code in the url. In Postman or curl; do the following:
* POST http://localhost:9090/realms/lol-demo-server-realm/protocol/openid-connect/token
* body with url-encoded type checked
* grant_type => authorization_code
* client_id => lol-demo-server-client (or your client id)
* redirect_uri => [http://localhost:3000/login/oauth2/code/keycloak](http://localhost:8081/login/oauth2/code/keycloak)
* code => the code you copied from url
* client_secret => client secret key if client is confidential
#### endpoints



