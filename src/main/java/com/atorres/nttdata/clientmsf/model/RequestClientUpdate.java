package com.atorres.nttdata.clientmsf.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;


/**.
 * Clase RequestClientUpdate
 */
@Data
public class RequestClientUpdate {
  /**.
   * Numero del documento
   */
  @NotBlank
  private String nroDocument;
}
