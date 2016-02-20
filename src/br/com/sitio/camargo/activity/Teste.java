package br.com.sitio.camargo.activity;

public class Teste {

	
	
	public static void main(String[] args) {
		
		
		String teste = "R$2.5555,08";
		
		System.out.println(teste.replaceAll("[^\\d.]",""));
	}
}
