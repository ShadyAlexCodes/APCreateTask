package com.xanderendre.apcreatetask;

import java.util.*;

public class GradeCalculator {

    public static final Map<String, Double> letterGradeToGPA = new LinkedHashMap<>();
    public static final Map<String, String> sampleGradeRange = new LinkedHashMap<>();
    public static final Map<String, Double> classInformation = new HashMap<>();
    public static int totalClasses = 1;

    public static void main(String[] args) {
        startUp();
    }

    /**
     * Creates the student, prints the values, calculates the GPA
     */
    public static void startUp() {
        setLetterGradeToGPA();
        setSampleGradeRange();
        System.out.println(">>-------- GPA Calculator --------<<");
        System.out.println("Welcome to the AP CSP Create Task GPA Calculator");
        System.out.println("Before you calculate your GPA we need a little bit more information.");
        wait(4000);
        try (Scanner scanner = new Scanner(System.in)) {
            Student student = new Student();
            getStudentInformation(student, scanner);
            // Fix the table
            System.out.println(">>-------- GPA Calculator --------<<");
            System.out.println("In a moment you will see a table of score ranges. Choose the correlating letter based upon your score.");
            wait(2000);
            for (Map.Entry m : sampleGradeRange.entrySet()) {
                System.out.println(m.getKey() + "  | " + m.getValue());
            }

            System.out.println(">>-------- GPA Calculator --------<<");
            System.out.println("For each of your " + student.numOfClasses + " class(es) please state the class name, and your score received.");
            wait(2000);

            while (totalClasses != student.numOfClasses + 1) {
                loadClassInformation(student, scanner);
                wait(1000);
            }

            double gpa = calculateGPA(student);
            System.out.printf("Your GPA is: %.2f\n", gpa);
            scanner.close();
        } catch (Exception exception) {
            System.out.println("∎ GPA Calculator WARNING ∎ : " + exception);
        }


    }

    /**
     * @param student
     * @param scanner Prompts the user for their classes name, and the letter grade of that class.
     *                Checks if the value inputted is valid, and then places this information into
     *                a HashMap.
     */

    public static void loadClassInformation(Student student, Scanner scanner) {
        Classes classes = new Classes();


        System.out.println("Please enter the name of your " + totalClasses + " class:");
        classes.className = scanner.nextLine();
        System.out.println("Please enter the letter grade you received for the class " + classes.className + ":");
        classes.classGrade = scanner.nextLine();
        double gradeValue = 0;
        // fix case
        if (letterGradeToGPA.containsKey(classes.classGrade.toUpperCase())) {
            gradeValue = letterGradeToGPA.get(classes.classGrade.toUpperCase());
        } else {
            System.out.println("Invalid Grade Inputted");
        }
        classInformation.put(classes.className, gradeValue);
        totalClasses += 1;
    }

    /**
     * @param student
     * @return gpa
     * Calcuates the overall GPA from the student.
     * Inside the for loop it takes the value from each map key and addes it to the class total
     */

    public static double calculateGPA(Student student) {
        double totalClasses = student.numOfClasses;
        double classesTotal = 0;
        for (Map.Entry<String, Double> entry : classInformation.entrySet()) {
            double temp = entry.getValue();
            classesTotal += temp;
        }
        double gpa = classesTotal / totalClasses;
        return gpa;
    }

    /**
     * Adds all letter grades to the Hashmap
     */
    public static void setLetterGradeToGPA() {
        letterGradeToGPA.put("A+", 4.00);
        letterGradeToGPA.put("A", 4.00);
        letterGradeToGPA.put("A-", 3.70);
        letterGradeToGPA.put("B+", 3.33);
        letterGradeToGPA.put("B", 3.00);
        letterGradeToGPA.put("B-", 2.70);
        letterGradeToGPA.put("C+", 2.33);
        letterGradeToGPA.put("C", 2.00);
        letterGradeToGPA.put("C-", 1.70);
        letterGradeToGPA.put("D+", 1.33);
        letterGradeToGPA.put("D", 1.00);
        letterGradeToGPA.put("F", 0.0);
    }

    /**
     * Adds all letter grades to the Hashmap
     */
    public static void setSampleGradeRange() {
        sampleGradeRange.put("A+", "97 - 100");
        sampleGradeRange.put("A ", "93 - 96");
        sampleGradeRange.put("A-", "90 - 92");
        sampleGradeRange.put("B+", "87 - 89");
        sampleGradeRange.put("B ", "83 - 86");
        sampleGradeRange.put("B-", "80 - 82");
        sampleGradeRange.put("C+", "77 - 79");
        sampleGradeRange.put("C ", "73 - 76");
        sampleGradeRange.put("C-", "70 - 72");
        sampleGradeRange.put("D+", "67 - 69");
        sampleGradeRange.put("D ", "65 - 66");
        sampleGradeRange.put("F ", "Below 65");
    }

    /**
     * @param student
     * @param scanner Creates the student inside of the student class and allows easy access to information
     */

    private static void getStudentInformation(Student student, Scanner scanner) {
        System.out.println(">>-------- GPA Calculator --------<<");
        System.out.println("Hello! What is your name?");
        student.firstName = scanner.nextLine();
        System.out.println("How many classes do you have? (Please enter a integer)");
        student.numOfClasses = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nice to meet you " + student.firstName + ". Let's get some more information about your classes");
        wait(1000);

    }


    /**
     * @param milliseconds Creates a wait timer that can be use globally.
     * @author Xander Endre
     */
    public static void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            System.err.format("IOException: %s%n", ex);
        }
    }

    private static class Student {
        public final List<Double> studentScores = new ArrayList<>();
        public String firstName;
        public int numOfClasses;
    }

    private static class Classes {

        public String className;
        public String classGrade;
    }

}

/*

Idea:
    GPA Calculator

Step One:
    I started by making the startUp() function with the intention of this being my abstraction.
    I then created two HashMaps to store/convert Letter Grades and Numerical Grades to a GPA.
    Storing the letter grades was easy, but now I needed to figure out the numericalGradeToGPA part to ensure it displayed the correct information.
    Then i realized that i have to check if the user is putting in a number or a letter grade.
    I created a switch statement to check which the user is inputting
    Then asked for their grades -> checked if the hashamp contained that letter or numerical value.
    I strugged with checking the numerical grade because it was something I didn't know how to check the range.
    So I removed that part and instead made a table with each score range.
    Then i decided to make a data class to store the student. Storing their FirstName, Grade Level, Number of Classes, and their scores
    Then i created a private function to get their name and total classes.
    I placed all of this into a startup function nested in a try loop
    Then i created a function to load the classes information.
    I put a table in to list all the grade values
    Then i added a wait function
    Then I created the calculate gpa function
    Then I went back and made the calculator look nice
    then i ran into the issue where no matter what grade being entered was invalid

Started by writing everything to command line.


*/
