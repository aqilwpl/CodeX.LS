import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class LedgerSystem {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        LocalDate CurrentDate = LocalDate.now();
        
        //User Registration Validation
        String regName=" ";
        String regEmail=" ";
        String regPass=" ";
        String regEmailValid=" ";
        String regPassValid=" ";
        LocalDate loanStartDate = null;
        int repaymentPeriod =0;
        Double loan = 0.0;
        int monthsPaid =0;
        
        while(true){
            
            int LogReg=JOptionPane.showOptionDialog(null,"Welcome to Ledger System!","Ledger System",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,new String[]{"Login","Register"},"Login");

            if (LogReg==1){
                System.out.println("\n== Please fill in the form ==");
                System.out.print("Name: ");
                regName=sc.nextLine();
                
                while(true){
                    System.out.print("Email: ");
                    regEmail=sc.nextLine();
                
                    if(regEmail.endsWith("@gmail.com")){
                        regEmailValid=regEmail;
                        break;
                    }
                
                    else{
                        System.out.println("\nEmail must be in correct format(name@gmail.com).\n");
                    }
                } 
                
                while(true){
                    System.out.print("Password: ");
                    regPass=sc.nextLine();
                    
                    if (regPass.length()<8){
                        System.out.println("\nPassword must be at least 8 characters.\n");
                    }
                    
                    boolean letter=false;
                    boolean digit=false;
                    boolean spec=false;
                    
                    for(int i=0; i<regPass.length(); i++){
                        char charac=regPass.charAt(i);
                        
                        if (Character.isLetter(charac))
                            letter=true;
                        else if (Character.isDigit(charac))
                            digit=true;
                        else if (!Character.isLetterOrDigit(charac))
                            spec=true;
                    }
                    
                    if(letter&&digit&&spec){
                        regPassValid=regPass;
                        System.out.println("\nRegister succesful!!!\n");
                        break;
                    }
                    
                    else{
                        System.out.println("\nPassword must contain at least one letter, digit and special character.\n");
                    }
                }
            }
            
            else if (LogReg==0){
                System.out.println("\n== Please enter your email and password ==");
                
                while(true){
                    System.out.print("Email: ");
                    String email=sc.nextLine();
                    System.out.print("Password: ");
                    String pass=sc.nextLine();
                
                    if(email.equals(regEmailValid)&&pass.equals(regPassValid)){
                        System.out.println("\nLogin Successful!!!\n");

                        //reminder for loan repayment
                        if(loan >0 && loanStartDate != null && !HasPaidThisMonth(loanStartDate, repaymentPeriod, monthsPaid)){
                            LocalDate dueDate = loanStartDate.plusMonths(repaymentPeriod);
                            long DaysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
                            
                             System.out.printf("REMINDER!! Your loan repayment is due in %d days (Due Date: %s).\nPlease pay your monthly repayment.", DaysUntilDue,dueDate);
                        }
                        
                        break;
                    }
                
                    else{
                        System.out.println("\nYour email or password is wrong.");
                    }
                }
                break;
            }
            else{
                System.out.println("\nPlease choose between 1 or 2 only.");
            }
        }

        //Aqil&&Fathi
        
        Double []DebitCredit=new Double[100];    //Combine both Debit adn Credit in one array to ease the order of transaction(but it is limited to 100 transactions only)
        String [][]descDebitCredit=new String[2][100]; /*Two collums of array of Transaction description. 
                                                        first collum is to label whether it is a Debit or Credit. 
                                                        the other is to store the description
                                                        */
        LocalDate[] transactionDates = new LocalDate[100]; 
        int count=0;
        boolean running = true;
        Double balance =0.0;
        Double [] CurrentBalance=new Double[100];
        Double savings =0.0;
        Double SavingPercent = 0.0;
        boolean SavingActivated = false;
        double monthlyRepayment = 0.0;

        
        while(true){
            
            System.out.println("\n== Welcome, "+regName+" ==");
            System.out.println("Balance: "+balance);
            System.out.println("Savings: "+savings);
            System.out.println("Loan: "+loan);

            while(running){
            
                System.out.println("\n== Transaction ==");
                System.out.println("1. Debit\n"
                               + "2. Credit\n"
                               + "3. History\n"
                               + "4. Savings\n"
                               + "5. Credit loan\n"
                               + "6. Deposit Interest Predictor\n"
                               + "7. Logout");
                System.out.print("\n>");
                int option = sc.nextInt();
            
                switch (option){
                    case 1:
                        if(!HasPaidThisMonth(loanStartDate,repaymentPeriod, monthsPaid)){ //loan overdue restriction
                                System.out.println("Cannot perform debit. Please pay your monthly repayment first.");
                                break;
                            }
                        
                        while(true){
                            System.out.println("\n== Debit ==");
                            System.out.print("Enter debit amount: ");
                            DebitCredit[count]=sc.nextDouble();
                            sc.nextLine();

                            if(DebitCredit[count]>1000000000){
                                System.out.println("The amount exceeded 10 digits. Please try again.");
                            }
                            else if(DebitCredit[count]<0){
                                System.out.println("Please insert positive value only.");    
                            }
                            else{

                                //if savings was activated on option 4, these lines of code will run
                                if(SavingActivated){
                                    double savedMoney = (SavingPercent/100)*DebitCredit[count];
                                    DebitCredit[count]= DebitCredit[count]-savedMoney;
                                    savings+=savedMoney;
                                    
                                }

                                
                                CurrentBalance[count]=balance+DebitCredit[count];
                                balance+=DebitCredit[count];
                                break;
                            }
                        }

                        while(true){
                            System.out.print("Enter description: ");
                            descDebitCredit[0][count]=sc.nextLine();
                            descDebitCredit[1][count]="Debit";
                            
                            if(descDebitCredit[0][count].length()>20){
                                System.out.println("Transaction description exceeded 20 characters. Please try again.");
                                
                            }
                            else{
                                System.out.println("\nDebit Successfully Recorded!!!\n");
                                count++;
                                break;
                            }
                        }
                        break;
                        
                
                    case 2:
                        if(!HasPaidThisMonth(loanStartDate,repaymentPeriod, monthsPaid)){ //loan overdue restriction
                                System.out.println("Cannot perform credit. Please pay your monthly repayment first.");
                                break;
                            }
                        
                        while(true){
                            System.out.println("\n== Credit ==");
                            System.out.print("Enter credit amount: ");
                            DebitCredit[count]=sc.nextDouble();
                            sc.nextLine();

                            if(DebitCredit[count]>1000000000){
                                System.out.println("The amount exceeded 10 digits. Please try again.");
                            }
                            else if(DebitCredit[count]<0){
                                System.out.println("Please insert positive value only.");
                            }
                            else{
                                CurrentBalance[count]=balance-DebitCredit[count];
                                balance-=DebitCredit[count];
                                break;
                            }
                        }

                        while(true){
                            System.out.print("Enter description: ");
                            descDebitCredit[0][count]=sc.nextLine();
                            descDebitCredit[1][count]="Credit";

                            if(descDebitCredit[0][count].length()>20){
                                System.out.println("Transaction description exceeded 20 characters. Please try again.");
                            }
                            else{
                                System.out.println("\nCredit Successfully Recorded!!!\n");
                                count++;
                                break;
                            }
                        }
                        break;
                    
                
                    case 3:
                        System.out.println("\n== History ==");
                        filterAndSortHistory(sc, DebitCredit, descDebitCredit, transactionDates, count);
                        break;
                    
                    case 4:
                        System.out.println("== Savings ==");
                        if(SavingActivated == false){
                            System.out.print("Are you sure you want to activate it? (Y/N) : ");
                            String YesNo = sc.next();
                            sc.nextLine();
                            if(YesNo.equalsIgnoreCase("Y")){
                                SavingActivated = true;
                                System.out.print("Please enter the percentage you wish to deduct from your next debit: ");
                                SavingPercent = sc.nextDouble();
                                sc.nextLine();
                                System.out.println(SavingPercent+"% will be auto deduct from your next debit.");
                                System.out.println("Savings settings added successfully!!!");
                                SavingActivated = true;
                                break;
                            } 
                            else if(YesNo.equalsIgnoreCase("N")){
                                SavingActivated = false;
                                break;
                            }
                            else{
                                System.out.println("Wrong input sucker!");
                                break;
                            }
                        }
                        else if(SavingActivated == true){
                            System.out.println("You have already activated Savings.");
                            System.out.println("Current saving percentage: "+SavingPercent);
                            System.out.println("Would you like to deactivate it?");
                            String YN = sc.nextLine();
                            sc.nextLine();
                            if(YN.equalsIgnoreCase("Y")){
                                SavingActivated = false;
                                SavingPercent = 0.0;
                                System.out.println("Saving deactivated successfully");
                                break;
                            }
                            else if(YN.equalsIgnoreCase("N")){
                                break;
                            }
                            else{
                                System.out.println("Wrong input sucker!");
                            }
                        }
                        break;
                    
                    case 5:
                        System.out.println("\n== Credit Loan ==");
                        System.out.println("1. Apply");
                        System.out.println("2. Repay");
                        System.out.print("\n>");
                        int choice = sc.nextInt();
                        sc.nextLine();                        
                        
                        if(choice == 1){ //apply loan
                            System.out.print("Enter the total amount of money you want to take a loan: ");
                            double P = validatepositiveinput(sc); // principal
                            System.out.print("Enter the interest rate(%): ");
                            double InterestRate = validatepositiveinput(sc);
                            System.out.print("Enter the repayment period (in months): ");
                            repaymentPeriod = (int) validatepositiveinput(sc);
                            
                            loanStartDate = CurrentDate;
                            
                            double r= (InterestRate/(100*12));
                            double totalrepayment= (P*(r*Math.pow((1+r),repaymentPeriod)/(Math.pow((1+r),repaymentPeriod)-1)))*repaymentPeriod;
                            loan = Math.round(totalrepayment * 100.0) / 100.0;// assign total repayment to loan
                            monthlyRepayment = Math.round((totalrepayment/repaymentPeriod)*100.0) / 100.0;
                            monthsPaid = 0;
                            
                            System.out.println("\nYour loan has been authorized!");
                            System.out.printf("Total repayment amount: %.2f\n",totalrepayment);
                            System.out.printf("Monthly repayment : %.2f\n", monthlyRepayment );                                                                                                                
                            
                        }else if(choice ==2){ //repay loan
                            if(loan >0){
                                System.out.printf("Monthly repayment: %.2f\n",monthlyRepayment);
                                System.out.print("Enter the amount you want to repay :");
                                double repayment = validatepositiveinput(sc);
                                
                                if(repayment == monthlyRepayment){
                                    loan -= repayment;
                                    loan = Math.max(loan,0.0); //to make sure loan doesn't go negative
                                    monthsPaid++;
                                    
                                    System.out.printf("Repayment successful!! Remaining loan balance : %.2f\n", loan);
                                    
                                    if(loan==0){
                                        System.out.println("Congratulations! Your loan has been fully repaid");
                                    }
                                    
                                }else if(repayment < monthlyRepayment){
                                    System.out.println("Insufficient repayment. Please pay the exact monthly repayment amount.");                                        
                                }else{
                                    System.out.println("Overpayment is not allowed. Please pay the exact monthly repayment amount.");           
                                    }
                                
                            }else{
                                System.out.println("No active loan to repay");
                            }
                        }
                        System.out.println();
                        break;
                    
                    case 6:
                        System.out.println("\n== Deposit Interest Predictor ==");                       
                        System.out.print("Enter bank interest rate(%): ");
                        double rate = validatepositiveinput(sc);                       
                        
                        double interest = (balance*(rate/100))/12;
                        System.out.printf("Predicted interest monthly: %.2f\n", interest);
                        break;

                    case 7:
                        System.out.println("Thank you for using Ledger System!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
                break;
            }   
        }
    }
    public static boolean HasPaidThisMonth(LocalDate loanStartDate, int repaymentPeriod, int monthsPaid){
        if(loanStartDate == null || monthsPaid >= repaymentPeriod){
            return true; // no loan or fully paid
        }
        LocalDate dueDate = loanStartDate.plusMonths(monthsPaid);
        return !LocalDate.now().isAfter(dueDate);
    }

    public static double validatepositiveinput(Scanner sc){
        double value;
        while(true){
            value = sc.nextDouble();
            if(value>0){
                break;
            }else{
                System.out.print("Invalid input.Please enter a positive value: ");
            }            
        }
        return value;
    }

    public static void filterAndSortHistory(Scanner sc, Double[] DebitCredit, String[][] descDebitCredit, LocalDate[] transactionDates, int count) {
        List<Transaction> transactions = new ArrayList<>();
    
        // Collect transactions
        for (int i = 0; i < count; i++) {
            transactions.add(new Transaction(transactionDates[i], descDebitCredit[0][i], DebitCredit[i], descDebitCredit[1][i]));
        }
    
        while (true) {
            try {
                // History options
                System.out.println("1. Filter");
                System.out.println("2. Sort");
                System.out.println("3. View all");
                System.out.println("4. Back");
                System.out.print("\n>");
                int historyOption = sc.nextInt();
                sc.nextLine(); 
    
                switch (historyOption) {
                    // Filter option
                    case 1:
                        System.out.println("1. By Date");
                        System.out.println("2. By Amount");
                        System.out.println("3. By Transaction");
                        System.out.println("4. Back");
                        System.out.print("\n>");
                        int filterOption = sc.nextInt();
                        sc.nextLine();
    
                        switch (filterOption) {
                            case 1:
                                System.out.print("Enter start date (YYYY-MM-DD): ");
                                LocalDate startDate = LocalDate.parse(sc.nextLine());
                                System.out.print("Enter end date (YYYY-MM-DD): ");
                                LocalDate endDate = LocalDate.parse(sc.nextLine());
                                transactions.removeIf(t -> t.date.isBefore(startDate) || t.date.isAfter(endDate.plusDays(1)));
                                break;
    
                            case 2:
                                System.out.print("Enter minimum amount: ");
                                double minAmount = sc.nextDouble();
                                System.out.print("Enter maximum amount: ");
                                double maxAmount = sc.nextDouble();
                                transactions.removeIf(t -> t.amount < minAmount || t.amount > maxAmount);
                                sc.nextLine();
                                break;
    
                            case 3:
                                System.out.print("Enter transaction type (Debit/Credit): ");
                                String type = sc.nextLine();
                                transactions.removeIf(t -> !t.type.equalsIgnoreCase(type));
                                break;
    
                            case 4:
                                return;
    
                            default:
                                System.out.println("Invalid option.");
                                return;
                        }
                        break;
    
                    // Sorting option    
                    case 2:
                        System.out.println("1. By Date (Newest to Oldest)");
                        System.out.println("2. By Date (Oldest to Newest)");
                        System.out.println("3. By Amount (Highest to Lowest)");
                        System.out.println("4. By Amount (Lowest to Highest)");
                        System.out.println("5. Back");
                        System.out.print("\n>");
                        int sortOption = sc.nextInt();
                        sc.nextLine(); 
    
                        switch (sortOption) {
                            case 1:
                                transactions.sort(Comparator.comparing(Transaction::getDate).reversed());
                                break;
    
                            case 2:
                                transactions.sort(Comparator.comparing(Transaction::getDate));
                                break;
    
                            case 3:
                                transactions.sort(Comparator.comparing(Transaction::getAmount).reversed());
                                break;
    
                            case 4:
                                transactions.sort(Comparator.comparing(Transaction::getAmount));
                                break;
    
                            case 5:
                                return;
    
                            default:
                                System.out.println("Invalid option.");
                                return;
                        }
                        break;
    
                    case 3:
                        // Display all transaction
                        break;
    
                    case 4:
                        // No filter applied
                        return;
    
                    default:
                        System.out.println("Invalid option.");
                        return;
                }
    
                // Display filtered or sorted transactions
                System.out.printf("%-10s%-20s%-20s%-15s%-15s\n", "No.", "Date", "Description", "Amount", "Type");
                for (int i = 0; i < transactions.size(); i++) {
                    Transaction t = transactions.get(i);
                    System.out.printf("%-10d%-20s%-20s%-15.2f%-15s\n", (i + 1), t.date, t.description, t.amount, t.type);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    
    static class Transaction {
    
        LocalDate date;
        String description;
        double amount;
        String type;
    
        Transaction(LocalDate date, String description, double amount, String type) {
            this.date = date;
            this.description = description;
            this.amount = amount;
            this.type = type;
        }
    
        public LocalDate getDate() {
            return date;
        }
    
        public double getAmount() {
            return amount;
        }
    }
}
