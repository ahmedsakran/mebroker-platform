package com.mebroker.authservice.entity;

import com.mebroker.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "system_roles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "role_title"),
        @UniqueConstraint(columnNames = "role_number")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemRole extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_number", nullable = false)
    private Integer roleNumber;

    @Column(name = "role_title", nullable = false, length = 100)
    private String roleTitle;

    @Column(name = "role_description", length = 255)
    private String roleDescription;
}