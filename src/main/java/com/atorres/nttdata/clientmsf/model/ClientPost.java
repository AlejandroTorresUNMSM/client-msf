package com.atorres.nttdata.clientmsf.model;

import com.atorres.nttdata.clientmsf.utils.CreateClientType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class ClientPost {
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
    /**.
     * Tipo del cliente
     */
    @NotNull
    @JsonProperty("typeClient")
    private CreateClientType typeClient;
    /**
     * Numero celular
     */
    @NotBlank
    @JsonProperty("phone")
    private String phone;

}
