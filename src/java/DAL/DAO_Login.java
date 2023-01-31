/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Models.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ADMIN
 */
public class DAO_Login {

    private ArrayList<User> usList;
    private HashMap<String, Question> questHm;
    private ArrayList<VerifyToken> tokenList;

    public String status;
    Connection con;

    public DAO_Login() {
        try {
            con = new DBContext().getConnection();
        } catch (Exception e) {
            status = "Error connection" + e.getMessage();
        }
    }

    public ArrayList<User> getUsList() {
        return usList;
    }

    public void setUsList(ArrayList<User> usList) {
        this.usList = usList;
    }

    public HashMap<String, Question> getQuestHm() {
        return questHm;
    }

    public void setQuestHm(HashMap<String, Question> questHm) {
        this.questHm = questHm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<VerifyToken> getTokenList() {
        return tokenList;
    }

    public void setTokenList(ArrayList<VerifyToken> tokenList) {
        this.tokenList = tokenList;
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

    public void insertUser(String userId, String pass, String firstName, String lastName, String phone, String email, int admin, boolean active, String qId, String answer, String dob, String address) {
        String sql = "insert into User_HE161389 values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2, pass);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, phone);
            ps.setString(6, email);
            ps.setInt(7, admin);
            ps.setBoolean(8, active);
            ps.setString(9, qId);
            ps.setString(10, answer);
            ps.setString(11, dob);
            ps.setString(12, address);
            ps.execute();
        } catch (Exception e) {
            status = "Error at register " + e.getMessage();
        }
    }

    public void loadQuestion() {
        String sql = "select * from Question_HE161389";
        questHm = new HashMap<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String qId = rs.getString(1);
                if (!qId.equals("0")) {
                    String qName = rs.getString(2);
                    questHm.put(qId, new Question(qId, qName));
                }
            }
        } catch (Exception e) {
            status = "Error at load Question";
        }
    }


    public void updateUserDetail(String firstname, String lastname, String dob, String phone, String address,String email, String userid) {
        String sql = "Update User_HE161389 set LastName = ?, FirstName =?, dob = ?,Phone = ? , [address]  = ?, [Email] = ? where UserID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(7, userid);
            ps.setString(1, lastname);
            ps.setString(2, firstname);
            ps.setString(3, dob);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, email);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update user" + e.getMessage();
        }
    }

    public void updateUserPass(String userid, String password) {
        String sql = "update User_HE161389 set Pass = ? where UserID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(2, userid);
            ps.setString(1, password);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update user" + e.getMessage();
        }
    }

    public void insertVerirfyToken(String userid, String token) {
        String sql = "insert into VerifyToken_HE161389 values(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userid);
            ps.setString(2, token);
            ps.execute();
        } catch (Exception e) {
            status = "Error at VerifyToken " + e.getMessage();
        }
    }

    public void loadVerifyToken() {
        tokenList = new ArrayList<>();
        String sql = "select * from VerifyToken_HE161389";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userId = rs.getString(1);
                String token = rs.getString(2);
                tokenList.add(new VerifyToken(userId, token));
            }
        } catch (Exception e) {
            status = "Error at read User" + e.getMessage();
        }
    }
    
    public void updateActive(String userId){
        String sql ="update User_HE161389 set active = 1 where UserID = ?";
         try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update Active" + e.getMessage();
        }
        
    }
    
    public void updateToken(String userId,String token){
        String sql = "update VerifyToken_HE161389 set token = ? where UserID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(2, userId);
            ps.setString(1, token);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update Active" + e.getMessage();
        }
    }
    
    public void UpdateUserAdmin(String userid, String firstname, String lastname, String phone, String email,String qid, String answer, String dob, String address){
        String sql = "update User_HE161389 set FirstName = ?, LastName = ?, Phone = ?, Email = ?, qid = ?,\n" +
                    "answer = ?, dob = ?, [address] = ? where UserID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(9, userid);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, qid);
            ps.setString(6, answer);
            ps.setString(7, dob);
            ps.setString(8, address);
            ps.execute();
        } catch (Exception e) {
            status = "Error at Update statement"+ e.getMessage();
        }
                
    }

}
