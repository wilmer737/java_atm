/*
Inputs:
Outputs:
Constraints:
    1. Assume you have a bank account with a balance of $200
    2. Account Number is 000114 and Password is 114
        2.1 If user enters this wrong, then have them retry. They get 3 attempts before program quits with following message: “The account number and password doesn’t match! Please make sure you have the correct Account number and password, and try again.
        2.2 If user enters this correctly, let them into the program
    3. ATM has 4 options. Deposit Cash, Withdraw Cash, Print Statement, Exit.
    4. Input has to be number, make sure to validate this.
    5. User can’t withdraw more than what they have. throw error message if they do.



1. Ask User For Password
2. Ask user for username
3. Verify user name
    3.1 Try 2 more times and exit if all incorrect
4. Once correct, show user main menu

Questions: When do we ever collect the Users name and how do we return to the main menu from the Print Statement screen?
 */
package Guerra_Assignment3;
import java.util.Scanner;

class atmSession
{
    private int attempt;
    // I made accountNumber and password a string because normally they are in the real world
    private final  String accountNumber;
    private final String password;
    private String name;
    private float balance;


    public atmSession()
    {
        this.attempt = 1;
        this.accountNumber = "000114";
        this.password = "114";
        this.balance = 200;
    }

    public atmSession(String name)
    {
        this.attempt = 1;
        this.accountNumber = "000114";
        this.password = "114";
        this.name = name;
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

    public void deposit(float amount){
        this.balance += amount;
    }

    public float getBalance()
    {
        return this.balance;
    }
}

public class Guerra_Assignment3
{

    public static void main(String[] args) {
        String userNumber, userPassword;
        boolean success = false;
        int choice = 0;
        float depositAmount = 0;
        Scanner input = new Scanner(System.in);

        atmSession login = new atmSession("Wilmer");

        // Ok, lets try logging in
        do {
            // First lets check if this is the fourth attempt, if it is, print exit message and exit program
            if (login.getAttempts() == 4) {
                printExitMessage();
                System.exit(0);
            }

            printHeaders();
            System.out.print("Please Enter Your Account Number: ");
            userNumber = input.nextLine();
            System.out.print("Please Enter Your password: ");
            userPassword = input.nextLine();

            System.out.println(printAttempts(login.getAttempts()));
            printHeaders();

            if (login.loginAttempt(userNumber, userPassword))
                success = true;

        } while (!success);
        printHeaders();

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
            printHeaders();

            if(choice != 4)
                printHeaders();

            switch (choice) {
                case 1:
                    do {
                        if (depositAmount <= -1) {
                            printHeaders();
                            System.out.println("Input Error! Please Try Again!");
                        }
                        depositScreen();
                        while (!input.hasNextFloat()) {
                            printHeaders();
                            System.out.println("Input Error! Please Try Again!");
                            input.next();
                            depositScreen();
                        }
                        depositAmount = input.nextFloat();
                    } while (depositAmount < 0);

                    // If we're here, that means user input was a positive float
                    login.deposit(depositAmount);
                    printHeaders();
                    System.out.println(depositAmount + "\nhas been deposited into your account");
                    System.out.println("The current balance is: " + login.getBalance());
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
        printHeaders();
    }

    private static void printHeaders()
    {
        System.out.println("======================================");
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

}
