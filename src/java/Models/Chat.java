/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author ADMIN
 */
public class Chat {
    private int chatId;
    private int threadId;
    private String userId_Send;
    private String chat;
    private String userId_To;

    public Chat() {
    }

    public Chat(int chatId, int threadId, String userId_Send, String chat, String userId_To) {
        this.chatId = chatId;
        this.threadId = threadId;
        this.userId_Send = userId_Send;
        this.chat = chat;
        this.userId_To = userId_To;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getUserId_Send() {
        return userId_Send;
    }

    public void setUserId_Send(String userId_Send) {
        this.userId_Send = userId_Send;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getUserId_To() {
        return userId_To;
    }

    public void setUserId_To(String userId_To) {
        this.userId_To = userId_To;
    }

    
    
    
}
