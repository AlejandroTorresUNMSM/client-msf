package com.atorres.nttdata.clientmsf.service;

import ch.qos.logback.core.net.server.Client;
import com.atorres.nttdata.clientmsf.mapper.ClientMapper;
import com.atorres.nttdata.clientmsf.model.ClientDto;
import com.atorres.nttdata.clientmsf.model.ClientPost;
import com.atorres.nttdata.clientmsf.model.RequestClientUpdate;
import com.atorres.nttdata.clientmsf.model.dao.ClientDao;
import com.atorres.nttdata.clientmsf.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
  @Mock
  private ClientRepository clientRepository;
  @InjectMocks
  private ClientService clientService;
  @Mock
  private ClientMapper clientMapper;

  private final List<ClientDto> clientes = new ArrayList<>();

  private final ClientDto cl1 = new ClientDto();
  private final ClientDto cl2 = new ClientDto();
  private final ClientDao ca1 = new ClientDao();
  private final ClientDao ca2 = new ClientDao();

  private final ClientPost clientRequest = new ClientPost();
  @BeforeEach
  void setUp() {
    cl1.setId("1");
    cl1.setName("Alejandro");
    cl1.setNroDocument("222");
    cl1.setPhone("999");

    cl2.setId("2");
    cl2.setName("Marco");
    cl2.setNroDocument("3333");
    cl2.setPhone("988");

    ca1.setId("1");
    ca1.setName("Alejandro");
    ca1.setNroDocument("222");
    ca1.setPhone("999");

    ca2.setId("2");
    ca2.setName("Marco");
    ca2.setNroDocument("3333");
    ca2.setPhone("988");

    clientes.add(cl1);
    clientes.add(cl2);

    clientRequest.setName("Alejandro");
  }


  @Test
  void TEST_FINDALL() {
    Mockito.when(clientRepository.findAll()).thenReturn(Flux.just(ca1,ca2));
    Mockito.when(clientMapper.toDto(ca1)).thenReturn(cl1);
    Mockito.when(clientMapper.toDto(ca2)).thenReturn(cl2);
    Flux<ClientDto> resultado = clientService.findAll();

    StepVerifier.create(resultado)
        .expectNext(cl1)
        .expectNext(cl2)
        .expectComplete()
        .verify();
  }

  @Test
  void TEST_FINDBYID() {
    Mockito.when(clientRepository.findById("1")).thenReturn(Mono.just(ca1));
    Mockito.when(clientMapper.toDto(ca1)).thenReturn(cl1);

    Mono<ClientDto> resultado = clientService.findById("1");

    StepVerifier.create(resultado)
        .expectNext(cl1)
        .expectComplete()
        .verify();
  }

  @Test
  void TEST_SAVE() {
    Mockito.when(clientRepository.findAll()).thenReturn(Flux.empty());
    Mockito.when(clientRepository.save(ca1)).thenReturn(Mono.just(ca1));
    Mockito.when(clientMapper.toDao(clientRequest)).thenReturn(ca1);
    Mockito.when(clientMapper.toDto(ca1)).thenReturn(cl1);

    Mono<ClientDto> resultado = clientService.save(clientRequest);

    StepVerifier.create(resultado)
        .expectNext(cl1)
        .expectComplete()
        .verify();
  }

  @Test
  void TEST_DELETE() {
    Mockito.when(clientRepository.findById("1")).thenReturn(Mono.just(ca1));
    Mockito.when(clientRepository.deleteById("1")).thenReturn(Mono.empty());

    Mono<Void> resultado = clientService.delete("1");

    StepVerifier.create(resultado)
        .expectComplete()
        .verify();
  }

  @Test
  void update() {
    RequestClientUpdate clientUpdate = new RequestClientUpdate();
    clientUpdate.setNroDocument("5");
    Mockito.when(clientRepository.findAll()).thenReturn(Flux.just(ca1,ca2));
    Mockito.when(clientRepository.findById("1")).thenReturn(Mono.just(ca1));
    Mockito.when(clientRepository.save(ca1)).thenReturn(Mono.just(ca1));
    Mockito.when(clientMapper.toDao(ca1,clientUpdate)).thenReturn(ca1);
    Mockito.when(clientMapper.toDto(ca1)).thenReturn(cl1);

    Mono<ClientDto> resultado = clientService.update("1",clientUpdate);

    StepVerifier.create(resultado)
        .expectNext(cl1)
        .expectComplete()
        .verify();
  }

  @Test
  void searchByPhone() {
    Mockito.when(clientRepository.findByPhone("999")).thenReturn(Mono.just(ca1));
    Mockito.when(clientMapper.toDto(ca1)).thenReturn(cl1);

    Mono<ClientDto> resultado = clientService.searchByPhone("999");

    StepVerifier.create(resultado)
        .expectNext(cl1)
        .expectComplete()
        .verify();
  }
}