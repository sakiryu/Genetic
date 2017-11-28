
package genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import programLogic.LoadFile;
import programLogic.Genetic;
import programLogic.Gene;

/**
 * @author Luna
 */

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label fileNameLabel;
    @FXML
    private Button button;
    @FXML
    private Button clear;
    
    
    @FXML
    private ListView listView;
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea infoArea;
    
    @FXML
    private Rectangle recF, recL, recI, recV, recS, recP, recT, recA, recY;
    
    private Task task;
    private List<Gene> genes = Genetic.getHistance().getGenes();
    
    @FXML
    private void handleClearApp(ActionEvent event)
    {
        try
        {
            LoadFile.getInstance().setState(null);
            listView.getItems().clear();
            genes.clear();
            infoArea.setText("");
            fileNameLabel.setText("");
            textArea.clear();
            button.setDisable(false);
            clear.setDisable(true);
        }
        catch(NullPointerException e)
        {
            alert(e.getMessage(), AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {        
        FileChooser chooser = new FileChooser();
        
		 chooser.setTitle("Selecione o arquivo.");
		 
		 chooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

		 try
		 {
			Stage stage = new Stage();	
			stage.setResizable(false);
			stage.centerOnScreen();
			stage.getOnCloseRequest();
                        
                        
			
		    File file = chooser.showOpenDialog(stage);
                    
                    if(file!= null)
                    {
                        //fileNameLabel.setText("Carregado: "+file.getName());
                        loadData(file);
                        button.setDisable(true);                      
                    }
                    else
                    {
                        System.out.println("Operação cancelada.");
                        stage.close();
                    }
		 }
		 catch(RuntimeException r)
		 {
			System.out.println("Error:" + r.getMessage());
		 }
    }
    
    private void alert(String msg, AlertType alert)
    {
                Alert alerta = new Alert(alert);
		alerta.setHeaderText(msg);
		alerta.show();
    }
    
    private void loadData(File file)
    {
        LoadFile load = LoadFile.getInstance();
        fileNameLabel.setText("Carregando...");
        try
        {
            load.inicializa(file);
        }
        catch(FileNotFoundException e)
        {
                Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Arquivo não encontrado\n"+e.getMessage());
		alert.show();
        }
   
        genes.forEach(gene -> listView.getItems().add(gene.getLocus()));
        
          fileNameLabel.setText("Carregado: "+file.getName());
          clear.setDisable(false);

        
    }
    
    private String createFormatedCodons(Gene gene)
    {
      return "Sequência: " + gene.getSequence() + "\n" +
                               "\n[Distância] | Codons:\n" + gene.getFormatedCodons();   
    }
    
    private String createFormatedInfo(Gene gene)
    {
        return "Locus: " + gene.getLocus() + 
                               " | Início: " + gene.getBegin() + 
                               " | Fim: " + gene.getEnd() + 
                               " | Maior distância: " + gene.highDistance();   
    }
    
    private Gene getGeneByLocus(String locus)
    {
       return genes.stream().filter(g -> g.getLocus().equals(locus)).findFirst().get();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        clear.setDisable(true);
        Rectangle rec1 = new Rectangle(241, 331, 5, 63);
        rec1.setFill(Color.BLACK);
        
        //rec.setFill(Color.ORANGE);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, itemAntigo, novoItem) -> {
            
        if(novoItem != null)
        {
            Gene gene = getGeneByLocus(novoItem.toString());
            infoArea.setText(createFormatedInfo(gene));
            textArea.setText(createFormatedCodons(gene));
        }
    });
    }    
    
}
