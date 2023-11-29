package roche;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccountDistribute {

    static List<Account> accountData = new ArrayList<>();

    public static void main(String[] args) {
        String[] accounts = new String[]{"A:70", "B:80", "D:110", "E:130", "C:100"};

        storeData(accounts);

        distributeAmounts(accounts);

        executeTransaction("","", "");

    }

    private static void storeData(String[] accounts) {
        for (String ac : accounts) {
            String[] split = ac.split(":");
            accountData.add(new Account(split[0], Integer.valueOf(split[1])));
        }
    }

    private static void distributeAmounts(String[] accounts) {
        List<Account> accounts1 = accountData.stream().sorted(Comparator.comparing(c -> c.amount)).toList();

        for (Account ac : accounts1) {

            if (ac.amount > 100) {
                int i = ac.amount - 100;

            }
        }
    }

    public static void executeTransaction(String accountFrom, String accountTo, String amount) {
        System.out.println("from " + accountFrom + " to " + accountTo + ", amount " + amount);
    }

    static class Account {
        public String name;
        public Integer amount;

        public Account(String name, Integer amount) {
            this.name = name;
            this.amount = amount;
        }
    }
}
