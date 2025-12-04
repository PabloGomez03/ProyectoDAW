/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author apolo
 */
@Entity
@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :qemail")
@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :qid")
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ShoppingCart cart;

    
    private String name;
    @NotNull
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String role;
    @Column(name = "profile_image")
    private String profileImage;

    public User(ShoppingCart cart, String name, String email, String password, String address, String role,String profileImage) {
        this.cart = cart;
        if (cart != null) {
            cart.setUser(this); 
        }
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.profileImage = profileImage;
    }

    public User() {
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) { 
        this.cart = cart; 
        if (cart != null && cart.getUser() != this) {
            cart.setUser(this); 
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Users[ id=" + id + " ]";
    }

}
