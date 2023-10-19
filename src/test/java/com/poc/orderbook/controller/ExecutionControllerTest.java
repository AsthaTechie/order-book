package com.poc.orderbook.controller;

import com.poc.orderbook.entity.Execution;
import com.poc.orderbook.service.ExecutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class ExecutionControllerTest {

    private static final String EXECUTION_ENDPOINT = "/api/execution/1/1";
    private MockMvc mockMvc;

    @InjectMocks
    private ExecutionController executionController;

    @Mock
    private ExecutionService executionService;

    private Execution mockExecution1, mockExecution2;
    private List<Execution> mockExecutionList;

    @BeforeEach
    void SetUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(executionController).build();
        executionController = new ExecutionController(executionService);
        mockExecution1 = Execution.builder().eid("1").fid(1).quantity(20).eType("offer").price(50).isExecuted(Boolean.FALSE).build();
        mockExecution2 = Execution.builder().eid("2").fid(1).quantity(10).eType("offer").price(50).isExecuted(Boolean.FALSE).build();
        mockExecutionList = List.of(mockExecution1, mockExecution2);
    }

    @Test
    void executeOrders_shouldReturnStatusOk() throws Exception {
        when(executionService.executeOrderByFid(1, 1)).thenReturn(mockExecutionList);

        this.mockMvc.perform(post(EXECUTION_ENDPOINT)).andExpectAll(status().isOk(),
                jsonPath("$", hasSize(2)));
    }
}
