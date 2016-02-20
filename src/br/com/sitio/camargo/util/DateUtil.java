package br.com.sitio.camargo.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	
	public static Date obterPeriodoInicioMes (String mesAno){
		
		if(mesAno != null && mesAno.contains("/")){
			Calendar cal = Calendar.getInstance();
			String mesAnoString[]  = mesAno.split("/");
			cal.set(Calendar.MONTH,Integer.valueOf(mesAnoString[0])-1);
			cal.set(Calendar.YEAR, Integer.valueOf(mesAnoString[1]));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
			return  cal.getTime();  
			
		}else{
			return Calendar.getInstance().getTime() ;
		}
	}

	public static Date obterPeriodoFinalMes (String mesAno){
		
		if(mesAno != null && mesAno.contains("/")){
			Calendar cal = Calendar.getInstance();
			String mesAnoString[]  = mesAno.split("/");
			cal.set(Calendar.MONTH,Integer.valueOf(mesAnoString[0])-1);
			cal.set(Calendar.YEAR, Integer.valueOf(mesAnoString[1]));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
			return  cal.getTime();  
			
		}else{
			return Calendar.getInstance().getTime() ;
		}
	}
	
	
	public static Date obterdiaAtualByMesAno(String mesAno){
		
		if(mesAno != null && mesAno.contains("/")){
			Calendar cal = Calendar.getInstance();
			String mesAnoString[]  = mesAno.split("/");
			cal.set(Calendar.MONTH,Integer.valueOf(mesAnoString[0])-1);
			cal.set(Calendar.YEAR, Integer.valueOf(mesAnoString[1]));
			return  cal.getTime();  
			
		}else{
			return Calendar.getInstance().getTime() ;
		}
	}
	

}
