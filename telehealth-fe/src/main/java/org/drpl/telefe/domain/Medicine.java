package org.drpl.telefe.domain;

import java.math.BigDecimal;

public class Medicine {
    private String name;
    private String description;
    private String dosage;
    private BigDecimal price;

    public Medicine() {}

    public Medicine(String name, String description, String dosage, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.dosage = dosage;
        this.price = price;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public String toString() {
        return "Medicine{" + ", name='" + name + '\'' + ", dosage='" + dosage + '\'' + '}';
    }
}