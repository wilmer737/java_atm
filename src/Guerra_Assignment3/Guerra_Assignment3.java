/*
 * @author Wilmer Guerra
 */
package Guerra_Assignment3;

import java.util.Scanner;
import java.text.NumberFormat;

class atmSession
{
    private int attempt;
    // I made accountNumber and password a string because normally they are in the real world
    private final  String accountNumber;
    private final String password;
    private String first_name;
    private String last_name;
    private double balance;

    public atmSession()
    {
        this.attempt = 1;
        this.accountNumber = "000114";
        this.password = "114";
        this.balance = 200;
    }

    public atmSession(String first_name, String last_name)
    {
        this.attempt = 1;
        this.accountNumber = "000114";
        this.password = "114";
        this.first_name = first_name;
        this.last_name = last_name;
        this.balance = 200;
    }

    public int getAttempts()
    {
        return this.attempt;
    }

    public boolean loginAttempt(String accountNumber, String password)
    {
        this.attempt++;
        return (accountNumber.equals(this.accountNumber) && password.equals(this.password));
    }

    public void deposit(double amount)
    {
        this.balance += amount;
    }

    public void withdrawl(double amount)
    {
        this.balance -= amount;
    }

    public double getBalance()
    {
        return this.balance;
    }

    public String getName()
    {
        return this.last_name + ", " + this.first_name;
    }

    public String getFirstName()
    {
        return this.first_name;
    }

    public String getAccountNumber()
    {
        return this.accountNumber;
    }
}

public class Guerra_Assignment3
{
    public static void main(String[] args) {
        int choice;
        boolean success = false;
        double depositAmount = 0, withdrawAmount = 0;
        atmSession login;
        String userNumber, userPassword, firstName, lastName, fullName;
        Scanner input = new Scanner(System.in);
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();

        // Two versions of program: With name or no name
        System.out.println("Do you want to do the full version? (y/n)");
        if(input.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Before we begin, can I get your first name: ");
            firstName = input.nextLine();
            System.out.println("Now your last name: ");
            lastName = input.nextLine();
            login = new atmSession(firstName, lastName);
        } else {
            login = new atmSession();
        }

        // Ok, lets try logging in
        do {
            // First lets check if this is the fourth attempt, if it is, print exit message and exit program
            if (login.getAttempts() == 4) {
                printExitMessage();
                System.exit(0);
            }

            printMainHeaders();
            System.out.print("Please Enter Your Account Number: ");
            userNumber = input.nextLine();
            System.out.print("Please Enter Your password: ");
            userPassword = input.nextLine();
            System.out.println(printAttempts(login.getAttempts()));
            printMainHeaders();

            if (login.loginAttempt(userNumber, userPassword))
                success = true;

        } while (!success);
        printMainHeaders();

        do {
            // If we're here, that means we've logged in
            printOptions();
            // validating user input
            do {
                System.out.print("Your Choice: ");
                while (!input.hasNextInt()) {
                    input.next();
                }
                choice = input.nextInt();
            } while (choice == 0 || choice > 4);
            printMainHeaders();

            // User gave us a valid choice, now let's direct them to the right menu
            switch (choice) {
                // Deposit
                case 1:
                    do {
                        if (depositAmount <= -1) {
                            printMainHeaders();
                            System.out.println("Input Error! Please Try Again!");
                        }
                        depositScreen();
                        while (!input.hasNextFloat()) {
                            printMainHeaders();
                            System.out.println("Input Error! Please Try Again!");
                            input.next();
                            depositScreen();
                        }
                        depositAmount = input.nextFloat();

                    } while (depositAmount < 0);

                    // If we're here, that means user input was a positive float
                    login.deposit(depositAmount);
                    printMainHeaders();
                    System.out.println(defaultFormat.format(depositAmount) + "\nhas been deposited into your account");
                    System.out.println("The current balance is: " + defaultFormat.format(login.getBalance()));
                    break;
                // Withdraw
                case 2:
                    do {
                        if(withdrawAmount <= -1){
                            printMainHeaders();
                            System.out.print("Input Error! Please Try Again!");
                            withdrawScreen();
                        }
                        withdrawScreen();
                        while(!input.hasNextFloat()){
                            printMainHeaders();
                            System.out.print("Input Error! Please Try Again!");
                            input.next();
                            withdrawScreen();
                        }

                        withdrawAmount = input.nextFloat();

                        // Making sure the user doesn't take more than what they have
                        while(withdrawAmount > login.getBalance()) {
                            System.out.println("You can't withdraw more than your total balance! Try again");
                            withdrawAmount = 0;
                        }
                    } while(withdrawAmount <= 0);

                    login.withdrawl(withdrawAmount);
                    printMainHeaders();
                    System.out.println(defaultFormat.format(withdrawAmount) + "\nhas been withdrawn from your account");
                    System.out.println("The current balance is: " + defaultFormat.format(login.getBalance()));
                    break;
                // Print Statement
                case 3:
                    fullName = (login.getFirstName() == null) ? "No Name Provided" : login.getName();
                    printStatement(fullName, login.getAccountNumber(), defaultFormat.format(login.getBalance()));
                    printMainHeaders();
                    System.out.println("The bank statement has been created\n");
                    break;
                // Exit ATM
                case 4:
                    printMainHeaders();
                    System.out.println("Thank you for banking with us!");
                    System.out.println("Please come back soon!\n\n");
                    System.out.println("CSIS114 Bank");
                    System.out.println("Member of FDIC, Equal Housing Lender!");
                    printMainHeaders();
                    break;
                default:
            }
        } while(choice != 4);
    }


    private static String printAttempts(int attempts)
    {
        String returnStatement;

        switch (attempts){
            case 1:
                returnStatement = "1st Attempt";
                break;
            case 2:
                returnStatement = "2nd Attempt";
                break;
            case 3:
                returnStatement = "3rd Attempt";
                break;
            default:
                returnStatement = "We shouldn't be here";
                break;
        }

        return returnStatement;
    }

    private static void printExitMessage()
    {
        System.out.println("The account number and password don't match!");
        System.out.println("Please make sure you have the correct account number and password");
        System.out.println("and try again.");
        printMainHeaders();
    }

    private static void printMainHeaders()
    {
        System.out.println("======================================");
    }

    private static void printMiniHeaders()
    {
        System.out.println("--------------------------------------");
    }

    private static void printOptions()
    {
        System.out.println("Welcome to the Palomar CSCI114 ATM");
        System.out.println("Please choose one of the following: ");
        System.out.println("1. Deposit Cash\n2. Withdraw Cash\n3. Print Statement\n4. Exit");
    }

    private static void depositScreen()
    {
        System.out.println("Please enter the amount of deposit in US dollars:");
        System.out.println("(In Example: 89.75)");
        System.out.print("$: ");
    }

    private static void withdrawScreen()
    {
        System.out.println("Please enter the amount to withdraw in US dollars:");
        System.out.println("(In Example: 89.75)");
        System.out.print("$: ");
    }

    private static void printStatement(String name, String accountNumber, String currentBalance)
    {
        System.out.println("CSIS114 Bank\n\n");
        printMainHeaders();
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("\nCurrent Balance: " + currentBalance + "\n");
        printMiniHeaders();
        System.out.println("CSIS114 Bank");
        System.out.println("Member of FDIC, Equal Housing Lender!");
    }

}
