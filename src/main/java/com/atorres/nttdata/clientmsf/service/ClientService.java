package com.atorres.nttdata.clientmsf.service;

import com.atorres.nttdata.clientmsf.exception.CustomException;
import com.atorres.nttdata.clientmsf.mapper.ClientMapper;
import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import com.atorres.nttdata.clientmsf.model.dao.ClientDao;
import com.atorres.nttdata.clientmsf.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class ClientService {
    @Autowired
    private KafkaStringProducer kafkaStringProducer;
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

    public ClientService() {
    }

    /**.
     * Metodo que encuentra todos los clientes
     * @return lista de clientes
     */
    public Flux<ClientDto> findAll() {
        return dao.findAll()
                .map(clientMapper::toDto)
                .doOnComplete(()->kafkaStringProducer.sendMessage("Clientes encontrados con exito"));
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
                .map(clientMapper::toDto)
                .doOnSuccess(v -> kafkaStringProducer.sendMessage("Cliente por Id encontrado con exito"));
    }

    /**.
     * Metodo para crear un cliente
     * @param client cliente request
     * @return clienteDao
     */
    public Mono<ClientDto> save(final ClientPost client) {
        return validateClient(client)
            .flatMap(clientDao -> dao.save(clientDao))
            .map(clientMapper::toDto)
            .doOnSuccess(v -> kafkaStringProducer.sendMessage("Cliente creado con exito"));
    }

    /**
     * Vemos si ya existe un cliente con ese documento o celular
     * @param clientpost cliente request
     * @return clientDao
     */
    private Mono<ClientDao> validateClient(ClientPost clientpost){
        return dao.findAll()
            .filter(cl -> cl.getNroDocument().equals(clientpost.getNroDocument()) || cl.getPhone().equals(clientpost.getPhone()))
            .any(cl -> true)
            .flatMap(exist -> {
                if(Boolean.TRUE.equals(exist))
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Existe el nroDocumento o phone"));
                else
                    return Mono.just(clientMapper.toDao(clientpost));
            });
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
                        "No existe el cliente a eliminar")))
                .doOnSuccess(v -> kafkaStringProducer.sendMessage("Cliente eliminado con exito"));
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
                .filter(cl -> cl.getNroDocument().equals(request.getNroDocument()))
                .any(cl -> true)
                .flatMap(exist -> Boolean.TRUE.equals(exist)
                        ?   Mono.error(new CustomException(HttpStatus.BAD_REQUEST,
                        "Ya existe el nroDocumento"))
                        : dao.findById(id))
                .map(client -> clientMapper.toDao(client, request))
                .flatMap(client -> dao.save(client))
                .map(clientMapper::toDto);
    }

    /**
     * Metodo que trae un cliente por su celular
     * @param phone celular
     * @return clientDto
     */
    public Mono<ClientDto> searchByPhone(String phone){
        return dao.findByPhone(phone)
            .map(clientMapper::toDto);

    }
}
