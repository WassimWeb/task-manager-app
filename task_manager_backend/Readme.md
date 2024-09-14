# Task Manager App Backend
This repository contains the backend service of the Task Manager App, built with Spring Boot and designed to run seamlessly within a Docker environment. By following the project setup instructions, you can quickly get the backend up and running in Docker, enabling smooth integration and functionality.

## How to Use
The backend service is containerized using Docker to simplify the development and deployment process. To get started, follow the setup steps outlined below, and you’ll have a fully functional backend running inside Docker containers.

### Prerequisites
Before getting started, ensure you have the following installed on your local machine:

    Docker
    Docker Compose
    Maven (for building the project)

### Project Setup
Follow these steps to set up and run the backend service in a Docker container:

#### Build the Project
```bash
mvn clean package -DskipTests
```

#### Running the Application with Docker
The project is configured with a docker-compose.yml file to simplify the containerization process. Docker Compose will set up both the backend service and the PostgreSQL database.

To start the services, run the following command:
```bash
docker-compose up --build
```
This command will:
- Build the Docker image for the backend service.
- Set up the PostgreSQL database in a separate container.
- Expose the backend service on port 8080 (configurable in the docker-compose.yml file).,


### Additional Notes
You can modify the Spring Boot application properties (such as the database URL, username, and password) in the application.properties file.
For production environments, ensure sensitive data like database credentials are managed securely (e.g., using environment variables).