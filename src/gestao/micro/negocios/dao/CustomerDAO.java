package gestao.micro.negocios.dao;

import gestao.micro.negocios.model.Customer;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author fael_
 */
public class CustomerDAO {
    private final Connection connection;
    private Statement stmnt;
    private static CustomerDAO instance;
    private static Integer idUser;


    public CustomerDAO() throws Exception {
        connection = connectionDAO.getInstance().connect("CLIENTE");
        stmnt = connection.createStatement();
    }
    
    public static CustomerDAO getInstance(Integer id) throws Exception {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        idUser = id;
        return instance;
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void createCustomer (String custName, String custEmail, String custTel) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try{
            stmnt.executeUpdate("INSERT INTO `sys`.`empresa` (`nome`, `tipo`, `detalhe`, `usuario`, `email`, `telefone`) "
                    + "VALUES ('"+custName+"', 'cliente', 'cliente', '"+idUser+"', '"+custEmail+"', '"+custTel+"');");
            LogDAO.getInstance().GenerateLog("Inserir cliente na tabela EMPRESA");
        }  finally   {
            stmnt.close();
        }
    }
    
    public void editCustomer (Customer customer) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try {       
            stmnt.executeUpdate("UPDATE `sys`.`empresa` SET `nome`='"+customer.getName()+"', `detalhe`='', " 
                    + " `email`='"+customer.getEmail()+"', `tipo`='cliente', `telefone`='"+customer.getTelefone()+"'"
                            + " WHERE `id`='"+customer.getId().toString()+"';");

            LogDAO.getInstance().GenerateLog("Editar cliente na tabela EMPRESA");
        } finally   {
            stmnt.close();
        }
    }
    
    public void deleteCustomer (Customer customer) throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try (
            Statement stmnt = connection.createStatement();
        ){           
            stmnt.executeUpdate("DELETE FROM `sys`.`empresa` WHERE `id`='"+customer.getId().toString()+"';");

            LogDAO.getInstance().GenerateLog("Excluir cliente da tabela EMPRESA");
        } finally   {
            stmnt.close();
        }
    }

    public List<Customer> getCustomerList () throws Exception {
        if(stmnt.isClosed()){
            stmnt = connection.createStatement();
        }
        try {
            ResultSet rs = stmnt.executeQuery("SELECT * FROM `sys`.`empresa` WHERE `tipo` = 'cliente'");
            LogDAO.getInstance().GenerateLog("Consulta cliente na tabela EMPRESA");
            List<Customer> customerList = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("nome");
                String email  = rs.getString("email");
                String telefone = rs.getString("telefone");
                Customer person = new Customer(id, name, email, telefone);
                customerList.add(person);
            }
            return customerList;
        }  finally   {
            stmnt.close();
        }
    }
}
