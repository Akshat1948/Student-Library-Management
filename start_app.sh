#!/bin/bash

# Kill any existing processes on the backend port (8080)
echo "Stopping any existing library servers..."
lsof -ti:8080 | xargs kill -9 2>/dev/null

# Start the Spring Boot Backend in the background
echo "Starting Backend (Spring Boot)..."
./mvnw spring-boot:run &

# Wait for a few seconds for the backend to initialize
sleep 5

# Start the React Frontend in the foreground
echo "Starting Frontend (React)..."
cd frontend
npm run dev
