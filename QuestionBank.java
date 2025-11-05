import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionBank {
        enum Category {
                Theoretical, Programming
        }

        HashMap<Question, Integer> questions = new HashMap<>();
        private List<Question> tanong = new ArrayList<>();

        public QuestionBank() {
                questions.put(q1, 100);
                questions.put(q2, 100);
                questions.put(q3, 200);
                questions.put(q4, 200);
                questions.put(q5, 300);
                questions.put(q6, 300);
                questions.put(q7, 400);
                questions.put(q8, 400);
                questions.put(q9, 500);
                questions.put(q10, 500);
                tanong.add(q1);
                tanong.add(q2);
                tanong.add(q3);
                tanong.add(q4);
                tanong.add(q5);
                tanong.add(q6);
                tanong.add(q7);
                tanong.add(q8);
                tanong.add(q9);
                tanong.add(q10);
        }

        public void loadCategory(Category category) {

        }

        Question q1 = new Question("Which paradigm does Java follow?",
                        new String[] { "A. Procedural", "B. Object-Oriented", "C. Functional", "D. Logical" }, 'B');

        Question q2 = new Question("Which of the following best describes a programming paradigm?",
                        new String[] { "A. A specific programming language", "B. A style or approach to programming",
                                        "C. A software development tool", "D. A computer hardware architecture" },
                        'B');

        Question q3 = new Question("In the procedural programming paradigm, programs are organized around:",
                        new String[] { "A. Objects and methods", "B. Functions and procedures", "C. Rules and facts",
                                        "D. Events and listeners" },
                        'B');

        Question q4 = new Question("Which of the following is NOT an object-oriented programming concept?",
                        new String[] { "A. Inheritance", "B. Encapsulation", "C. Polymorphism", "D. Backtracking" },
                        'D');

        Question q5 = new Question(
                        "Which programming paradigm is primarily based on mathematical functions and avoids changing state?",
                        new String[] { "A. Functional", "B. Logic", "C. Procedural", "D. Event-driven" }, 'A');

        Question q6 = new Question("Prolog is an example of which programming paradigm?",
                        new String[] { "A. Object-Oriented", "B. Procedural", "C. Logic", "D. Functional" }, 'C');

        Question q7 = new Question(
                        "Which of the following paradigms supports recursion as a natural form of iteration?",
                        new String[] { "A. Procedural", "B. Object-Oriented", "C. Functional", "D. Logical" }, 'C');

        Question q8 = new Question("Which of the following languages is MOST associated with functional programming?",
                        new String[] { "A. Java", "B. C", "C. Haskell", "D. Python" }, 'C');

        Question q9 = new Question("Imperative programming is characterized by:",
                        new String[] { "A. Using mathematical equations only", "B. Describing facts and rules",
                                        "C. Representing problems in terms of objects",
                                        "D. Giving step-by-step instructions that change program state" },
                        'D');

        Question q10 = new Question("Event-driven programming is most commonly used in:",
                        new String[] { "A. Operating system kernels", "B. Web and GUI applications",
                                        "C. Compiler design",
                                        "D. Mathematical modeling" },
                        'B');

}
