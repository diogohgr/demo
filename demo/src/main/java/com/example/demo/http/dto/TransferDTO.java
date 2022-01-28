package com.example.demo.http.dto;

import com.example.demo.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TransferDTO {

    private Long id;

    private BigDecimal amount;

    private BigDecimal feeAmount;

    private LocalDate createdDate;

    private LocalDate transferDate;

    private String accountOrigin;

    private String accountDestination;

    private Type type;


}
