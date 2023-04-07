package br.feliperossi.task.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend/";
    }
    @Test
    public void retornarTodasTarefasComSucesso(){
        RestAssured
                .given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200); // Código 200 significa com sucesso
    }

    @Test
    public void adicionarTerefaComSucesso(){
        RestAssured
                .given()
                    .body("{ \"task\": \"Teste Via API\", \"dueDate\": \"2023-04-10\"}") // Passando no Body o que vamos fazer no Teste
                    .contentType(ContentType.JSON) // Informando para API que estamos enviando um conteúdo do tipo JSON
                .when()
                    .post("/todo")
                .then()
                    .statusCode(201);
    }

    @Test
    public void erroAoAdicionarTarefaInvalida(){
        RestAssured
                .given()
                    .body("{ \"task\": \"Teste Via API\", \"dueDate\": \"2022-04-10\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(400)
                    // Informando que a mensagem que deve exibir é "Due date must not be in past"
                    .body("message", CoreMatchers.is("Due date must not be in past"));

    }


}


