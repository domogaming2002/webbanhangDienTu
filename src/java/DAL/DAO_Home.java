/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class DAO_Home {

    private ArrayList<ProductReview> rvList;
    private ArrayList<User> usList;
    private ArrayList<Product> prdList;
    private ArrayList<Manufacturer> manuList;
    private ArrayList<Category> cateList;
    private ArrayList<ThreadChat> threadList;
    private ArrayList<Chat> chatList;

    public String status;
    Connection con;

    public DAO_Home() {
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

    public ArrayList<ProductReview> getRvList() {
        return rvList;
    }

    public void setRvList(ArrayList<ProductReview> rvList) {
        this.rvList = rvList;
    }

    public ArrayList<ThreadChat> getThreadList() {
        return threadList;
    }

    public void setThreadList(ArrayList<ThreadChat> threadList) {
        this.threadList = threadList;
    }

    public ArrayList<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(ArrayList<Chat> chatList) {
        this.chatList = chatList;
    }

    public ArrayList<User> getUsList() {
        return usList;
    }

    public void setUsList(ArrayList<User> usList) {
        this.usList = usList;
    }

    public void LoadProduct() {
        prdList = new ArrayList<>();
        String sql = "select * from Product_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        } catch (Exception e) {
            status = "Error at read Product" + e.getMessage();
        }

    }

    public void loadManufacturer() {
        manuList = new ArrayList<>();
        String sql = "select * from Manufacturer_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String manufacturerId = rs.getString(1);
                String name = rs.getString(2);
                String address = rs.getString(3);
                manuList.add(new Manufacturer(manufacturerId, name, address));
            }
        } catch (Exception e) {
            status = "Error at Manufacturer" + e.getMessage();
        }
    }

    public void loadCategory() {
        cateList = new ArrayList<>();
        String sql = "select * from Category_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String categoryId = rs.getString(1);
                String name = rs.getString(2);
                String content = rs.getString(3);
                cateList.add(new Category(categoryId, name, content));
            }
        } catch (Exception e) {
            status = "Error Category" + e.getMessage();
        }
    }

    public void LoadUser() {
        usList = new ArrayList<>();
        String sql = "select * from User_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userId = rs.getString(1);
                String pass = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String phone = rs.getString(5);
                String email = rs.getString(6);
                int admin = rs.getInt(7);
                boolean active = rs.getBoolean(8);
                String qId = rs.getString(9);
                String answer = rs.getString(10);
                String dob = rs.getString(11);
                String address = rs.getString(12);
                usList.add(new User(userId, pass, firstName, lastName, phone, email, admin, active, qId, answer, dob, address));
            }
        } catch (Exception e) {
            status = "Error at read User" + e.getMessage();
        }

    }

    public void addOrderUser(User us, Cart cart) {
        LocalDate curDate = LocalDate.now();
        String date = curDate.toString();
        String sql = "insert into Order_HE161389 values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, us.getUserId());
            ps.setDouble(2, cart.getTotalMoney());
            ps.setString(3, us.getPhone());
            ps.setString(4, us.getAddress());
            ps.setString(5, date);
            ps.setInt(6, 1);
            ps.execute();
            //Lay id cua order vua add
            String sql2 = "select top 1 OrderID from Order_HE161389 order by OrderID desc";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ResultSet rs = ps2.executeQuery();
            //add vao OrderDetail
            if (rs.next()) {
                int orderId = rs.getInt(1);
                for (ProductInCart prd : cart.getItems()) {
                    String sql3 = "insert into OrderDetail_HE161389 values(?,?,?,?)";
                    PreparedStatement ps3 = con.prepareStatement(sql3);
                    ps3.setInt(1, orderId);
                    ps3.setString(2, prd.getProduct().getProductId());
                    ps3.setInt(3, prd.getQuantity());
                    ps3.setDouble(4, prd.getPrice());
                    ps3.execute();
                }
            }
            //Update lai so luong
            String sql4 = "update Product_HE161389 set Amount = Amount- ? where Product_ID =?";
            PreparedStatement ps4 = con.prepareStatement(sql4);
            for (ProductInCart prd : cart.getItems()) {
                ps4.setInt(1, prd.getQuantity());
                ps4.setString(2, prd.getProduct().getProductId());
                ps4.execute();
            }
        } catch (Exception e) {
            status = "Error at insert Product" + e.getMessage();
        }

    }

    public void loadReview() {
        rvList = new ArrayList<>();
        String sql = "select * from ProductReview_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int reviewId = rs.getInt(1);
                String productId = rs.getString(2);
                String userId = rs.getString(3);
                int star = rs.getInt(4);
                String comment = rs.getString(5);
                rvList.add(new ProductReview(reviewId, productId, userId, star, comment));
            }
        } catch (Exception e) {
            status = "Error Review" + e.getMessage();
        }
    }

    public void insertReview(String userId, String productId, int star, String comment) {
        String sql = "insert into ProductReview_HE161389 values(?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, productId);
            ps.setString(2, userId);

            ps.setInt(3, star);
            ps.setString(4, comment);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Insert Manufacture" + e.getMessage();
        }
    }

    public void loadThread() {
        threadList = new ArrayList<>();
        String sql = "select * from Thread_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int threadId = rs.getInt(1);
                String userId = rs.getString(2);
                boolean isReply = rs.getBoolean(3);
                threadList.add(new ThreadChat(threadId, userId, isReply));
            }
        } catch (Exception e) {
            status = "Error Review" + e.getMessage();
        }
    }

    public void insertThread(String userId, boolean isReply) {
        String sql = "insert into Thread_HE161389 values(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setBoolean(2, isReply);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Insert Thread" + e.getMessage();
        }
    }

    public void updateThread(int threadId, boolean isReply) {
        String sql = "update Thread_HE161389 set isReply = ? where threadId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, threadId);
            ps.setBoolean(1, isReply);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update user" + e.getMessage();
        }
    }

    public void loadChat() {
        chatList = new ArrayList<>();
        String sql = "select * from Chat_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int chatId = rs.getInt(1);
                int threadId = rs.getInt(2);
                String userId_Send = rs.getString(3);
                String chat = rs.getString(4);
                String userId_To = rs.getString(5);
                chatList.add(new Chat(chatId, threadId, userId_Send, chat, userId_To));
            }
        } catch (Exception e) {
            status = "Error Chat" + e.getMessage();
        }
    }

    public void insertChat(int threadId, String userIdSend, String chat, String userIdTo) {
        String sql = "insert into Chat_HE161389 values(?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, threadId);
            ps.setString(2, userIdSend);
            ps.setString(3, chat);
            ps.setString(4, userIdTo);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Insert Thread" + e.getMessage();
        }
    }

}
