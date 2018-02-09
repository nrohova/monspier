package com.management.money.monspier.core.wallets;

import com.management.money.monspier.core.ErrorState;
import com.management.money.monspier.core.FieldName;

final class WalletValidationRules {

  private static final String REQUIRED_FIELD = "This is required field.";

  private WalletValidationRules() {}

  static void checkName(String name, ErrorState errorState) {
    if (name == null || name.isEmpty()) {
      errorState.addError(FieldName.WALLET_NAME, REQUIRED_FIELD);
    }
  }
}
