package com.spring.reactive.registration.handler;


import com.spring.reactive.registration.document.Customer;
import com.spring.reactive.registration.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class RegistrationHandler {
    @Autowired
    RegistrationRepository registrationRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> createCustomer(ServerRequest serverRequest) {

        Mono<Customer> newCustomer = serverRequest.bodyToMono(Customer.class);

        return newCustomer.flatMap(customer -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registrationRepository.save(customer), Customer.class));
    }

    public Mono<ServerResponse> userLogin(ServerRequest serverRequest) {
        Mono<Customer> existCustomer = serverRequest.bodyToMono(Customer.class);
        return existCustomer.flatMap(login -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(registrationRepository.findByUserIdAndPassword(login.getUserId(), login.getPassword()), Customer.class)
                .switchIfEmpty(notFound));

    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("userId");

        Mono<Customer> update = serverRequest.bodyToMono(Customer.class)
                .flatMap(updateCustomer -> {
                    Mono<Customer> existUser = registrationRepository.findById(id)
                            .flatMap(currentCustomer -> {
                                currentCustomer.setAddress(updateCustomer.getAddress());
                                currentCustomer.setContactNumber(updateCustomer.getContactNumber());
                                currentCustomer.setEmail(updateCustomer.getEmail());

                                return registrationRepository.save(currentCustomer);
                            });
                    return existUser;
                });

        return update.flatMap(updated -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON).body(fromObject(updated)).switchIfEmpty(notFound));

    }


}
