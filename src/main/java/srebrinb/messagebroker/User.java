/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srebrinb.messagebroker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.Unique;

@Entity
public class User implements Serializable {   
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private long id;
    @Index(unique="true") 
    private String userName;
    @Index
    private long custId;
    @Index
    private String groupName;
    private List<String> chennalId;
    public User(){
        chennalId=new ArrayList();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the custId
     */
    public long getCustId() {
        return custId;
    }

    /**
     * @param custId the custId to set
     */
    public void setCustId(long custId) {
        this.custId = custId;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    /**
     * @return the ChennalId
     */
    public List<String> getChennalId() {
        return chennalId;
    }

    /**
     * @param ChennalId the ChennalId to set
     */
    public void setChennalId(List ChennalId) {
        this.chennalId = ChennalId;
    }
    @Override
    public String toString(){
        return id+"\t"+userName+"\t"+groupName+"\t"+chennalId.toString();
    }
}
