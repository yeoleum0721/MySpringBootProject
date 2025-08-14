package com.rookies4.myspringboot.entity;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name="customers")
@Getter @Setter
@DynamicUpdate
public class Customer {
    //primary Key, PK값을 Persistence Provider가 결정해라
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Unique한 값을 가져야하고, Null값을 허용하지 않음
    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt = LocalDateTime.now();
}
