package com.uniovi.myWallapop.entities;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy="author")
    private Set<Message> messages = new HashSet<>();


    /**
     * Constructo sin parámetros
     */
    public User(){}


    /**
     * Constructor que recibe el nombre, apellido e email
     * del usuario
     * @param name String
     * @param lastName String
     * @param email String
     */
    public User(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.amount = 100;
    }


    /**
     * Retorna el valor del atributo id
     * @return Long
     */
    public Long getId() {
        return id;
    }


    /**
     * Cambiae el valor del atributo id
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Retorna el valor del atributo name
     * @return String
     */
    public String getName() {
        return name;
    }


    /**
     * Cambia el valor del atributo name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Retorna el valor del atributo lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Cambia el valor del atributo lastName
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Retorna el valor del atributo email
     * @return String
     */
    public String getEmail() {
        return email;
    }


    /**
     * Cambia el valor del atributo email
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Retorna el valor del atributo password
     * @return String
     */
    public String getPassword() {
        return password;
    }


    /**
     * Cambia el valor del atributo password
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Devuelve el valor del atributo passwordConfirm
     * @return String
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }


    /**
     * Cambia el valor del atributo passwrodConfirm
     * @param passwordConfirm String
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }


    /**
     * Retorna el valor del atributo role
     * @return String
     */
    public String getRole() {
        return role;
    }


    /**
     * Cambia el valor del atributo role
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }


    /**
     * Retorna el valor del atributo boughtOffers
     * @return Set<Offer>
     */
    public Set<Offer> getBoughtOffers() {
        return boughtOffers;
    }


    /**
     * Cambia el valor del atributo boughtOffers
     * @param boughtOffers Set<Offer>
     */
    public void setBoughtOffers(Set<Offer> boughtOffers) {
        this.boughtOffers = boughtOffers;
    }


    /**
     * Cambia el valor del atributo postedOffers
     * @return Set<Offer>
     */
    public Set<Offer> getPostedOffers() {
        return postedOffers;
    }


    /**
     * Cambia el valor del atributo postedOffers
     * @param postedOffers Set<Offer>
     */
    public void setPostedOffers(Set<Offer> postedOffers) {
        this.postedOffers = postedOffers;
    }


    /**
     * Retorna el valor del atributo amount
     * @return double
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Cambia el valor del atributo amount
     * @param amount double
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }


    /**
     * Método equals de User
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.amount, amount) == 0 && id.equals(user.id) && name.equals(user.name) && lastName.equals(user.lastName) && email.equals(user.email) && password.equals(user.password) && passwordConfirm.equals(user.passwordConfirm) && role.equals(user.role);
    }


    /**
     * Método hashCode de User
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, email, password, passwordConfirm, role, amount);
    }


    /**
     * Método toString de User
     * @return String
     */
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
