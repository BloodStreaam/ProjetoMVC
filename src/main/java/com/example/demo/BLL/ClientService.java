package com.example.demo.BLL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    //variaveis
    private int c_id;
    private int o_id;
    private String name;
    private Date birthdate;
    private String mail;
    private int phone;
    private String password;



    //metodos


    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getC_id() {return c_id;}
    public void setC_id(int c_id) {this.c_id = c_id;}

    public Date getBirthdate() {return birthdate;}
    public void setBirthdate(Date birthdate) {this.birthdate = birthdate;}

    public int getO_id() {return o_id;}
    public void setO_id(int o_id) {this.o_id = o_id;}

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public static List<ClientService> readAll(){
        Connection conn =SQLConnection.criarConexao();
        String sqlCommand = "SELECT * FROM CLIENT";
        List<ClientService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                ClientService client = new ClientService();
                client.setC_id(rs.getInt("C_ID"));
                if (rs.getString("NAME") != null) client.setName(rs.getString("NAME"));
                if (rs.getDate("BIRTHDATE") != null) client.setBirthdate(rs.getDate("BIRTHDATE"));
                if (rs.getString("MAIL") != null) client.setMail(rs.getString("MAIL"));
                client.setPhone(rs.getInt("PHONE"));
                if (rs.getString("PASSWORD") != null) client.setPassword(rs.getString("PASSWORD"));
                //
                list.add(client);
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }
        return list;
    }
    public void read(int idClient){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand="SELECT * FROM CLIENT WHERE C_ID= ?";
        try{
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idClient);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                this.c_id=(rs.getInt("C_ID"));
                if(rs.getString("NAME") != null) this.name= (rs.getString("NAME"));
                if (rs.getString("MAIL") != null) this.mail=(rs.getString("MAIL"));
                if (rs.getDate("BIRTHDATE") != null) this.birthdate=(rs.getDate("BIRTHDATE"));
                if (rs.getString("PASSWORD") != null) this.password=(rs.getString("PASSWORD"));
                this.phone=(rs.getInt("PHONE"));


            }else{

                System.out.println("Erro: Não existe Client com o ID Definido");
            }


        }catch(SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }




    }

    public void readMail(String mail){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand="SELECT * FROM CLIENT WHERE to_char(MAIL)= ?";
        try{
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                this.c_id=(rs.getInt("C_ID"));
                if(rs.getString("NAME") != null) this.name= (rs.getString("NAME"));
                if (rs.getString("MAIL") != null) this.mail=(rs.getString("MAIL"));
                if (rs.getDate("BIRTHDATE") != null) this.birthdate=(rs.getDate("BIRTHDATE"));
                if (rs.getString("PASSWORD") != null) this.password=(rs.getString("PASSWORD"));
                this.phone=(rs.getInt("PHONE"));


            }else{

                System.out.println("Erro: Não existe Client com o mail Definido");
            }


        }catch(SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }




    }
    /*public void delete(int id){

        Connection conn = SQLConnection.criarConexao();
        String sqlCommand = "DELETE CLIENT WHERE C_ID = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, id);

            st.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        String sqlCommandAddress = "DELETE ADDRESS WHERE C_ID = ?";
        try {
            PreparedStatement stAddress = conn.prepareStatement(sqlCommandAddress);
            stAddress.setInt(1, id);

            stAddress.execute();

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }*/

    public void create(String name, Date birthdate, String mail, int phone, String address, int zip, int housenumber, String Pass) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "INSERT INTO CLIENT COLUMNS (NAME, BIRTHDATE, MAIL, PHONE, PASSWORD) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setDate(2, birthdate);
        pst.setString(3, mail);
        pst.setInt(4, phone);
        pst.setString(5, Pass);
        pst.execute();

       String sqlCommandSelectClient ="SELECT C_ID FROM CLIENT WHERE PHONE = ? AND to_char(NAME) = ?";

        PreparedStatement st = conn.prepareStatement(sqlCommandSelectClient);
        st.setInt(1, phone);
        st.setString(2, name);
        ResultSet rs = st.executeQuery();

        if(rs.next()) {
            this.c_id = (rs.getInt("C_ID"));
        }

        String sqlCommandAddress = "INSERT INTO ADDRESS COLUMNS (STREETNAME, HOUSENUMBER, C_ID, ZIP) "
                + "VALUES (?, ?, ?, ?)";

        PreparedStatement pstAddress = conn.prepareStatement(sqlCommandAddress);


        pstAddress.setString(1, address);
        pstAddress.setInt(2, housenumber);
        pstAddress.setInt(3, this.c_id);
        pstAddress.setInt(4, zip);

        pstAddress.execute();




    }

    public void createClientOnly(String name, Date birthdate, String mail, int phone, String Pass) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "INSERT INTO CLIENT COLUMNS (NAME, BIRTHDATE, MAIL, PHONE, PASSWORD) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setDate(2, birthdate);
        pst.setString(3, mail);
        pst.setInt(4, phone);
        pst.setString(5, Pass);
        pst.execute();


    }

    public void updateClientOnly(String name, Date birthdate, String mail, int phone, String Pass, int c_id) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "UPDATE CLIENT SET NAME = ?, BIRTHDATE = ?, MAIL = ?, PHONE = ?, PASSWORD = ? WHERE C_ID = ?";

        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setDate(2, birthdate);
        pst.setString(3, mail);
        pst.setInt(4, phone);
        pst.setString(5, Pass);
        pst.setInt(6, c_id);
        pst.execute();


    }

    public void update(String name, Date birthdate, String mail, int phone, String pass, int c_id, String streetname, int houseNumber, int zip) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "UPDATE CLIENT SET NAME = ?, BIRTHDATE = ?, MAIL = ?, PHONE = ?, PASSWORD = ? WHERE C_ID = ?";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setString(1, name);
        pst.setDate(2, birthdate);
        pst.setString(3, mail);
        pst.setInt(4, phone);
        pst.setString(5, pass);
        pst.setInt(6, c_id);

        pst.execute();

        String sqlCommandAddress = "UPDATE ADDRESS SET STREETNAME = ?, HOUSENUMBER = ?, ZIP = ? WHERE C_ID = ?";
        PreparedStatement pstAddress = conn.prepareStatement(sqlCommandAddress);

        pstAddress.setString(1, streetname);
        pstAddress.setInt(2, houseNumber);
        pstAddress.setInt(3, zip);
        pstAddress.setInt(4, c_id);

        pstAddress.execute();



    }

    public static List<ClientService> search(String client) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT * FROM CLIENT ";



        if(!client.isEmpty()){
            sqlCommand+= "WHERE to_char(NAME) LIKE ? ";
        }

        sqlCommand+="ORDER BY to_char(NAME)";
        List<ClientService> list = new ArrayList<>();

        try {

            PreparedStatement st = conn.prepareStatement(sqlCommand);


            if(client.isEmpty() == false ){

                st.setString(1, "%" + client + "%");

            }

            ResultSet rs = st.executeQuery();

            while(rs.next()){

                ClientService cli = new ClientService();

                cli.setC_id(rs.getInt("C_ID"));
                cli.setName(rs.getString("NAME"));
                cli.setMail(rs.getString("MAIL"));
                cli.setBirthdate(rs.getDate("BIRTHDATE"));

                list.add(cli);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }



}
