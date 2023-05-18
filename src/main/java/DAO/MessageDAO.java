package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    /**
     * TODO: retrieve all messages from the Message table.
     * @return all Accounts.
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * TODO: retrieve all messages from a specific account from the Message table.
     * @return all messages from an account.
     */

     public List<Message> getAllMessagesByAccount(int by_account) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, by_account);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
     }

    /**
     * TODO: retrieve a message from the Message table, identified by its message_id.
     * @return a message identified by message_id.
     */
    public Message getMessageByMessageID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                //Message message = 
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_test"),
                    rs.getLong("time_posted_epoch"));
                //return message;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: insert a message into the message table
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: delete a message from the message table, identified by its message_id
     */
    public void deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "delete from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * TODO: edit a message from the message table, identified by its message_id
     */
    public Message updateMessageText(int message_id, String new_message_text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text = ? where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, new_message_text);
            preparedStatement.setInt(2, message_id);
            //return preparedStatement.executeUpdate();
            preparedStatement.executeQuery();
            return getMessageByMessageID(message_id);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
        // return 0;
    }

}
