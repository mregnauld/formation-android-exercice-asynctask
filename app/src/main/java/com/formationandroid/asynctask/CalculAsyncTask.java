package com.formationandroid.asynctask;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class CalculAsyncTask extends AsyncTask<Long, Float, Boolean>
{
	
	// Référence faible sur l'activité appelante :
	WeakReference<MainActivity> weakActivity = null;
	
	
	/**
	 * Constructeur.
	 * @param activity MainActivity
	 */
	public CalculAsyncTask(MainActivity activity)
	{
		weakActivity = new WeakReference<>(activity);
	}
	
	@Override
	protected Boolean doInBackground(Long... nombres)
	{
		// récupération de l'argument :
		if (nombres.length > 0)
		{
			// nombre :
			long nombre = nombres[0];
			
			// calcul :
			long diviseurMax = (long) Math.ceil(Math.sqrt(nombre));
			for (long a = 2 ; a <= diviseurMax ; a++)
			{
				if (nombre % a == 0)
				{
					return false;
				}
				
				// mise à jour de la barre de progression tous les 10000 itérations, par exemple :
				if (a % 10000 == 0)
				{
					publishProgress((float) ((double) a / diviseurMax));
				}
			}
			return true;
		}
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Float... values)
	{
		if (weakActivity.get() != null)
		{
			weakActivity.get().updateProgression(values[0]);
		}
	}
	
	@Override
	protected void onPostExecute(Boolean resultat)
	{
		if (weakActivity.get() != null)
		{
			weakActivity.get().afficherResultat(resultat);
		}
	}
	
}
