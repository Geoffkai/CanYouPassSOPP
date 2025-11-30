package logic.characters;

import logic.Question;

public abstract class Classmate {
    private String name;
    private String specialty;
    private final double OTHER_TOPIC_ACCURACY = 0.25;
    private java.util.Random rand = new java.util.Random();

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

    public int chooseAnswerIndex(Question question) {
        if (question.getTopic().equalsIgnoreCase(specialty)) {
            return question.getCorrectChoice();
        } else {
            // gives a random number between 0.0 and 1.0
            // basically 25% chance to answer correctly if not specialty
            if (rand.nextDouble() <= 0.25) {
                return question.getCorrectChoice();
            } else {
                // choose a wrong answer
                int wrongIndex;
                do {
                    wrongIndex = rand.nextInt(question.getOptions().length);
                } while (wrongIndex == question.getCorrectChoice());
                return wrongIndex;
            }
        }
    };
}
