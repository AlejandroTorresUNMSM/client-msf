package com.atorres.nttdata.clientmsf.utils;

import org.springframework.stereotype.Component;

/**.
 * Convertidor de enum
 */
@Component
public class EnumConverter {
  /**
   * .
   * Convierte CreateClientType a ClientType
   *
   * @param enum1Value enum
   * @return ClientType
   */
  public ClientType toClientType(CreateClientType enum1Value) {
    return ClientType.valueOf(enum1Value.name());
  }
}
