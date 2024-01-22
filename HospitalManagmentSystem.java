package HospitalManagmentSystem;

import javax.crypto.Cipher;
import java.awt.*;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagmentSystem{
    private static final String url ="jdbc:mysql://localhost:3306/hospital";
    private static final String username= "root";
    private static final String password = "123456";


    public static void main(String args[]){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);

            while(true){
                System.out.println("HOSPITAL MANAGMENT SYSTEM");
                System.out.println("1 : Add Patient");
                System.out.println("2 : View Patient");
                System.out.println("3 : View Doctor");
                System.out.println("4 : Book Appointment");
                System.out.println("5 : EXIT");
                System.out.println("Enter your choice : ");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        patient.addPatient();
                        break;

                    case 2:
                        //viewPatient
                        patient.viewPatients();
                        break;

                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;

                    case 4:
                        //Book Appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Enter Valid Choice");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.print("Enter patient ID : ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor ID : ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD)");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvability(doctorId, appointmentDate,connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id,doctors_id,appointment_date) VALUES (?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if(rowsAffected > 0){
                        System.out.println("Appointment booked succesfully");
                    }
                    else{
                        System.out.println("Failed to book appointment");
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Doctor not available on this date..");
            }
        }
        else{
            System.out.println("Either doctor of Patient doesn't exists!!!");
        }
    }

    public static boolean checkDoctorAvability(int doctor_id, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctors_id = ? AND appointment_date = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count == 0){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

}
