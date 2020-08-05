package com.spring.reactive.registration.controller;

import com.spring.reactive.registration.document.Customer;
import com.spring.reactive.registration.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
public class RegistrationController {


    @Autowired
    RegistrationRepository registrationRepository;


    @PostMapping("/v1/controller/create")
    public Mono<ResponseEntity<String>> createRegister(@RequestBody Customer customer) {


        return registrationRepository.save(customer)
                .map(customer2 -> new ResponseEntity<>("successfully Registered", HttpStatus.FOUND))
                .defaultIfEmpty(new ResponseEntity<>("Not Registered", HttpStatus.NOT_FOUND));
    }

    @PostMapping("/v1/controller/login")
    public Mono<ResponseEntity<String>> loginCustomer(@RequestBody Customer customer) {
        return registrationRepository.findByUserIdAndPassword(customer.getUserId(), customer.getPassword())
                .map(customer2 -> new ResponseEntity<>("Logged in successfully", HttpStatus.FOUND))
                .defaultIfEmpty(new ResponseEntity<>("No user Found", HttpStatus.NOT_FOUND));
    }


    @PutMapping("/v1/controller/update/{userId}")
    public Mono<ResponseEntity<Customer>> updateItem(@PathVariable String userId, @RequestBody Customer customer) {

        return registrationRepository.findById(userId)
                .flatMap(existingCustomer -> {
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setContactNumber(customer.getContactNumber());
                    existingCustomer.setEmail(customer.getEmail());
                    return registrationRepository.save(existingCustomer);
                })
                .map(updatedCustomer -> new ResponseEntity<>(updatedCustomer, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
