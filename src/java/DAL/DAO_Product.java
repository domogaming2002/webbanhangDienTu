/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.util.ArrayList;
import Models.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author ADMIN
 */
public class DAO_Product {
    private ArrayList<Product> prdList;
    private HashMap<String, Manufacturer> manuHm;
    private HashMap<String, Category> cateHm;
    
    public String status;
    Connection con;
    
    public DAO_Product() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error connection" + e.getMessage();
        }
    }
    
    public ArrayList<Product> getPrdList() {
        return prdList;
    }

    public void setPrdList(ArrayList<Product> prdList) {
        this.prdList = prdList;
    }

    public HashMap<String, Manufacturer> getManuHm() {
        return manuHm;
    }

    public void setManuHm(HashMap<String, Manufacturer> manuHm) {
        this.manuHm = manuHm;
    }

    public HashMap<String, Category> getCateHm() {
        return cateHm;
    }

    public void setCateHm(HashMap<String, Category> cateHm) {
        this.cateHm = cateHm;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void LoadProduct(){
        prdList = new ArrayList<>();
        String sql = "select * from Product_HE161389";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String productId = rs.getString(1);
                String name = rs.getString(2);
                String content = rs.getString(3);
                double price = Double.parseDouble(rs.getString(4));
                int amount = rs.getInt(5);
                double discount = Double.parseDouble(rs.getString(6));
                String imgUrl = rs.getString(7);
                String manufactorId = rs.getString(8);
                String categoryId = rs.getString(9);
                prdList.add(new Product(productId, name, content, price, amount, discount, imgUrl, manufactorId, categoryId));      
            }
        }catch(Exception e){
            status = "Error at read Product"+e.getMessage();
        }
        
    }
    
    public void loadManufacturer(){
        manuHm = new HashMap<>();
        String sql = "select * from Manufacturer_HE161389";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String manufacturerId = rs.getString(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                manuHm.put(manufacturerId, new Manufacturer(manufacturerId, name, address));
            }
        }catch(Exception e){
            status = "Error at Manufacturer"+e.getMessage();
        }
    }
    
    public void loadCategory(){
        cateHm = new HashMap<>();
        String sql = "select * from Category_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String categoryId = rs.getString(1);
                String name = rs.getString(2);
                String content = rs.getString(3);
                cateHm.put(categoryId, new Category(categoryId, name, content));
            }
        } catch (Exception e) {
            status = "Error Category" + e.getMessage();
        }
    }
    
    public void Insert(String productId, String name, String content, double price, int amount, double discount, String imgUrl, String manufacturerId,String categoryId){
        String sql ="insert into Product_HE161389 values(?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, productId);
            ps.setString(2, name);
            ps.setString(3, content);
            ps.setDouble(4, price);
            ps.setInt(5, amount);
            ps.setDouble(6, discount);
            ps.setString(7, imgUrl);
            ps.setString(8, manufacturerId);
            ps.setString(9, categoryId);
            ps.execute();
        }catch(Exception e){
            status = "Error at insert Product" + e.getMessage();
        }
    }
    
    public void del(String productId){
        String sql = "delete from Product_HE161389 where Product_ID =?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            status = "Error connection" + e.getMessage();
        }
        
    }
    
    public void Update(String productId, String name, String content, double price, int amount, double discount, String imgUrl, String manufacturerId,String categoryId ){
        String sql = "update Product_HE161389 set Name =?, [Content] =?,"
                + "Price =?, Amount = ?, Discount = ?, imgUrl =?, ManufacturerID = ?,"
                + "CategoryId = ? where Product_ID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(9, productId);
            ps.setString(1, name);
            ps.setString(2, content);
            ps.setDouble(3, price);
            ps.setInt(4, amount);
            ps.setDouble(5, discount);
            ps.setString(6, imgUrl);
            ps.setString(7, manufacturerId);
            ps.setString(8, categoryId);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update statement"+ e.getMessage();
        }
                
    }
}
