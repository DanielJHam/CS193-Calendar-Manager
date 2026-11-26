import java.time.LocalDateTime;
public class Assignment {
    private String course;
    private String name;
    private LocalDateTime dueDate;

    public Assignment(String course, String name, LocalDateTime dueDate) {
        this.course = course;
        this.name = name;
        this.dueDate = dueDate;
    }

    public String getCourse {
        return course;
    }

    public String getName {
        return name;
    }

    public LocalDateTime getDueDate {
        return dueDate;
    }

}
