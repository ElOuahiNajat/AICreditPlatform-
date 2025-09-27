CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         nom VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         telephone VARCHAR(20),
                         date_naissance DATE,
                         adresse VARCHAR(255)
);