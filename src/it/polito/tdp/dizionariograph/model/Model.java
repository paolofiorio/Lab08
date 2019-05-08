package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private Graph<String, DefaultEdge> graph;
	private int numeroLettere = 0;
	private WordDAO wordDAO;

	public Model() {
		wordDAO = new WordDAO();
}

	public List<String> createGraph(int numeroLettere) {

		graph = new SimpleGraph<>(DefaultEdge.class);
		this.numeroLettere = numeroLettere;

		List<String> parole = wordDAO.getAllWordsFixedLength(numeroLettere);

		// Aggiungo tutti i vertici del grafo
		for (String parola : parole)
			graph.addVertex(parola);
		
		
		for(String parola:parole) {
			
			List<String> paroleSimili = getParoleSimili(parole, parola);

			// Creo l'arco
			for (String parolaSimile : paroleSimili) {
				graph.addEdge(parola, parolaSimile);
			}
			
			
		}
		
		return parole;
		
		
		
			}

	public List<String> displayNeighbours(String parolaInserita) {

		System.err.println("displayNeighbours -- TODO");
		return new ArrayList<String>();
	}

	public int findMaxDegree() {
		System.err.println("findMaxDegree -- TODO");
		return -1;
	}
	
	
	public static boolean distanzaUno(String first, String second) {

		if (first.length() != second.length())
			throw new RuntimeException("Le due parole hanno una lunghezza diversa.");

		int distanza = 1;
		for (int i = 0; i < first.length(); i++) {
			if (first.charAt(i) != second.charAt(i))
				distanza--;
		}

		if (distanza == 0)
			return true;
		else
			return false;
	}

	public static List<String> getParoleSimili(List<String> parole, String parola) {

		List<String> paroleSimili = new ArrayList<String>();
		for (String p : parole) {
			if (distanzaUno(parola, p))
				paroleSimili.add(p);
		}

		return paroleSimili;
}
	
	
	
	
	
	
	
}
