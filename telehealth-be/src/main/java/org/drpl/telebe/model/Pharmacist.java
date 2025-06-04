package org.drpl.telebe.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pharmacist extends User {

    @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    protected Pharmacist() {
    }

    public Pharmacist(String name, String email, String hashedPassword, String alamat, Date tanggalLahir) {
        super(name, email, hashedPassword, alamat, tanggalLahir);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setPharmacist(this);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.setPharmacist(null);
    }
}