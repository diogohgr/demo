package com.example.demo.http;

import com.example.demo.entity.Transfer;
import com.example.demo.http.dto.TransferDTO;
import com.example.demo.http.mapper.DemoMapper;
import com.example.demo.usecase.DemoUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/transfer")
public class DemoController {

    private final DemoUseCase demoUseCase;
    private final DemoMapper demoMapper;

    DemoController(DemoUseCase demoUseCase, DemoMapper demoMapper){
        this.demoUseCase = demoUseCase;
        this.demoMapper = demoMapper;
    }

    @GetMapping
    Page<TransferDTO> getAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size){

        var pageRequest = PageRequest.of(page, size);
        List<TransferDTO> transferDTOList = demoUseCase.getAll(pageRequest).stream().toList().stream().map(it ->{
            var dto = demoMapper.convertModelToDTO(it);
            return dto;
        }).collect(Collectors.toList());

        return  new PageImpl<>(transferDTOList,pageRequest,transferDTOList.size());
    }

    @PostMapping
    TransferDTO save(@RequestBody TransferDTO transferDTO){

        Transfer persistedModel = demoUseCase.save(demoMapper.convertDTOToModel(transferDTO));

        return demoMapper.convertModelToDTO(persistedModel);
    }

}
