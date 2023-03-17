package com.uniovi.myWallapop.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name="logs")
public class Log {

    public enum Tipo {PET, ALTA, ALTA_ERR, LOGIN_EX, LOGIN_ERR, LOGOUT, OFFER_ERR};
    @Id
    @GeneratedValue
    private Long id;

    private Tipo tipo;

    private String description;

    private Timestamp date;

    /**
     * Constructor sin parámetros
     */
    public Log(){}


    /**
     * Constructor del Log con parámetros
     * tipo y description
     * @param tipo
     * @param description
     */
    public Log(Tipo tipo, String description) {
        this.tipo = tipo;
        this.description = description;
        this.date = new Timestamp(System.currentTimeMillis());
    }


    /**
     * Retorna el valor del atributo id
     * @return
     */
    public Long getId() {
        return id;
    }


    /**
     * Cambia el valor del atributo id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Retorna el valor del atributo tipo
     * @return
     */
    public Tipo getTipo() {
        return tipo;
    }


    /**
     * Cambia el valor del atributo tipo
     * @param tipo
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }


    /**
     * Retorna el valor del atributo description
     * @return
     */
    public String getDescription() {
        return description;
    }


    /**
     * Cambia el valor del atributo
     * description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Retorna el valor del atributo date
     * @return
     */
    public Timestamp getDate() {
        return date;
    }


    /**
     * Cambia el valor del atributo date
     * @param date
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }


    /**
     * Método equals del Log
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (getId() != null ? !getId().equals(log.getId()) : log.getId() != null) return false;
        if (getTipo() != log.getTipo()) return false;
        if (getDescription() != null ? !getDescription().equals(log.getDescription()) : log.getDescription() != null)
            return false;
        return getDate() != null ? getDate().equals(log.getDate()) : log.getDate() == null;
    }


    /**
     * Método hashCode de Log
     * @return
     */
    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTipo() != null ? getTipo().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }


    /**
     * Método toString de Log
     * @return
     */
    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
