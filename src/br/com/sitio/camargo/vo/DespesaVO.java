package br.com.sitio.camargo.vo;

public class DespesaVO {
	
	private int _id;
	private String descricao;
	private String dataInsercao;
	private String nomePessoa;
	private double valorDespesa;
	private double valorTotalDespesaPeriodo;
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(String dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	public String getNomePessoa() {
		return nomePessoa;
	}
	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	public double getValorDespesa() {
		return valorDespesa;
	}
	public void setValorDespesa(double valorDespesa) {
		this.valorDespesa = valorDespesa;
	}
	public double getValorTotalDespesaPeriodo() {
		return valorTotalDespesaPeriodo;
	}
	public void setValorTotalDespesaPeriodo(double valorTotalDespesaPeriodo) {
		this.valorTotalDespesaPeriodo = valorTotalDespesaPeriodo;
	}
	
	
	
}
