package com.management.money.monspier.core.wallets;

public interface WalletRepository {

  Wallet create(Wallet income);

  boolean isExist(String walletName);
}
