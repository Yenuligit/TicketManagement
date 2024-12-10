# 🎟️ Real-Time Ticket Management System

A real-time event ticketing system that handles concurrent ticket releases (by vendors) and purchases (by customers) using the **Producer-Consumer pattern**. This system features live updates via **WebSockets** and a **React + Spring Boot** architecture.

---

## 📚 **Table of Contents**
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Installation](#-installation)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)
- [WebSocket Channels](#-websocket-channels)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🚀 **Features**
- **Real-time updates** of ticket availability, tickets added, and tickets bought using **WebSockets**.
- **Multi-threaded Producer-Consumer Pattern** for managing concurrent ticket releases and purchases.
- **Interactive Control Panel** for setting system parameters like ticket count, release rate, and customer retrieval rate.
- **Modern Tech Stack**: **React** (frontend) + **Spring Boot** (backend) + **MySQL** (database).

---

## 💻 **Tech Stack**
- **Frontend**: React, HTML/CSS, Axios, STOMP.js (WebSockets)
- **Backend**: Spring Boot (Java), WebSocket, SimpMessagingTemplate
- **Database**: MySQL (for persistence)
- **Concurrency**: Java Threads, Producer-Consumer pattern

### 📸 **Screenshots**
 **1. Control Panel**
**Control the ticketing system with parameters for total tickets, release rate, and retrieval rate.  
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const ControlPanel = () => {
  const [totalTickets, setTotalTickets] = useState('');
  const [ticketReleaseRate, setTicketReleaseRate] = useState('');
  const [customerRetrievalRate, setCustomerRetrievalRate] = useState('');
  const [maxTicketCapacity, setMaxTicketCapacity] = useState('');
  const [simulationRunning, setSimulationRunning] = useState(false);
  const [ticketAvailability, setTicketAvailability] = useState(0);
  const [ticketActions, setTicketActions] = useState({ added: 0, bought: 0 });
  const [errorMessage, setErrorMessage] = useState('');
  const [stompClient, setStompClient] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const validValue = Math.max(0, parseInt(value, 10) || 0);
    if (name === 'totalTickets') setTotalTickets(validValue);
    if (name === 'ticketReleaseRate') setTicketReleaseRate(validValue);
    if (name === 'customerRetrievalRate') setCustomerRetrievalRate(validValue);
    if (name === 'maxTicketCapacity') setMaxTicketCapacity(validValue);
  };

  const startSimulation = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/simulation/start", {
        totalTickets, 
        ticketReleaseRate, 
        customerRetrievalRate, 
        maxTicketCapacity,
      });
      setSimulationRunning(true);
      setErrorMessage('');
      startWebSocket();
    } catch (error) {
      setErrorMessage("Error starting simulation");
      console.error("Error starting simulation", error);
    }
  };

  const startWebSocket = () => {
    const socket = new SockJS('http://localhost:8080/websocket');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log("WebSocket connected!");
        
        client.subscribe('/topic/ticketAvailability', (message) => {
          const availableTickets = parseInt(message.body);
          setTicketAvailability(availableTickets);
        });

        client.subscribe('/topic/ticketActions', (message) => {
          const data = JSON.parse(message.body);
          setTicketActions(data);
        });
      },
      onDisconnect: () => console.log("WebSocket disconnected"),
    });

    client.activate();
    setStompClient(client);
  };

  return (
    <div className="control-panel">
      <h1>🎟 Ticket Management Simulation</h1>
      <div className="parameters-section">
        <div className="input-group">
          <label htmlFor="totalTickets">Total Tickets</label>
          <input 
            type="number" 
            name="totalTickets" 
            id="totalTickets"
            value={totalTickets} 
            onChange={handleChange} 
            placeholder="Enter total tickets" 
          />
        </div>

        <div className="input-group">
          <label htmlFor="ticketReleaseRate">Ticket Release Rate (sec)</label>
          <input 
            type="number" 
            name="ticketReleaseRate" 
            id="ticketReleaseRate"
            value={ticketReleaseRate} 
            onChange={handleChange} 
            placeholder="Enter ticket release rate" 
          />
        </div>

        <div className="input-group">
          <label htmlFor="customerRetrievalRate">Customer Retrieval Rate (sec)</label>
          <input 
            type="number" 
            name="customerRetrievalRate" 
            id="customerRetrievalRate"
            value={customerRetrievalRate} 
            onChange={handleChange} 
            placeholder="Enter customer retrieval rate" 
          />
        </div>

        <div className="input-group">
          <label htmlFor="maxTicketCapacity">Max Ticket Capacity</label>
          <input 
            type="number" 
            name="maxTicketCapacity" 
            id="maxTicketCapacity"
            value={maxTicketCapacity} 
            onChange={handleChange} 
            placeholder="Enter max ticket capacity" 
          />
        </div>
      </div>

      <button onClick={startSimulation} disabled={simulationRunning}>Start Simulation</button>
      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <h3>🎯 Ticket Availability: {ticketAvailability}</h3>
      <p>🎫 Tickets Added: {ticketActions.added}</p>
      <p>🛒 Tickets Bought: {ticketActions.bought}</p>
    </div>
  );
};

export default ControlPanel;**

### **2. Real-Time Updates**
See live updates of **ticket availability**, **tickets added**, and **tickets bought**.  
![image](https://github.com/user-attachments/assets/c12d2733-9ffc-4d66-ae86-8ca58913ba86)


## 🚀 **Live Demo**


## 📚 **How It Works**
1. **Set Parameters**: Enter total tickets, release rate, and customer rate.  
2. **Start Simulation**: See tickets released by the vendor and bought by customers in real time.  
3. **Stop Simulation**: Stop the process anytime and view final ticket counts.

## 🚀 **Run Locally**
  Prerequisites
Java 21+
Node.js 16+
MySQL 8.0+
Maven 3.8+
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/real-time-ticket-system.git


## 📁 **Project Structure**
📂 TicketManagementSystem/
├── 📁 src/
│   ├── 📁 main/
│   │    ├── 📁 java/com/example/ticketmanagementsystem/
│   │    │    ├── 📂 Config/           # WebSocket configuration
│   │    │    ├── 📂 Controller/      # REST controllers
│   │    │    ├── 📂 Model/           # Ticket and TicketPool entities
│   │    │    ├── 📂 Repository/      # JPA repositories
│   │    │    ├── 📂 Service/         # Business logic for simulation
│   │    │    └── 📄 Application.java  # Main entry point
│   │    └── 📁 resources/
│   │         └── 📄 application.properties # Database and WebSocket configuration
│   └── 📁 test/                      # Unit and integration tests
├── 📂 frontend/
│   └── 📁 src/
│       ├── 📂 components/           # React components
│       └── 📄 index.js              # Main entry point for React app
└── 📄 README.md                     # Project instructions


## 🔧 **Installation**

### 1️⃣ **Back-End Setup (Spring Boot)**
1. **Install Prerequisites**:  
   - Java **21** or higher  
   - MySQL **8.0**  
   - Maven **3.8+**

2. **Configure the Database**:
   Update the `application.properties` file in `src/main/resources/`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ticketing_db
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
3.**Run the Application**:
  mvn clean install
  mvn spring-boot:run

### **Front-End Setup (React)**
1.Install Node.js (v16 or higher)
2.Install dependencies:
  cd frontend
  npm install
3.Run the React App:
  npm start

### **Usage**
Set Parameters:

Total Tickets: Number of tickets to be released.
Ticket Release Rate: How often tickets are released (in seconds).
Customer Retrieval Rate: How often customers purchase tickets (in seconds).
Max Ticket Capacity: Maximum number of tickets in the pool at any time.
Start Simulation: Click the "Start Simulation" button.

Watch Real-Time Updates: View live updates of tickets added, tickets bought, and current availability.

Stop Simulation: Click "Stop Simulation" to stop.


### ** API Endpoints**
Method	Endpoint	Description
POST	/api/simulation/start	Starts the ticket simulation.
POST	/api/simulation/stop	Stops the running simulation.
GET	/api/simulation/tickets/available-count	Returns the number of available tickets.
GET	/api/simulation/tickets/actions	Returns ticket actions (added, bought).

### ** WebSocket Channels **
Channel	Description
/topic/ticketAvailability	Shows live updates on ticket availability.
/topic/ticketActions	Shows live updates on tickets added and bought.

📈 Project Goals
Simulate real-world ticketing systems for concerts, events, and bookings.
Implement the Producer-Consumer Pattern for concurrency.
Use WebSockets for real-time data updates on the user interface.

📝 License
This project is licensed under the MIT License.

❤️ Made with love by Yenuli Premawardana.
