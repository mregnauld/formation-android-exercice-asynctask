package com.formationandroid.asynctask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity
{
	
	// Vues :
	private EditText editTextNombre = null;
	private TextView textViewResultat = null;
	private ProgressBar progressBar = null;
	private Button buttonValider = null;
	
	// Timestamp début calcul :
	private long timestampDebut = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// vues :
		editTextNombre = findViewById(R.id.saisie_nombre);
		textViewResultat = findViewById(R.id.resultat);
		progressBar = findViewById(R.id.progressbar);
		buttonValider = findViewById(R.id.bouton_valider);
		
		// exemple de nombre premier de 15 chiffres :
		editTextNombre.setText("993960000099397");
	}
	
	/**
	 * Listener clic bouton valider.
	 * @param view Bouton valider
	 */
	public void onClickBoutonValider(View view)
	{
		// réinitialisation du message résultat :
		textViewResultat.setText("");
		
		// affichage de la progressbar et masquage du bouton :
		progressBar.setVisibility(View.VISIBLE);
		buttonValider.setVisibility(View.GONE);
		
		// récupération de la saisie :
		String saisie = editTextNombre.getText().toString();
		if (!TextUtils.isEmpty(saisie))
		{
			// timestamp de début :
			timestampDebut = new Date().getTime();
			
			// lancement du calcul :
			CalculAsyncTask asyncTask = new CalculAsyncTask(this);
			asyncTask.execute(Long.parseLong(saisie));
		}
		else
		{
			// message d'erreur si aucune saisie :
			Toast.makeText(this, R.string.main_erreur_saisie, Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Mise à jour de la progression.
	 * @param progression Progression
	 */
	public void updateProgression(float progression)
	{
		progressBar.setProgress((int) (progression * 100));
	}
	
	/**
	 * Affichage du résultat du calcul
	 * @param resultat Résultat
	 */
	public void afficherResultat(Boolean resultat)
	{
		// timestamp de fin :
		long timestampFin = new Date().getTime();
		
		// affichage du bouton et masquage de la progressbar :
		progressBar.setVisibility(View.GONE);
		buttonValider.setVisibility(View.VISIBLE);
		
		// résultat :
		if (resultat == null)
		{
			Toast.makeText(this, R.string.main_erreur_calcul, Toast.LENGTH_LONG).show();
		}
		else
		{
			if (resultat)
			{
				textViewResultat.setText(getString(R.string.main_resultat_positif, (timestampFin - timestampDebut) + " ms"));
				textViewResultat.setTextColor(ContextCompat.getColor(this, R.color.couleurMainResultatPositif));
			}
			else
			{
				textViewResultat.setText(getString(R.string.main_resultat_negatif));
				textViewResultat.setTextColor(ContextCompat.getColor(this, R.color.couleurMainResultatNegatif));
			}
		}
	}
	
}
