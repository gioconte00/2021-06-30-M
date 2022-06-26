/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {

    	
    	try {
        	double soglia = Double.parseDouble(this.txtSoglia.getText());
        	
    		if(this.model.ifCompreso(soglia)==true) {
    	
    	this.txtResult.appendText("\nsoglia: "+soglia+" ---> Maggiori: "+this.model.getArchiMaggiori(soglia)+
    			", Minori: "+this.model.getArchiMinori(soglia));
    	}
    	else {
    		this.txtResult.setText("inserisci una soglia valida!");
    		return;
    	}
    		
    } catch (NumberFormatException e) {
    	this.txtResult.setText("Inserisci un formato valido nel campo soglia!");
    	
    }
    	
    }

    @FXML
    void doRicerca(ActionEvent event) {

    	this.txtResult.clear();
    	double soglia = Double.parseDouble(this.txtSoglia.getText());
    	this.model.getCammino(soglia);
    	this.txtResult.setText("Percorso: \n");
    	for(Integer i:this.model.getRisultato()) {
    		this.txtResult.appendText("Cromosoma "+i+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		
		this.txtResult.appendText(this.model.creaGrafo()+"\n peso min: "+this.model.getPesoMin()+
						", peso max: "+this.model.getPesoMax());
		
		
		
	}
}
