package org.example.data.entity;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	private Long id;
	private String name;

	public Client(String name) {
		this.name = name;
	}
}
