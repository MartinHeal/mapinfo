# mapinfo
REST API for map locations, tracks and areas.


## REST API's

### Locations

| Task           | Http Request Type | Path            | Request Parameters | Request Body |
| -------------- | ----------------- | --------------- | ------------------ | ------------ |
| Create         | POST              | /locations      | None               | Required     |
| Read One       | GET               | /locations/{id} | None               | None         |
| Read All       | GET               | /locations      | None               | None         |
| Update Full    | PUT               | /locations/{id} | None               | Required     |
| Update Partial | PATCH             | /locations/{id} | None               | Required     |
| Delete         | DELETE            | /locations/{id} | None               | None         |

#### Example request body - for Create & Update Full:
```
{
    "name": "Location name",
    "description": "Location description",
    "point": {
        "latitude": -37.123456789,
        "longitude": 144.123456789
    }
}
```

#### Example request body - Update Partial - Name:
```
{
    "name": "Updated location name"
}
```

#### Example request body - Update Partial - Name:
```
{
    "description": "Updated location description"
}
```


### Tracks

| Task           | Http Request Type | Path         | Request Parameters | Request Body |
| -------------- | ----------------- | ------------ | ------------------ | ------------ |
| Create         | POST              | /tracks      | None               | Required     |
| Read One       | GET               | /tracks/{id} | None               | None         |
| Read All       | GET               | /tracks      | None               | None         |
| Update Full    | PUT               | /tracks/{id} | None               | Required     |
| Update Partial | PATCH             | /tracks/{id} | None               | Required     |
| Delete         | DELETE            | /tracks/{id} | None               | None         |

#### Example request body - for Create & Update Full:
```
{
    "name": "Track name",
    "description": "Track description",
    "point": [
        {
            "latitude": -37.123456789,
            "longitude": 144.123456789
        },
        {
            "latitude": -37.123456788,
            "longitude": 144.123456788
        },
        {
            "latitude": -37.123456787,
            "longitude": 144.123456787
        }
    ]
}
```

#### Example request body - Update Partial - Name:
```
{
    "name": "Updated track name"
}
```

#### Example request body - Update Partial - Name:
```
{
    "description": "Updated track description"
}
```


### Areas

| Task           | Http Request Type | Path        | Request Parameters | Request Body |
| -------------- | ----------------- | ----------- | ------------------ | ------------ |
| Create         | POST              | /areas      | None               | Required     |
| Read One       | GET               | /areas/{id} | None               | None         |
| Read All       | GET               | /areas      | None               | None         |
| Update Full    | PUT               | /areas/{id} | None               | Required     |
| Update Partial | PATCH             | /areas/{id} | None               | Required     |
| Delete         | DELETE            | /areas/{id} | None               | None         |

#### Example request body - for Create & Update Full:
```
{
    "name": "Area name",
    "description": "Area description",
    "point": [
        {
            "latitude": -37.123456789,
            "longitude": 144.123456789
        },
        {
            "latitude": -37.123456789,
            "longitude": 144.123456788
        },
        {
            "latitude": -37.123456788,
            "longitude": 144.123456788
        },
        {
            "latitude": -37.123456788,
            "longitude": 144.123456789
        }
    ]
}
```

#### Example request body - Update Partial - Name:
```
{
    "name": "Updated area name"
}
```

#### Example request body - Update Partial - Name:
```
{
    "description": "Updated area description"
}
```

## Developer Notes

### Tech stack
- Java 21
- Spring Boot 3.5
- Postgres 17.5

### Postgres Database
The database is used to store the location, track and area data.

Integration tests use an in-memory H2 database.

Commands to start Postgres in Docker - from the project root directory:

    cd docker/postgres/
    sudo docker compose up


