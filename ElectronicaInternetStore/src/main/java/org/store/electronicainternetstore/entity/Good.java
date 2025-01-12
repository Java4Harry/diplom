package org.store.electronicainternetstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name="goods")
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "part_number")
    private int partNumber;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "weight")
    private String weight;

    @Column(name = "width")
    private String width;

    @Column(name = "length")
    private String length;

    @Column(name = "height")
    private String height;

    @Column(name = "price")
    private double price;

    @Column(name = "count")
    private int count;

    @Column(name = "reserved")
    private int reserved;

    @Column(name = "status")
    private String status;

    public int getId() {
        return id;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getWeight() {
        return weight;
    }

    public String getWidth() {
        return width;
    }

    public String getLength() {
        return length;
    }

    public String getHeight() {
        return height;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public int getReserved() {
        return reserved;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
