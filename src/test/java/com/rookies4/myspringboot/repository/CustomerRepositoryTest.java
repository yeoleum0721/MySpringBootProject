package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Customer;
import org.hibernate.annotations.DynamicUpdate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Transactional//??????? 이거 뭘까? create-drop하면 같은 거아닌가? 다른 기능이 있는가? - rollback은  test하나씩
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    //Customer 등록
    @Test
    @Transactional
    @Rollback(value = false)//Rollback 처리하지 마세요
    //@Disabled
    void testCreateCustomer(){
        //Given(준비단계)
        Customer customer = new Customer();
        customer.setCustomerId("AC003");
        customer.setCustomerName("스프링FW3");
        //When(실행단계)
        Customer savedCustomer = customerRepository.save(customer);
        //Then(검증단계)
        //등록된 Customer Entity객체가 NULL이 아닌지 검증하기
        assertThat(savedCustomer).isNotNull();
        //등록된 customer의 name값이 동일한지 검증하기
        assertThat(savedCustomer.getCustomerName()).isEqualTo("스프링FW3");
    }
    //Customer조회
    @Test
    void testFindCustomer(){
        //findById() 호출
        Optional<Customer> customerById = customerRepository.findById(1L);
        //assertThat(customerById).isNotEmpty();
        //assertThat(customerById).isEmpty();
        if (customerById.isPresent()){
            Customer existCustomer = customerById.get();
            assertThat(existCustomer.getId()).isEqualTo((1L));
        }

        //Optional의 T orElseGet(Supplier) 고객번호(AC001)가 존재하는 경우
        //Supplier의 추상메서드 T get()
        Optional<Customer> customerByCustomerId = customerRepository.findByCustomerId("AC001");
        Customer acc001Customer = customerByCustomerId.orElseGet(() -> new Customer());
        assertThat(acc001Customer.getCustomerName()).isEqualTo("스프링부트");

        //고객번호(AC003)가 존재하지 않는 경우
        Customer notFountCustomer =customerRepository.findByCustomerId("AC003").orElseGet(() -> new Customer());
        assertThat(notFountCustomer.getCustomerName()).isNull();
    }
    //Customer 조회, 존재하지 않으면 예외발생
    @Test
    @Disabled
    void testNotFoundCustomer(){
        Customer notFountCustomer =customerRepository.findByCustomerId("AC003")
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        assertThat(notFountCustomer.getCustomerName()).isNull();
    }

    //Customer 업데이트하기
    @Test
    //@Rollback(value = false)//Rollback 처리하지 마세요
    void testUpdateCustomer(){
        Customer customer = customerRepository.findByCustomerId("AC001")
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customer.setCustomerName("마이둘리2");
        customerRepository.save(customer);

    }
}