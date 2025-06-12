package org.drpl.telefe.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pharmacist extends User{
    private List<Order> orders = new ArrayList<>();

    protected Pharmacist() { }

    public Pharmacist(Long id, String name, String email, String alamat, Date tanggalLahir) {
        super(id, name, email, alamat, tanggalLahir);
    }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    public void addOrder(Order order) { this.orders.add(order); }
    public void removeOrder(Order order) { this.orders.remove(order); }
}
