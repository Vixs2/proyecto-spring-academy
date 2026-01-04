package com.example.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prueba de Integración para la aplicación CashCard.
 * @SpringBootTest inicia el servidor en un puerto aleatorio para evitar conflictos.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate; // Cliente HTTP para simular peticiones del usuario

    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
        // --- 1. ACTUAR (Fase 'When') ---
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);

        // --- 2. VALIDAR ESTADO (Fase 'Then') ---
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // --- 3. ANALIZAR EL CONTENIDO (JSON Parsing) ---
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        // --- 4. VALIDAR DATOS DEL ID ---
        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(99);

        // --- 5. VALIDAR DATOS DEL MONTO (Amount) ---
        Double amount = documentContext.read("$.amount");
        assertThat(amount).isNotNull();
        assertThat(amount).isEqualTo(123.45);
    }

    @Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        // --- 1. ACTUAR (Fase 'When') ---
        // Intentamos obtener una tarjeta con un ID que sabemos que no existe (1000)
        ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);

        // --- 2. VALIDAR ESTADO (Fase 'Then') ---
        // El servidor NO debe encontrar el recurso, por lo tanto debe responder 404 NOT FOUND
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // --- 3. VALIDAR CUERPO VACÍO ---
        // Al no existir el recurso, lo ideal es que el cuerpo de la respuesta esté en blanco o vacío
        assertThat(response.getBody()).isBlank();
    }
}