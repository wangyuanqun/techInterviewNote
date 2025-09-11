import java.util.*;

public class Student implements Comparable<Student> {

    private final int score;

    public Student(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
        
    @Override
    public int compareTo(Student other) {
        // return Integer.compare(other.score, this.score); this is the descending order
        return Integer.compare(this.score, other.score); // while this is the ascending order
    }

    public static void main(String[] args) {

        List<Student> students = Arrays.asList(new Student[] {
            new Student(2), new Student(3), new Student(1)
        });

        Collections.sort(students);

        for (Student s : students) {
            System.out.println(s.getScore());
        }
    }
}