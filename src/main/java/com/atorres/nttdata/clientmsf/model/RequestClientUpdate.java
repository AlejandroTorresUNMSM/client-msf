package com.atorres.nttdata.clientmsf.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class RequestClientUpdate {
  /**.
   * Tipo del documento
   */
  @NotBlank
  private String typeDocument;
  /**.
   * Numero del documento
   */
  @NotBlank
  private String nroDocument;
  /**.
   * Nombre del cliente
   */
  @NotBlank
  private String name;
}
