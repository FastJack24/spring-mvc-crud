# CRUD Spring MVC Web App
This app has one entity (model) Person with the corresponding controller. Endpoints:
1) GET URL:/people (We get all people (READ))
2) POST URL:/people (We create a new person (CREATE))
3) GET URL:/people/new (Form for creating)
4) GET URL:/people/:id/edit (Form for editing)
5) GET URL:/people/:id (We get a person for the corresponding ID (READ))
6) PATCH or we have PUT URL:/people/:id (We update a person for the corresponding ID (UPDATE))
7) DELETE URL:/people/:id (We delete a person for the corresponding ID (DELETE))

Tomcat 9 is used for launch (Spring 5.* on Tomcat 10.* does not run).
### Versions
1) Array (Server is not stateless)
2) PostgreSQL with JDBC Templates
3) PostgreSQL with Hibernate
