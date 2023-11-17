# hstflly

---

## Candidate Notes

- Project Structure is following concepts of Clean Architecture
- Implemented a very simple `User` authentication and `Property` management to give more context to the project around `Booking`
  - HOST users and GUEST users have different permissions in different endpoints
  - Users don't have access to other users resources(properties or bookings), only the users owning or participating can manage the resource
  - Bearer `Authorization` header with the JWT token received from the login endpoint required for protected endpoints
- DB 
  - In memory volatile DB (H2)
  - Schemas auto generated
  - Console available at http://localhost:8080/h2-console
    - configure it with the following properties set in `src/main/resources/application-properties` :
      - JDBC URL: `jdbc:h2:mem:volatile_db` (spring.datasource.url)
      - User Name: `user` (spring.datasource.username)
      - Password: `pass` (spring.datasource.password)  
- Using JPA Optimistic Locking `Version` to take care of race conditions in booking transactions 
- Postman collection attached in the `/assets/postman` folder containing all endpoints configured, alongside request examples

### Potential improvements

- Unit/E2E testing
- Inject environment variables instead of static config

---

## Running the Application with Docker

- Ensure you have Docker installed
- Ensure you have the application port available(8080)
- Run the makefile helper command, which will build the docker image and run it:
```
  $ make run
```
  - Alternatively, run the docker commands manually:
```
  $ docker build --tag hstflly --file Dockerfile .
  $ docker run --publish 8080:8080 --name hstflly hstflly
```


After building and running, the service will be available at http://localhost:8080

---

## Original description

```
Java Technical Test
We invite you to our technical test. This allows us to verify your skills with the languages we use in our
technical stack.
Do not spend more than 4 hours on this test. Please carefully read over the sections below to see exactly
what we are looking for.
Please create a RESTful webservice with any framework you are familiar with.
Terminology
A booking is when a guest selects a start and end date and submits a reservation on a property.
A block is when the property owner or manager selects a range of days during which no guest can make
a booking (e.g. the owner wants to use the property for themselves, or the property manager needs to
schedule the repainting of a few rooms).
Backend
Java (11 or newer)
The REST API should allow users to:
●

Create a booking

●

Update booking dates and guest details

●

Cancel a booking

●

Rebook a canceled booking

●

Delete a booking from the system

●

Get a booking

●

Create, update and delete a block

Implement proper validation to ensure data integrity. Provide logic to prevent bookings from overlapping
(in terms of dates and property) with non-canceled bookings or blocks.
Database
We recommend use of in-memory volatile DB.
Instructions
When you are done, upload your project to a public Github repository and email us a link.

Please have this technical test completed within 1 week. If you have any questions related to this
technical test, let us know.
Thank you for taking the time to complete this test and best of luck!


```
