package model;

import java.sql.*;

public class UserDAO {
    
    public User getUser(String login) {
        User resultUser = null;
        
        try {
            
            //configurando a conexão com o banco de dados: url, usuário do BD e senha
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/todoapp";
            String usuarioBD = "bogosort";
            String senhaBD = "avilar123";
            Connection c = DriverManager.getConnection(url, usuarioBD, senhaBD);
            
            //utilizando PreparedStatement pra dinamizar a consulta de acordo com o login do usuário
            PreparedStatement s = c.prepareStatement("SELECT * FROM usuario WHERE login = ?");
            s.setString(1, login);
            ResultSet rs = s.executeQuery();
            
            if(rs.next() == false){
            // se a consulta não retornar nenhum usuário, retorna o usuário nulo
                return resultUser;
            } else {
            // se retornar, inicializa um novo usuário e o TaskDAO
           
                resultUser = new User();
                TaskDAO dao = new TaskDAO();
                
                // pega o dado da primeira linha do ResultSet antes de chamar o método next()
                // é uma solução que funciona em todos os tipos de bancos de dados
                // como eu já chamei rs.next() ali no if, ele já sai da linha zero (sim, isso mesmo)
                
                do {
                    resultUser.setID(rs.getInt("user_id"));
                    resultUser.setLogin(rs.getString("login"));
                    resultUser.setPassword(rs.getString("senha"));
                    resultUser.setPicture(rs.getString("picture"));
                    resultUser.taskList = dao.getTask(resultUser);
                } while (rs.next());
            }
            
            rs.close();
            s.close();
            c.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        return resultUser;
    }
    
    public void setPicture(User u, String picture_url){
        Connection c = null;
        try {
            //configurando a conexão com o banco de dados: url, usuário do BD e senha
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/todoapp";
            String usuarioBD = "bogosort";
            String senhaBD = "avilar123";
            c = DriverManager.getConnection(url, usuarioBD, senhaBD);
                   
            PreparedStatement s = c.prepareStatement("UPDATE usuario SET picture = ? WHERE login = ?");
            s.setString(1, picture_url);
            s.setString(2, u.getLogin());
	    
            s.executeUpdate();
            s.close();
            c.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}