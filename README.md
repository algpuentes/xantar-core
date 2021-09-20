# Xantar

Xantar is a tool designed to help you scheduling a meal plan.

## Installation

First of all, create the application.properties file with the content of the application-template.properties and replace the values to set the datasource. Do the same with the docker-compose.yml.

To run the application locally with Docker, first generate the jar file with maven. The next commands run in a PowerShell in the xantar-core directory will generate the xantar.jar file in the target directory:

```bash
mvn clean
mvn install -DskipTests
```

After generating the jar, the Docker image of the service can be generated with:

```bash
docker build -t xantar.jar .
```

Once the image is ready, everything is ready to be deployed on Docker containers. For this purpose, the docker-compose.yml file will create the PostgreSQL container and the service container:

```bash
docker-compose up -d
```

## Contributing
Contributions and suggestions are welcomed! Please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)