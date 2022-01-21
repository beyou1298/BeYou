package com.beyou.common.entity;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.beyou.common.Constants;

//@Entity annotation from JPA to indicate that this Java class will map to a table in the database.
@Entity
@Table(name="users")
public class User extends IdBasedEntity{

    @Column(length=128,nullable=false, unique=true)
    private String email;

    @Column(length=64,nullable=false)
    private String password;

    @Column(name="first_name",length=45,nullable=false)
    private String firstName;

    @Column(name="last_name",length=45,nullable=false)
    private String lastName;

    @Column(length=64)
    private String photos;

    private boolean enabled;
    
    //And to map this Set collection to a many-to-many entity relationship We need to use the @ManyToMany annotation
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="users_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns=@JoinColumn(name = "role_id")
    )
    /*And to implement the many-to-many entity relationship between user and role, we need to have a collection
    a Set of Role objects here so in this User entity class.So we declare a collection of type Set here*/
    private Set<Role> roles = new HashSet<>();

    public User(){

    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);

    }

    @Override
    public String toString() {
        return "User [FirstName=" + firstName + ", LastName=" + lastName + ", email=" + email + ", id=" + id
                + ", roles=" + roles + "]";
    }

    //This annotation is used to indicate that this getter is not mapped to any field in database
    @Transient
    public String getPhotosImagePath(){
        if(id==null || photos==null) return "/images/default-user.png";

        return Constants.S3_BASE_URI + "/user-photos/" + this.id + "/" + this.photos;
    }

    @Transient
    public String getFullName(){
        return firstName + " " + lastName;
    }

    public boolean hasRole(String roleName){
        Iterator<Role> iterator = roles.iterator();

        while(iterator.hasNext()){
            Role role = iterator.next();
            if(role.getName().equals(roleName)){
                return true;
            }
        }
        return false;
    }

    
}
