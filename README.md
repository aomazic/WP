# Aperture Science Web Shop

Simple web shop made for Web programming.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - For running the Spring Boot backend
- [Node.js and npm](https://nodejs.org/) - For running the Angular frontend

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/aomazic/WP.git
   cd WP
   ```
   
2. Setup backend:
   ```sh
   cd Backend
   ./gradlew build Backend
   ```

3. Setup FrontEnd:
   ```sh
   cd FrontEnd
   npm install
   ng serve
   ```

# Application Overview
This project is composed of two main components: a **backend service** and a **frontend application**.

## Backend
- **Technology**: Spring Boot  
- **Runs on**: `http://localhost:8080`

## Frontend
- **Technology**: Angular  
- **Runs on**: `http://localhost:4200`

## Project Structure

### Modules
1. **Backend**:  
   - The main backend service.  
   - Built with Spring Boot.

2. **Frontend**:  
   - The graphical user interface.  
   - Developed using Angular.

## License
This project is licensed under the **MIT License**.  
