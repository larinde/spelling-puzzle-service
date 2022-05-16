# Spelling Puzzle

## 

Building and running the app:

```bash
 mvn clean package
 java  java -jar target/spelling-puzzle-service.jar 
```

This application renders a UI that calls a downstream service running on localhost port 8080.

```
 http://localhost:8080/
```

Below is a brief description of the API operations:

| Action                     | HTTP Method  | URI                      |
|:---------------------------|:------------:|:-------------------------|
| Generates a new puzzle     |  GET         | /api/puzzles             |
| Validates submitted answer |  POST        | /api/puzzles             |
