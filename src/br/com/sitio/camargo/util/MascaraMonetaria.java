package br.com.sitio.camargo.util;

import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MascaraMonetaria implements TextWatcher {

	final EditText campo;

	public MascaraMonetaria(EditText campo) {
		super();
		this.campo = campo;
	}

	private boolean isUpdating = false;
	// Pega a formatacao do sistema, se for brasil R$ se EUA US$
	private NumberFormat nf = NumberFormat.getCurrencyInstance();

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int after) {
		// Evita que o m�todo seja executado varias vezes.
		// Se tirar ele entre em loop
		if (isUpdating) {
			isUpdating = false;
			return;
		}

		isUpdating = true;
		String str = s.toString();
		// Verifica se j� existe a m�scara no texto.
		boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) && (str
				.indexOf(".") > -1 || str.indexOf(",") > -1));
		// Verificamos se existe m�scara
		if (hasMask) {
			// Retiramos a m�scara.
			str =  str.replaceAll("[^\\d]", "");
		}

		try {
			// Transformamos o n�mero que est� escrito no EditText em
			// monet�rio.
			str = nf.format(Double.parseDouble(str) / 100);
			campo.setText(str);
			campo.setSelection(campo.getText().length());
		} catch (NumberFormatException e) {
			s = "";
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// N�o utilizado
	}

	@Override
	public void afterTextChanged(Editable s) {
		// N�o utilizado
	}

	public EditText getCampo() {
		return campo;
	}
	
	
}