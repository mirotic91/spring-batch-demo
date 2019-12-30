package me.mirotic.demo.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@ToString
@NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private Boolean dormant;

    private LocalDateTime created;

    @Builder
    private Account(String username) {
        this.username = username;
        this.dormant = Boolean.FALSE;
        this.created = LocalDateTime.now();
    }

}
