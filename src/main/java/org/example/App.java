package org.example;

import org.example.config.Database;
import org.example.data.daos.ClientDaoService;
import org.example.service.client.ClientService;
import org.example.service.client.ClientServiceInterface;
import org.example.service.dto.ClientDto;

import java.sql.Connection;
import java.util.List;

public class App {
	public static void main(String[] args) {
		Connection connection = Database.getInstance().getConnection();
		ClientDaoService clientDaoService = new ClientDaoService(connection);
		ClientServiceInterface clientService = new ClientService(clientDaoService);

		long createClient = clientService.create("Igor Druz");
		System.out.println("===> " + createClient);

		String getClientById = clientService.getById(2L);
		System.out.println("---> " + getClientById);

		clientService.setName(10L, "Djon Dou");

		clientDaoService.deleteById(7L);

		List<ClientDto> dtos = clientService.listAll();
		dtos.forEach(dto -> System.out.println("++++> " + dto));

	}
}
