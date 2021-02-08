package uk.gov.hmcts.probate.functional.tests;

import io.restassured.RestAssured;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.hmcts.probate.functional.IntegrationTestBase;

@RunWith(SpringIntegrationSerenityRunner.class)
public class PaymentUpdateControllerFunctionalTests extends IntegrationTestBase{
    private static final String PAYMENT_UPDATE_URL = "/payment-updates";

    @Test
    public void updatePaymentDetails() {
        String draftJsonStr = utils.getJsonFromFile("payment.json");
        RestAssured.given()
                .relaxedHTTPSValidation()
                .headers(utils.getCitizenHeaders())
                .body(draftJsonStr)
                .when()
                .put(PAYMENT_UPDATE_URL)
                .then()
                .assertThat()
                .statusCode(200);
    }
}
