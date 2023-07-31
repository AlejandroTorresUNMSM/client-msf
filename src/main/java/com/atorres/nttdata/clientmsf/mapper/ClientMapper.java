package com.atorres.nttdata.clientmsf.mapper;

import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import com.atorres.nttdata.clientmsf.model.dao.ClientDao;
import com.atorres.nttdata.clientmsf.utils.EnumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * .
 * Mapper de cliente-msf
 */
@Component
public class ClientMapper {
  /**
   * .
   * Convertidor CreateClientType a ClientType
   */
  @Autowired
  EnumConverter enumConverter;

  /**
   * .
   * Metodo que transforma Clientepost a ClienteDao
   *
   * @param clientPost clientepost
   *
   * @return clientdao
   */
  public ClientDao toDao(final ClientPost clientPost) {
    ClientDao clientDao = new ClientDao();
    clientDao.setId(generateId());
    clientDao.setName(clientPost.getName());
    clientDao.setNroDocument(clientPost.getNroDocument());
    clientDao.setTypeClient(enumConverter.toClientType(clientPost.getTypeClient()));
    clientDao.setPhone(clientPost.getPhone());
    return clientDao;
  }

  /**
   * .
   * Metodo usado en el Update para actulizar el Client
   *
   * @param clientDao clientpost
   * @param request   idcliente
   *
   * @return client
   */
  public ClientDao toDao(
      final ClientDao clientDao,
      final RequestClientUpdate request) {
    clientDao.setNroDocument(request.getNroDocument());
    return clientDao;
  }

  /**.
   * Metodo que transformar el dao a dto.
   *
   * @param clientDao clienteDao
   *
   * @return clientDto
   */
  public ClientDto toDto(ClientDao clientDao) {
    ClientDto client = new ClientDto();
    client.setId(clientDao.getId());
    client.setName(clientDao.getName());
    client.setNroDocument(clientDao.getNroDocument());
    client.setTypeClient(clientDao.getTypeClient());
    client.setPhone(clientDao.getPhone());
    return client;
  }

  /**
   * .
   * Metodo para generar un Id aleatorio
   *
   * @return id aleatorio
   */
  private String generateId() {
    return java.util.UUID.randomUUID().toString().replace("-", "");
  }
}
