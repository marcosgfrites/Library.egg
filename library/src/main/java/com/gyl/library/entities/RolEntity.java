package com.gyl.library.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rol", schema = "libreriaweb")
@EntityListeners(AuditingEntityListener.class)
public class RolEntity implements Serializable {

    private static final long serializableVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rol;

    @NotEmpty
    @Column(nullable = false)
    private String namerol;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @NotEmpty
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @NotEmpty
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activate;

}
