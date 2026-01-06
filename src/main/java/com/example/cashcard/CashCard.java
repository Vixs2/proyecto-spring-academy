package com.example.cashcard;

import org.springframework.data.annotation.Id;

/**
 * Un 'Record' es una clase inmutable que actúa como un contenedor de datos.
 * Java genera automáticamente los campos, el constructor, los getters, 
 * equals, hashCode y toString.
 */
record CashCard(@Id Long id, Double amount) {

}
