/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connection.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author theca
 */
public class Herb {
    
    private int herbId;
    private String herbName;
    private double herbPrice;
    private int herbAmount;
    private String herbType;
    private String herbDetail;
    private final static String SQL_SEARCH_HERB_BY_NAME = "SELECT * FROM HERB WHERE herbName LIKE ? AND herbType LIKE ?";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM HERB WHERE herbId = ?";
    private final static String SQL_SEARCH_HERB_BY_PRICE = "SELECT * FROM HERB WHERE herbPrice > ? AND herbPrice < ? AND herbType LIKE ?";
    private final static String SQL_LISTING_HERB_BY_TYPE = "SELECT * FROM HERB WHERE herbType LIKE ?";
    
    public Herb() {
    }
    
    public Herb(int herbId, String herbName, double herbPrice, int herbAmount, String herbType, String herbDetail) {
        this.herbId = herbId;
        this.herbName = herbName;
        this.herbPrice = herbPrice;
        this.herbAmount = herbAmount;
        this.herbType = herbType;
        this.herbDetail = herbDetail;
    }
    
    public int getHerbId() {
        return herbId;
    }
    
    public void setHerbId(int herbId) {
        this.herbId = herbId;
    }
    
    public String getHerbName() {
        return herbName;
    }
    
    public void setHerbName(String herbName) {
        this.herbName = herbName;
    }
    
    public double getHerbPrice() {
        return herbPrice;
    }
    
    public void setHerbPrice(double herbPrice) {
        this.herbPrice = herbPrice;
    }
    
    public int getHerbAmount() {
        return herbAmount;
    }
    
    public void setHerbAmount(int herbAmount) {
        this.herbAmount = herbAmount;
    }
    
    public String getHerbType() {
        return herbType;
    }
    
    public void setHerbType(String herbType) {
        this.herbType = herbType;
    }
    
    public String getHerbDetail() {
        return herbDetail;
    }
    
    public void setHerbDetail(String herbDetail) {
        this.herbDetail = herbDetail;
    }
    
    public static List<Herb> searchHerbByName(String name, String type) {
        List<Herb> herbs = null;
        ResultSet rs = null;
        Herb h = null;
        Connection con = ConnectionBuilder.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQL_SEARCH_HERB_BY_NAME);
            ps.setString(1, "%" + name+ "%");
            ps.setString(2, "%"+type+"%");
            rs = ps.executeQuery();
            if (rs != null) {
                if (herbs == null) {
                    herbs = new ArrayList<Herb>();
                }
                h = new Herb();
                h.ORM(rs, h);
                herbs.add(h);
            }
            rs.close();
            con.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Herb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return herbs;
    }
    
    public static List<Herb> searchHerbByPrice(double upper,double lower,String type){
         List<Herb> herbs = null;
        ResultSet rs = null;
        Herb h = null;
        Connection con = ConnectionBuilder.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQL_SEARCH_HERB_BY_PRICE);
            ps.setDouble(1, lower);
            ps.setDouble(2, upper);
            ps.setString(3, type);
            rs = ps.executeQuery();
            if (rs != null) {
                if (herbs == null) {
                    herbs = new ArrayList<Herb>();
                }
                h = new Herb();
                h.ORM(rs, h);
                herbs.add(h);
            }
            rs.close();
            con.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Herb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return herbs;
    }
    
    public static List<Herb> listingHerbByType(String type) {
        List<Herb> herbs = null;
        ResultSet rs = null;
        Herb h = null;
        Connection con = ConnectionBuilder.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQL_LISTING_HERB_BY_TYPE);
            ps.setString(1, "%" + type + "%");
            rs = ps.executeQuery();
            if (rs != null) {
                if (herbs == null) {
                    herbs = new ArrayList<Herb>();
                }
                h = new Herb();
                h.ORM(rs, h);
                herbs.add(h);
            }
            rs.close();
            con.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Herb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return herbs;
    }
    
    public static Herb findById(int id){
        Herb h = null;
        ResultSet rs = null;
        Connection con = ConnectionBuilder.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(SQL_FIND_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs!=null){
                h.ORM(rs, h);
            }
            rs.close();
            con.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Herb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;
    }
    
    public static void ORM(ResultSet rs, Herb h) throws SQLException {
        h.setHerbId(rs.getInt("herbId"));
        h.setHerbName(rs.getString("herbName"));
        h.setHerbPrice(rs.getDouble("herbPrice"));
        h.setHerbAmount(rs.getInt("herbAmount"));
        h.setHerbType(rs.getString("herbType"));
        h.setHerbDetail(rs.getString("herbDetail"));
    }
}
