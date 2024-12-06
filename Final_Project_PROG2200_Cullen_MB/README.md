
# SafetyNet Alerts Notification System

## Overview

The **SafetyNet Alerts Notification System** is a Spring Boot application designed to send emergency notifications, such as fire, storm, and flood alerts, to both first responders and the general public. This system retrieves critical information about individuals in affected areas, such as phone numbers, addresses, medications, and allergies, to assist emergency responders in preparing for incidents effectively.

The project follows the **Model-View-Controller (MVC)** design pattern and adheres to **SOLID principles** to ensure scalability, maintainability, and ease of extension.

## Features

1. **Fire Station and Resident Data Retrieval**
   - Fetch information about fire stations and the individuals they serve based on station number or address.

2. **Child Identification in Emergency Zones**
   - Identify children residing in specific households or areas, critical for prioritizing assistance during emergencies.

3. **Emergency Contact System**
   - Provide phone numbers of residents based on fire station jurisdictions for streamlined communication.

4. **Resident Medical Data**
   - Retrieve detailed medical information about individuals in affected areas, such as medical conditions, allergies, and medications, to assist first responders.

## API Endpoints

### 1. Retrieve All Fire Stations
- **URL**: `http://localhost:8080/api/firestations`
- **Output**: Complete list of fire stations, including station numbers and addresses.

### 2. Retrieve Fire Station by Station Number
- **URL**: `http://localhost:8080/api/firestations/station/1`
- **Output**: Details about fire station 1, including associated addresses.

### 3. Retrieve Residents Served by Fire Station
- **URL**: `http://localhost:8080/api/firestations/firestation?stationNumber=1`
- **Output**: List of individuals served by fire station 1, including names, addresses, phone numbers, and a summary of adults and children.

### 4. Retrieve Flood Information by Fire Stations
- **URL**: `http://localhost:8080/api/flood/stations?stations=1,2,3`
- **Output**: Household data grouped by fire stations, including names, phone numbers, ages, and medical details.

### 5. Retrieve Individual Information by Name
- **URL**: `http://localhost:8080/api/persons/personInfo?firstName=John&lastName=Doe`
- **Output**: Details for individuals named John Doe, including address, age, email address, and medical data.

### 6. Retrieve Community Emails by City
- **URL**: `http://localhost:8080/api/persons/communityEmail?city=Springfield`
- **Output**: Email addresses for all residents of Springfield, useful for city-wide notifications.

### 7. Retrieve Fire Station Details by Address
- **URL**: `http://localhost:8080/api/firestations/byAddress?address=456%20Elm%20St`
- **Output**: Fire station details for the address `456 Elm St`, including resident information such as names and medical details.

## SOLID Principles

| **SOLID Principle**                | **Description**                                                                                       | **Implementation in the Project**                                                                                      |
|------------------------------------|-------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| **Single Responsibility Principle** | A class should have one specific responsibility.                                                      | The `PersonService` class has distinct methods for specific tasks like fetching or saving data to ensure clarity.       |
| **Open/Closed Principle**           | Software components should be open for extension but closed for modification.                         | Interfaces and inheritance enable extending functionality, such as in `FireStationService` and `PersonService`.         |
| **Liskov Substitution Principle**   | Objects of a superclass should be replaceable by objects of its subclass without affecting correctness.| Flexible inheritance allows easy extension, such as adding new fire station types or residents without breaking logic.   |
| **Interface Segregation Principle** | Clients should not depend on interfaces they do not use.                                              | Interfaces were broken into smaller, purpose-specific ones for repositories like fire station and person data.          |
| **Dependency Inversion Principle**  | High-level modules should depend on abstractions rather than low-level implementations.               | Dependency injection was implemented using Spring’s `@Autowired` to rely on abstractions instead of specific classes.   |

## Testing

Testing was conducted using **Postman** to verify the functionality of each API endpoint. All tests successfully demonstrated the system's ability to meet project requirements.

## Conclusion

The **SafetyNet Alerts Notification System** effectively provides timely and detailed emergency notifications. By integrating key functionalities such as resident data retrieval, medical information access, and community-wide communication tools, the application enhances emergency preparedness for both responders and the public. The use of design principles ensures that the system is scalable, maintainable, and adaptable to future needs.
