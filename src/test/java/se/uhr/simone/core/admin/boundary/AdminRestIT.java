package se.uhr.simone.core.admin.boundary;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import se.uhr.simone.common.rs.ResponseBodyRepresentation;
import se.uhr.simone.common.rs.ResponseRepresentation;

@QuarkusTest
class AdminRestIT {

	@Test
	void shouldChangeGlobalResponceStatusCode() throws Exception {
		given().when().get("/order").then().statusCode(200).extract().body().jsonPath().getList(".", String.class).isEmpty();

		given().body("400").when().contentType(ContentType.TEXT).put("/admin/rs/response/code/global").then().statusCode(200);

		given().when().get("/order").then().statusCode(400);

		given().when().delete("/admin/rs/response/code/global").then().statusCode(200);

		given().when().get("/order").then().statusCode(200);

	}

	@Test
	void shouldChangeGResponceStatusCodeForPath() throws Exception {
		given().when().get("/order").then().statusCode(200).extract().body().jsonPath().getList(".", String.class).isEmpty();

		ResponseRepresentation action = ResponseRepresentation.of("/order", 403);

		given().body(action).when().contentType(ContentType.JSON).put("/admin/rs/response/code/path").then().statusCode(200);

		given().when().get("/order").then().statusCode(403);

		given().body("/order").when().delete("/admin/rs/response/code/path").then().statusCode(200);

		given().when().get("/order").then().statusCode(200);

	}

	@Test
	void shouldChangeGResponceStatusCodeAndBodyForPath() throws Exception {
		given().when().get("/order").then().statusCode(200).extract().body().jsonPath().getList(".", String.class).isEmpty();

		ResponseBodyRepresentation action = ResponseBodyRepresentation.of("/order", 403, "[\"hello\"]");

		given().body(action).when().contentType(ContentType.JSON).put("/admin/rs/response/body").then().statusCode(200);

		List<String> orders =
				given().when().get("/order").then().statusCode(403).extract().body().jsonPath().getList(".", String.class);

		assertThat(orders).hasSize(1).first().isEqualTo("hello");

		given().body("/order").when().delete("/admin/rs/response/body").then().statusCode(200);

		given().when().get("/order").then().statusCode(200).extract().body().jsonPath().getList(".", String.class).isEmpty();
	}
}
