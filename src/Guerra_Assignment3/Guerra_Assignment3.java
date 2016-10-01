/*
 * @author Wilmer Guerra
 */

package Guerra_Assignment3;
import java.util.Scanner;

class atmSession
{
    private int attempt;
    // I made accountNumber and password a string because normally they are in the real world
    private final  String accountNumber;
    private final String password;
    private String first_name;
    private String last_name;
    private float balance;


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

    public void deposit(float amount)
    {
        this.balance += amount;
    }

    public void withdrawl(float amount)
    {
        this.balance -= amount;
    }

    public float getBalance()
    {
        return this.balance;
    }

    public String getName()
    {
        return this.last_name + ", " + this.first_name;
    }

    public String getAccountNumber()
    {
        return this.accountNumber;
    }
}

public class Guerra_Assignment3
{

    public static void main(String[] args) {
        String userNumber, userPassword;
        boolean success = false;
        int choice;
        float depositAmount = 0, withdrawAmount = 0;
        Scanner input = new Scanner(System.in);

        atmSession login = new atmSession("Wilmer", "Guerra");

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

            switch (choice) {
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
                        // Making sure the user doesn't take more than what they have
                        if(depositAmount > login.getBalance()) {
                            System.out.println("You can't withdraw more than your total balance! Try again");
                            depositAmount = 0;
                        }
                    } while (depositAmount < 0);

                    // If we're here, that means user input was a positive float
                    login.deposit(depositAmount);
                    printMainHeaders();
                    System.out.println(depositAmount + "\nhas been deposited into your account");
                    System.out.println("The current balance is: " + login.getBalance());
                    break;
                case 2:
                    do {
                        if(withdrawAmount <= -1){
                            printMainHeaders();
                            System.out.print("Input Error! Please Try Again!");
                        }
                        withdrawScreen();
                        while(!input.hasNextFloat()){
                            printMainHeaders();
                            System.out.print("Input Error! Please Try Again!");
                            input.next();
                            withdrawScreen();
                        }

                        withdrawAmount = input.nextFloat();
                    } while(withdrawAmount < 0);

                    login.withdrawl(withdrawAmount);
                    printMainHeaders();
                    System.out.println(withdrawAmount + "\nhas been withdrawn from your account");
                    System.out.println("The current balance is: " + login.getBalance());
                    break;
                case 3:
                    printStatement(login.getName(), login.getAccountNumber(), login.getBalance());
                    printMainHeaders();
                    System.out.println("The bank statement has been created\n");
                    break;
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
        System.out.println("Please enter the amount of withdrawl in US dollars:");
        System.out.println("(In Example: 89.75)");
        System.out.print("$: ");
    }

    private static void printStatement(String name, String accountNumber, float currentBalance)
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
