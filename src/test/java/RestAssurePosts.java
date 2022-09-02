import Constants.PostsEndpoints;
import Entities.Posts;
import Utils.Requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RestAssurePosts {
    private Requests requests=new Requests(PostsEndpoints.base_url);
    @Test
    public void getPostsTest(){
        Response response = requests.get(PostsEndpoints.GET_POSTS);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }
    @Test
    public void getPostsWithIdTest(){

        Response response = requests.getWithId(PostsEndpoints.GET_POST,"2");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(4));
        response.then().assertThat().body("userId", Matchers.equalTo(1));
        response.then().assertThat().body("id", Matchers.equalTo(2));
    }
    @Test
    public void postPostsTest() throws JsonProcessingException {
        Posts posts= new Posts();
        posts.setTitle("Prueba 123");
        posts.setBody("prueba de body");
        posts.setUserId("1");
        ObjectMapper mapper = new ObjectMapper();
        String payload= mapper.writeValueAsString(posts);

        Response response = requests.post(PostsEndpoints.POST_POSTS,payload);
        response.then().assertThat().statusCode(201 );

        response.then().assertThat().body("title", Matchers.equalTo(posts.getTitle()));
        response.then().assertThat().body("body", Matchers.equalTo(posts.getBody()));
        response.then().assertThat().body("userId", Matchers.equalTo(posts.getUserId()));
        response.then().assertThat().body("id", Matchers.not(""));

    }
    @Test
    public void putPostsTest() throws JsonProcessingException {
        Posts posts= new Posts();
        posts.setTitle("Prueba 123");
        posts.setBody("prueba de body");
        posts.setUserId("1");
        ObjectMapper mapper = new ObjectMapper();
        String payload= mapper.writeValueAsString(posts);

        Response response = requests.put(PostsEndpoints.PUT_POSTS,"2",payload);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("title", Matchers.equalTo(posts.getTitle()));
        response.then().assertThat().body("body", Matchers.equalTo(posts.getBody()));
        response.then().assertThat().body("userId", Matchers.equalTo(posts.getUserId()));
        response.then().assertThat().body("id", Matchers.equalTo(2));
    }
    @Test
    public void deletePostsTest() throws JsonProcessingException {
        Response response = requests.delete(PostsEndpoints.DELETE_POSTS,"2");
        response.then().assertThat().statusCode(200);
    }
}
