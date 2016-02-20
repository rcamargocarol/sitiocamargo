package br.com.sitio.camargo.vo;

import java.util.Calendar;

public class PessoaVO {
	
	private int _id ;
	private String nome ;
	private Calendar calendar ;
	private String cpf ;
	private String cnpj ;
	private String logradouro;
	private int cep;
	private String complemento;
	private String telefone;
	private int numeroTelefoneDDD;
	private int numeroTelefone;
	private long identificacaoCpfOrCnpj;
	private String dateYYYYMMdd ;
	public static  String columnID = "_ID";
	public static String columID_Pessoa = "ID_PESSOA" ;
	public static  String columnNome = "NOME";
	public static String columndtInsert = "DT_INSERT";
	public static String columncpf = "CPF";
	public static  String columncnpj = "CNPJ";
	public static  String columnlogradouro = "LOGRADOURO";
	public static  String columnCEP = "CEP";
	public static  String columnComplemento = "COMPLEMENTO";
	public static String columnRazaoSocial = "DESCRICAO";
	public static String columnTelefoneDD = "NUMERO_DDD";
	public static String columnTelefoneNumero = "NUMERO_TELEFONE" ;
	
	
	
	
	
	
	public void setIdentificacaoCpfOrCnpj(long identificacaoCpfOrCnpj) {
		this.identificacaoCpfOrCnpj = identificacaoCpfOrCnpj;
	}
	public int getNumeroTelefoneDDD() {
		return numeroTelefoneDDD;
	}
	public void setNumeroTelefoneDDD(int numeroTelefoneDDD) {
		this.numeroTelefoneDDD = numeroTelefoneDDD;
	}
	public int getNumeroTelefone() {
		return numeroTelefone;
	}
	public void setNumeroTelefone(int numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public int getCep() {
		return cep;
	}
	public void setCep(int cep) {
		this.cep = cep;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getIdentificacaoCpfOrCnpj() {
		return identificacaoCpfOrCnpj;
	}
	public void setIdentificacaoCpfOrCnpj(Long identificacaoCpfOrCnpj) {
		this.identificacaoCpfOrCnpj = identificacaoCpfOrCnpj;
	}
	public String getDateYYYYMMdd() {
		return dateYYYYMMdd;
	}
	public void setDateYYYYMMdd(String dateYYYYMMdd) {
		this.dateYYYYMMdd = dateYYYYMMdd;
	}
	

	
	
	
	
	
	

}
