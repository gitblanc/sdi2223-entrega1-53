package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String passwordConfirm;
    private String role;

    private double amount;

    // Esta lista representa las compras de cada usuario
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
    private Set<Offer> boughtOffers = new HashSet<>();

    // Esta lista representa las ofertas creadas por cada usuario
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    private Set<Offer> postedOffers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private Set<Chat> chats = new HashSet<>();

    public User(){}

    public User(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.amount = 100;
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

    public Set<Offer> getBoughtOffers() {
        return boughtOffers;
    }

    public void setBoughtOffers(Set<Offer> boughtOffers) {
        this.boughtOffers = boughtOffers;
    }

    public Set<Offer> getPostedOffers() {
        return postedOffers;
    }

    public void setPostedOffers(Set<Offer> postedOffers) {
        this.postedOffers = postedOffers;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.amount, amount) == 0 && id.equals(user.id) && name.equals(user.name) && lastName.equals(user.lastName) && email.equals(user.email) && password.equals(user.password) && passwordConfirm.equals(user.passwordConfirm) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, email, password, passwordConfirm, role, amount);
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
                ", amount=" + amount +
                '}';
    }
}
