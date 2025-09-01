# 🛰️ Satellite Path Prediction

A Java-based application to predict and visualize satellite orbits using TLE data.  
Built with **Spring Boot**, **PostgreSQL**, and **Predict4Java**, this project provides:

- Orbit propagation from TLE data  
- Current satellite position (latitude, longitude, altitude, azimuth, elevation)  
- Ground footprint calculation and WKT geometry storage  
- REST API for retrieving satellite positions  
- Database integration with upsert logic for efficient updates  

## 🚀 Tech Stack
- Java 21, Spring Boot  
- PostgreSQL + JPA/Hibernate  
- Predict4Java (orbit propagation)  
- REST API  

## 📂 Features
- Parse TLE data and extract NORAD ID  
- Link satellites with metadata (country, operator, category)  
- Store/update current position details  
- Provide API for real-time satellite tracking  

## 🛠️ Usage
```bash
git clone https://github.com/yourusername/satellite-path-prediction.git
cd satellite-path-prediction
mvn spring-boot:run
