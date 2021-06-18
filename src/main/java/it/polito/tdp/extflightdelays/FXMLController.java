package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Adiacenza;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare ai branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField distanzaMinima;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	txtResult.clear();
    	String xS=this.distanzaMinima.getText();
    	Integer x=0;
    	try {
    		x=Integer.parseInt(xS);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	this.model.creaGrafo(x);
    	this.txtResult.appendText("GRAFO CRATO!\n");
    	this.txtResult.appendText("# vertici: "+this.model.getVertici()+"\n");
    	this.txtResult.appendText("# archi: "+this.model.getArchi()+"\n");
    	this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getAirport());
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	txtResult.clear();
    	Airport partenza=this.cmbBoxAeroportoPartenza.getValue();
    	List<Adiacenza> adia=new ArrayList<Adiacenza>(this.model.getAdiacenti(partenza));
    	this.txtResult.appendText("VICINI DI "+partenza+" SONO :\n");
    	for(Adiacenza a:adia)
    	{
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	txtResult.clear();
    	Airport partenza=this.cmbBoxAeroportoPartenza.getValue();
    	String sogliaS=this.numeroVoliTxtInput.getText();
    	Integer soglia=0;
    	try {
    		soglia=Integer.parseInt(sogliaS);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	List<Airport> best=new ArrayList<Airport>(this.model.getListaBest(partenza, soglia));
    	for(Airport a:best)
    	{
    		txtResult.appendText(a.toString()+"\n");
    	}
    	txtResult.appendText(this.model.distanzaTot+"\n");
    	
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
