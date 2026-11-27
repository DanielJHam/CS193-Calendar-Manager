import java.time.OffsetDateTime;
public class Assignment {
    private String name;
    private OffsetDateTime dueDate;

    public Assignment( String name, String dueDate){
        this.name = name;
        this.dueDate = OffsetDateTime.parse(dueDate);
    }

    public Assignment(String name) {
        this.name = name;
        dueDate = null;
    }
    public String getName(){
        return name;
    }

    public OffsetDateTime getDueDate(){
        return dueDate;
    }

}
