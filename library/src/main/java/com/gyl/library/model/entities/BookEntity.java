package com.gyl.library.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "book", schema = "librarygyl")
public class BookEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_book;

    @NotNull
    @Column(nullable = false)
    private Long isbn;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private Integer year;

    @NotNull
    @Column(nullable = false)
    private Integer copies;

    @NotNull
    @Column(nullable = false)
    private Integer loanedCopies;

    @NotNull
    @Column(nullable = false)
    private Integer remainingCopies;

    @NotEmpty
    @ManyToOne
    @JoinColumn(nullable = false)
    private AuthorEntity author;

    @NotEmpty
    @ManyToOne
    @JoinColumn(nullable = false)
    private EditorialEntity editorial;

    @NotEmpty
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activate;

    public BookEntity() {
    }

    public BookEntity(Integer id_book, Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial, Boolean activate) {
        this.id_book = id_book;
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.copies = copies;
        this.loanedCopies = loanedCopies;
        this.remainingCopies = remainingCopies;
        this.author = author;
        this.editorial = editorial;
        this.activate = activate;
    }

    public Integer getId_book() {
        return id_book;
    }

    public void setId_book(Integer id_book) {
        this.id_book = id_book;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getLoanedCopies() {
        return loanedCopies;
    }

    public void setLoanedCopies(Integer loanedCopies) {
        this.loanedCopies = loanedCopies;
    }

    public Integer getRemainingCopies() {
        return remainingCopies;
    }

    public void setRemainingCopies(Integer remainingCopies) {
        this.remainingCopies = remainingCopies;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public EditorialEntity getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialEntity editorial) {
        this.editorial = editorial;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }
}
