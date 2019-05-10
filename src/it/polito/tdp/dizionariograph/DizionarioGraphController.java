package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtLettere;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGeneraGrafo;

    @FXML
    private Button btnTrovaVicini;

    @FXML
    private Button btnGradoMax;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void doGeneraGrafo(ActionEvent event) {

    	txtRisultato.clear();
    	txtParola.clear();

		try {
			int numeroLettere = Integer.parseInt(txtLettere.getText());
			System.out.println("numero di Lettere: " + numeroLettere);

			List<String> parole = model.createGraph(numeroLettere);

			if (parole != null) {
				txtRisultato.setText("Trovate " + parole.size() + " parole di lunghezza " + numeroLettere);
			} else {
				txtRisultato.setText("Trovate 0 parole di lunghezza: " + numeroLettere);
			}

			txtLettere.setDisable(true);
			btnGeneraGrafo.setDisable(true);
			txtParola.setDisable(false);
			btnTrovaVicini.setDisable(false);
			btnGradoMax.setDisable(false);

		} catch (NumberFormatException nfe) {
			txtRisultato.setText("Inserire un numero corretto di lettere!");
		} catch (RuntimeException re) {
			txtRisultato.setText(re.getMessage());
		}
    
    }

    

    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    	try {
			txtRisultato.setText(model.findMaxDegree());

		} catch (RuntimeException re) {
			txtRisultato.setText(re.getMessage());
}
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	try {
			String parolaInserita = txtParola.getText();
			if (parolaInserita == null || parolaInserita.length() == 0) {
				txtRisultato.setText("Parola non iserita, inserirne una cercare");
				return ;
			}

			List<String> parole = model.displayNeighbours(parolaInserita);
			if (parole != null) {
				txtRisultato.setText(parole.toString());
			} else {
				txtRisultato.setText("Non è stato trovato nessun risultato");
			}
		} catch (RuntimeException re) {
			txtRisultato.setText(re.getMessage());
}

    }
    
    @FXML
    void doReset(ActionEvent event) {
    	txtLettere.clear();
    	txtParola.clear();
    	txtRisultato.clear();	
    	
    	txtLettere.setDisable(false);
    	txtParola.setDisable(true);
    	btnGeneraGrafo.setDisable(false);
    	btnTrovaVicini.setDisable(true);
    	btnGradoMax.setDisable(true);
    }

    @FXML
    void initialize() {
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtLettere != null : "fx:id=\"txtLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGradoMax != null : "fx:id=\"btnGradoMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

        txtParola.setDisable(true);
        btnTrovaVicini.setDisable(true);
    	btnGradoMax.setDisable(true);
    }
    public void setModel(Model model) {
    	this.model=model;
    }
}
