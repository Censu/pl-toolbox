package plt.gui;

import java.io.File;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import plt.dataset.PreprocessedDataSet;
import plt.dataset.TrainableDataSet;
import plt.dataset.preprocessing.Ignoring;
import plt.dataset.preprocessing.Nominal;
import plt.dataset.preprocessing.Numeric;
import plt.dataset.preprocessing.PreprocessingOperation;
import plt.dataset.sushireader.SushiFormatDataSet;
import plt.dataset.sushireader.UramakiFileParseStatus;
import plt.featureselection.FeatureSelection;
import plt.plalgorithm.PLAlgorithm;
import plt.report.Report;
import plt.validator.Validator;
import plt.validator.examples.KFoldCV;
import plt.validator.examples.NoValidation;

/**
 *
 * @author luca
 */
public class Experiment {
    
    
    public class FeaturesDataModel {
        private  SimpleStringProperty name;
        private SimpleBooleanProperty status;

        public FeaturesDataModel(String name, boolean status) {
            this.name = new SimpleStringProperty(name);
            this.status = new SimpleBooleanProperty(status);
        }
        
        public SimpleStringProperty nameProperty() { return this.name; }
        public SimpleBooleanProperty statusProperty() { return this.status; }

    }

    /*input*/
    private ObjectProperty<File> idata;
    private ObjectProperty<File> order;
    private StringProperty idataSeparator;
    private StringProperty orderSeparator;
    private IntegerProperty orderSkipLines;
    private IntegerProperty orderSkipColumns;
    
    /*dataSet & status*/
    private ObjectProperty<SushiFormatDataSet> dataSet;
    private BooleanProperty isReadyToParseIdata;
    private BooleanProperty isReadyToParseOrder;
    private BooleanProperty hasPerformedAParseStage;
    private BooleanProperty hasParsedBothIdataNOrder;
    private BooleanProperty isReadyToParse;
    private BooleanProperty isParsing;
    private BooleanProperty isReadyToUseDataSet;
    private ObjectProperty<UramakiFileParseStatus> dataSetParseStatus;
    
    /* algorithm*/
    private ObjectProperty<PLAlgorithm> algorithm;
    
    /* validation*/
    private BooleanProperty useValidator;
    private StringProperty k;

    /* preprocessing*/
    private ObjectProperty<PreprocessingOperation[]> preprocessingOperations;
    private ObjectProperty<boolean[]> ignoredFeatures;
    
    /* feature selection*/
    private BooleanProperty useValidatorForFeatureSelection;
    private StringProperty kForFeatureSelection;    
    private ObjectProperty<PLAlgorithm> algorithmForFeatureSelection;
    private ObjectProperty<FeatureSelection> featureSelection;
    
    /* property*/
    public BooleanProperty isReadyToParseIdataProperty() { return this.isReadyToParseIdata; }
    public BooleanProperty isReadyToParseOrderProperty() { return this.isReadyToParseOrder; }
    public BooleanProperty hasPerformedAParseStageProperty() { return this.hasPerformedAParseStage; }
    public BooleanProperty hasParsedBothIDataNOrderProperty() { return this.hasParsedBothIdataNOrder; }
    public BooleanProperty isReadyToUseDataSetProperty() { return this.isReadyToUseDataSet; }
    public BooleanProperty isParsingProperty() { return this.isParsing; }
    public BooleanProperty isReadyToParseroperty() { return this.isReadyToParse; }
    public ObjectProperty<File> idataProperty() { return this.idata; }
    public ObjectProperty<File> orderProperty() { return this.order; }
    public StringProperty idataSeparatorProperty() { return this.idataSeparator; }
    public StringProperty orderSeparatorProperty() { return this.orderSeparator; }
    public IntegerProperty orderSkipLinesProperty() { return this.orderSkipLines; }
    public IntegerProperty orderSkipColumnsProperty() { return this.orderSkipColumns; }
    public ObjectProperty<SushiFormatDataSet> dataSetProperty() { return this.dataSet; }
    public ObjectProperty<UramakiFileParseStatus> dataSetParseStatusProperty() { return this.dataSetParseStatus; }
    public ObjectProperty<PreprocessingOperation[]> preprocessingOperationsProperty() { return this.preprocessingOperations; }
    public ObjectProperty<boolean[]> ignoredFeaturesProperty() { return this.ignoredFeatures ; }
    public ObjectProperty<PLAlgorithm> algorithmProperty() { return this.algorithm; }
    public BooleanProperty useValidatorProperty() { return this.useValidator; }
    public BooleanProperty useValidatorForFeatureSelectionProperty() { return this.useValidatorForFeatureSelection; }
    public StringProperty kProperty() { return this.k; }
    public StringProperty kForFeatureSelectionProperty() {return this.kForFeatureSelection; }
    public ObjectProperty<PLAlgorithm> algorithmForFeatureSelectionProperty() { return this.algorithmForFeatureSelection; }
    public ObjectProperty<FeatureSelection> featureSelectionProperty() { return this.featureSelection; }

    public Experiment() {
        
        final Experiment self = this;
        this.isReadyToParseIdata = new SimpleBooleanProperty(false);
        this.isReadyToParseOrder = new SimpleBooleanProperty(false);
        this.hasPerformedAParseStage = new SimpleBooleanProperty(false);
        this.hasParsedBothIdataNOrder = new SimpleBooleanProperty(false);
        this.isParsing = new SimpleBooleanProperty(false);
        this.isReadyToParse = new SimpleBooleanProperty(true);
        this.isReadyToUseDataSet = new SimpleBooleanProperty(false);
        this.orderSeparator = new SimpleStringProperty("");
        this.idataSeparator = new SimpleStringProperty("");
        this.orderSkipLines = new SimpleIntegerProperty(0);
        this.orderSkipColumns = new SimpleIntegerProperty(0);
        this.idata = new SimpleObjectProperty<>();
        this.order = new SimpleObjectProperty<>();
        this.dataSet = new SimpleObjectProperty<>();
        this.dataSetParseStatus = new SimpleObjectProperty<>();
        this.preprocessingOperations = new SimpleObjectProperty<>();
        this.ignoredFeatures = new SimpleObjectProperty<>();
        this.algorithm = new SimpleObjectProperty<>();
        this.useValidator = new SimpleBooleanProperty(false);
        this.useValidatorForFeatureSelection = new SimpleBooleanProperty(false);
        this.k = new SimpleStringProperty();
        this.kForFeatureSelection = new SimpleStringProperty();
        this.featureSelection = new SimpleObjectProperty<>();
        this.algorithmForFeatureSelection = new SimpleObjectProperty<>();

        self.dataSet.set(new SushiFormatDataSet());
        
        
        this.isReadyToParseIdata.addListener(new ChangeListener<Boolean>() {
                
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue)
            {
                if(newValue)
                {
                    SushiFormatDataSet internalDataSet = self.dataSet.get();
                    internalDataSet.setIData(self.idata.getValue(),self.idataSeparator.getValue());
                    
                    UramakiFileParseStatus updatedParseStatus = internalDataSet.parseIData();
                    
                    if(internalDataSet.containsOrderData())
                    {
                        updatedParseStatus = internalDataSet.parseOrderData();
                    }
                    
                    self.dataSetParseStatus.set(updatedParseStatus); 
                    
                    self.dataSet.set(internalDataSet);
                    
                    self.hasPerformedAParseStage.setValue(true);
                    
                    if((internalDataSet.containsIData())&&(internalDataSet.containsOrderData()))
                    {
                        self.hasParsedBothIdataNOrder.setValue(true);
                    }
                }
            }
        });
        
        this.isReadyToParseOrder.addListener(new ChangeListener<Boolean>() {
            
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue)
            {
                if(newValue)
                {
                    SushiFormatDataSet internalDataSet = self.dataSet.get();
                    internalDataSet.setOrderData(self.order.getValue(),
                                                 self.orderSeparator.getValue(),
                                                 self.orderSkipLines.getValue(),
                                                 self.orderSkipColumns.getValue());
                    
                    UramakiFileParseStatus updatedParseStatus = internalDataSet.parseOrderData();
                    self.dataSetParseStatus.set(updatedParseStatus);
                    
                    self.dataSet.set(internalDataSet);
                    
                    self.hasPerformedAParseStage.setValue(true);
                    
                    if((internalDataSet.containsIData())&&(internalDataSet.containsOrderData()))
                    {
                        self.hasParsedBothIdataNOrder.setValue(true);
                    }
                }
            }
        });
        
        
        
        
        this.hasParsedBothIdataNOrder.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (newValue) 
                {
                    self.hasParsedBothIdataNOrder.set(false);
                    
                    self.isReadyToUseDataSet.set(false);
                    self.isParsing.set(true);
                    
                    Task<Boolean> task = new Task<Boolean>() {

                        @Override
                        protected Boolean call() throws Exception {

                            boolean result;               
                            
                            
                            // Assumes parsing has been done previously.
                            // This part only checks if the parsing was successful.
                            

                            result = self.dataSetParseStatus.get().overallParseResult;
                                

                            if (result) {
                                PreprocessingOperation[] po = new PreprocessingOperation[self.dataSet.get().getNumberOfFeatures()];
                                boolean[] ignored = new boolean[self.dataSet.get().getNumberOfFeatures()];

                                for (int i=0; i<po.length; i++) {
                                    if (!self.dataSet.get().isNumeric(i)) 
                                        po[i] = new Nominal(self.dataSet.get(),i);
                                    else 
                                        po[i] = new Numeric(self.dataSet.get(), i);
                                    
                                    ignored[i] = false;
                                }
                                
                                self.preprocessingOperations.set(po);
                                self.ignoredFeatures.set(ignored);
                            }
                            return result;
                        }
                    };
                    
                    task.valueProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                            self.isParsing.set(false);
                            self.isReadyToUseDataSet.set(t1);
                        }
                    });
                    new Thread(task).start();
               }
            }
        });
   }
   
   public Report start() {

        
       boolean[] ignored = this.ignoredFeatures.get();
       PreprocessingOperation[] ops = new PreprocessingOperation[this.preprocessingOperations.get().length];
       
       
       for (int i=0; i < ignored.length ; i++)
           if (ignored[i]) 
               ops[i] = new Ignoring(this.dataSetProperty().get(), i);
           else
               ops[i] = this.preprocessingOperations.get()[i];

       TrainableDataSet t = new PreprocessedDataSet(this.dataSet.get(), ops);
       
       
       this.algorithm.get().setDataSet(t);

       
       ExecutionProgress.updateProgress(0.33f);
       
       
       Validator validator = new NoValidation();
          
       if (this.useValidator.get()) {
        int d = Integer.parseInt(this.k.get());
        validator = new KFoldCV(d);
       }
       

      
       if (this.featureSelectionProperty().get() != null) {
           
           
           Logger.getLogger("plt.logger").log(Level.INFO, "running feature selection");

           FeatureSelection f = this.featureSelectionProperty().get();
           Validator validatorForFS = new NoValidation();
           if (this.useValidatorForFeatureSelection.get()) {
               int d = Integer.parseInt(this.kForFeatureSelection.get());
               validatorForFS = new KFoldCV(d);
           }
           
           PLAlgorithm algoFS = this.algorithmForFeatureSelection.get();
           algoFS.setDataSet(t);
           
           f.run(validatorForFS, algoFS);
           
            Logger.getLogger("plt.logger").log(Level.INFO, "selected feature: \n"+f.getResult());

           this.algorithm.get().setSelectedFeature(f.getResult());
       }
       
       ExecutionProgress.updateProgress(0.66f);
       
       Logger.getLogger("plt.logger").log(Level.INFO, "running experimen - dataset: \n"+t);

       Report retRep = this.algorithm.get().createModelWithValidation(validator);
       ExecutionProgress.updateProgress(1.0f);
       
       return retRep;
   }
    
}
