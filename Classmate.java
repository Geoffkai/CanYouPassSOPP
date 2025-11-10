import java.util.Random;

public abstract class Classmate {
    private String name;
    private String specialty;
    private final double OTHER_TOPIC_ACCURACY = 0.25;
    private Random rand = new Random();

    public Classmate(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public boolean willAnswerCorrectly(String questionTopic) {
        if (questionTopic.equalsIgnoreCase(this.specialty)) {
            return true;
        } else {
            return rand.nextDouble() < OTHER_TOPIC_ACCURACY;
        }
    };
}
