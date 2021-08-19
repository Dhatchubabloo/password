package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum MapHandler {
    OBJECT;
    private HashMap<Integer,HashMap<Integer, AccountInfo>>accountDetailsMap = new HashMap<>();
     HashMap<Integer, CustomerInfo>customerDetailsMap = new HashMap<>();

    public void accountMapper(HashMap<Integer,HashMap<Integer, AccountInfo>> accountMap) {
        accountDetailsMap = accountMap;
    }

    public void customerMapper(HashMap<Integer, CustomerInfo> customeMap) {
        customerDetailsMap=customeMap;
    }
    public void accountMapper(AccountInfo account) {
        int customerId=account.getCustomer_id();
        HashMap<Integer,AccountInfo> accountHashMap=accountDetailsMap.get(customerId);
        if(accountHashMap==null){
            accountHashMap=new HashMap<>();
            accountDetailsMap.put(customerId,accountHashMap);
        }
        accountHashMap.put(account.getAccount_no(),account);
    }
    public void customerMapper(CustomerInfo customer){
        int customer_id = customer.getCustomerId();
        customerDetailsMap.put(customer_id,customer);
    }
    public  HashMap<Integer, CustomerInfo> retriveCustomerDetails() {
        return customerDetailsMap;
    }
    public  HashMap<Integer,HashMap<Integer, AccountInfo>> retriveAccountDetails() {
        return accountDetailsMap;
    }
    public void customerDeletion(int id){
        customerDetailsMap.remove(id);
    }
    public void accountDeletion(int id)
    {
        accountDetailsMap.remove(id);
    }
    public void balanceUpdation(TransactionInfo info, BigDecimal totalAmount){
        HashMap<Integer,HashMap<Integer, AccountInfo>>account = MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo>update = account.get(info.getCustomer_id());
        AccountInfo object = update.get(info.getAccount_no());
        object.setBalance(totalAmount);
    }
//    public static void main(String[]args){
//        for(Map.Entry<Integer,CustomerInfo> entry:OBJECT.customerDetailsMap.entrySet()){
//            System.out.println(entry.getKey()+"="+entry.getValue());
//        }
//    }
}
