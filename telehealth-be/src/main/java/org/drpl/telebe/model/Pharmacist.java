package org.drpl.telebe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pharmacist extends User {

    private List<Order> orders;

    public Pharmacist(String id, String name, String email, String alamat, Date tanggalLahir) {
        super(id, name, email, alamat, tanggalLahir);
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrder() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void finishOrder(Order order) {
        this.orders.remove(order);
    }

    // Getters and Setters

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}