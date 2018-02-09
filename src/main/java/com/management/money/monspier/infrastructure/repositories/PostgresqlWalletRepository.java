package com.management.money.monspier.infrastructure.repositories;

import com.management.money.monspier.core.wallets.Wallet;
import com.management.money.monspier.core.wallets.WalletRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class PostgresqlWalletRepository implements WalletRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Wallet create(Wallet wallet) {
    Query query = entityManager.createNativeQuery("INSERT INTO wallet (name) VALUES (:name)");
    query.setParameter("name", wallet.getName());
    int i = query.executeUpdate();
    return wallet;
  }

  @Override
  public boolean isExist(String walletName) {
    return false;
  }
}
