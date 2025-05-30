package org.example.currencycenter.service;

import org.example.currencycenter.dto.FinancialSummaryDTO;
import org.example.currencycenter.dto.ResponseEmployeeDTO;
import org.example.currencycenter.dto.ResponseTransactionBreakDown;
import org.example.currencycenter.dto.TransactionDTO;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.EmployeeRepository;
import org.example.currencycenter.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AdminService {

    private EmployeeRepository employeeRepository;
    private TransactionRepository transactionRepository;

    public AdminService(EmployeeRepository employeeRepository,TransactionRepository transactionRepository) {
        this.employeeRepository = employeeRepository;
        this.transactionRepository = transactionRepository;
    }

    public Optional<List<ResponseEmployeeDTO>> getEmployees() {
        return employeeRepository.findAllEmployeesIdAndUsername();
    }

    public ResponseTransactionBreakDown getTransactionBreakDownStats(List<TransactionDTO> transactions){
        int transactions_this_day = 0;
        int transactions_this_week = 0;
        int transactions_this_month = 0;
        int transactions_this_year = 0;
        for (TransactionDTO t : transactions) {
            LocalDateTime transaction_date = t.date();
            LocalDateTime now = LocalDateTime.now();
            //if transaction was made today
            boolean isToday = transaction_date.toLocalDate().equals(now.toLocalDate());
            if (isToday) {
                transactions_this_day += 1;
            }
            // if transaction was made this week
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int transactionWeek = transaction_date.get(weekFields.weekOfWeekBasedYear());
            int currentWeek = now.get(weekFields.weekOfWeekBasedYear());
            if (transactionWeek == currentWeek && transaction_date.getYear() == now.getYear()) {
                transactions_this_week += 1;
            }
            // if transaction was made this month
            if (transaction_date.getMonth().equals(now.getMonth()) && transaction_date.getYear() == now.getYear()) {
                transactions_this_month += 1;
            }
            // if transaction was made this year
            if (transaction_date.getYear() == now.getYear()) {
                transactions_this_year += 1;
            }

        }

        return new ResponseTransactionBreakDown(transactions_this_day, transactions_this_week, transactions_this_month, transactions_this_year);
    }

    public FinancialSummaryDTO computeFinancialSummary(){
        //full transactions value
        BigDecimal fullExchangedAmount = transactionRepository.getTotalExchangedAmount();

        // full buy_transactions value

        BigDecimal fullBUYExchangedAmount = transactionRepository.getTotalBUYExchangedAmount();

        // full sell_transactions value

        BigDecimal fullSELLExchangedAmount = transactionRepository.getTotalSELLExchangedAmount();

        // average exchange amount

        BigDecimal numberOfTransactions = BigDecimal.valueOf(transactionRepository.count());

        BigDecimal avg_amount = fullExchangedAmount.divide(numberOfTransactions,2 , RoundingMode.HALF_UP);
        return new FinancialSummaryDTO(fullExchangedAmount,fullBUYExchangedAmount,fullSELLExchangedAmount,avg_amount);

    }

}
