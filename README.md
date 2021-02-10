# Vanilla XP

## Introduction

Vanilla XP is an application which tracks RSC Vanilla player hiscore total XP changes over 1, 2, 3, 7, 14, 30 days. RSC Vanilla is a Runescape Classic dedicated private server. This application will provide indication about player activity in the server by crawling data every day from the hiscore and calculating the difference from previous day(s) and displaying the results.

## Overview
The root directory contains server side (server) and client side (client) projects.
* Server side is developed in Spring Boot framework to provide API endpoints and hiscore data crawling scheduled task. It stores data in PostgresSQL database.
* Client side is developed in Vue 3 which consumes server side API endpoints and displays XP changes per player in result table.

The project is dockerized and there's `docker-compose.yml` file which contains 3 services.
* web -  Spring Boot project including the client side code.
* db - PostgresSQL database which will provide data store to web.
* proxy - nginx reverse proxy which will forward requests to web.

Gradle is used as the automation tool.

## Getting started

### Run release application
To run project locally in docker you need to create release (dist folder). The release will contain necessary folder structure and configuration files to execute `docker-compose.yml`. Before invoking the `docker-compose up` you may modify configurations, but in order to run the application as it is, the extra configuration isn't necessary. In the dist folder there are 3 configuration files.
- dist/app/config/application.yml - App main settings
- dist/app/config/logback.xml - App logger settings
- dist/proxy/config/nginx.config - Proxy settings

#### Steps
1. Create release folder to `./tasks/out/dist`
   -- Run gradle task `./gradlew release` or execute `./tasks/release.sh` script which will invoke the gradle task.
2. Change directory to `./tasks/out/dist`.
3. Invoke `docker-compose up`.


## TODO
- [S] Add exception handling in case of API endpoint errors or external hiscore unreachable
- [S] Fix SystemTimeContext RequestTime and TempTime collision when API request is made while syncro is executing.
- [S] Remove thymleaf dependecy.
- [S] Fix [warn](https://stackoverflow.com/questions/30549489/what-is-this-spring-jpa-open-in-view-true-property-in-spring-boot)
- [S] Add packages into modules.
- [S] Move bean definitions out from Application.java
- [C] Add exception handling with notifications for ajax request.
- [C] Add transitions to improve UX.
- [C] Refaktor code to use provide/inject dependencies.
- [T] Add section `Getting Started -> Develop locally` section to README.MD 