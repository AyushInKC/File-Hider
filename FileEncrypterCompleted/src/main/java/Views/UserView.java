package Views;

import DAO.dataDao;
import Model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email=email;
    }
    public void home(){
         do{
             System.out.println("Welcome:- "+this.email);
             System.out.println("Press 1 to Show Hidden Files");
             System.out.println("Press 2 to Hide a new File");
             System.out.println("Press 3 to Un-hide a File");
             System.out.println("Press 0 to Exit");
             Scanner sc=new Scanner(System.in);
             int ch=Integer.parseInt(sc.nextLine());
                switch (ch){
                    case 1-> {
                        try {
                            List<Data> files= dataDao.getAllFiles(this.email);
                            System.out.println("ID - File");
                            for(Data file:files){
                                System.out.println(file.getId()+" - "+file.getFileName());
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 2-> {
                        System.out.println("Enter the File Path:-");
                        String path=sc.nextLine();
                        File f=new File(path);
                        Data file=new Data(0,f.getName(),path,this.email);
                        try {
                            dataDao.hideFiles(file);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 3-> {
                        List<Data> files = null;
                        try {
                            files = dataDao.getAllFiles(this.email);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("ID - File");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("Enter the Id of the file to Un-hide");
                        int id=Integer.parseInt(sc.nextLine());
                        boolean isValidId=false;
                        for(Data file:files){
                            if(file.getId()==id){
                                isValidId=true;
                                break;
                            }
                        }
                        if(isValidId){
                            try {
                                dataDao.unHide(id);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else{
                            System.out.println("Wrong Id");
                        }
                    }
                    case 0-> {
                        System.exit(0);
                    }
                }
         }while(true);
    }
}
