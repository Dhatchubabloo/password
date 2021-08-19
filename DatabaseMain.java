package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.*;

public class DatabaseMain {
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args){
		DatabaseMain db = new DatabaseMain();
		db.execution();
	}

	public void execution(){
		System.out.println();
		System.out.println("1.Enroll both customer details and account details");
		System.out.println("2.Enroll account details");
		System.out.println("3.Check account details");
		System.out.println("4.Transaction");
		System.out.println("5.delete the details");
		System.out.println("6.Exit");
		System.out.println("Enter your choice");
		int choice = scan.nextInt();
		Helper logic = new Helper();
		switch (choice) {
			case 1:
				ArrayList<Object> inputList = new ArrayList();
				System.out.println("Enter number of customers do you want to enroll");
				int count = scan.nextInt();
				scan.nextLine();
				for (int i = 1; i <= count; i++) {
					CustomerInfo customerObject = new CustomerInfo();
					System.out.println("customer details for customer" + i);
					System.out.println("Enter Name");
					customerObject.setName(scan.next());
					System.out.println("Enter City");
					customerObject.setCity(scan.next());
					System.out.println("Enter Password");
					customerObject.setPassword(scan.next());
					inputList.add(customerObject);
					scan.nextLine();
					System.out.println("Account details for customer" + i);
					AccountInfo accountObject = new AccountInfo();
					System.out.println("Enter Balance");
					accountObject.setBalance(scan.nextBigDecimal());
					System.out.println("Enter Branch");
					accountObject.setBranch(scan.next());
					inputList.add(accountObject);
					scan.nextLine();
				}
				HashMap<String,String>insertionStatus = logic.caseNewUser(inputList,count);
				System.out.println("Insertion status:");
                for (Map.Entry<String,String> entry : insertionStatus.entrySet())
                    System.out.println( entry.getKey() +
                            " = " + entry.getValue());
				execution();
				break;

			case 2:
				AccountInfo in = new AccountInfo();
				System.out.println("Account details for existing customer");
				System.out.println("Enter customer id");
				int id = scan.nextInt();
				if(Helper.idCheck(id)) {
					in.setCustomer_id(id);
					System.out.println("Enter balance");
					in.setBalance(scan.nextBigDecimal());
					System.out.println("Enter Branch");
					in.setBranch(scan.next());
					ArrayList<Integer> accountStatus = logic.caseExistingUser(in);
					if (accountStatus.get(0) >0)
						System.out.println("account insertion succcessful");
					else
						System.out.println("Account insertion Failed");
				}else
					System.out.println("invalid customer_id");
				execution();
				break;

			case 3:
				System.out.println("Enter Valid Customer id:");
				int mainId = scan.nextInt();
				if(Helper.idCheck(mainId)){
					System.out.println("Enter Password");
					String password = scan.next();
                    CustomerInfo info = logic.helperThreeCustomer().get(mainId);
					String existPassword = info.getPassword();
					if(password.equals(existPassword)) {
						System.out.println(logic.helperThreeCustomer().get(mainId));
						HashMap<Integer, AccountInfo> temp = logic.helperThreeAccount(mainId);
						System.out.println("1.To check Entire Account");
						System.out.println("2.To check particular Account");
						int option = scan.nextInt();
						switch (option) {
							case 1:
								if (temp != null) {
									for (AccountInfo ac : temp.values()) {
										System.out.println(ac);
									}
									System.out.println("----------------------------------------------------------");
								} else
									System.out.println("customer deactivated");
								break;
							case 2:
								System.out.println("Enter valid Account Number");
								int mainAccount_no = scan.nextInt();
								if (Helper.accountCheck(mainId, mainAccount_no)) {
									if (temp != null)
										System.out.println(temp.get(mainAccount_no));
									else
										System.out.println("account deactivated");
								} else
									System.out.println("invalid account number");
								break;
							default:
								System.out.println("invalid option");
								break;
						}
					}
					else
						System.out.println("incorrect password");
					System.out.println();
					System.out.println("1.Forgot password");
					System.out.println("2.Exit");
					int val = scan.nextInt();
					if(val==1){
						System.out.println("Enter new password");
						String newPassword =scan.next();
						System.out.println("Re-Enter password");
						String rePassword = scan.next();
						CustomerInfo obj = new CustomerInfo();
						if(newPassword.equals(rePassword)){
							obj.setCustomerId(mainId);
							obj.setPassword(newPassword);
							logic.password(obj);
						}
						else
							System.out.println("Password did not match");
					}
				}else {
					System.out.println("invalid Customer id");
				}
				execution();
				break;
			case 4:
				TransactionInfo info = new TransactionInfo();
				System.out.println("Enter customer_id");
				int customerId = scan.nextInt();
				info.setCustomer_id(customerId);
				if(Helper.idCheck(customerId)) {
					System.out.println("Enter account_no");
					int aacount_no = scan.nextInt();
					info.setAccount_no(aacount_no);
					if(Helper.accountCheck(customerId,aacount_no)) {
						System.out.println("1.withdrawl");
						System.out.println("2.deposite");
						int option = scan.nextInt();
						switch (option) {
							case 1:
								String type = "withdrawl";
								System.out.println("Enter withdrawl amount");
								BigDecimal wamount = scan.nextBigDecimal();
								info.setAmount(wamount);
								boolean condition = Helper.amountWithdrawl(info,type);
								if (condition) {
									System.out.println("transaction completed");
								}
								else
									System.out.println("Insufficient amount");
								break;
							case 2:
								String type1 = "deposite";
								System.out.println("Enter deposite amount");
								BigDecimal damount = scan.nextBigDecimal();
								info.setAmount(damount);
								if (Helper.amountDeposite(info,type1))
									System.out.println("Deposition completed");
								else
									System.out.println("Deposition Failed");
						}
					}
					else
						System.out.println("invalid account number");
				}
				else
					System.out.println("invalid customer_id");
				execution();
				break;
			case 5:
				System.out.println("1.Entire customer_id deletion");
				System.out.println("2.particular account details deletion");
				int number = scan.nextInt();
				AccountInfo accountObject = new AccountInfo();
				switch (number) {
					case 1:
						String type = "customer";
						System.out.println("Enter customer_id");
						accountObject.setCustomer_id(scan.nextInt());
						int status=Helper.entireDeletion(accountObject,type);
						if(status>0)
							System.out.println("Deleted Successfully;");
						else
							System.out.println("Deletion Failed....." +
									"invalid Customer_id");
						execution();
						break;
					case 2:
						String type1 = "account";
						System.out.println("Enter customer_id");
						accountObject.setCustomer_id(scan.nextInt());
						System.out.println("Enter account_no");
						accountObject.setAccount_no(scan.nextInt());
						if(Helper.particularAccountDeletion(accountObject,type1))
							System.out.println("Deleted Successfully;");
						else
							System.out.println("Deletion Failed....." +
									"invalid Account_no");
						execution();
				}
				break;
			case 6:
				if(Helper.dbClose())
					System.out.println("your Connection was closed successfully");
				else
					System.out.println("Not closed");
				break;

			default:
				System.out.println("OOPS!.........Enter valid choice");
				execution();
		}
	}
}

