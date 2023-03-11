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

    public enum Tipo {PET, ALTA, ALTA_ERR, LOGIN_EX, LOGIN_ERR, LOGOUT};
    @Id
    @GeneratedValue
    private Long id;

    private Tipo tipo;

    private String description;

    private Timestamp date;
    public Log(){}

    public Log(Tipo tipo, String description) {
        this.tipo = tipo;
        this.description = description;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

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

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTipo() != null ? getTipo().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

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
