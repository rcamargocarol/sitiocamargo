package br.com.sitio.camargo.vo;

import br.com.sitio.camargo.constantes.AnimalTipo;
import br.com.sitio.camargo.constantes.Pasto;

public class AnimalVO {
	
	public int _id;

	public int quantidade;

	public AnimalTipo animalTipo;
	
	public Pasto pasto;
	
	public String dateInsert;
	
	public String descricaoAnimalTipo ;
	
	public String descricaoPasto ;
	
	public int totalGeralPasto;
	
	public static String columnID = "_id" ;
	
	public static String colum_ID_PASTO = "ID_PASTO"  ;
	
	public static String column_ID_ANIMAL_TIPO  = "ID_ANIMAL_TIPO"  ;

	public static String column_ANIMAL_QUANTIDADES  = "QUANTIDADES" ;
	
	public static String column_DT_INSERT  = "DT_INSERT" ;
	

	public int getTotalGeralPasto() {
		return totalGeralPasto;
	}

	public void setTotalGeralPasto(int totalGeralPasto) {
		this.totalGeralPasto = totalGeralPasto;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public AnimalTipo getAnimalTipo() {
		return animalTipo;
	}

	public void setAnimalTipo(AnimalTipo animalTipo) {
		this.animalTipo = animalTipo;
	}

	public Pasto getPasto() {
		return pasto;
	}

	public void setPasto(Pasto pasto) {
		this.pasto = pasto;
	}

	public String getDateInsert() {
		return dateInsert;
	}
	
	

	public String getDescricaoAnimal() {
		return descricaoAnimalTipo;
	}

	public void setDescricaoAnimalTipo(String descricaoAnimal) {
		this.descricaoAnimalTipo = descricaoAnimal;
	}

	public String getDescricaoPasto() {
		return descricaoPasto;
	}

	public void setDescricaoPasto(String descricaoPasto) {
		this.descricaoPasto = descricaoPasto;
	}

	public void setDateInsert(String dateInsert) {
		this.dateInsert = dateInsert;
	}
	
	
	
}
