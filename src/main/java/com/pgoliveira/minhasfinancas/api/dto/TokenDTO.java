package com.pgoliveira.minhasfinancas.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class TokenDTO {
	
	private String nome;
	private String token;
}
