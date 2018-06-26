/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemafinalluan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lucasmoura
 */
public class DB {
   Connection connection = null;  
   ResultSet resultSet = null;  
   Statement statement = null;  
   
   
   public void ConectarBanco() throws ClassNotFoundException, SQLException{
       //Define o JDBC (tipo do banco)
        Class.forName("org.sqlite.JDBC");  
        //Estabelece o caminho do banco através do JDBC
        connection = DriverManager.getConnection("jdbc:sqlite:banco.db");  
        //Estabelece conexão
        statement = connection.createStatement();  
   }
   
   
   public void DesconectarBanco() throws SQLException{
        //Fecha conexão
       statement.close();
   }
   
   public void alteraDados(String sql) throws ClassNotFoundException, SQLException{
    
       statement.executeUpdate(sql);
      
   }
   
   public ResultSet lerDados(String sql) throws SQLException, ClassNotFoundException{
       //Comando de seleção de dados
        
        ResultSet dados = statement.executeQuery(sql);
        
        return dados;
   }
   
   public DB(){
       try {
           this.ConectarBanco();
           this.CriaBanco();
           this.DesconectarBanco();
       } catch (SQLException ex) {
           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
    
    public void CriaBanco() throws SQLException, ClassNotFoundException, IOException{
        
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS especialidades ("
                + "idespecialidades INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "titulacao varchar(45) NOT NULL"
                + ");");
        
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS professores ("
                + "idprofessor INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nomeCompleto varchar(45) NOT NULL,"
                + "email varchar(45),"
                + "ativo TINYINT,"
                + "especialidade INTEGER,"
                + "FOREIGN KEY(especialidade) REFERENCES especialidades(idespecialidades)"
                + ");");
        
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS disciplina ("
                + "iddisciplina INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nomedisciplina varchar(45) NOT NULL,"
                + "carga_horaria INTEGER NOT NULL"
                + ");");
       
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS turma ("
                + "idturma INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "professor INTERGER,"
                + "semestre varchar(45) NOT NULL,"
                + "dia_semana INTEGER NOT NULL,"
                + "horario_inicio varchar(45) NOT NULL,"
                + "horario_fim varchar(45) NOT NULL,"
                + "data_inicio_aulas DATE,"
                + "data_fim_aulas DATE,"
                + "capacidade_maxima INTERGER,"
                + "disciplina INTEGER,"
                + "mediaminimaapto DOUBLE NOT NULL,"
                + "FOREIGN KEY(professor) REFERENCES professores(idprofessor),"
                + "FOREIGN KEY(disciplina) REFERENCES disciplina(iddisciplina)"
                + ");");
        
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS aluno ("
                + "idaluno INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome varchar(45) NOT NULL,"
                + "nascimento DATE NOT NULL,"
                + "responsavel varchar(45),"
                + "observacoes TEXT,"
                + "email varchar(45),"
                + "necessidades_especiais TEXT,"
                + "matriculado_desde DATE,"
                + "ativo TINYINT"
                + ");");
        
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS alunos_turma ("
                + "codigo_aluno INTEGER,"
                + "codigo_turma INTEGER,"
                + "nota1 DOUBLE NOT NULL,"
                + "nota2 DOUBLE NOT NULL,"
                + "nota3 DOUBLE NOT NULL,"
                + "FOREIGN KEY(codigo_aluno) REFERENCES aluno(idaluno),"
                + "FOREIGN KEY(codigo_turma) REFERENCES turma(idturma)"
                + ");");
        
    }
    
   
    

    
}