package com.management.money.monspier.infrastructure.web;

import com.management.money.monspier.core.ErrorState;
import com.management.money.monspier.core.wallets.Wallet;
import com.management.money.monspier.core.wallets.WalletFacade;
import com.management.money.monspier.core.wallets.WalletResponse;
import com.management.money.monspier.infrastructure.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {

  @Autowired
  private Factory factory;

  /**
   * Post request handler for wallet value.
   */
  @RequestMapping(method = RequestMethod.POST,
                  consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> create(@RequestBody Wallet wallet) {
    ErrorState errorState = new ErrorState();
    WalletFacade facade = factory.walletFacade(errorState);
    Wallet result = facade.create(wallet);

    if (result == WalletResponse.INVALID) {
      return new ResponseEntity<>(errorState, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  /**
   * Get request handler for wallet id value.
   */
  @RequestMapping(value = "/{id}",
                  method = RequestMethod.GET)
  public ResponseEntity<Object> retrieve(@PathVariable long id) {
    ErrorState errorState = new ErrorState();
    WalletFacade facade = factory.walletFacade(errorState);
    Wallet result = facade.retrieve(id);

    if (result == WalletResponse.NOT_FOUND) {
      return new ResponseEntity<>(errorState, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
