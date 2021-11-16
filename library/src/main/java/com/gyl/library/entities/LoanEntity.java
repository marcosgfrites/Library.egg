package com.gyl.library.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "loan", schema = "libreriaweb")
@EntityListeners(AuditingEntityListener.class)
public class LoanEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_loan;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate loanDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate returnDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @NotEmpty
    @ManyToOne
    @JoinColumn(nullable = false)
    private BookEntity book;

    @NotEmpty
    @ManyToOne
    @JoinColumn(nullable = false)
    private CustomerEntity customer;

    @NotEmpty
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activate;

}
