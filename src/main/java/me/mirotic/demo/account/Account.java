package me.mirotic.demo.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@DynamicUpdate
@Getter
@ToString
@NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private Boolean inactive;

    private LocalDateTime updated;

    @Builder
    private Account(String username) {
        this.username = username;
        this.inactive = Boolean.FALSE;
        this.updated = LocalDateTime.now();
    }

    public void inactive() {
        this.inactive = Boolean.TRUE;
    }

}
