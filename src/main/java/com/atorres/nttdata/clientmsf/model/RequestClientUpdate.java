package com.atorres.nttdata.clientmsf.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class RequestClientUpdate {
  /**.
   * Numero del documento
   */
  @NotBlank
  private String nroDocument;
}
