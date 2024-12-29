import java.util.Scanner;
import java.time.LocalDate;
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
        
        while(true){
            System.out.println("\n== Ledger System ==");
            System.out.println("Login or Register");
            System.out.println("1.Login");
            System.out.println("2.Register");
            System.out.print("\n>");
            int LogReg=sc.nextInt();
            
            sc.nextLine();
        
            if (LogReg==2){
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
            
            else if (LogReg==1){
                System.out.println("\n== Please enter your email and password ==");
                
                while(true){
                    System.out.print("Email: ");
                    String email=sc.nextLine();
                    System.out.print("Password: ");
                    String pass=sc.nextLine();
                
                    if(email.equals(regEmailValid)&&pass.equals(regPassValid)){
                        System.out.println("\nLogin Successful!!!\n");
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
        int count=0;
        boolean running = true;
        Double balance =0.0;
        Double [] CurrentBalance=new Double[100];
        Double savings =0.0;
        Double loan =0.0;
        Double SavingPercent = 0.0;
        boolean SavingActivated = false;

        
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
                       System.out.println("1. Filter\n2. Sort\n3. View All\n4. Back");
                       System.out.print("\n>");
                       int historyOption = sc.nextInt();
                       sc.nextLine();

                       switch (historyOption) {
                           case 1: // Filter
                               System.out.println("\n== Filter Options ==");
                               System.out.println("1. Date Range\n2. Transaction Type\n3. Amount Range");
                               System.out.print("\n>");
                               int filterOption = sc.nextInt();
                               sc.nextLine();

                               switch (filterOption) {
                                   case 1: // Date Range
                                       System.out.print("Enter start date (dd-mm-yyyy): ");
                                       LocalDate startDate = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                       System.out.print("Enter end date (dd-mm-yyyy): ");
                                       LocalDate endDate = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                                       System.out.println("\nFiltered Transactions:");
                                       for (int i = 0; i < count; i++) {
                                           if (!transactionDates[i].isBefore(startDate) && !transactionDates[i].isAfter(endDate)) {
                                            printTransaction(i, transactionDates, descDebitCredit, DebitCredit, CurrentBalance);
                                        }
                                    }
                                    break;

                                case 2: // Transaction Type
                                    System.out.print("Enter transaction type (Debit/Credit): ");
                                    String type = sc.nextLine();

                                    System.out.println("\nFiltered Transactions:");
                                    for (int i = 0; i < count; i++) {
                                        if (descDebitCredit[1][i].equalsIgnoreCase(type)) {
                                            printTransaction(i, transactionDates, descDebitCredit, DebitCredit, CurrentBalance);
                                        }
                                    }
                                    break;

                                case 3: // Amount Range
                                    System.out.print("Enter minimum amount: ");
                                    double minAmount = sc.nextDouble();
                                    System.out.print("Enter maximum amount: ");
                                    double maxAmount = sc.nextDouble();

                                    System.out.println("\nFiltered Transactions:");
                                    for (int i = 0; i < count; i++) {
                                        if (DebitCredit[i] >= minAmount && DebitCredit[i] <= maxAmount) {
                                            printTransaction(i, transactionDates, descDebitCredit, DebitCredit, CurrentBalance);
                                        }
                                    }
                                    break;

                                default:
                                    System.out.println("Invalid filter option.");
                                    break;
                            }
                            break;

                        case 2: // Sort
                            System.out.println("\n== Sort Options ==");
                            System.out.println("1. By Date\n2. By Amount");
                            System.out.print("\n>");
                            int sortOption = sc.nextInt();
                            sc.nextLine();

                            switch (sortOption) {
                                case 1: // By Date
                                    System.out.println("1. Newest to Oldest\n2. Oldest to Newest");
                                    System.out.print("\n>");
                                    int dateOrder = sc.nextInt();
                                    sc.nextLine();
                                    sortByDate(transactionDates, descDebitCredit, DebitCredit, CurrentBalance, count, dateOrder == 1);
                                    System.out.println("\nSorted Transactions:");
                                    for (int i = 0; i < count; i++) {
                                        printTransaction(i, transactionDates, descDebitCredit, DebitCredit, CurrentBalance);
                                    }
                                    break;

                                case 2: // By Amount
                                    System.out.println("1. Highest to Lowest\n2. Lowest to Highest");
                                    System.out.print("\n>");
                                    int amountOrder = sc.nextInt();
                                    sc.nextLine();
                                    sortByAmount(transactionDates, descDebitCredit, DebitCredit, CurrentBalance, count, amountOrder == 1);
                                    System.out.println("\nSorted Transactions:");
                                    for (int i = 0; i < count; i++) {
                                        printTransaction(i, transactionDates, descDebitCredit, DebitCredit, CurrentBalance);
                                    }
                                    break;

                                default:
                                    System.out.println("Invalid sort option.");
                                    break;
                            }
                            break;

                        case 3: // View All
                            for (int i = 0; i < count; i++) {
                                printTransaction(i, transactionDates, descDebitCredit, DebitCredit, CurrentBalance);
                            }
                            break;

                        case 4:
                            break;

                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
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
                    
                    case 6:

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
        private static void sortByDate(LocalDate[] dates, String[][] descDebitCredit, Double[] DebitCredit, Double[] CurrentBalance, int count, boolean newestFirst) {
            for (int i = 0; i < count - 1; i++) {
                for (int j = 0; j < count - 1 - i; j++) {
                    boolean condition = newestFirst
                            ? dates[j].isBefore(dates[j + 1])
                            : dates[j].isAfter(dates[j + 1]);

                    if (condition) {
                        // Swap dates
                        LocalDate tempDate = dates[j];
                        dates[j] = dates[j + 1];
                        dates[j + 1] = tempDate;

                        // Swap corresponding descDebitCredit
                        String[] tempDesc = descDebitCredit[j];
                        descDebitCredit[j] = descDebitCredit[j + 1];
                        descDebitCredit[j + 1] = tempDesc;

                        // Swap corresponding DebitCredit
                        Double tempDebitCredit = DebitCredit[j];
                        DebitCredit[j] = DebitCredit[j + 1];
                        DebitCredit[j + 1] = tempDebitCredit;

                        // Swap corresponding CurrentBalance
                        Double tempCurrentBalance = CurrentBalance[j];
                        CurrentBalance[j] = CurrentBalance[j + 1];
                        CurrentBalance[j + 1] = tempCurrentBalance;
                    }
                }
            }
        }

        private static void sortByAmount(LocalDate[] dates, String[][] descDebitCredit, Double[] DebitCredit, Double[] CurrentBalance, int count, boolean highestFirst) {
            for (int i = 0; i < count - 1; i++) {
                for (int j = 0; j < count - 1 - i; j++) {
                    boolean condition = highestFirst
                            ? DebitCredit[j] < DebitCredit[j + 1]
                            : DebitCredit[j] > DebitCredit[j + 1];

                    if (condition) {
                        // Swap DebitCredit
                        Double tempDebitCredit = DebitCredit[j];
                        DebitCredit[j] = DebitCredit[j + 1];
                        DebitCredit[j + 1] = tempDebitCredit;

                        // Swap corresponding dates
                        LocalDate tempDate = dates[j];
                        dates[j] = dates[j + 1];
                        dates[j + 1] = tempDate;

                        // Swap corresponding descDebitCredit
                        String[] tempDesc = descDebitCredit[j];
                        descDebitCredit[j] = descDebitCredit[j + 1];
                        descDebitCredit[j + 1] = tempDesc;

                        // Swap corresponding CurrentBalance
                        Double tempCurrentBalance = CurrentBalance[j];
                        CurrentBalance[j] = CurrentBalance[j + 1];
                        CurrentBalance[j + 1] = tempCurrentBalance;
                    }
                }
            }
        }
}
