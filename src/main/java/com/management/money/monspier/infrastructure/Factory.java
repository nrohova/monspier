package com.management.money.monspier.infrastructure;

import com.management.money.monspier.core.ErrorState;
import com.management.money.monspier.core.wallets.WalletFacade;
import com.management.money.monspier.core.wallets.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Factory {

  @Autowired
  private WalletRepository walletRepository;

  public WalletFacade walletFacade(ErrorState errorState) {
    return new WalletFacade(errorState, walletRepository);
  }
}
