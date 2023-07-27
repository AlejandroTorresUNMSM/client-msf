package com.atorres.nttdata.clientmsf.model;

import com.atorres.nttdata.clientmsf.utils.ClientType;
import lombok.Data;

@Data
public class ClientDto {
    /**.
     * Id del cliente
     */
    private String id;
    /**.
     * Tipos de documento
     */
    private String typeDocument;
    /**.
     * Numero del documento
     */
    private String nroDocument;
    /**.
     * Nombre del cliente
     */
    private String name;
    /**.
     * Tipo del cliente
     */
    private ClientType typeClient;
}
