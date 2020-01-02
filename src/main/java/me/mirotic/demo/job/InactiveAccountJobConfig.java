package me.mirotic.demo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.mirotic.demo.account.Account;
import me.mirotic.demo.account.AccountRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class InactiveAccountJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AccountRepository accountRepository;


    @Bean
    public Job inactiveAccountJob() {
        return jobBuilderFactory.get("inactiveAccountJob")
                .start(createAccountStep())
                .next(inactiveAccountStep())
                .build();
    }


    @Bean
    public Step createAccountStep() {
        return stepBuilderFactory.get("createAccountStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>> createAccountStep Start");

                    for (int i = 0; i < 10; i++) {
                        Account account = Account.builder()
                                .username("user" + i)
                                .build();

                        accountRepository.save(account);

                        log.info(account.toString());
                    }

                    log.info(">>> createAccountStep Stop");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public Step inactiveAccountStep() {
        return stepBuilderFactory.get("inactiveAccountStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>> inactiveAccountStep Start");

                    List<Account> all = accountRepository.findAllByUpdatedBefore(LocalDateTime.now());
                    all.forEach(account -> {
                        account.inactive();
                        accountRepository.save(account);

                        log.info(account.toString());
                    });

                    log.info(">>> inactiveAccountStep Stop");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
