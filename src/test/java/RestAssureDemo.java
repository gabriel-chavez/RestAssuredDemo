import Constants.EmployeeEndpoints;
import Entities.Employee;
import Utils.Requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RestAssureDemo  {
    //https://dummy.restapiexample.com/
    private Requests requests=new Requests(EmployeeEndpoints.base_url);
    @Test
    public void getEmployeesTest(){
        /*RestAssured.baseURI="https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .when().get("/employees");
        response.then().log().body();*/

        Response response = requests.get(EmployeeEndpoints.GET_EMPLOYEES);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }
    @Test
    public void getEmployeesWithIdTest(){
      /*  RestAssured.baseURI="https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().pathParam("id","2")
                        .when().get("/employee/{id}");
        response.then().log().body();*/
        Response response = requests.getWithId(EmployeeEndpoints.GET_EMPLOYEE,"2");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(3));
        response.then().assertThat().body("data.id", Matchers.equalTo(2));
        response.then().assertThat().body("data.employee_name", Matchers.equalTo("Garrett Winters"));
        response.then().assertThat().body("data.employee_salary", Matchers.equalTo(170750));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been fetched."));
    }
    @Test
    public void postEmployeeTest() throws JsonProcessingException {
        Employee employee= new Employee();
        employee.setName("Gabriel");
        employee.setAge("30");
        employee.setSalary("13000");
        ObjectMapper mapper = new ObjectMapper();
        String payload= mapper.writeValueAsString(employee);
        /*RestAssured.baseURI="https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .when().post("/create");
        response.then().log().body();*/
        Response response = requests.post(EmployeeEndpoints.POST_EMPLOYEE,payload);
        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("data.name", Matchers.equalTo(employee.getName()));
        response.then().assertThat().body("data.salary", Matchers.equalTo(employee.getSalary()));
        response.then().assertThat().body("data.age", Matchers.equalTo(employee.getAge()));
        response.then().assertThat().body("data.id", Matchers.not(""));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been added."));
    }
    @Test
    public void putEmployeeTest() throws JsonProcessingException {
        Employee employee= new Employee();
        employee.setName("luis");
        employee.setAge("25");
        employee.setSalary("12000");
        ObjectMapper mapper = new ObjectMapper();
        String payload= mapper.writeValueAsString(employee);

       /* RestAssured.baseURI="https://dummy.restapiexample.com/api/v1";


        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .and().pathParam("id","2")
                .when().put("/update/{id}");

        response.then().log().body();*/

        Response response = requests.put(EmployeeEndpoints.PUT_EMPLOYEE,"2",payload);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("status", Matchers.equalTo("success"));
        response.then().assertThat().body("data.name", Matchers.equalTo(employee.getName()));
        response.then().assertThat().body("data.salary", Matchers.equalTo(employee.getSalary()));
        response.then().assertThat().body("data.age", Matchers.equalTo(employee.getAge()));
        response.then().assertThat().body("data.id", Matchers.not("2"));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been updated."));
    }
    @Test
    public void deleteEmployeeTest() throws JsonProcessingException {
       /* RestAssured.baseURI="https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().pathParam("id","2")
                .when().delete("/delete/{id}");
        response.then().log().body();*/
        Response response = requests.delete(EmployeeEndpoints.DELETE_EMPLOYEE,"2");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("status", Matchers.equalTo("success"));
        response.then().assertThat().body("data", Matchers.equalTo("2"));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been deleted"));
    }
}
