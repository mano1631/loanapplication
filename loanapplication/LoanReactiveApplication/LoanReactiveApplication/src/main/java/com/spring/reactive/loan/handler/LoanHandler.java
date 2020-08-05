package com.spring.reactive.loan.handler;

import com.spring.reactive.loan.entity.LoanEntity;
import com.spring.reactive.loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.awt.*;

@Component
public class LoanHandler {
    @Autowired
    LoanRepository loanRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> applyLoan(ServerRequest serverRequest) {

        Mono<LoanEntity> loanDetails = serverRequest.bodyToMono(LoanEntity.class);
        return loanDetails.flatMap(loan -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanRepository.save(loan), LoanEntity.class).switchIfEmpty(notFound)
        );

    }

    public Mono<ServerResponse> getAllLoan(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(loanRepository.findAll(), LoanEntity.class);


    }
}
