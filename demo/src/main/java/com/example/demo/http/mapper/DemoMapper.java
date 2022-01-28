package com.example.demo.http.mapper;

import com.example.demo.entity.Transfer;
import com.example.demo.http.dto.TransferDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoMapper {

    public Transfer convertDTOToModel(TransferDTO transferDTO){
        return Transfer.builder()
                .transferDate(transferDTO.getTransferDate())
                .createdDate(LocalDate.now())
                .amount(transferDTO.getAmount())
                .destination(transferDTO.getAccountDestination())
                .origin(transferDTO.getAccountOrigin())
                .build();
    }

    public TransferDTO convertModelToDTO(Transfer transfer){
        TransferDTO dto =  new TransferDTO(
                transfer.getId(),
                transfer.getAmount(),
                transfer.getFeeAmount(),
                transfer.getCreatedDate(),
                transfer.getTransferDate(),
                transfer.getOrigin(),
                transfer.getDestination(),
                transfer.getType()
                );
        return dto;
    }

}
