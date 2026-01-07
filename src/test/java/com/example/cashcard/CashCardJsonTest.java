package com.example.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
/*
        La anotación @JsonTest es una herramienta de Spring Boot 
        que se utiliza para realizar pruebas de "rebanada" (slice testing) 
        enfocadas exclusivamente en la capa de JSON de tu aplicación. */
@JsonTest
class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    // Test de Serialización (Objeto -> JSON)
    /*
        Es el proceso de convertir un objeto que vive en la memoria de tu programa 
        (como tu record CashCard) en un formato que se pueda guardar o enviar a través de internet
        (como un texto JSON, un archivo XML o una secuencia de bytes).
     */
    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("/expected.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
             .isEqualTo(123.45);
    }

    // NUEVO: Test de Deserialización (JSON -> Objeto)
    /*
        Es el proceso inverso. Consiste en tomar un formato de datos 
        (como un texto JSON que llega de una API) y transformarlo de nuevo
        en un objeto vivo en la memoria de Java para poder usar sus métodos y atributos.
     */
    @Test
    void cashCardDeserializationTest() throws IOException {
       String expected = """
               {
                   "id": 99,
                   "amount": 123.45
               }
               """;
       
       // Comparamos que el objeto creado desde el JSON sea igual a uno construido manualmente
       assertThat(json.parse(expected))
               .isEqualTo(new CashCard(99L, 123.45));
       
       // Verificamos campo por campo entrando al objeto parseado
       assertThat(json.parseObject(expected).id()).isEqualTo(99L);
       assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}