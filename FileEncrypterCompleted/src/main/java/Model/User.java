package Model;

import java.nio.file.FileSystemAlreadyExistsException;

public class User {

   private String Name;
   private String Email;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String Name, String Email){
       this.Name=Name;
       this.Email= Email;

   }
}
