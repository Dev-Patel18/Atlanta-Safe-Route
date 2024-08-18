### README for Atlanta Safe Route Project

---

## Project Overview

The **Atlanta Safe Route Project** is a web-based application that helps users find the safest and fastest driving routes within Atlanta. The application uses the OpenRouteService API to calculate routes, considering user-defined safety levels by avoiding certain high-crime areas. The user can choose between "Fastest", "Safe", and "Very Safe" route options, and the application will provide a route that avoids specific polygons defined as dangerous areas based on the selected safety level.

## Features

- **User-friendly Interface**: A simple and interactive map where users can input their origin, destination, and preferred safety level.
- **Safety Level Options**:
  - **Fastest**: Provides the quickest route without considering safety.
  - **Safe**: Avoids high-crime areas.
  - **Very Safe**: Avoids both high-crime and moderate-crime areas.
- **Google Maps Integration**: Visual representation of routes on a Google Map.
- **Dynamic Routing**: Fetches routes dynamically from the OpenRouteService API, adjusting based on the chosen safety level.

## Tech Stack

- **Backend**: Java with OkHttp for HTTP requests.
- **Frontend**: HTML, JavaScript, Google Maps API.
- **API**: OpenRouteService for route calculations.


## Installation and Setup

### Prerequisites

- **Java 8+**
- **Apache Tomcat** or any other Java-compatible web server
- **Maven** (for dependency management)
- **Google Maps API Key**
- **OpenRouteService API Key**

### Setup Instructions

1. **Clone the Repository**

   ```sh
   git clone https://github.com/yourusername/atlanta-safe-route.git
   cd atlanta-safe-route
   ```

2. **Configure API Keys**

   - Open `RoutePlanner.java`.
   - Replace `API_KEY` with your OpenRouteService API key.
   - Open `index.html`.
   - Replace the `Google Maps API Key` with your key.

3. **Build and Deploy**

   - Use Maven to package the project:
   
     ```sh
     mvn clean package
     ```
   - Deploy the `.war` file to your web server (e.g., Apache Tomcat).

4. **Run the Application**

   - Start your web server (e.g., Apache Tomcat).
   - Access the application via `http://localhost:8080/atlanta-safe-route/`.

## Usage

1. **Open the Application** in your web browser.
2. **Enter the Origin and Destination** coordinates (latitude, longitude format).
3. **Select the Safety Level** from the dropdown menu.
4. **Click on "Get Route"** to see the suggested route on the map.

## Troubleshooting

- **API Errors**: Check your API keys for OpenRouteService and Google Maps. Ensure they are correct and have not exceeded their usage limits.
