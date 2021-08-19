package javaDatabaseDemo;


import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Helper {
   private static Persistance db = null;
    public Helper(){
        try {
            FileReader reader = new FileReader("dbConnector.properties");
            Properties props = new Properties();
            props.load(reader);
            String path = (String) props.get("dbName");
            db = (Persistance) Class.forName(path).newInstance();
            customerDataStore();
            accountDataStore();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public HashMap<Integer, CustomerInfo> customerDataStore() {
        HashMap<Integer, CustomerInfo>customerDataMap=null;
        try{
            customerDataMap=db.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        MapHandler.OBJECT.customerMapper(customerDataMap);
        return customerDataMap;
    }
    public void accountDataStore() {
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountDataMap=null;
        try {
            accountDataMap=db.accountRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        MapHandler.OBJECT.accountMapper(accountDataMap);
    }

    public HashMap<String,String> caseNewUser(ArrayList<Object> inputList,int size){
        HashMap<String,String>insertionStatus = new HashMap();
        ArrayList<Integer> idList = db.customerInsertion(inputList);
        if(inputList.size()==idList.size()) {
            dataInsertion(inputList,size,idList,insertionStatus);
        }
        else{
            int index;
            int count=0;
            for(int i=0;i< idList.size();i++){
                if(idList.get(i)<0){
                    if(count==0)
                        index =(i*2);
                    else
                        index=(i*2)-(count*2);
                    CustomerInfo details = (CustomerInfo)inputList.get(index);
                    String item ="Name :"+details.getName()+" customer_id:"+details.getCustomerId();
                    insertionStatus.put(item,"Fail(Customer details)");
                    inputList.remove(index);
                    inputList.remove(index);
                    count++;
                }
            }
            dataInsertion(inputList,size,idList,insertionStatus);
        }
        try{
            db.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        return insertionStatus;
    }
    public void dataInsertion(ArrayList<Object> inputList,int size,
                                     ArrayList<Integer> idList,HashMap<String,String>insertionStatus){
        int j=0,k=1;
        for (int i =size;i < idList.size(); i++) {
            CustomerInfo customerObject = (CustomerInfo) inputList.get(j);
            customerObject.setCustomerId(idList.get(i));
            j += 2;
            AccountInfo accountObject = (AccountInfo) inputList.get(k);
            accountObject.setCustomer_id(idList.get(i));
            k+=2;
            ArrayList<Integer> account_noList = db.accountInsertion(accountObject);
            if(account_noList.get(0)>0) {
                accountObject.setAccount_no(account_noList.get(1));
                MapHandler.OBJECT.customerMapper(customerObject);
                MapHandler.OBJECT.accountMapper(accountObject);
                String item ="Name :"+customerObject.getName()+" customer_id:"+customerObject.getCustomerId()+" account no:"+accountObject.getAccount_no();
                insertionStatus.put(item, "Success");
            }
            else {
                String item ="Name :"+customerObject.getName()+" customer_id:"+customerObject.getCustomerId();
                insertionStatus.put(item, "Fail(account details)");
                int customer_id = customerObject.getCustomerId();
                db.customerDeletion(customer_id);
            }
        }
    }
    public  ArrayList<Integer> caseExistingUser(AccountInfo in) {
        ArrayList<Integer>accountIdList = db.accountInsertion(in);
        if(accountIdList.get(0)>0) {
            in.setAccount_no(accountIdList.get(1));
            MapHandler.OBJECT.accountMapper(in);
        }
        return accountIdList;
    }
    public static int entireDeletion(AccountInfo info,String type){
        int status = db.dataUpdation(info,type);
        MapHandler.OBJECT.customerDeletion(info.getCustomer_id());
        MapHandler.OBJECT.accountDeletion(info.getCustomer_id());
        return status;
    }
    public static boolean particularAccountDeletion(AccountInfo info,String type){
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountMap =MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo>inner = accountMap.get(info.getCustomer_id());
        int status=0;
        if(inner.size()==1) {
            status = db.dataUpdation(info, "customer");
        }
        else
            status = db.dataUpdation(info, type);
        if(status==1) {
            if (inner != null) {
                inner.remove(info.getAccount_no());
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }
    public static boolean amountWithdrawl(TransactionInfo info, String type){
        BigDecimal balance = getBalanceAmount(info.getCustomer_id(),info.getAccount_no());
        int rate =balance.compareTo(info.getAmount());
        if(rate>=0) {
            BigDecimal totalAmount = balance.subtract(info.getAmount());
            int status = db.transcation(info, totalAmount);
            if(status>0) {
                Helper.transData(info, type);
                MapHandler.OBJECT.balanceUpdation(info,totalAmount);
                return true;
            }else
                return false;
        }
        else
            return false;
    }
    public static boolean amountDeposite(TransactionInfo info, String type){
        BigDecimal balance = getBalanceAmount(info.getCustomer_id(),info.getAccount_no());
        BigDecimal totalAmount = balance.add(info.getAmount());
        int rate = db.transcation(info,totalAmount);
        if(rate>0){
            Helper.transData(info,type);
            MapHandler.OBJECT.balanceUpdation(info,totalAmount);
            return true;
        }
        else
            return false;
    }
    public static void transData(TransactionInfo info,String type){
        db.transInsertion(info,type);
    }
    public static BigDecimal getBalanceAmount(int customer_id, int account_no){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(customer_id);
        AccountInfo info = temp.get(account_no);
        BigDecimal balance = info.getBalance();
        return balance;
    }
    public static boolean idCheck(int id) {
        if(MapHandler.OBJECT.retriveCustomerDetails().containsKey(id)){
            return true;
        }
        else
            return false;
    }
    public static boolean accountCheck(int id,int account_no){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(id);
        if(temp.containsKey(account_no))
            return true;
        else
            return false;
    }
    public void password(CustomerInfo info){
        db.passwordSetter(info);
    }
    public static boolean  dbClose(){
        QueryHandler qh = new QueryHandler();
        boolean status = qh.closingProcess();
        return status;
    }
    public HashMap<Integer, CustomerInfo> helperThreeCustomer(){
        HashMap<Integer, CustomerInfo>finalCustomerMap=MapHandler.OBJECT.retriveCustomerDetails();
        return finalCustomerMap;
    }
    public HashMap<Integer, AccountInfo> helperThreeAccount(int id){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(id);
            return temp;
    }
}