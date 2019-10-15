/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml_loan;

/**
 *
 * @author Student
 */
public class ML_Loan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // creditRisk_training.arff

        Classification_Logistics_Loan loan = new Classification_Logistics_Loan(
                "creditRisk_training.arff",
                "creditRisk_testing.arff",
                "creditRisk_predict.arff");
        loan.setClassAtribute(9);
        loan.traningAndTesting();
        loan.predictDataset();
        LoanAppplication loanApp = new LoanAppplication();
        loanApp.setVisible(true);
        loanApp.setClassifier(loan.getClassifier());

        System.out.println("preDictOneInstance");
        String result = loan.preDictOneInstance("Yes", "1", "NotGraduate", "No", 2653, 1500, 113, 180, "Rural");
        System.out.println("result --->" + result);
    }

}
