package com.gyl.library.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "editorial", schema = "librarygyl")
public class EditorialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_editorial;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activate;

    @OneToMany(mappedBy = "editorial")
    private List<BookEntity> books;

    public EditorialEntity() {
    }

    public EditorialEntity(Integer id_editorial, String name, Boolean activate, List<BookEntity> books) {
        this.id_editorial = id_editorial;
        this.name = name;
        this.activate = activate;
        this.books = books;
    }

    public Integer getId_editorial() {
        return id_editorial;
    }

    public void setId_editorial(Integer id_editorial) {
        this.id_editorial = id_editorial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

}
