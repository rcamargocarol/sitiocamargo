package br.com.sitio.camargo.constantes;

public enum AnimalTipo {
	VACA (1), BOI (2), BEZERRO (3), BEZERRA (4), NOVILHA(5), CAVALO (6);
	
	private int identificacao ;
	
	
	
	public int getIdentificacao() {
		return identificacao;
	}



	public void setIdentificacao(int identificacao) {
		this.identificacao = identificacao;
	}



	AnimalTipo(int elemento) {
		// TODO Auto-generated constructor stub
		identificacao = elemento ;
	}
	
	
	
	public static AnimalTipo getEAnimalTipo(int value){  
        
        for (AnimalTipo animalTipo : AnimalTipo.values()){  
            if(animalTipo.getIdentificacao() == value){  
                return animalTipo;  
            }  
        }  
          
        return null;  
    }  
	
	
	public static int  getEAnimalTipo(String value){  
        
        for (AnimalTipo animalTipo : AnimalTipo.values()){  
            if(animalTipo.name().equalsIgnoreCase(value)){  
                return animalTipo.getIdentificacao();  
            }  
        }  
          
        return 0;  
    }  
	
}
