/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.Order;
import Models.OrderDetail;
import Models.OrderStatus;
import Models.Product;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ADMIN
 */
public class DAO_Order {

    private ArrayList<Order> ordList;
    private ArrayList<OrderDetail> ordDetaiList;
    private HashMap<String, Product> prdHM;
    private HashMap<String, OrderStatus> stsHm;
    public String status;
    Connection con;

    public DAO_Order() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error connection" + e.getMessage();
        }
    }

    public ArrayList<Order> getOrdList() {
        return ordList;
    }

    public void setOrdList(ArrayList<Order> ordList) {
        this.ordList = ordList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OrderDetail> getOrdDetaiList() {
        return ordDetaiList;
    }

    public void setOrdDetaiList(ArrayList<OrderDetail> ordDetaiList) {
        this.ordDetaiList = ordDetaiList;
    }

    public HashMap<String, Product> getPrdHM() {
        return prdHM;
    }

    public void setPrdHM(HashMap<String, Product> prdHM) {
        this.prdHM = prdHM;
    }

    public HashMap<String, OrderStatus> getStsHm() {
        return stsHm;
    }

    public void setStsHm(HashMap<String, OrderStatus> stsHm) {
        this.stsHm = stsHm;
    }
    
    
    

    public void LoadOrder() {
        ordList = new ArrayList<>();
        String sql = "select * from Order_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String orderId = rs.getString(1);
                String userId = rs.getString(2);
                double total = rs.getDouble(3);
                String phone = rs.getString(4);
                String address = rs.getString(5);
                String date = rs.getString(6);
                int statusid  = rs.getInt(7);
                ordList.add(new Order(orderId, userId, total, phone, address, date,statusid));
            }
        } catch (Exception e) {
            status = "Error at read Order" + e.getMessage();
        }
    }
    
    public void LoadOrderDetail() {
        ordDetaiList = new ArrayList<>();
        String sql = "select * from OrderDetail_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String orderId = rs.getString(1);
                String productId = rs.getString(2);
                int quantity = rs.getInt(3);
                double price = rs.getDouble(4);
                ordDetaiList.add(new OrderDetail(orderId, productId, quantity, price));
            }
        } catch (Exception e) {
            status = "Error at read Order" + e.getMessage();
        }
    }
    
    public void loadProduct(){
        prdHM = new HashMap<>();
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
                prdHM.put(productId, new Product(productId, name, content, price, amount, discount, imgUrl, manufactorId, categoryId));
            }
        }catch(Exception e){
            status = "Error at Product"+e.getMessage();
        }
    }
    
    public void loadOrderStatus(){
        stsHm = new HashMap<>();
        String sql = "select * from OrderStatus_HE161389";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int statusId = rs.getInt(1);
                String statusDetail = rs.getString(2);
                stsHm.put(statusId+"", new OrderStatus(statusId, statusDetail));
            }
        }catch(Exception e){
            status = "Error at Product"+e.getMessage();
        }
    }
    
    public void updateOrderStatus(int statusId, String orderid){
        String sql = "update Order_HE161389 set statusId = ? where OrderID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, statusId);
            ps.setString(2, orderid);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update statement"+ e.getMessage();
        }
    }
    
    

}
