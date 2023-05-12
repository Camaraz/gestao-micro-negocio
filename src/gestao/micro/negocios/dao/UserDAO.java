package gestao.micro.negocios.dao;

import gestao.micro.negocios.model.User;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author upper
 */
public class UserDAO {
    private final Connection connection;
    private Statement stmnt;
    private static UserDAO instance;


    public UserDAO() throws Exception {
        connection = connectionDAO.getInstance().connect("USUARIO");
        stmnt = connection.createStatement();
    }
    
    public static UserDAO getInstance() throws Exception {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public void createPerson(String usrName, String usrEmail, String usrPass, FileInputStream img) throws Exception {
        PreparedStatement prep = connection.prepareStatement("INSERT INTO `sys`.`usuario` (`email`, `senha`, `nome`, `logo`) VALUES ((?), (?), (?), (?));");
        prep.setString(1, usrEmail);
        prep.setString(2, usrPass);
        prep.setString(3, usrName);
        prep.setBinaryStream(4, img);

        try {
            prep.executeUpdate();
            LogDAO.getInstance().GenerateLog("Inserir na tabela USUARIO para CADASTRO EMPRESA");
        }  finally   {
            prep.close();
        }
    }

    public User Login(String usrEmail, String usrPass) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try {
            String id = null;
            String name = null;
            String email = null;
            String pass = null;
            ResultSet rs = stmnt.executeQuery("SELECT * FROM `sys`.`usuario` WHERE `email` = '"+usrEmail+"' and `senha` = '"+usrPass+"' limit 1;");
            LogDAO.getInstance().GenerateLog("Consulta na tabela USUARIO para LOGIN");
            while (rs.next()) {
                id = rs.getString("id");
                name = rs.getString("nome");
                email = rs.getString("email");
                pass = rs.getString("senha");
            }
            if( name == null || email == null ){
                return null;
            }else {
                User person = new User(id, name, email, pass);
                rs.close();
                return person;
            }
        } finally   {
            stmnt.close();
        }
    }

    public List<User> getPersonList() throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        
        try {
            ResultSet rs = stmnt.executeQuery("SELECT * FROM `sys`.`usuario`");
            LogDAO.getInstance().GenerateLog("Consulta na tabela USUARIO");
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("nome");
                String email = rs.getString("email");
                String pass = rs.getString("senha");
                User person = new User(id, name, email, pass);
                userList.add(person);
            }
            return userList ;
        }  finally   {
            stmnt.close();
        }
    }
    
    public Image getLogo(Integer idUser) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        ResultSet rs;
        Image img = null;
        try {
            rs = stmnt.executeQuery("SELECT * FROM `sys`.`usuario` WHERE `id` = '"+idUser+"' limit 1;");    
            if(rs.next())   {
                Blob foto = rs.getBlob("logo");
                InputStream is = foto.getBinaryStream();
                img = new Image(is);
            }
            LogDAO.getInstance().GenerateLog("Consulta de LOGO tabela USUARIO");
        }  finally   {
            stmnt.close();
        }
        return img;
    }
}
