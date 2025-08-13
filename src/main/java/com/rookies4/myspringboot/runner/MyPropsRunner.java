package com.rookies4.myspringboot.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyPropsRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    private String name;

    @Value("${myboot.age}")
    private int age;

    @Autowired
    private Environment environment;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Properties myboot.name = " + name);
        System.out.println("Properties myboot.age = " + age);
        System.out.println("Properties myboot.fullName = " + environment.getProperty("myboot.fullName"));

        System.out.println("VM Arguments = " + args.containsOption("foo")); //false
        System.out.println("Program Arguments = " + args.containsOption("bar")); //true

        //Program Argument의 모든 이름을 출력하기
        for(String argName: args.getOptionNames()){
            System.out.println("아규먼트 이름 = " + argName);
        }
        //args.getOptionNames() 의 리턴타입 Set<String>
        //Iterable의 foreach(Consumer) 메서드 호출하기
        //Consumer의 추상메서드 void accept(T t)
        //1. 익명의 Inner class ( Anonymous Inner Class)
        args.getOptionNames().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("Inner Class 아규먼트 이름 =  = " + s);
            }
        });
        System.out.println("===> 람다함수");
        //2. 함수형 인터페이스 (람다함수)
        args.getOptionNames().forEach(name -> System.out.println(name));
        System.out.println("===> Method Reference");
        //3. Method Reference (아규먼트를 생략한 람다함수)
        args.getOptionNames().forEach(System.out::println);

    }
}