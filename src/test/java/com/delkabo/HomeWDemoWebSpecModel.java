package com.delkabo;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

public class HomeWDemoWebSpecModel {

    @Test
    void getItemsInCard() {

        String document =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookie("Nop.customer=873ac1a8-5e6b-4c60-b30e-da8238d1aa54;")
                        .when()
                        .get("http://demowebshop.tricentis.com/")
                        .then()
                        .extract().asString();

        String cardItems = document.substring(document.lastIndexOf("cart-qty")
                + 11, document.indexOf("span", document.lastIndexOf("cart-qty")) - 3);
        int countCard = Integer.parseInt(cardItems) + 1;
        System.out.println(countCard + " LOOK ON IT!!!!!!!!!!!!");

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=873ac1a8-5e6b-4c60-b30e-da8238d1aa54;")
                .body("product_attribute_16_5_4=14"
                        + "&product_attribute_16_6_5=15&product_attribute_16_3_6=18"
                        + "&product_attribute_16_4_7=44"
                        + "&product_attribute_16_8_8=22"
                        + "&addtocart_16.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/16/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your" +
                        " <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is(String.format("(%s)", countCard)));
    }

    @Test
    @Description("Ввод в поисковую строку Laptop")
    void enterInSearchBar() {
        String response =
        given()
                .contentType("application/json;")
                .when()
                .get("http://demowebshop.tricentis.com/catalog/searchtermautocomplete?term=laptop")
                .then()
                .log().all()
                .statusCode(200)
                .body("label", hasSize(greaterThan(0)))
                .extract().asString();
        assertThat(response).isNotEqualTo("");
    }
        // todo
        /*
                HttpResponse<String> response = Unirest.get("http://demowebshop.tricentis.com/catalog/searchtermautocomplete?term=laptop")
  .header("X-Requested-With", "XMLHttpRequest")
  .header("Cookie", "ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687; Nop.customer=873ac1a8-5e6b-4c60-b30e-da8238d1aa54")
  .asString();
                */

}

