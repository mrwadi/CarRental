package com.crd.interview.carrental.reservation.init;

import com.crd.interview.carrental.reservation.model.Car;
import com.crd.interview.carrental.reservation.model.CarType;
import com.crd.interview.carrental.reservation.model.Customer;
import com.crd.interview.carrental.reservation.repository.CarRepository;
import com.crd.interview.carrental.reservation.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DevelopmentDataLoader implements CommandLineRunner {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        System.out.println("Init development data...");

        carRepository.saveAll(List.of(
                Car.builder()
                        .carType(CarType.SEDAN)
                        .licensePlate("KK1111")
                        .build(),
                Car.builder()
                        .carType(CarType.SEDAN)
                        .licensePlate("KK2222")
                        .build(),
                Car.builder()
                        .carType(CarType.SEDAN)
                        .licensePlate("KK3333")
                        .build(),
                Car.builder()
                        .carType(CarType.SUV)
                        .licensePlate("KK4444")
                        .build(),
                Car.builder()
                        .carType(CarType.SUV)
                        .licensePlate("KK55555")
                        .build(),
                Car.builder()
                        .carType(CarType.VAN)
                        .licensePlate("KK6666")
                        .build()
        ));

        customerRepository.saveAll(List.of(
                Customer.builder().email("first@t.com").build(),
                Customer.builder().email("second@t.com").build()
        ));

    }
}
