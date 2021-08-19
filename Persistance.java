package javaDatabaseDemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public interface Persistance {

    ArrayList<Integer> customerInsertion(ArrayList<Object> list);

    HashMap<Integer, CustomerInfo> customerRetrival();

    ArrayList<Integer> accountInsertion(AccountInfo accountIn);

    HashMap<Integer,HashMap<Integer, AccountInfo>> accountRetrival();

    int customerDeletion(int id);

    int dataUpdation(AccountInfo info,String type);

    void passwordSetter(CustomerInfo info);

    int transcation(TransactionInfo info,BigDecimal amount);

    void transInsertion(TransactionInfo info,String type);

    boolean closingProcess();

}
