package com.gyl.library.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "book", schema = "libreriaweb")
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

}
