package org.example.service.client;

import org.example.data.entity.Client;
import org.example.service.dto.ClientDto;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {
	public static List<ClientDto> mapToDtoList(List<Client> entities) {
		return entities.stream()
				.map(entity -> mapToDto(entity))
				.collect(Collectors.toList());
	}

	private static ClientDto mapToDto (Client entity) {
		ClientDto dto = new ClientDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
}
