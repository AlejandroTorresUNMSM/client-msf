package com.atorres.nttdata.clientmsf.service;

import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**.
 * Interfaz de metodos para clientService
 */
public interface ClientService {
  Flux<ClientDto> findAll();

  Mono<ClientDto> findById(final String id);

  Mono<ClientDto> save(final ClientPost client);

  Mono<Void> delete(final String id);

  Mono<ClientDto> update(final String id, final RequestClientUpdate request);

  Mono<ClientDto> searchByPhone(String phone);
}
