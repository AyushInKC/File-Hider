package DAO;

import Model.Data;
import db.MyConnection;

import javax.naming.CompositeName;
import java.io.*;
import java.sql.*;
import java.util.*;

public class dataDao {
  public static List<Data> getAllFiles(String Email) throws SQLException {
      Connection con= MyConnection.getConnection();
      PreparedStatement ps=con.prepareStatement("select * from data where email=?");
          ps.setString(1,Email);
      ResultSet rs=ps.executeQuery();
      List<Data> files=new ArrayList<>();
      while(rs.next()){
     int id=rs.getInt(1);
     String name=rs.getString(2);
     String Path=rs.getString(3);
     files.add(new Data(id,name,Path));

      }
  return files;
  }

  public static int hideFiles( Data file) throws SQLException, IOException {
      Connection con=MyConnection.getConnection();
      PreparedStatement ps=con.prepareStatement("insert into data(name,path,email,bin_data) values(?,?,?,?)");
      ps.setString(1,file.getFileName());
      ps.setString(2,file.getPath());
      ps.setString(3,file.getEmail());
      File f=new File(file.getPath());
      FileReader fr=new FileReader(f);
      ps.setCharacterStream(4,fr,f.length());
      int ans=ps.executeUpdate();
      fr.close();
      f.delete();
      return ans;
  }

  public static void unHide(int id) throws SQLException,IOException{
      Connection con=MyConnection.getConnection();
      PreparedStatement ps=con.prepareStatement("select path,bin_data from data where id=?");
      ps.setInt(1,id);
      ResultSet rs=ps.executeQuery();
      rs.next();
      String path=rs.getString("path");
      Clob c=rs.getClob("bin_data");
      Reader r=c.getCharacterStream();
      FileWriter fw=new FileWriter(path);
     int i;
     while ((i=r.read())!=-1){
               fw.write((char)i);
     }
           fw.close();
     ps=con.prepareStatement("delete from data where id=?");
     ps.setInt(1,id);
     ps.executeUpdate();
      System.out.println("Successfully Unhidden");
  }
}
