package com.gyl.library.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "author", schema = "librarygyl")
public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_author;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activate;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books;

    public AuthorEntity() {
    }

    public AuthorEntity(Integer id_author, String name, Boolean activate, List<BookEntity> books) {
        this.id_author = id_author;
        this.name = name;
        this.activate = activate;
        this.books = books;
    }

    public Integer getId_author() {
        return id_author;
    }

    public void setId_author(Integer id_author) {
        this.id_author = id_author;
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
