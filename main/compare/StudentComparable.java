import java.util.*;

public class StudentComparable implements Comparable<StudentComparable> {

    private final int score;

    public StudentComparable(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
        
    @Override
    public int compareTo(StudentComparable other) {
        // return Integer.compare(other.score, this.score); this is the descending order
        return Integer.compare(this.score, other.score); // while this is the ascending order
    }

    public static void main(String[] args) {

        List<StudentComparable> students = Arrays.asList(new StudentComparable[] {
            new StudentComparable(2), new StudentComparable(3), new StudentComparable(1)
        });

        Collections.sort(students);

        for (StudentComparable s : students) {
            System.out.println(s.getScore());
        }
    }
}