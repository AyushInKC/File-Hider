package Views;

import DAO.UserDAO;
import Model.User;
import Service.OTPGenerator;
import Service.SendOTP;
import Service.UserService;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Welcome {
    public void welcomeRunner(){
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the Application");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to SignUp");
        System.out.println("Press 0 to exit");
        int choice=0;
        try{
choice=Integer.parseInt(br.readLine());
        }
        catch (IOException ex){
   ex.printStackTrace();
        }

        switch (choice){
            case 1-> login();
            case 2-> SignUp();
            case 0-> System.exit(0);
        }
    }

    private void login(){
         Scanner sc=new Scanner(System.in);
        System.out.println("Enter Email:- ");
         String Email=sc.nextLine();
         try{
             if(UserDAO.isExists(Email)){
                 String OTP= OTPGenerator.getOTP();
                 SendOTP.sendOTP(Email,OTP);
                 System.out.println("Enter the OTP:- ");
                 String otp=sc.nextLine();
                 if(otp.equals(OTP)){
                     new UserView(Email).home();

                 }
                 else{
                     System.out.println("Wrong OTP");
                 }
             }
             else{
                 System.out.println("User Not Found");
             }
         }
         catch(SQLException ex){
             ex.printStackTrace();
         }
    }
    private void SignUp(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Name:- ");
        String name=sc.nextLine();
        System.out.println("Enter Email:- ");
        String email=sc.nextLine();
        String OTP= OTPGenerator.getOTP();
        SendOTP.sendOTP(email,OTP);
        System.out.println("Enter the OTP:- ");
        String otp=sc.nextLine();
        if(otp.equals(OTP)){
            User user=new User(name,email);
            int response= UserService.saveUser(user);

            switch (response){
                case 0-> System.out.println("User Registered");
                case 1-> System.out.println("User Already Existed");
            }
        }
        else{
            System.out.println("Wrong OTP");
        }
    }
    public static void main(String[] args) {

    }
}
