package org.rsm;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.hasItems;

public class RsmResourceTest {
    @Test
    public void should_provide_all_customers() {
        get("rsm/rest-api/customers").then().assertThat().body("name", hasItems("Monica","Chandler"));
    }
}