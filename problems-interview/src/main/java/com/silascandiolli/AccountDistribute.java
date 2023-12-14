package com.silascandiolli;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccountDistribute {

    static List<Account> accountData = new ArrayList<>();

    public static void main(String[] args) {
        String[] accounts = new String[]{"A:70", "B:80", "D:110", "E:130", "C:100"};

        storeData(accounts);

        List<Account> accountsSorted = accountData.stream().sorted(Comparator.comparing(c -> c.amount)).toList();

        distributeAmounts(accountsSorted);

    }

    private static void storeData(String[] accounts) {
        for (String ac : accounts) {
            String[] split = ac.split(":");
            accountData.add(new Account(split[0], Integer.valueOf(split[1])));
        }
    }

    private static void distributeAmounts(List<Account> accounts) {
        Account accoutNeedMoney = null;
        for (Account ac : accounts) {
            int e = 0;
            int i = 0;
            if (ac.amount < 100) {
                e = ac.amount - 100;
                accoutNeedMoney = ac;
            }
            if (ac.amount > 100) {
                i = ac.amount - 100;
            }
            if (e == i) {
                accoutNeedMoney.amount = accoutNeedMoney.amount + i;
                executeTransaction(ac.name, accoutNeedMoney.name, i);
            }
        }
    }

    public static void executeTransaction(String accountFrom, String accountTo, int amount) {
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
