package com.management.money.monspier.core.wallets;

import com.management.money.monspier.core.ErrorState;

public final class WalletFacade {
  private final ErrorState errorState;
  private final WalletRepository repository;

  public WalletFacade(ErrorState errorState, WalletRepository repository) {
    this.errorState = errorState;
    this.repository = repository;
  }

  /**
   * Retrieve wallet for given id.
   */
  public Wallet retrieve(long id) {
    Wallet wallet = repository.retrieve(id);

    if (wallet == null) {
      errorState.addError("id", "No results for given value.");
      return WalletResponse.NOT_FOUND;
    }

    return wallet;
  }

  /**
   * Create valid wallet item.
   */
  public Wallet create(Wallet wallet) {
    if (!valid(wallet)) {
      return WalletResponse.INVALID;
    }

    return repository.create(wallet);
  }

  private boolean valid(Wallet wallet) {
    WalletValidationRules.checkName(wallet.getName(), errorState);

    return !errorState.hasErrors();
  }
}
