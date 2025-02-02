package com.study.projectvoucher.config;

import com.study.projectvoucher.domain.contract.ContractEntity;
import com.study.projectvoucher.domain.contract.ContractRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class LocalDataInit {


    @Bean
    public CommandLineRunner commandLineRunner(ContractRepository contractRepository) {
        return args -> {
           var contract1 = new ContractEntity("CT001",
                    LocalDate.now().minusDays(7L),
                    LocalDate.now().plusDays(7L), 366 * 5);

            var contract2 = new ContractEntity("CT010",
                    LocalDate.now().minusDays(30L),
                    LocalDate.now().minusDays(7L), 366 * 5);

            contractRepository.save(contract1);
            contractRepository.save(contract2);
        };
    }
}
