package app.gradingsystem;

import java.util.Scanner;

public class GradingSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int studentCount = 1;
        // Array to store frequency of grades 1 through 9
        int[] gradeSummary = new int[10]; 

        while (studentCount <= 5) {
            System.out.print("Enter score for student " + studentCount + " (0-100): ");
            int score = input.nextInt();
            
            int grade;
            String remark;

            // Determine grade and remark using if-else-if
            if (score >= 80) { grade = 1; remark = "D1"; }
            else if (score >= 75) { grade = 2; remark = "D2"; }
            else if (score >= 66) { grade = 3; remark = "C3"; }
            else if (score >= 60) { grade = 4; remark = "C4"; }
            else if (score >= 50) { grade = 5; remark = "C5"; }
            else if (score >= 45) { grade = 6; remark = "C6"; }
            else if (score >= 35) { grade = 7; remark = "P7"; }
            else if (score >= 30) { grade = 8; remark = "P8"; }
            else { grade = 9; remark = "F"; }

            System.out.println("Score: " + score + " | Grade: " + grade + " | Remark: " + remark);
            
            // Record the grade for the summary
            gradeSummary[grade]++;
            studentCount++;
            System.out.println("---------------------------");
        }

        // Display Summary
        System.out.println("\nGRADE SUMMARY:");
        for (int i = 1; i <= 9; i++) {
            System.out.println("Grade " + i + ": " + gradeSummary[i] + " student(s)");
        }
        input.close();
    }
}
