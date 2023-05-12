/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao.micro.negocios.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Escola
 */
public class LogDAO {
    
    private final Connection connection;
    private static LogDAO instance;
    
    public LogDAO() throws Exception {
        connection = connectionDAO.getInstance().connect();
        GenerateLog("Conexão obtida com o banco para LOG");
    }
    
    /* Implementação do Singleton */
    public static LogDAO getInstance() throws Exception {
        if (instance == null) {
            instance = new LogDAO();
        }
        return instance;
    }
    /* Fim da implementação do Singleton */
    
    public void GenerateLog(String action) throws SQLException{
        Statement stmnt = connection.createStatement();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stmnt.executeUpdate("INSERT INTO `sys`.`logs` (`date`, `action`) VALUES ('"+formatter.format(date)+"', '"+action+"')");
            stmnt.close();
    };
    
}
