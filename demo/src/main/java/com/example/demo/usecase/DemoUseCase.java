package com.example.demo.usecase;

import com.example.demo.entity.Transfer;
import com.example.demo.entity.Type;
import com.example.demo.entity.exception.DateIntervalInvalidException;
import com.example.demo.entity.exception.InvalidTransferException;
import com.example.demo.entity.exception.UndefinedFeeException;
import com.example.demo.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@Service
public class DemoUseCase {

    @Autowired
    private DemoRepository demoRepository;

    public Page<Transfer> getAll(PageRequest pageRequest) {

        return demoRepository.findAll(pageRequest);

    }

    public Transfer save(Transfer transfer) {
        
        Type type = this.validateType(transfer);
        transfer.setType(type);
        transfer.setFeeAmount(this.calculateTaxAmount(transfer));
        return demoRepository.save(transfer);

    }

    private BigDecimal calculateTaxAmount(Transfer transfer) {

        long difference = Duration.between(transfer.getCreatedDate().atStartOfDay(), transfer.getTransferDate().atStartOfDay()).toDays();

        if (transfer.getType() != null) {
            switch (transfer.getType()) {
                case A:
                    return BigDecimal.valueOf(3L).add(transfer.getAmount().multiply(BigDecimal.valueOf(0.03)));
                case B:
                    if(difference == 0){
                        return BigDecimal.valueOf(12L);
                    }
                    return BigDecimal.valueOf(12L).multiply(BigDecimal.valueOf(difference));
                case C:
                    if (difference > 10 && difference <= 20) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.08));
                    }
                    if (difference > 20 && difference <= 30) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.06));
                    }
                    if (difference > 30 && difference <= 40) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.04));
                    }
                    if (difference > 40 && transfer.getAmount().longValue() > 100000) {
                        return transfer.getAmount().multiply(BigDecimal.valueOf(0.02));
                    }
            }
        }
        throw new UndefinedFeeException("Undefined tax");

    }

    private Type validateType(Transfer transfer) {

        if(transfer.getOrigin().equals(transfer.getDestination())) {
            throw new InvalidTransferException("Account origin and destination cannot be the same");
        }

        if(transfer.getTransferDate().isBefore(LocalDate.now())){
            throw new DateIntervalInvalidException("Invalid Date interval");
        }
        if(transfer.getTransferDate().isEqual(LocalDate.now())){
            return Type.A;
        }
        if(Duration.between(LocalDate.now().atStartOfDay(), transfer.getTransferDate().atStartOfDay()).toDays() <= 10){
            return Type.B;
        }
        if(Duration.between(LocalDate.now().atStartOfDay(), transfer.getTransferDate().atStartOfDay()).toDays() > 10){
            return Type.C;
        }

        return null;
    }
}
