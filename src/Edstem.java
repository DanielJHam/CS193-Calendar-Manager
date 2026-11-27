import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
public class Edstem {
    HttpClient client;
    private static final String URLS = "https://edstem.org";
    private String apiKey;
    private ArrayList<String> courseID;
    private ArrayList<Assignment> assignments;

    public Edstem(String key) {
        apiKey = key;
        courseID = new ArrayList<>();
        assignments = new ArrayList<>();
        client = HttpClient.newHttpClient();
    }

    private String buildAndGetCourses() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URLS))
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200) {
            ReaderWriter.writeError("EDSTEM error " + response.statusCode());
            return null;
        }
        return response.body();
    }

    public void getCourses(){
        try{
            Object o = JSONValue.parse(this.buildAndGetCourses());
            JSONObject jsonCourses = (JSONObject) o;
            JSONArray courses = (JSONArray) jsonCourses.get("courses");
            for (Object jsonCourse: courses) {
                JSONObject itemData = (JSONObject) jsonCourse;
                JSONObject courseData = (JSONObject) itemData.get("course");
                String status = (String) courseData.get("status");
                if (status.equals("active")) {
                    courseID.add((String) courseData.get("id"));
                }
            }
        } catch (IOException i) {
            ReaderWriter.writeError("Invalid Input");
        } catch (InterruptedException e) {
            ReaderWriter.writeError("Interrupted Connection");
        }
    }
    public void getAssignmentJSON() {
        try {
            for (String id : courseID) {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URLS + "/" + id + "/lessons?limit=100"))
                        .header("Authorization", "Bearer " + apiKey)
                        .header("Accept", "application/json")
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    ReaderWriter.writeError("EDSTEM Error " + response.statusCode());
                }
                getLessons(response.body());
            }
        } catch(IOException i) {
            ReaderWriter.writeError("Invalid Input");
        } catch (InterruptedException r) {
            ReaderWriter.writeError("Interrupted Connection");
        }
    }

    public void getLessons(String courseData) {
            try {
                Object o = JSONValue.parse(courseData);
                JSONObject jsonContents = (JSONObject) o;
                JSONArray lessons = (JSONArray) jsonContents.get("lessons");
                for(Object lesson : lessons) {
                    JSONObject lessonData = (JSONObject) lesson;
                    String status = (String) lessonData.get("state");
                    if(!(status.equals("completed"))) {
                        String title = (String) lessonData.get("title");
                        String date = (String) lessonData.get("due_at");
                        if (date != null) {
                            assignments.add(new Assignment(title, date));
                        } else {
                            assignments.add(new Assignment(title));
                        }
                    }
                }
            } catch (ClassCastException c){
                ReaderWriter.writeError("Class not found");
            } catch (NullPointerException n){
                ReaderWriter.writeError("Read Null");
            }
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void automate() {
        getCourses();
        getAssignmentJSON();
    }

}