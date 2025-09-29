CREATE DATABASE RETOHOLAMUNDO;
USE RETOHOLAMUNDO;

CREATE TABLE USUARIO (
    idU INT PRIMARY KEY,
    nom VARCHAR(50),           
    pass VARCHAR(50),          
    nombre_real VARCHAR(50),   
    apellido VARCHAR(50),      
    email VARCHAR(100),        
    direccion VARCHAR(150)    
);

INSERT INTO USUARIO (idU, nom, pass, nombre_real, apellido, email, direccion)
VALUES (1, 'Ana', 'abcd*1234', 'Ana María', 'Gómez', 'ana.gomez@gmail.com', 'Calle 123, Ciudad X');

INSERT INTO USUARIO (idU, nom, pass, nombre_real, apellido, email, direccion)
VALUES (2, 'Carlos', '1234', 'Carlos Alberto', 'Pérez', 'carlos.perez@gmail.com', 'Av. Central 456, Ciudad Y');

INSERT INTO USUARIO (idU, nom, pass, nombre_real, apellido, email, direccion)
VALUES (3, 'Lucía', 'abcd', 'Lucía Fernanda', 'Rodríguez', 'lucia.rodriguez@gmail.com', 'Colonia Norte 789, Ciudad Z');



