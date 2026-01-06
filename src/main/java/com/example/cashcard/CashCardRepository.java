package com.example.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface CashCardRepository extends CrudRepository<CashCard, Long>{
    
}
