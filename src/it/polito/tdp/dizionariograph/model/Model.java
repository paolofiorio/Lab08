package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
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
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		List<String> parole = new ArrayList<String>();

		// Ottengo la lista dei vicini di un vertice
		parole.addAll(Graphs.neighborListOf(graph, parolaInserita));

		// Ritorno la lista dei vicini
		return parole;
		
	}

	public String findMaxDegree() {
		int max = 0;
		String temp = null;

		for (String vertex : graph.vertexSet()) {
			if (graph.degreeOf(vertex) > max) {
				max = graph.degreeOf(vertex);
				temp = vertex;
			}
		}

		if (max != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Grado max: %d dal vertice: %s\n", max, temp));

			for (String v : Graphs.neighborListOf(graph, temp))
				sb.append(v + "\n");

			return sb.toString();
		}
		return "Non trovato.";
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
