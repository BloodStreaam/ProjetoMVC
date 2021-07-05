package com.example.demo.BLL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private int o_id;
    private int t_id;
    private String bill;
    private String invoice;
    private String payment_status;
    private Date o_date;
    private float total;
    private int c_id;

    public Date getO_date() {
        return o_date;
    }

    public void setO_date(Date o_date) {
        this.o_date = o_date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getO_id() {
        return o_id;
    }
    public void setO_id(int o_id) {
        this.o_id = o_id;
    }
    public int getT_id() {
        return t_id;
    }
    public void setT_id(int t_id) {
        this.t_id = t_id;
    }
    public String getBill() {
        return bill;
    }
    public void setBill(String bill) {
        this.bill = bill;
    }
    public String getInvoice() {
        return invoice;
    }
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
    public String getPayment_status() {
        return payment_status;
    }
    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public static List<OrderService> readAll(){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand = "SELECT * FROM ONLINE_ORDERS";
        List<OrderService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                OrderService order = new OrderService();

                order.setO_id(rs.getInt("o_id"));
                order.setT_id(rs.getInt("t_id"));
                order.setBill(rs.getString("billing_address"));
                order.setInvoice(rs.getString("invoice_address"));
                order.setPayment_status(rs.getString("payment_status"));
                order.setO_date(rs.getDate("O_DATE"));
                order.setTotal(rs.getFloat("TOTAL"));
                order.setC_id(rs.getInt("C_ID"));
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }

    public List<OrderService> readFatura(int c_id){
        Connection conn = SQLConnection.criarConexao();
        String sqlCommand = "SELECT * FROM ONLINE_ORDERS WHERE C_ID = ? ORDER BY O_DATE";
        List<OrderService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, c_id);

            ResultSet rs = st.executeQuery();


            while(rs.next()){
                OrderService order = new OrderService();

                order.setO_id(rs.getInt("o_id"));
                order.setT_id(rs.getInt("t_id"));
                order.setBill(rs.getString("billing_address"));
                order.setInvoice(rs.getString("invoice_address"));
                order.setPayment_status(rs.getString("payment_status"));
                order.setO_date(rs.getDate("O_DATE"));
                order.setTotal(rs.getFloat("TOTAL"));
                order.setC_id(rs.getInt("C_ID"));
                list.add(order);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }

    public int create(Date o_Date, int c_id, float total) throws SQLException {
        int last_inserted_id = 0;
        
        Connection conn = SQLConnection.criarConexao();
 
        String sqlCommand = "INSERT INTO ONLINE_ORDERS COLUMNS (O_DATE, T_ID, C_ID, TOTAL) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setDate(1, o_Date);
        pst.setInt(2, 1);
        pst.setInt(3, c_id);
        pst.setFloat(4, total);

        pst.execute();

        String sqlCommand1 = "SELECT MAX(O_ID) as O_ID FROM ONLINE_ORDERS";
        PreparedStatement pst1 = conn.prepareStatement(sqlCommand1);

        PreparedStatement st = conn.prepareStatement(sqlCommand1);
        ResultSet rs = st.executeQuery();

        if(rs.next()){
            last_inserted_id=(rs.getInt("O_ID"));

        }else{

            System.out.println("Erro: Não existe Order com o id Definido");
        }

        return last_inserted_id;
    }



}
