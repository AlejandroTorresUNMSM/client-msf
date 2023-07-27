package com.atorres.nttdata.clientmsf.mapper;

import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import com.atorres.nttdata.clientmsf.model.dao.ClientDao;
import com.atorres.nttdata.clientmsf.utils.EnumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    @Autowired
    EnumConverter enumConverter;
    /**.
     * Metodo que transforma Clientepost a ClienteDao
     * @param clientPost clientepost
     * @return clientdao
     */
    public ClientDao clientposttoClientDao(final ClientPost clientPost) {
        ClientDao clientDao = new ClientDao();
        clientDao.setId(generateId());
        clientDao.setName(clientPost.getName());
        clientDao.setTypeDocument(clientPost.getTypeDocument());
        clientDao.setNroDocument(clientPost.getNroDocument());
        clientDao.setTypeClient(enumConverter.toClientType(clientPost.getTypeClient()));
        return clientDao;
    }

    /**.
     * Metodo usado en el Update para actulizar el Client
     * @param clientDao clientpost
     * @param request idcliente
     * @return client
     */
    public ClientDao clientposttoClientDaoUpdate(
            final ClientDao clientDao,
            final RequestClientUpdate request) {
        clientDao.setName(request.getName());
        clientDao.setTypeDocument(request.getTypeDocument());
        clientDao.setNroDocument(request.getNroDocument());
        return clientDao;
    }

    public ClientDto toClient(ClientDao clientDao){
        ClientDto client = new ClientDto();
        client.setId(clientDao.getId());
        client.setName(clientDao.getName());
        client.setTypeDocument(clientDao.getTypeDocument());
        client.setNroDocument(clientDao.getNroDocument());
        client.setTypeClient(clientDao.getTypeClient());
        return client;
    }

    /**.
     * Metodo para generar un Id aleatorio
     * @return id aleatorio
     */
    private String generateId() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
