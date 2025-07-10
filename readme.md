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

Just run the `WineryDesktopApp-1.0.0.exe` file in the ``build/launch4j`` directory (only Windows System), 
or you may need to run it via command line `java -jar build/libs/WineryDesktopApp-1.0.0.jar`, for all System.

When the application requires `src/main/resources/db/WineryDesktopApp.accdb` (Microsoft Access Database), provide this file as input.
The database does not require a password.

# Usage
## Some credentials

| role      | username        | password |
------------|-----------------|----------|
| Admin     | marica.pasquali | ciao1234 |  
| Employee  | mario.rossi     | ciao     |  
| Part-Time | filippo.bianchi | ciao5678 |  
