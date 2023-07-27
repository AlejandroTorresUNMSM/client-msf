package com.atorres.nttdata.clientmsf.service;

import com.atorres.nttdata.clientmsf.exception.CustomException;
import com.atorres.nttdata.clientmsf.mapper.ClientMapper;
import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import com.atorres.nttdata.clientmsf.repository.ClientRepository;
import com.atorres.nttdata.clientmsf.utils.ClientType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class ClientService {
    /**.
     * Repositorio para cliente
     */
    @Autowired
    private ClientRepository dao;
    /**.
     * Mapper Cliente
     */
    @Autowired
    private ClientMapper clientMapper;

    /**.
     * Metodo que encuentra todos los clientes
     * @return lista de clientes
     */
    public Flux<ClientDto> findAll() {
        return dao.findAll()
                .map(clientMapper::toClient);
    }

    /**.
     * Metodo que trae un cliente por su Id
     * @param id id del cliente
     * @return clientDao
     */
    public  Mono<ClientDto> findById(final String id) {
        //obtengo el cliente por id si no lo encuentra devuelve una excepcion
        return dao.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("No se encontro al cliente {}", id);
                    return Mono.error(new CustomException(HttpStatus.NOT_FOUND,
                            "No se encontró al cliente"));
                }))
                .map(clientMapper::toClient);
    }

    /**.
     * Metodo para crear un cliente
     * @param client cliente request
     * @return clienteDao
     */
    public Mono<ClientDto> save(final ClientPost client) {
        return dao.findAll()
                .filter(cl -> cl.getTypeDocument().equals(client.getTypeDocument())
                        && cl.getNroDocument().equals(client.getNroDocument()))
                .any(cl -> true)
                .flatMap(exist -> Boolean.TRUE.equals(exist)
                        ?   Mono.error(new CustomException(HttpStatus.BAD_REQUEST,
                        "Ya existe el nroDocumento y tipo"))
                        : dao.save(clientMapper.clientposttoClientDao(client)) )
                .map(clientMapper::toClient);
    }

    /**.
     * Metodo para borrar un cliente
     * @param id id del cliente
     * @return Vacio
     */
    public Mono<Void> delete(final String id) {
        //reviso que el cliente exista
        Mono<Boolean> existeClient = dao.findById(id).hasElement();
        //reviso el flag del client si no existe lanzo un mensaje de error
        return existeClient
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? dao.deleteById(id)
                        : Mono.error(new CustomException(HttpStatus.NOT_FOUND,
                        "No existe el cliente a eliminar")));
    }

    /**.
     * Metodo para actualizar cliente
     * @param id id del cliente
     * @param request request del cliente
     * @return ClienteDao
     */
    public Mono<ClientDto> update(
            final String id,
            final RequestClientUpdate request) {
        return dao.findAll()
                .filter(cl -> cl.getTypeDocument().equals(request.getTypeDocument())
                        && cl.getNroDocument().equals(request.getNroDocument()))
                .any(cl -> true)
                .flatMap(exist -> Boolean.TRUE.equals(exist)
                        ?   Mono.error(new CustomException(HttpStatus.BAD_REQUEST,
                        "Ya existe el nroDocumento y tipo"))
                        : dao.findById(id))
                .map(client -> clientMapper.
                        clientposttoClientDaoUpdate(client, request))
                .flatMap(client -> dao.save(client))
                .map(clientMapper::toClient);
    }

    public Mono<ClientDto> updateType(String idClient, ClientType clientType){
        return dao.findById(idClient)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "No existe el cliente")))
                .flatMap(cl ->{
                    cl.setTypeClient(clientType);
                    return  dao.save(cl);
                })
                .map(clientMapper::toClient);
    }
}
