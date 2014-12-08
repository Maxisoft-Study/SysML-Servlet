package maxime.mica;

import com.squareup.okhttp.*;

import java.io.IOException;

public class TestPutHttp {
    MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public static void main(String... args) throws IOException {
        TestPutHttp testPutHttp = new TestPutHttp();
        Response post = testPutHttp.post("http://127.0.0.1:8080/documents/post.xml", "<doc/>");
        System.out.println(post.code());
        System.out.println(post.body().string());
    }

    Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
