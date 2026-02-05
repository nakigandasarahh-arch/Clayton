

package app.skmotors;
import java.util.Scanner;

public class SKMotors {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1. Enter Vehicle Details
        System.out.print("Enter Vehicle Registration Number: ");
        String regNum = input.nextLine();
        System.out.print("Enter Original Vehicle Cost: ");
        double vehicleCost = input.nextDouble();
        System.out.print("Enter Total Expenses incurred: ");
        double expenses = input.nextDouble();

        double totalInvestment = vehicleCost + expenses;

        // 2. Capture 3 Bidders
        double highestBid = 0;
        for (int i = 1; i <= 3; i++) {
            System.out.print("Enter bid amount for Bidder " + i + ": ");
            double currentBid = input.nextDouble();
            if (currentBid > highestBid) {
                highestBid = currentBid;
            }
        }

        // 3. Process the Sale
        System.out.println("\n--- Auction Result ---");
        System.out.println("Vehicle Reg: " + regNum);
        System.out.println("Winning Bid: " + highestBid);
        
        System.out.print("Enter amount paid by bidder (Clear balance): ");
        double amountPaid = input.nextDouble();
        double balanceLeft = highestBid - amountPaid;

        System.out.println("Balance remaining: " + balanceLeft);

        // 4. Calculate Profit or Loss
        if (balanceLeft == 0) {
            double profitOrLoss = highestBid - totalInvestment;
            if (profitOrLoss >= 0) {
                System.out.println("Final Profit: " + profitOrLoss);
            } else {
                System.out.println("Final Loss: " + Math.abs(profitOrLoss));
            }
        } else {
            System.out.println("Profit/Loss cannot be finalized until balance is 0.");
        }

        input.close();
    }
}
