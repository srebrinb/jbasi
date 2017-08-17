/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates     
 * and open the template in the editor.
 */
package srebrinb.messagebroker;

import com.google.gson.Gson;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sbalabanov
 */
public class JdoUserTest {
    
    public JdoUserTest() {
    }
    private User getUser(){
        return getUser("");
    }
    private User getUser(String N){
        User user=new User();
        user.setUserName("testUserName"+N);
        user.setGroupName("testGroupName"+N);
        user.setCustId(12);
        return user;
    }
   @Test
    public void testUser() {
        
        Gson gson=new Gson();
        String str=gson.toJson(getUser());
        System.out.println("str = " + str);
    }
    /**
     * Test of insertUser method, of class JdoUser.
     */
    @Test
    public void testInsertUser() {
        System.out.println("insertUser");
        User user = getUser();
        user=JdoUser.insertUser(user);
        System.out.println("user = " + user);
    }

    /**
     * Test of getUser method, of class JdoUser.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String key = "groupName";
        String val = "testGroupName";
        User expResult = null;
        User result = JdoUser.getUser(key, val);
        System.out.println("result = " + result);
    }




    /**
     * Test of getUsers method, of class JdoUser.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        String key = "custId";
        String val = "12";
        List<User> expResult = null;
        List<User> result = JdoUser.getUsers(key, val);
        System.out.println("result = " + result);
    }

    /**
     * Test of addChennelId method, of class JdoUser.
     */
    @Test
    public void testAddChennelId() {
        System.out.println("addChennelId");
        User user = getUser();
        String ChennelId = "1234";
        
        User result = JdoUser.addChennelId(user, ChennelId);
        System.out.println("result = " + result);
    }

    /**
     * Test of removeChennelId method, of class JdoUser.
     */
    @Test
    public void testRemoveChennelId() {
        System.out.println("removeChennelId");
        User user = getUser();
        String ChennelId = "1234";        
        User result = JdoUser.removeChennelId(user, ChennelId);
        System.out.println("result = " + result);
    }

    

    /**
     * Test of getUsersIn method, of class JdoUser.
     */
    @Test
    public void testGetUsersIn() {
        System.out.println("getUsersIn");
        String key = "chennalId";
        String val = "1234";
        List<User> expResult = null;
        List<User> result = JdoUser.getUsersIn(key, val);
        System.out.println("result = " + result);
    }

    
}
