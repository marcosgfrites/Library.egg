package com.gyl.library.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "book", schema = "libreriaweb")
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @NotEmpty
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activate;

}
