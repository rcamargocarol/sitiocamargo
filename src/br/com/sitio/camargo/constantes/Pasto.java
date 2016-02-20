package br.com.sitio.camargo.constantes;

public enum Pasto {
	
	CARLINHOS(1), TIMOTEO_MATA(2), DORCILIO(3), LIMA_SEDE(4),PAULINHO(5), DIOLA(6),SITINHO_JOAQUIM(7), PASTO_LEITEIRAS(8),TODOS_OS_PASTOS(9) ;
	
	int identificacao ;
	
	Pasto(int identificacao){
		this.identificacao = identificacao;
	}

	public int getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(int identificacao) {
		this.identificacao = identificacao;
	}
	
	public static Pasto getEPasto(int value){  
        
        for (Pasto pasto : Pasto.values()){  
            if(pasto.getIdentificacao() == value){  
                return pasto;  
            }  
        }  
          
        return null;  
    }  
	
	
	public static int getEPasto(String value){  
        
        for (Pasto pasto : Pasto.values()){  
            if(pasto.name().equalsIgnoreCase(value)){  
                return pasto.getIdentificacao();  
            }  
        }  
          
        return 0;  
    }  
	
	 
	
	
}
