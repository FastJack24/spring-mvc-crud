CREATE TABLE person
(
    person_id int GENERATED ALWAYS AS IDENTITY,
    person_name varchar(32) NOT NULL,
    age smallint NOT NULL,
    email text NOT NULL,
    CONSTRAINT pk_person_person_id PRIMARY KEY(person_id)
);

INSERT INTO person (person_name, age, email)
VALUES
('Andrew', 23, 'foxs.100@yahoo.com'),
('Tom', 38, 'tm@ex.org'),
('Jack', 54, 'jack@mail.com');