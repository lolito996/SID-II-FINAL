package com.example.demo.DTO;


import java.math.BigDecimal;

public class SubjectDTO{
    private Integer id;
    private String name;
    private String description;
    private BigDecimal minimumPassingGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getMinimumPassingGrade() {
        return minimumPassingGrade;
    }

    public void setMinimumPassingGrade(BigDecimal minimumPassingGrade) {
        this.minimumPassingGrade = minimumPassingGrade;
    }
}
