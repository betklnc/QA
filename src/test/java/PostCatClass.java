import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostCatClass {

    @Test
    public void PostCatCase1() {
        // https://mocki.io/fake-json-api fake json api oluşturma
        // {"status":"success","data":[{"id":1,"employee_name":"Tiger Nixon","employee_salary":320800,"employee_age":61,"profile_image":""},{"id":2,"employee_name":"Garrett Winters","employee_salary":170750,"employee_age":63,"profile_image":""},{"id":3,"employee_name":"Ashton Cox","employee_salary":86000,"employee_age":66,"profile_image":""},{"id":4,"employee_name":"Cedric Kelly","employee_salary":433060,"employee_age":22,"profile_image":""},{"id":5,"employee_name":"Airi Satou","employee_salary":162700,"employee_age":33,"profile_image":""},{"id":6,"employee_name":"Brielle Williamson","employee_salary":372000,"employee_age":61,"profile_image":""},{"id":7,"employee_name":"Herrod Chandler","employee_salary":137500,"employee_age":59,"profile_image":""},{"id":8,"employee_name":"Rhona Davidson","employee_salary":327900,"employee_age":55,"profile_image":""},{"id":9,"employee_name":"Colleen Hurst","employee_salary":205500,"employee_age":39,"profile_image":""},{"id":10,"employee_name":"Sonya Frost","employee_salary":103600,"employee_age":23,"profile_image":""},{"id":11,"employee_name":"Jena Gaines","employee_salary":90560,"employee_age":30,"profile_image":""},{"id":12,"employee_name":"Quinn Flynn","employee_salary":342000,"employee_age":22,"profile_image":""},{"id":13,"employee_name":"Charde Marshall","employee_salary":470600,"employee_age":36,"profile_image":""},{"id":14,"employee_name":"Haley Kennedy","employee_salary":313500,"employee_age":43,"profile_image":""},{"id":15,"employee_name":"Tatyana Fitzpatrick","employee_salary":385750,"employee_age":19,"profile_image":""},{"id":16,"employee_name":"Michael Silva","employee_salary":198500,"employee_age":66,"profile_image":""},{"id":17,"employee_name":"Paul Byrd","employee_salary":725000,"employee_age":64,"profile_image":""},{"id":18,"employee_name":"Gloria Little","employee_salary":237500,"employee_age":59,"profile_image":""},{"id":19,"employee_name":"Bradley Greer","employee_salary":132000,"employee_age":41,"profile_image":""},{"id":20,"employee_name":"Dai Rios","employee_salary":217500,"employee_age":35,"profile_image":""},{"id":21,"employee_name":"Jenette Caldwell","employee_salary":345000,"employee_age":30,"profile_image":""},{"id":22,"employee_name":"Yuri Berry","employee_salary":675000,"employee_age":40,"profile_image":""},{"id":23,"employee_name":"Caesar Vance","employee_salary":106450,"employee_age":21,"profile_image":""},{"id":24,"employee_name":"Doris Wilder","employee_salary":85600,"employee_age":23,"profile_image":""}],"message":"Successfully! All records has been fetched."}
        // {"id":12,"name":"asgkjdsh","category":{"id":3,"name":"Hljdhskş"},"photoUrls":["ajshgda.com.tr"],"tags":[{"id":5,"name":"test"}],"status":"sold"}

        //given
        RestAssured.baseURI = "https://petstore3.swagger.io/api/v3";
        RequestSpecification httpRequest = RestAssured.given();


        // JSONObject is a class that represents a Simple JSON.    // We can add Key - Value pairs using the put method

        /**
         * Burada API request body'sindeki tüm alanları , alanların tiplerine göre JSONArray ya da JSONObje oluşturarak
         * JSONObjectt ipinde oluşturduğumuz requestBody nesnesine ekliyoruz.
         */

        JSONObject requestBody = new JSONObject();
        requestBody.put("id", 10);
        requestBody.put("name", "doggie");
        requestBody.put("status", "available");

        /**
         * Aşağıdaki gibi object tipinde olan category alanını doğrudan  requestBody içine atamaya izin vermediğinden önce JSONObject tippinde oluşturduğumuz
         * jo1 nesnesi içine id ve name alanlarını  atıyoruz.Daha sonra requestBody nesnesi içine category'nin karşılığı olarak jo1 nesnesini atıyoruz.
         */
        JSONObject jo1 = new JSONObject();
        jo1.put("id", 1);
        jo1.put("name", "Dogs");
        requestBody.put("category", jo1);

        /**
         * Aşağıdaki gibi Array tipinde olan photoUrls field'ı string'lerden oluşan bir array.  Bu array'in içerisine String türünde olan test değerini
         * ja nesnesine ekliyoruz.
         * Daha sonra requestBody nesnesi içine photoUrls karşılığı olarak ja nesnesini atıyoruz.
         */
        JSONArray ja = new JSONArray();
        ja.put("test");
        requestBody.put("photoUrls", ja);

        /**
         * Aşağıdaki gibi object içeren bir Array olduğunda ise bir JSONObject jo nesnesine alanları [int id, string name] atadıktan sonra JSONArray ja2 nesnesine,
         * jo nesnesini atıyoruz.
         * Artık ja2 arrayinin içinde beklediğimiz id ve name alanları eklendiği için bunu da requestBody içine tags alanının karşılığı olarak ja2 nesnesini atıyoruz.
         */

        JSONArray ja2 = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("id", 0);
        jo.put("name", "animal");
        ja2.put(jo);
        requestBody.put("tags", ja2);

        // Add a header stating the Request body is a JSON
        httpRequest.header("Content-Type", "application/json"); // Add the Json to the body of the request

        /**
         * requestBody JSONObject tipinde olduğu için ve body ksımında string bir değer beklendiği için toString() ile string dönüşümü yapıldı.
         */
        httpRequest.body(requestBody.toString()); // Post the request and check the responseResponse

        //when
        Response response = httpRequest.post("/pet");

        //then
        JsonPath jsonPathEvaluator = response.jsonPath();

        /**
         * Response status code'u ve ve response içindeki bazı alanlar alınarak assertion işlemleri yapıldı
         */
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("name"), "doggie");
        Assert.assertEquals(jsonPathEvaluator.get("tags[0].name"), "animal");
        Assert.assertEquals(jsonPathEvaluator.get("photoUrls[0]"), "test");
        Assert.assertEquals(jsonPathEvaluator.get("category.name"), "Dogs");
    }
    @Test
    public void PostCatCase2(){

        RestAssured.baseURI = "https://petstore3.swagger.io/api/v3";
        RequestSpecification httpRequest = RestAssured.given();

        /**
         * Request içindeki alanlar herhangi bir JSONObject ya da JSONArray e atanmadan, string variable oluşturulup API requesti içine gömüldü.
         * Tek tek obje oluşturmak yerine JSON ı string olarak requestBody variable'ına verebiliriz.
         *
         */
       String requestBody = "{\n" +
               "\"id\":10,\n" +
               "\"name\":\"doggie\",\n" +
               "\"category\":{\n" +
               "\"id\":1,\n" +
               "\"name\":\"Dogs\"\n" +
               "},\n" +
               "\"photoUrls\":[\n" +
               "\"string\"\n" +
               "],\n" +
               "\"tags\":[\n" +
               "{\n" +
               "\"id\":0,\n" +
               "\"name\":\"string\"\n" +
               "}\n" +
               "],\n" +
               "\"status\":\"available\"\n" +
               "}";

        // Add a header stating the Request body is a JSON
        httpRequest.header("Content-Type", "application/json"); // Add the Json to the body of the request
        httpRequest.body(requestBody); // Post the request and check the responseResponse
        Response response = httpRequest.post("/pet");


        /**
         * Response status code'u ve ve response içindeki bir alan alınarak assertion işlemleri yapıldı
         */
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("name"), "doggie");
        Assert.assertEquals((int) jsonPathEvaluator.get("category.id"), 1);

    }
}
