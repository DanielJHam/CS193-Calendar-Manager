import java.util.ArrayList;
import java.time.OffsetDateTime;
public class main {
    public static void main(String[] args) {
        ArrayList<Assignment> assignments;
        String[] information = ReaderWriter.readInfo();
        Edstem edstem = new Edstem(information[0]);
        //put these two in seperate because the other 2 places isn't guaranteed a JSON or put directly into the calendar
        edstem.automate();
        assignments = edstem.getAssignments();

        //This line is because assignments that have not been submitted even when done were going through
        //An example would be HW11, where edstem does not show Submitted, just Closed and DueDate
        assignments.removeIf(a -> a.getDueDate().isBefore((OffsetDateTime.now())));
        for(Assignment a : assignments) {
            IO.println(a.getName());
        }
    }

}

