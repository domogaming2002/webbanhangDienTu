/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.util.ArrayList;
import Models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
/**
 *
 * @author ADMIN
 */
public class DAO_LoadManu_Cate {
    public ArrayList<Manufacturer> manuList;
    public  ArrayList<Category> cateList;
    
      
    public String status;
    Connection con;


    public DAO_LoadManu_Cate() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error connection" + e.getMessage();
        }
    }

    public ArrayList<Manufacturer> getManuList() {
        return manuList;
    }

    public void setManuList(ArrayList<Manufacturer> manuList) {
        this.manuList = manuList;
    }

    public ArrayList<Category> getCateList() {
        return cateList;
    }

    public void setCateList(ArrayList<Category> cateList) {
        this.cateList = cateList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void loadManufacturer(){
        manuList = new ArrayList<>();
        String sql = "select * from Manufacturer_HE161389";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String manufacturerId = rs.getString(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                manuList.add(new Manufacturer(manufacturerId, name, address));
            }
        }catch(Exception e){
            status = "Error at Manufacturer"+e.getMessage();
        }
    }
    
    public void loadCategory(){
        cateList= new ArrayList<>();
        String sql = "select * from Category_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String categoryId = rs.getString(1);
                String name = rs.getString(2);
                String content = rs.getString(3);
                cateList.add(new Category(categoryId, name, content));
            }
        } catch (Exception e) {
            status = "Error Category" + e.getMessage();
        }
    }

    public void insertManu(String manufacturerId, String name, String address){
        String sql = "insert into Manufacturer_HE161389 values(?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, manufacturerId);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.execute();
        }catch(Exception e){
            status = "Error at Insert Manufacture" + e.getMessage();
        }
    }
    
    public void insertCate(String categoryId, String name, String content){
        String sql = "insert into Category_HE161389 values(?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, categoryId);
            ps.setString(2, name);
            ps.setString(3, content);
            ps.execute();
        }catch(Exception e){
            status = "Error at insert category" + e.getMessage();
        }
    }
    
    public void delManu(String manuId){
        String sql = "delete from Manufacturer_HE161389 where ManufacturerID = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, manuId);
            ps.executeUpdate();
        }catch(Exception e){
            status = "Error connection" + e.getMessage();
        }
    }
    
    public void delCate(String cateId){
        String sql = "delete from Category_HE161389 where CategoryID = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cateId);
            ps.executeUpdate();
        }catch(Exception e){
            status = "Error connection" + e.getMessage();
        }
    }
    
    public void updateManu(String manuId, String name, String address){
        String sql = "Update Manufacturer_HE161389 set [Name] = ?, [Address]  = ? where ManufacturerID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(3, manuId);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.execute();
        } catch (Exception e) {
            status = "Error at UpdateMaunu" +e.getMessage();
        }
    }
    
    public void updateCate(String cateId, String name, String content){
        String sql ="Update Category_HE161389 set [Name] = ?, Content  = ? where CategoryID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(3, cateId);
            ps.setString(1, name);
            ps.setString(2, content);
            ps.execute();
        } catch (Exception e) {
        }
    }
    
}
