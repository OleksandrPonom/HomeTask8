package org.example.service.client;

import org.example.data.daos.ClientDaoService;
import org.example.data.entity.Client;
import org.example.service.dto.ClientDto;

import java.util.List;
import java.util.Optional;

import static org.example.service.client.ClientMapper.mapToDtoList;

public class ClientService implements ClientServiceInterface{

	private ClientDaoService clientDaoService;

	public ClientService(ClientDaoService clientDaoService) {
		this.clientDaoService = clientDaoService;
	}

	@Override
	public long create(String name) {
		Client newClient = new Client(name);
		if(name.length() > 2 && name.length() < 1000 && name != null){
			clientDaoService.saveClient(newClient);
			Optional<Client> optionalSaveClient = clientDaoService.findLastClient();
			if(optionalSaveClient.isPresent()){
				return optionalSaveClient.get().getId();
			}else {
				throw new RuntimeException("Client not create!");
			}
		} else {
			throw new RuntimeException("The client name does not meet the condition!");
		}
	}

	@Override
	public String getById(long id) {
		Optional<Client> getOptionalClient = clientDaoService.findClientById(id);
		if(getOptionalClient.isPresent()){
			return getOptionalClient.get().getName();
		}else {
			throw new RuntimeException("Client with id " + id + " not found!");
		}
	}

	@Override
	public void setName(long id, String name) {
		Optional<Client> getOptionalClient = clientDaoService.findClientById(id);
		if(getOptionalClient.isPresent()){
			if(name.length() > 2 && name.length() < 1000 && name != null){
				clientDaoService.updateClient(id, name);
			} else{
				throw new RuntimeException("The client name does not meet the condition!");
			}
		}else {
			throw new RuntimeException("Client with id " + id + " not found!");
		}
	}

	@Override
	public void deleteById(long id) {
		Optional<Client> getOptionalClient = clientDaoService.findClientById(id);
		if(getOptionalClient.isPresent()){
			clientDaoService.deleteById(id);
		}else {
			throw new RuntimeException("Client with id " + id + " not found!");
		}
	}

	@Override
	public List<ClientDto> listAll() {
		return mapToDtoList(clientDaoService.findAllClient());
	}
}
