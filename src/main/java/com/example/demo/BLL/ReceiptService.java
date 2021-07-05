package com.example.demo.BLL;

import java.sql.*;

public class ReceiptService {

    private int rc_id;
    private int o_id;
    private int pt_id;

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

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public void create( int o_id, int pt_id) throws SQLException {

        Connection conn = SQLConnection.criarConexao();

        String sqlCommand = "INSERT INTO RECEIPT COLUMNS ( O_ID, PT_ID) "
                + "VALUES ( ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sqlCommand);


        pst.setInt(1, o_id);
        pst.setInt(2, pt_id);

        pst.execute();

    }

}
