# Distributed Board Game Engine: Galaxy Trucker
### Final Project for Software Engineering | Politecnico di Milano

> **Note:** This repository is a personal fork of the original group project. It is intended for portfolio purposes to highlight my specific contributions and the technical architectural choices made during development. The original team repository can be found at: https://github.com/anto11preve/ing-sw-2025-Dumitrache-Prevedello-Puddu-Zlatkovic-

## Overview
This project is a digital implementation of the board game **Galaxy Trucker**. Developed as the final requirement for the Software Engineering course, the system is a distributed application that supports multiple concurrent games over a network. The architecture is centered around a robust Model-View-Controller (MVC) pattern designed to handle a high degree of game state complexity.

## Technical Architecture and Challenges
The implementation of Galaxy Trucker required managing an extensive ruleset and ensuring a smooth distributed experience.

* **Complex Model Architecture:** I led the design of the Model layer, which had to account for a vast number of components and rules. The architecture supports both "Base Flight" and "Test Flight" modes, requiring a highly scalable and detailed representation of the game world.
* **State Pattern for Non-Blocking Gameplay:** We implemented a sophisticated State Pattern to manage game phases. This was crucial not only for logic separation but also to ensure that the game remained responsive, preventing players from being unnecessarily blocked while waiting for others' actions.
* **Hybrid Networking:** The system was developed to support communication via both standard **TCP Sockets** and **Java RMI** (Remote Method Invocation), using object serialization to transfer complex state updates between Server and Clients.
* **Distributed State Synchronization:** A significant challenge involved efficiently updating the massive game model across all connected clients to maintain a synchronized "source of truth" throughout the match.
* **Multi-Lobby Support:** The server handles multiple independent game instances simultaneously, utilizing Java multi-threading and synchronization primitives for thread-safe session management.
* **User Interface:** The primary interaction method is a comprehensive **CLI (Command Line Interface)** designed for stable and low-latency gameplay.

## My Specific Contributions
In this project, I acted as both a technical lead and a coordinator:

* **Lead Software Architect (Model & Controller):** I designed the core game logic and the controller layer. I was responsible for translating the rule-heavy board game mechanics into a functional software architecture, focusing on state consistency in a distributed environment.
* **Project Management & Coordination:** I took on the role of de facto Project Manager, organizing the development workflow and ensuring the integration of different modules. I managed the team's progress to meet the project's strict deadlines and technical requirements.
* **Core Implementation:** I implemented the most critical parts of the Model and Controller, ensuring that the complex rules of Galaxy Trucker were correctly and efficiently coded.

## Software Quality & Testing
* **Automated Testing:** Core game logic and state transitions were verified using the **JUnit** framework to ensure reliability and prevent regressions during the integration of new features.

## How to Run the Game

### Prerequisites
* Java 11 or higher
* Maven

### 1. Build the Project
Open a terminal in the root directory and run:
```bash
mvn clean package
```
This will generate the executable JAR files in the `target` folder.

### 2. Start the Server
Run the following command to launch the game server:
```bash
java -jar target/ServerApp.jar
```

### 3. Start the Client
Open a new terminal window and run the client:
```bash
java -jar target/ClientApp.jar
```
Follow the on-screen CLI instructions to connect to the server and join a lobby. Further explanations on how to start the match in the original repo at https://github.com/progetto-ingegneria-software/ing-sw-2025-Dumitrache-Prevedello-Puddu-Zlatkovic-

## Original Group Members
* Antonio Prevedello
* Alexandra Dumitrache
* Marco Puddu
* Filip Zlatkovic
