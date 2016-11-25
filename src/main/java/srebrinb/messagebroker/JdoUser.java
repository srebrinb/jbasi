/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srebrinb.messagebroker;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author sbalabanov
 */
public class JdoUser {

    static EntityManager em = JdoUtils.getEntityManager();

    public static User insertUser(User user) {

        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            // em.getTransaction().rollback();
            if (ex.getMessage().contains("Unique")) {
                user = getUser("userName", user.getUserName());
            }
        }

        return user;
    }

    public static User convUser(String strUserJson) {
        Gson gson = new Gson();
        User user = gson.fromJson(strUserJson, User.class);
        return user;
    }

    public static User insertUser(String strUserJson) {
        return insertUser(convUser(strUserJson));
    }

    public static List<User> getUsers(String key, String val) {
        EntityManager iem = JdoUtils.getEntityManager();
        TypedQuery<User> queryUser = iem.createQuery(
                "SELECT u FROM User u WHERE u." + key + " = :val", User.class);
        List users = queryUser.setParameter("val", val).getResultList();
        return users;
    }

    public static List<User> getUsersIn(String key, String val) {
        TypedQuery<User> queryUser = em.createQuery(
                "SELECT u FROM User u WHERE :val IN " + key, User.class);
        List users = queryUser.setParameter("val", val).getResultList();
        return users;
    }

    public static User getUser(String key, String val) {
        TypedQuery<User> queryUser = em.createQuery(
                "SELECT u FROM User u WHERE u." + key + " = :val", User.class);
        User user = queryUser.setParameter("val", val).getSingleResult();
        return user;
    }

    public static User getUserIn(String key, String val) {        
        TypedQuery<User> queryUser = em.createQuery(
                "SELECT u FROM User u WHERE :val IN u." + key+" ", User.class);
        User user = queryUser.setParameter("val", val).getSingleResult();
        return user;
    }

    public static User addChennelId(User user, String ChennelId) {
        EntityManager inEm = JdoUtils.getEntityManager();
        user = insertUser(user);
        inEm.getTransaction().begin();
        user = inEm.find(User.class, user);
        // inEm.refresh(user);
        user.getChennalId().add(ChennelId);
        //  inEm.persist(user);
        inEm.getTransaction().commit();
     //   inEm.close();
        return user;
    }

    public static User removeChennelId(User user, String ChennelId) {
        EntityManager inEm = JdoUtils.getEntityManager();
        user = insertUser(user);
        inEm.getTransaction().begin();
        user = inEm.find(User.class, user);
        List<String> chennalIds = user.getChennalId();
        for (int i = chennalIds.size() - 1; i >= 0; i--) {
            if (chennalIds.get(i).equals(ChennelId)) {
                chennalIds.remove(i);
            }
        }
        user.setChennalId(chennalIds);

        inEm.getTransaction().commit();
        inEm.close();
        return user;
    }
}
