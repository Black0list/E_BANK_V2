package main.java.utils;

import main.java.repositories.BankFeeRepository;
import main.java.repositories.CreditRepository;
import main.java.repositories.FeeRuleRepository;
import main.java.repositories.interfaces.BankFeeRepositoryIntf;
import main.java.repositories.interfaces.CreditRepositoryIntf;
import main.java.repositories.interfaces.FeeRuleRepositoryIntf;
import main.java.services.CreditService;
import main.java.services.FeeRuleService;

import java.util.concurrent.*;
import java.sql.SQLException;

public class Schedule {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final CreditService creditService;
    private final FeeRuleRepositoryIntf feeRuleRepo = new FeeRuleRepository();
    private final FeeRuleService serviceFeeRule = new FeeRuleService(feeRuleRepo);
    private final CreditRepository creditRepo = new CreditRepository();
    private final BankFeeRepositoryIntf bankFeeRepo = new BankFeeRepository();

    public Schedule() {
        this.creditService = new CreditService(creditRepo, serviceFeeRule, bankFeeRepo);
    }

    public void start() {
        Runnable task = () -> {
            try {
//                System.out.println("\n[SCHEDULER] Running credit update job...\n");
                creditService.updateAllCredits();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);
    }
}
