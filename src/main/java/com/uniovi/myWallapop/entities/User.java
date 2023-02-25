package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String passwordConfirm;
    private String role;

    // Esta lista representa las compras de cada usuario
    @OneToMany(mappedBy = "buyer")
    private List<Offer> boughtOffers;

    // Esta lista representa las ofertas creadas por cada usuario
    @OneToMany(mappedBy = "seller")
    private List<Offer> postedOffers;

    public User(){}

    public User(String name, String lastName, String email, String password, String passwordConfirm, String role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Offer> getBoughtOffers() {
        return boughtOffers;
    }

    public void setBoughtOffers(List<Offer> boughtOffers) {
        this.boughtOffers = boughtOffers;
    }

    public List<Offer> getPostedOffers() {
        return postedOffers;
    }

    public void setPostedOffers(List<Offer> postedOffers) {
        this.postedOffers = postedOffers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getPasswordConfirm() != null ? !getPasswordConfirm().equals(user.getPasswordConfirm()) : user.getPasswordConfirm() != null)
            return false;
        return getRole() != null ? getRole().equals(user.getRole()) : user.getRole() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getPasswordConfirm() != null ? getPasswordConfirm().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
