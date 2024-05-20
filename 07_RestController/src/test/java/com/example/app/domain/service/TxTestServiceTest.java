package com.example.app.domain.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TxTestServiceTest {

    @Autowired
    private TxTestService txTestService;

    @Test
    @Transactional(rollbackFor =Exception.class,transactionManager = "dataSourceTransactionManager")
    public void v1(){
        txTestService.txMapper();
    }
}