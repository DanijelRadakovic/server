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

Building container images can also be achieved using docker compose:
```shell
docker compose --env-file env.conf build
```

Generate docker compose configuration with specified environment variables:
```shell
docker compose --env-file env.conf config
```

Provision the infrastructure:
```shell
docker compose --env-file env.conf up
```

Destroy the provisioned infrastructure:
```shell
docker compose --env-file env.conf down -v
```

## Heroku Deployment

Add heroku email and API key to deployment/env.conf file

Build servers container image and push to your container registry (e.g. DockerHub)
```shell
docker compose --env-file env.conf build servers
docker push danijelradakovic/servers:0.3.2
```

Create terraform backend on Heroku Postgres:
```shell
pushd deployment
docker compose up init
popd
```

Provision the infrastructure on Heroku cloud:
```shell
pushd deployment
docker compose up deploy
popd
```

When you first time deploy the infrastructure the following error will occur:
```text
Error: Provider produced inconsistent final plan
 
When expanding the plan for heroku_build.gateway to include new values
learned so far during apply, provider "registry.terraform.io/heroku/heroku"
produced an invalid new value for .local_checksum: was
cty.StringVal("SHA256:91b4da2f8ac05745d4dc15131e1aa4b62914ee621c07191cc90eff537dd1b872"),
but now
cty.StringVal("SHA256:1db6fde1dad41199134e78388eebc2c427ef479d19cd78de6d993490886c5dfb"). 
deThis is a bug in the provider, which should be reported in the provider's
own issue tracker.
```

This error happens because deployment/terraform/gateway/heroku.yml file is generated dynamically
and [that change the content hash](https://registry.terraform.io/providers/heroku/heroku/latest/docs/resources/build#local-source) of /deployment/terraform/gateway directory.

Just rerun the deployment and error should be resolved:
```shell
pushd deployment
docker compose up deploy
popd
```

In order to destroy the provisioned infrastructure run following command:
```shell
pushd deployment
docker compose up destroy
popd
```

In order to destroy terraform backend provision on Heroku Postgres run following command:
```shell
pushd deployment
docker compose up cleanup
popd
```