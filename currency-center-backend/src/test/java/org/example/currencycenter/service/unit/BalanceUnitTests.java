package org.example.currencycenter.service.unit;


import org.example.currencycenter.repository.BalanceRepository;
import org.example.currencycenter.service.BalanceService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BalanceUnitTests {

    @Mock
    private BalanceRepository repo;
    @InjectMocks
    private BalanceService service;






}
