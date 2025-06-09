package org.drpl.telefe.model;

import java.math.BigDecimal;

public class Medicine {
    private Long id;
    private String name;
    private String description;
    private String dosage;
    private BigDecimal price;

    public Medicine() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
        return "Medicine{" + "id=" + id + ", name='" + name + '\'' + ", dosage='" + dosage + '\'' + '}';
    }
}