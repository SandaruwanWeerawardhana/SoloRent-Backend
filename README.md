<h1 align="center">Solo Rent Backend</h1>

## Backend and Frontend Links
- **Frontend GitHub Repository:** [Solo Rent System Frontend](https://github.com/SandaruwanWeerawardhana/SoloRent-Frontend.git)
- **Backend GitHub Repository:** [Solo Rent System Backend](https://github.com/SandaruwanWeerawardhana/SoloRent-Backend.git)

## Features

- User authentication and authorization (JWT-based)
- Admin management
- Vehicle listing and management
- Booking system for vehicles
- Payment processing
- Review and rating system
- Maintenance and massage (service) management
- RESTful API endpoints
- Docker support for containerized deployment
- Configurable via `application.yml`

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Maven
- Docker

## Folder Structure

```
Solo Rent-Backend/
│
├── docker-compose.yml         # Docker Compose configuration
├── Dockerfile                 # Dockerfile for building the backend image
├── pom.xml                    # Maven project configuration
├── qodana.sarif.json          # Qodana static analysis report
├── qodana.yaml                # Qodana configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── edu/icet/solorent/
│   │   │       ├── Main.java
│   │   │       ├── config/         # Configuration classes (security, model mapping, etc.)
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── dto/            # Data Transfer Objects
│   │   │       ├── entity/         # JPA entities
│   │   │       ├── filter/         # Security filters (e.g., JWT)
│   │   │       ├── repository/     # Spring Data repositories
│   │   │       ├── service/        # Business logic services
│   │   │       └── util/           # Utility classes
│   │   └── resources/
│   │       └── application.yml     # Application configuration
│   └── test/
│       └── java/                   # Test sources
├── target/                        # Build output (generated)
└── ...
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- Docker (optional, for containerized deployment)

### Build and Run

1. **Clone the repository:**
   ```sh
   git clone <repository-url>
   cd Solo Rent-Backend
   ```
2. **Build the project:**
   ```sh
   mvn clean install
   ```
3. **Run with Maven:**
   ```sh
   mvn spring-boot:run
   ```
4. **Or run with Docker:**
   ```sh
   docker build -t solo-rent-backend .
   docker run -p 8080:8080 solo-rent-backend
   ```

### Configuration
- Edit `src/main/resources/application.yml` to configure database, server port, and other properties.

## API Documentation
- The API endpoints are organized under `/api/` routes. For detailed API documentation, refer to the controller classes in `src/main/java/edu/icet/solorent/controller/`.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.

