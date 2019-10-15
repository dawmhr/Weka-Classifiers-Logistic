/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml_loan;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 *
 * @author Student
 */
public class Classification_Logistics_Loan {

    private String traningFilename;
    private String testingFilename;
    private String predictFilename;
    private Classifier classifier;
    private int classAtribute;

       public Classification_Logistics_Loan() {
        //  this.classAtribute = classAtribute;
    }
    public Classification_Logistics_Loan(String traningFilename, String testingFilename, String predictFilename) {
        this.traningFilename = traningFilename;
        this.testingFilename = testingFilename;
        this.predictFilename = predictFilename;
        //  this.classAtribute = classAtribute;
    }

    public Classification_Logistics_Loan(Classifier classifier) {
        this.classifier = classifier;
    }

    public Classifier getClassifier() {
        return classifier;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }

    public String getTraningFilename() {
        return traningFilename;
    }

    public void setTraningFilename(String traningFilename) {
        this.traningFilename = traningFilename;
    }

    public String getTestingFilename() {
        return testingFilename;
    }

    public void setTestingFilename(String testingFilename) {
        this.testingFilename = testingFilename;
    }

    public String getPredictFilename() {
        return predictFilename;
    }

    public void setPredictFilename(String predictFilename) {
        this.predictFilename = predictFilename;
    }

    public int getClassAtribute() {
        return classAtribute;
    }

    public void setClassAtribute(int classAtribute) {
        this.classAtribute = classAtribute;
    }

    public Instances getDataset(String fileName) {
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File(fileName));
            Instances dataset = loader.getDataSet();
            dataset.setClassIndex(classAtribute);
            return dataset;
        } catch (IOException ex) {
            Logger.getLogger(Classification_Logistics_Loan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void traningAndTesting() {
        Instances trainingDataSet = getDataset(traningFilename);
        Instances testingDataSet = getDataset(testingFilename);
        classifier = new Logistic();
        try {
            classifier.buildClassifier(trainingDataSet);
            Evaluation evaluation = new Evaluation(trainingDataSet);
            evaluation.evaluateModel(classifier, testingDataSet);
            System.out.println("Load Model");
            System.out.println(classifier);
            System.out.println("Load Evaluation");
            System.out.println(evaluation.toSummaryString());
        } catch (Exception ex) {
            Logger.getLogger(Classification_Logistics_Loan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void predictDataset() {
        System.out.println("Predict From Data set file");
        Instance predictDataSet;
        double answerValue;

        Instances predictDataSets = getDataset(testingFilename);
        for (int i = 0; i < predictDataSets.numInstances(); i++) {
            try {
                predictDataSet = predictDataSets.instance(i);
                answerValue = classifier.classifyInstance(predictDataSet);
                printAttribute(predictDataSet);
                if (answerValue == 0) {
                    System.out.println("You can loan");
                } else {
                    System.out.println("sorry you cannot loan");
                }
            } catch (Exception ex) {
                Logger.getLogger(Classification_Logistics_Loan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void printAttribute(Instance ins) {
        System.out.println("Married " + ins.stringValue(0));
        System.out.println("Dependents " + ins.stringValue(1));
        System.out.println("Education " + ins.stringValue(2));
        System.out.println("Self_Employed " + ins.stringValue(3));
        System.out.println("ApplicantIncome " + ins.value(4));
        System.out.println("CoapplicantIncome " + ins.value(5));
        System.out.println("LoanAmount " + ins.value(6));
        System.out.println("Loan_Amount_Term " + ins.value(7));
        System.out.println("Property_Area " + ins.stringValue(8));
    }

    public String preDictOneInstance(String Married,
            String Dependents,
            String Education,
            String SelfEmployed,
            double ApplicantIncome,
            double CoapplicantIncome,
            double LoanAmount,
            double LoanAmountTerm,
            String PropertyArea
    ) {
        try {
            String loanAnswer = "";
            double value;
            Instance predictDataset = getDataset(predictFilename).instance(0);
            predictDataset.setValue(0, Married);
            predictDataset.setValue(1, Dependents);
            predictDataset.setValue(2, Education);
            predictDataset.setValue(3, SelfEmployed);
            predictDataset.setValue(4, ApplicantIncome);
            predictDataset.setValue(5, CoapplicantIncome);
            predictDataset.setValue(6, LoanAmount);
            predictDataset.setValue(7, LoanAmountTerm);
            predictDataset.setValue(8, PropertyArea);
            value = classifier.classifyInstance(predictDataset);
            printAttribute(predictDataset);
            loanAnswer = value == 0 ? "You can loan":"sorry you cannot loan";
            return loanAnswer;
        } catch (Exception ex) {
            Logger.getLogger(Classification_Logistics_Loan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
