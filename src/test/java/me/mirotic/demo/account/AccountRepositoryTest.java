package me.mirotic.demo.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    void save() {
        String username = "test";
        Account account = Account.builder()
                .username(username)
                .build();

        account = repository.save(account);

        assertThat(account.getId()).isNotNull();
        assertThat(account.getUsername()).isEqualTo(username);
        assertThat(account.getDormant()).isFalse();
        assertThat(account.getCreated()).isBefore(LocalDateTime.now());
    }
}