package org.example.service.client;

import org.example.service.dto.ClientDto;

import java.util.List;

public interface ClientServiceInterface {
	long create(String name);

	String getById(long id);

	void setName(long id, String name);

	void deleteById(long id);

	List<ClientDto> listAll();
}
