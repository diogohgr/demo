package com.example.demo.http;

import com.example.demo.entity.Transfer;
import com.example.demo.entity.Type;
import com.example.demo.http.dto.TransferDTO;
import com.example.demo.usecase.DemoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoControllerTest {

    @MockBean
    private DemoUseCase useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTest(@Autowired MockMvc mvc) throws Exception {

        var pageRequest = PageRequest.of(0,10);
        var list = List.of(this.createTransferResponse());

        given(useCase.getAll(pageRequest)).willReturn(new PageImpl<>(list,pageRequest,list.size()));

        mvc.perform(get("/v1/transfer")).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    void getEmptyTest(@Autowired MockMvc mvc) throws Exception {
        var pageRequest = PageRequest.of(0,10);
        when(useCase.getAll(pageRequest)).thenReturn(Page.empty());

        mvc.perform(get("/v1/transfer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    public static Transfer createTransferResponse(){
        return new Transfer(new Random().nextLong(), BigDecimal.valueOf(1), BigDecimal.ONE, LocalDate.now(), LocalDate.now().plusDays(2), "1234567", "123456789987", Type.A);
    }

    @Test
    public void saveSucess(@Autowired MockMvc mvc) throws Exception {

        given(useCase.save(any())).willReturn(this.createTransferResponse());
        var request= this.createTransferDTORequest();
        mvc.perform(post("/v1/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.feeAmount").value(BigDecimal.ONE));
    }

    public static TransferDTO createTransferDTORequest(){
        return new TransferDTO(null, BigDecimal.valueOf(1), BigDecimal.ONE, LocalDate.now(), LocalDate.now().plusDays(2), "1234567", "123456789987", Type.A);
    }

}
