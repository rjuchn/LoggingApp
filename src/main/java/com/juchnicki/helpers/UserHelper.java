package com.juchnicki.helpers;

import com.juchnicki.constraints.Constraints;
import com.juchnicki.pojos.User;

import java.sql.*;

/**
 * Created by Rafal on 2016-11-13.
 */
public class UserHelper {

    private PreparedStatement authenticateUserStatement;

    public UserHelper() {
        try {

            Class.forName(Constraints.driverClassName);
            Connection conn = DriverManager.getConnection(Constraints.dbUrl, Constraints.username, Constraints.password);

            authenticateUserStatement = conn.prepareStatement("select * from SYSTEM.users where user_name = ? and user_password = ?");
        } catch (Exception e){
            System.out.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public User authenticateUser(String username, String password){
        User user = null;
        try{
            authenticateUserStatement.setString(1, username);
            authenticateUserStatement.setString(2, password);
            ResultSet rs = authenticateUserStatement.executeQuery();

            if(rs.next()){
                user = new User(rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
