package com.quest.etna.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "address")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(length = 100, nullable = false)
    String street;

    @Column(name = "postal_code", length = 30, nullable = false)
    String postalCode;

    @Column(length = 50, nullable = false)
    String city;

    @Column(length = 50, nullable = false)
    String country;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date",columnDefinition = "datetime")
    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date",columnDefinition = "datetime")
    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Date updatedDate;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinColumn(name = "user_id", nullable = false)
    User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) &&
                postalCode.equals(address.postalCode) &&
                city.equals(address.city) &&
                country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, postalCode, city, country);
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }
}

