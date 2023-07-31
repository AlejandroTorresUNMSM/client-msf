package com.atorres.nttdata.clientmsf.repository;

import com.atorres.nttdata.clientmsf.model.dao.ClientDao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * .
 * Repositorio Cliente
 */
@Repository
public interface ClientRepository extends ReactiveMongoRepository
    <ClientDao, String> {
  /**
   * .
   * Repositorio para REST con la base mongo client
   *
   * @param nroDocument nro del documento
   *
   * @return clientDao
   */
  Mono<ClientDao> findByNroDocument(String nroDocument);

  /**.
   * Repositorio que trae un cliente por su numero celular
   *
   * @param phone celular
   *
   * @return clientedao
   */
  Mono<ClientDao> findByPhone(String phone);
}
