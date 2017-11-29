
package genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
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
    private TextArea sequenceArea;
    @FXML
    private TextArea infoArea;
    @FXML
    private TabPane tabPane;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis category;
  
    private List<Gene> genes = Genetic.getHistance().getGenes();
    private final int HIGH = 6;
    
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
            sequenceArea.setText("");
            textArea.clear();
            button.setDisable(false);
            clear.setDisable(true);
            barChart.getData().clear();
            tabPane.setDisable(true);
            infoArea.setDisable(true);
            sequenceArea.setDisable(true);
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
        fileNameLabel.setText("Carregando...");
        LoadFile load = LoadFile.getInstance();
        try
        {
            load.inicializa(file);
            tabPane.setDisable(false);
            infoArea.setDisable(false);
            sequenceArea.setDisable(false);
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
    
    private String createSequence(Gene gene)
    {
        return "Sequência: " + gene.getSequence();
    }
    
    private String createFormatedCodons(Gene gene)
    {
      return gene.getFormatedCodons();   
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
    
    private int getCodonFrequency(Gene gene, String codon)
    {
        return (int)gene.getCodons().stream().filter(c -> c.getCodon().contains(codon)).count();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
 
        clear.setDisable(true);
               
        listView.getSelectionModel().selectedItemProperty().addListener((observable, itemAntigo, novoItem) -> {
            
        if(novoItem != null)
        {
            Gene gene = getGeneByLocus(novoItem.toString());
            sequenceArea.setText(createSequence(gene));
            infoArea.setText(createFormatedInfo(gene));
            textArea.setText(createFormatedCodons(gene));
                       
            barChart.getData().clear();
            XYChart.Series<String, Integer> codonsFrequency = new XYChart.Series<>();           
            
            //Perdoa o  possível NullPointerException e não desiste de mim, sor.
            codonsFrequency.getData().add(new XYChart.Data<>("F", getCodonFrequency(gene, "F")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("L", getCodonFrequency(gene, "L")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("I", getCodonFrequency(gene, "I")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("V", getCodonFrequency(gene, "V")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("P", getCodonFrequency(gene, "P")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("T", getCodonFrequency(gene, "T")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("A", getCodonFrequency(gene, "A")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("Y", getCodonFrequency(gene, "Y")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("H", getCodonFrequency(gene, "H")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("Q", getCodonFrequency(gene, "Q")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("N", getCodonFrequency(gene, "N")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("K", getCodonFrequency(gene, "K")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("D", getCodonFrequency(gene, "D")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("E", getCodonFrequency(gene, "E")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("C", getCodonFrequency(gene, "C")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("W", getCodonFrequency(gene, "W")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("R", getCodonFrequency(gene, "R")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("S", getCodonFrequency(gene, "S")*HIGH));
            codonsFrequency.getData().add(new XYChart.Data<>("G", getCodonFrequency(gene, "G")*HIGH));
         
             barChart.getData().add(codonsFrequency);
        }
         
    });
    }    
    
}
