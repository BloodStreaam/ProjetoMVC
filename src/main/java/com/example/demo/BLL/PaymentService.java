package com.example.demo.BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private int payment_id;
    private String method;

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<PaymentService> readAllPayments(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT PAYMENT_ID, METHOD FROM PAYMENT_METHOD";

        List<PaymentService> listPayment = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                PaymentService methods = new PaymentService();

                methods.setPayment_id(rs.getInt("PAYMENT_ID"));
                if (rs.getString("METHOD") != null) methods.setMethod(rs.getString("METHOD"));
                listPayment.add(methods);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return listPayment;
    }

    public void readPayment(String payment){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT PAYMENT_ID, METHOD FROM PAYMENT_METHOD WHERE METHOD = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setString(1, payment);


            ResultSet rs = st.executeQuery();

            if(rs.next()){
                this.payment_id=(rs.getInt("PAYMENT_ID"));
                if (rs.getString("METHOD") != null) this.method= (rs.getString("METHOD"));

            }
            else{
                System.out.println("ERRO: Não existe PAYMENT com o NOME definido ");
            }
        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

    }
}
