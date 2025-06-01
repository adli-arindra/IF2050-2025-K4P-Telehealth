package org.drpl.telebe.model;

import java.math.BigDecimal;

public class Medicine {
    private String id;
    private String name;
    private String description;
    private String dosage;
    private BigDecimal price;

    public Medicine() {
    }

    public Medicine(String id, String name, String description, String dosage, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dosage = dosage;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
