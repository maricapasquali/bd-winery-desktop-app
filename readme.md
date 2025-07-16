# Desktop application (Java) for management of a winery

This is the final project for the "Basi di dati" course.

The final report is located in the folder "_Relazione_": [RelazioneBasiDati.pdf](/Relazione/RelazioneBasiDati.pdf).

# Installation

1. Clone the repository:
```bash 
  git clone https://github.com/maricapasquali/bd-winery-desktop-app
```
2. Navigate to the project directory:
```bash
  cd bd-winery-desktop-app
```
3. Build the project
```bash
  # Only Windows users
  ./gradlew clean createExe 
```

```bash
  # All other users
  ./gradlew clean jar 
```

# Run

Just run the `WineryDesktopApp-1.0.1.exe` file in the ``build/launch4j`` directory (only Windows System), 
or you may need to run it via command line `java -jar build/libs/WineryDesktopApp-1.0.1.jar`, for all System.

# Database

The default database is now PostgreSQL (for details, see [config.properties](./src/main/resources/config.properties)).
This database requires a username and password.

Create a `.env` file in the root directory with the following properties:
- `POSTGRES_HOST` (e.g., `POSTGRES_HOST=localhost:5432`)
- `POSTGRES_USER` (e.g., `POSTGRES_USER=postgres`)
- `POSTGRES_PASSWORD` (e.g., `POSTGRES_PASSWORD=postgres`)

# Usage
## Some credentials

| role      | username        | password |
------------|-----------------|----------|
| Admin     | marica.pasquali | ciao1234 |  
| Employee  | mario.rossi     | ciao     |  
| Part-Time | filippo.bianchi | ciao5678 |  
