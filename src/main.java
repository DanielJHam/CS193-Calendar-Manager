import java.util.ArrayList;
import java.time.OffsetDateTime;
public class main {
    public static void main(String[] args) {
        ArrayList<Assignment> assignments;
        String[] information = ReaderWriter.readInfo();
        Edstem edstem = new Edstem(information[0]);
        edstem.automate();
        assignments = edstem.getAssignments();
        assignments.removeIf(a -> a.getDueDate().isBefore((OffsetDateTime.now())));
        for(Assignment a : assignments) {
            IO.println(a.getName());
        }
    }

}

