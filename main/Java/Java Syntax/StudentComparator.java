import java.util.*;


public class StudentComparator {
    private final int score;

    public StudentComparator(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public static void main(String[] args) {
        List<StudentComparator> students = Arrays.asList(new StudentComparator[] {
            new StudentComparator(3), new StudentComparator(2), new StudentComparator(6)
        });

        students.sort((s1, s2) -> {
            // change the order in compare function will change the order sorted
            return Integer.compare(s1.getScore(), s2.getScore());
        });

        for (StudentComparator s : students) {
            System.out.println(s.getScore());
        }
    }
}