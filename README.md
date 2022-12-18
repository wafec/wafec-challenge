# Code Challenge

## Overview 
### Documentation

I have created the `runbooks` and `architecture` folders for this project.

#### Runbooks

This folder is for placing guidelines and troubleshooting for this service.

#### Architecture Decisions

The Architecture Decision Records is for create a record for every decision taken that changes the architecture of this service.

### Java Modules

I did split the code in the following modules:
- API
- App
- External

#### API

This module is meant for exposing the contract to other services.

#### App

This module is the current implementation of the contracts of this service.

#### External

This module contains the interfaces and implementation of external services. 

Ideally, we should have it implemented in a shared library so that many other services could also use.

### Database

I have created a schema file which contains the initial setup of our database.

Migrations should be placed in this folder also.

### Infrastructure

In this folder, I have stored initial Terraform scripts to create an infrastructure in AWS with:
- RDS set up
- Auto Scaling
- High Availability with multiple AZs
- A launch script to install this service every time the ASG needs more instances

### Docker

The Dockerfile in this project is still just for local test. This service so far is using EC2 instances.

Maybe, in the future, we might migrate this service to ECS or Fargate.

Docker Compose will include LocalStack.

## Code Challenge Questions

### How this Challenge Was Approached

### What I Could Implement in 6 Hours of Work

Team and User services are external services. They have their own database.
The master data of Users and Teams is not owned by this Roles service.

I have added the client interface to be implemented further. 
So first I started working with the concept of working with abstractions and not with concretions.
This helped me to use Dependency Injection more easily.

At controllers level, I injected the DAOs and the REST clients (the abstractions).
At DAOs level, I injected the repositories from Spring JPA.

To map between one level to another, or one domain to another, I have used `mapstruct` which is very handy.

Also, to reduce the amount of boilerplate code I preferred `lombok`.
With lombok, I could use the Builder pattern and the POJO classes for transfer.

All tests are unit so far, however in the future we could create acceptance tests with JGiven and TestContainers, as we already have some initial Dockerfile scripts in this repo.
Also, docker-compose should be elaborated to have LocalStack from AWS, as we are using AWS infrastructure in this project.

There is not too much validations at API level, because either:
- We fail on invalid parameters
- Database schema violation

#### What I Left Over for Future Work

I chose to left it over for future increments:
- A more flexible and complete Dockerfile
- A docker-compose file for this service
  - With an Application Load Balancer like Envoy
  - LocalStack for testing
  - MySQL for development purposes
- The implementation of the clients covering data and exceptions
- The properties file
- The properties management by different environments
- An initial runbook file
- Acceptance tests

### How to Run the Code

This code can be run by creating a properties file with the configurations of the Hibernate database which might be:
- Local in the developer machine
- For some staging environment
- For prod environment

Also, the client libraries are not included in this project. I did not implement the client libraries as I was running out of time for this project.

### Suggestion for Improvement in the Team or User Services

As I have to call the Team service whatever someone calls to assign a role to a membership,
an improvement might be to decouple these services.

Team service or User service should be using SNS and SQS. With SNS we send events of any modification we noticed on the master data.
With SQS the events are queued, and then a free instance of the service will pick it up and process that event as soon it gets in.

Also, this project would be decoupled from the others if we just change the architecture this way. In case some change is made externally, we might create a processing task in this service to update the state of the data.