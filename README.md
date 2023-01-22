# Safe application for storing notes

## Running the application

### Prerequisites

-   docker
-   docker compose
-   make

Start

```shell
make start
```

Frontend will start on `https://localhost:3000` (ssl certificate is self signed so you will get a flashy warning) and backend on `http://localhost:8080`

Stop

```shell
make stop
```

Remove docker images created by compose

```shell
make clean
```

You can also chain the commands, for example

```
make stop clean
make clean start
etc.
```

## API docs

You can access swagger ui at

```
http://localhost:8080/swagger-ui.html
```

Or just the raw json OpenAPI definition at

```
http://localhost:8080/v3/api-docs
```
