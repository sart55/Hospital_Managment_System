create database hospital;
use hospital;

create table patients(
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     age INT NOT NULL,
     gender VARCHAR(10) NOT NULL);
  
CREATE TABLE doctors(
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     specialization VARCHAR(255) NOT NULL);
 
 CREATE TABLE appointments(
      id INT AUTO_INCREMENT PRIMARY KEY,
      patient_id INT NOT NULL,
      doctors_id INT NOT NULL,
      appointment_date DATE NOT NULL,
      FOREIGN KEY (patient_id) REFERENCES patients(id),
      FOREIGN KEY (doctors_id) REFERENCES doctors(id));
      
INSERT INTO doctors(name , specialization) VALUES ("Parakh" , "Physician");
INSERT INTO doctors(name , specialization) VALUES ("Satish Patil" , "NeuroSurgeon");

SELECT * FROM patients;
