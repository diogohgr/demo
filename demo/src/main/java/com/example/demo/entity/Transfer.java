package com.example.demo.entity;

import lombok.*;
import lombok.experimental.Accessors;
import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TRANSFER")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "FEE_AMOUNT")
    private BigDecimal feeAmount;

    @Column(columnDefinition = "DATE", name = "CREATED_DATE")
    private LocalDate createdDate;

    @Column(columnDefinition = "DATE", name = "TRANSFER_DATE")
    private LocalDate transferDate;

    @Column(name = "ORIGIN_ACCOUNT")
    private String origin;

    @Column(name = "DESTINATION_ACCOUNT")
    private String destination;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

}
