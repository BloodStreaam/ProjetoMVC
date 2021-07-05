package com.example.demo.BLL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsService {
    private int or_id;
    private int units;
    private float price;
    private int product_id;
    private int rc_id;
    private int o_id;

    public int getOr_id() {
        return or_id;
    }

    public void setOr_id(int or_id) {
        this.or_id = or_id;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getRc_id() {
        return rc_id;
    }

    public void setRc_id(int rc_id) {
        this.rc_id = rc_id;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public static List<OrderDetailsService> readAll(){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT OR_ID,UNITS, PRICE, PRODUCT_ID, RC_ID, O_ID FROM ORDER_DETAILS";

        List<OrderDetailsService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);


            ResultSet rs = st.executeQuery();

            while(rs.next()){
                OrderDetailsService OrderDetail = new OrderDetailsService();

                OrderDetail.setOr_id(rs.getInt("OR_ID"));
                OrderDetail.setUnits(rs.getInt("UNITS"));
                OrderDetail.setPrice(rs.getFloat("PRICE"));
                OrderDetail.setProduct_id(rs.getInt("PRODUCT_ID"));
                OrderDetail.setRc_id(rs.getInt("RC_ID"));
                OrderDetail.setO_id(rs.getInt("O_ID"));

                list.add(OrderDetail);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;
    }



    public static List<OrderDetailsService> read(int idOrderDetail){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT OR_ID, UNITS, PRICE, PRODUCT_ID, RC_ID, O_ID FROM Order_DETAILS WHERE O_ID = ?";
        List<OrderDetailsService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idOrderDetail);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                OrderDetailsService OrderDetail = new OrderDetailsService();

                OrderDetail.setOr_id(rs.getInt("OR_ID"));
                OrderDetail.setUnits(rs.getInt("UNITS"));
                OrderDetail.setPrice(rs.getFloat("PRICE"));
                OrderDetail.setProduct_id(rs.getInt("PRODUCT_ID"));
                OrderDetail.setRc_id(rs.getInt("RC_ID"));
                OrderDetail.setO_id(rs.getInt("O_ID"));

                list.add(OrderDetail);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;

    }

    public List<OrderDetailsService> readReq(int idOrderDetail){
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "SELECT OR_ID, UNITS, PRICE, PRODUCT_ID, RC_ID, O_ID FROM Order_DETAILS WHERE O_ID = ?";
        List<OrderDetailsService> list = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement(sqlCommand);
            st.setInt(1, idOrderDetail);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                OrderDetailsService OrderDetail = new OrderDetailsService();

                OrderDetail.setOr_id(rs.getInt("OR_ID"));
                OrderDetail.setUnits(rs.getInt("UNITS"));
                OrderDetail.setPrice(rs.getFloat("PRICE"));
                OrderDetail.setProduct_id(rs.getInt("PRODUCT_ID"));
                OrderDetail.setRc_id(rs.getInt("RC_ID"));
                OrderDetail.setO_id(rs.getInt("O_ID"));

                list.add(OrderDetail);
            }

        } catch (SQLException ex) {
            System.out.println("ERRO: " + ex.getMessage());
        }

        return list;

    }

    public void create(int units, float price, int product_id, int o_id) throws SQLException {
        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "INSERT INTO ORDER_DETAILS COLUMNS (UNITS, PRICE, PRODUCT_ID, O_ID) "
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);

        pst.setInt(1, units);
        pst.setFloat(2, price);
        pst.setInt(3, product_id);
        pst.setInt(4, o_id);


        pst.execute();
    }

}
