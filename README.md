# Servers

Application that manages servers. Data are stored in relational database POSTGRES 13.

Dockerfile contains two runtime stages: 
- **appServerRuntime**  - Spring Boot application server that provides REST API.
- **appWebServerRuntime** - Spring Boot application that provides REST API and contains Angular front-end [web application](https://github.com/DanijelRadakovic/Servers-Front)

[BuildKit](https://github.com/moby/buildkit) is used for building container images.

In order to build **appServerRuntime** image run the following command (Dockerfile has to be in current working direcotry and allowed values for stage are dev, test, prod):

```shell
DOCKER_BUILDKIT=1 docker build --target appServerRuntime --build-args STAGE=dev -t danijelradakovic/servers:0.2.0 .
```

In order to build **appWebServerRuntime** image run the following command (Dockerfile has to be in current working direcotry and allowed values for stage are dev, test, prod):
```shell
DOCKER_BUILDKIT=1 docker build --target appWebServerRuntime --build-args STAGE=dev -t danijelradakovic/servers:0.2.1 .
```

Building container images can also be achieved using docker compose.
```shell
docker compose --env-file env.conf build
```

Generate docker compose configuration with specified environment variables
```shell
docker compose --env-file env.conf config
```

Provision the infrastructure
```shell
docker compose --env-file env.conf up
```

Destroy the provisioned infrastructure
```shell
docker compose --env-file env.conf down -v
```