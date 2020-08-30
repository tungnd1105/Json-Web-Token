package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 2702302985812286828L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private ERole name;
    //
    @Column(length = 50)
    private String note;

}
