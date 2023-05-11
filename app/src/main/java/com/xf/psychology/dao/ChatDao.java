package com.xf.psychology.dao;

import android.util.Log;

import com.xf.psychology.App;
import com.xf.psychology.bean.ChatBean;
import com.xf.psychology.utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ChatDao {
    private Connection connection;
    public void insert(ChatBean chatBean) throws SQLException {
        int num = 0;
        while(connection == null) {
            connection = JDBCUtils.getConn();
            if(connection == null) {
                Log.e("null","访问为空" + ++num);
            } else {
                Log.e("not null","有东西");
            }
        }
        if(connection == null) {
            Log.e("null","访问为空");
        } else {
            Log.e("not null","有东西");
        }
        try {
            String sql = "INSERT INTO chatbean (messageId, isLeft, message, sendId, catchId, sendIconPath, catchIconPath, messageTime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, chatBean.getMessageId());
            statement.setBoolean(2, chatBean.isLeft());
            statement.setString(3, chatBean.getMessage());
            statement.setInt(4, chatBean.getSendId());
            statement.setInt(5, chatBean.getCatchId());
            statement.setString(6, chatBean.getSendIconPath());
            statement.setString(7, chatBean.getCatchIconPath());
            statement.setString(8, chatBean.getMessageTime());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public List<ChatBean> queryBySendId(int sendId, int catchId) throws SQLException {
        List<ChatBean> chatBeans = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * FROM chatbean WHERE sendId=2 OR catchId=1";
            statement = connection.prepareStatement(sql);
//            statement.setInt(1, sendId);
//            statement.setInt(2, catchId);
            resultSet = statement.executeQuery();
//            Log.e("aaa", String.valueOf(resultSet.next()));
            while (resultSet.next()) {
                Log.e("aaa","bbb");
                int messageId = resultSet.getInt("messageId");
                boolean isLeft = resultSet.getBoolean("isLeft");
                String message = resultSet.getString("message");
                Log.e("aaa",message);
                String sendIconPath = resultSet.getString("sendIconPath");
                String catchIconPath = resultSet.getString("catchIconPath");
                String messageTime = resultSet.getString("messageTime");
                ChatBean chatBean = new ChatBean(isLeft, message, sendId, catchId, sendIconPath, catchIconPath, messageTime);
                chatBean.setMessageId(messageId);
                chatBeans.add(chatBean);
                Log.e("aaa",chatBean.toString());
            }
            Log.e("NULL", String.valueOf(chatBeans.size()));
            return chatBeans;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(resultSet, statement, connection);
        }
        return null;
    }


}
