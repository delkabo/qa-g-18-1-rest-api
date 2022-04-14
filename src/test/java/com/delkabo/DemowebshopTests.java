package com.delkabo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemowebshopTests {

    @Test
    void addToCardAsNewUserTest() {
//        String response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
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
                        .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                        .body("updatetopcartsectionhtml", is("(1)"))
                        .extract().response().asString();
//        System.out.println(response);
    }

    @Test
    void addToCardWithCookieTest1() {
//        String response =
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
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
//                .extract().response().asString();
//        System.out.println(response);
    }

    // todo
                /*
HttpResponse<String> response = Unirest.post("http://demowebshop.tricentis.com/addproducttocart/details/16/1")
  .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
  .header("Cookie", "Nop.customer=873ac1a8-5e6b-4c60-b30e-da8238d1aa54; __utmc=78382081; __utmz=78382081.1647876836.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utma=78382081.2090393981.1647876836.1649707991.1649933542.4; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=16&RecentlyViewedProductIds=72; __utmt=1; __atuvc=10%7C15; __atuvs=625801b98526ac64003; __utmb=78382081.7.10.1649933542; ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687; Nop.customer=873ac1a8-5e6b-4c60-b30e-da8238d1aa54")
  .body("product_attribute_16_5_4=14&product_attribute_16_6_5=15&product_attribute_16_3_6=18&product_attribute_16_4_7=44&product_attribute_16_8_8=22&addtocart_16.EnteredQuantity=1")
  .asString();
  .asString();*/

}
