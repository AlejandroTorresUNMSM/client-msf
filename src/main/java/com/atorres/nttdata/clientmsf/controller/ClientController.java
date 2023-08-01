package com.atorres.nttdata.clientmsf.controller;

import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import com.atorres.nttdata.clientmsf.service.impl.ClientServiceImpl;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**.
 * Controlador API Clientes
 */
@RestController
@RequestMapping("/api/client")
@Slf4j
public class ClientController {
  /**.
   * ClienteService
   */
  @Autowired
  private ClientServiceImpl clientServiceImpl;

  /**.
   * Metodo que retorna todos los clientes.
   *
   * @return lista clientDao.
   */
  @GetMapping(path = {"", "/"},
          produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ClientDto> getListClients() {
    return clientServiceImpl.findAll()
            .doOnNext(cl -> log.info("Cliente encontrado: {}", cl));
  }

  /**.
   * Metodo para crear un cliente
   *
   * @param cp cliente request
   *
   * @return cliente
   */
  @PostMapping(path = {"", "/"},
          produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<ClientDto> createClient(
          @Valid @RequestBody
          final Mono<ClientPost> cp) {
    return cp.flatMap(client -> clientServiceImpl.save(client))
            .doOnSuccess(v -> log.info("Cliente creado con exito"));
  }

  /**.
   * Metodo para encontrar un cliente por su id
   *
   * @param id id del cliente
   *
   * @return cliente
   */
  @GetMapping(value = "/{id}",
          produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<ClientDto> getClient(
          @PathVariable final String id) {
    return clientServiceImpl.findById(id)
            .doOnSuccess(v -> log.info("Cliente encontrado con exito"));
  }

  /**.
   * Metodo para encontrar un cliente por su celular.
   *
   * @param phone id del cliente.
   *
   * @return cliente.
   */
  @GetMapping(value = "/phone/{phone}",
      produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<ClientDto> getClientByPhone(
      @PathVariable final String phone) {
    return clientServiceImpl.searchByPhone(phone)
        .doOnSuccess(v -> log.info("Cliente encontrado con exito"));
  }

  /**.
   * Metodo para actualizar un cliente.
   *
   * @param id id cliente.
   * @param cp cliente request.
   *
   * @return cliente.
   */
  @PutMapping(value = "/update/{id}",
          produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<ClientDto> updateClient(
          @PathVariable final String id,
          @RequestBody final RequestClientUpdate cp) {
    return clientServiceImpl.update(id, cp)
            .doOnSuccess(v -> log.info("Cliente actualizado con exito"));
  }

  /**.
   * Metodo para eliminar un cliente
   *
   * @param id clientId
   *
   * @return void
   */
  @DeleteMapping(value = "/{id}",
          produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Mono<Void> deleteClient(
          @PathVariable final String id) {
    return clientServiceImpl.delete(id)
            .doOnSuccess(v -> log.info("Cliente eliminado con exito"));
  }

}
