package gestao.micro.negocios.dao;

import gestao.micro.negocios.model.Provider;
import gestao.micro.negocios.model.User;
import gestao.micro.negocios.MainApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author upper
 */
public class ProviderDAO {
    private final Connection connection;
    private Statement stmnt;
    private static ProviderDAO instance;
    private static Integer idUser;


    public ProviderDAO() throws Exception {
        connection = connectionDAO.getInstance().connect("FORNECEDOR");
        stmnt = connection.createStatement();
    }
    
    public static ProviderDAO getInstance(Integer id) throws Exception {
        if (instance == null) {
            instance = new ProviderDAO();
        }
        idUser = id;
        return instance;
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void createProvider (String provName, String provDetail) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try{
            stmnt.executeUpdate("INSERT INTO `sys`.`empresa` (`nome`, `tipo`, `detalhe`, `usuario`) VALUES ('"+provName+"', 'fornecedor', '"+provDetail+"', '"+idUser+"');");
            LogDAO.getInstance().GenerateLog("Inserir fornecedor na tabela EMPRESA para CADASTRO FORNECEDOR");
        }  finally   {
            stmnt.close();
        }
    }
    
    public void editProvider (Provider prov) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try {       
            stmnt.executeUpdate("UPDATE `sys`.`empresa` SET `nome`='"+prov.getName()+"',"
                    + " `detalhe`='"+prov.getDetail()+"', `tipo`='fornecedor' WHERE `id`='"+prov.getId().toString()+"';");

            LogDAO.getInstance().GenerateLog("Editar fornecedor na tabela PRODUTO");
        } finally   {
            stmnt.close();
        }
    }
    
    public void deleteProvider (Provider prov) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try (
            Statement stmnt = connection.createStatement();
        ){           
            stmnt.executeUpdate("DELETE FROM `sys`.`empresa` WHERE `id`='"+prov.getId().toString()+"';");

            LogDAO.getInstance().GenerateLog("Excluir fornedor da tabela EMPRESA");
        } finally   {
            stmnt.close();
        }
    }

    public List<Provider> getProviderList () throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try {
            ResultSet rs = stmnt.executeQuery("SELECT * FROM `sys`.`empresa` WHERE `usuario` = '"+idUser+"' and `tipo` = 'fornecedor'");
            LogDAO.getInstance().GenerateLog("Consulta fornecedor na tabela EMPRESA");
            List<Provider> providerList = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("nome");
                String detail  = rs.getString("detalhe");
                Provider person = new Provider(name, detail, id);
                providerList.add(person);
            }
            return providerList ;
        }  finally   {
            stmnt.close();
        }
    }
}
