package org.example.data.daos;

import org.example.data.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoService {

	private static final String INSERT_STRING =
			"INSERT INTO client (name) VALUES (?);";
	private static final String SELECT_BY_ID_STRING =
			"SELECT id, name FROM client WHERE id = ?;";
	private static final String SELECT_ALL_STRING =
			"SELECT * FROM client;";
	private static final String UPDATE_CLIENT_STRING =
			"UPDATE client SET name = ? WHERE id = ?;";
	private static final String SELECT_LAST_CLIENT_STRING =
			"SELECT * FROM client WHERE id IN (SELECT MAX(id) FROM client);";
	private static final String DELETE_BY_ID_STRING =
			"DELETE FROM client WHERE id = ?;";

	private Connection connection;
	private PreparedStatement insertStatement;
	private PreparedStatement selectByIdStatement;
	private PreparedStatement selectAllStatement;
	private PreparedStatement updateStatement;
	private PreparedStatement selectLastClient;
	private PreparedStatement deleteByIdStatement;

	public ClientDaoService(Connection connection) {
		this.connection = connection;
		try {
			this.insertStatement = connection.prepareStatement(INSERT_STRING);
			this.selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_STRING);
			this.selectAllStatement = connection.prepareStatement(SELECT_ALL_STRING);
			this.updateStatement = connection.prepareStatement(UPDATE_CLIENT_STRING);
			this.selectLastClient = connection.prepareStatement(SELECT_LAST_CLIENT_STRING);
			this.deleteByIdStatement = connection.prepareStatement(DELETE_BY_ID_STRING);
		} catch (SQLException e) {
			System.out.println("Client Service construction exception. Reason: " + e.getMessage());
		}
	}

	public Optional<Client> findLastClient() {
		try (ResultSet resultSet = this.selectLastClient.executeQuery()) {
			if(resultSet.next()) {
				Client client = new Client(resultSet.getLong("id"),
						resultSet.getString("name"));
				return Optional.of(client);
			}
		} catch(SQLException e) {
			System.out.println("Select client exception. Reason: " + e.getMessage());
		}
		return Optional.empty();
	}

	public long saveClient(Client client) {
		try {
			this.insertStatement.setString(1, client.getName());
			return this.insertStatement.executeUpdate();
		} catch(SQLException e) {
			System.out.println("Insert client exception. Reason: " + e.getMessage());
			return -1;
		}
	}

	public Optional<Client> findClientById(Long id) {
		try {
			this.selectByIdStatement.setLong(1, id);
			try (ResultSet resultSet = this.selectByIdStatement.executeQuery()) {
				if(resultSet.next()) {
					Client client = new Client(resultSet.getLong("id"),
							resultSet.getString("name"));
					return Optional.of(client);
				}
			} catch(SQLException e) {
				System.out.println("Select client exception. Reason: " + e.getMessage());
			}
		} catch(SQLException e) {
			System.out.println("Select client exception. Reason: " + e.getMessage());
		}
		return Optional.empty();
	}

	public List<Client> findAllClient() {
		List<Client> clients = new ArrayList<>();
		try(ResultSet resultSet = this.selectAllStatement.executeQuery()) {
			while(resultSet.next()) {
				Client client = new Client(resultSet.getLong("id"),
						resultSet.getString("name"));
				clients.add(client);
			}
		} catch(SQLException e) {
			System.out.println("Select ALL client exception. Reason: " + e.getMessage());
		}
		return clients;
	}

	public Optional<Client> updateClient(Long id, String name) {
		try {
			this.updateStatement.setString(1, name);
			this.updateStatement.setLong(2, id);

			this.updateStatement.executeUpdate();
		} catch(SQLException e) {
			System.out.println("Update client exception. Reason: " + e.getMessage());
		}
		return findClientById(id);
	}

	public void deleteById(long id){
		try {
			deleteByIdStatement.setLong(1, id);
			deleteByIdStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Select client exception. Reason: " + e.getMessage());
		}
	}
}
