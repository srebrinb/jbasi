/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srebrinb.messagebroker;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author sbalabanov
 */
public class WebSockerMessager {
    public static void regUser(String jsonUser,String cId){
        User user=JdoUser.convUser(jsonUser);
        System.out.println("regUser = " + user+" "+cId);
        user=JdoUser.addChennelId(user, cId);
        System.out.println("regUser = " + user+" "+cId);
        
    }
    public static void unregUser(String cId){
        List<User> users=JdoUser.getUsersIn("chennalId", cId);
        for (User user : users) {
            JdoUser.removeChennelId(user, cId);
        }
    }
    public static List<String>getChennals(String key,String val){
        List chennals=new ArrayList();
        List<User> users=JdoUser.getUsers(key, val);
        for (User user : users) {
            System.out.println("user = " + user);
            chennals.addAll(user.getChennalId());
        }
        return chennals;
    }
}
