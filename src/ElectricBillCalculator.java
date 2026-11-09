
import javax.swing.JOptionPane;
import java.io.PrintWriter;

public class ElectricBillCalculator {
    public static void main(String[] args) throws Exception {

        String customerID = JOptionPane.showInputDialog("Enter Customer ID:");
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String electricity = JOptionPane.showInputDialog("Enter Electricity Usage:");
        String overdue = JOptionPane.showInputDialog("Is previous bill overdue? Y or N:");

        if (customerID == null || firstName == null ||
                lastName == null || electricity == null || overdue == null) {
            JOptionPane.showMessageDialog(null, "Invalid information");
            return;
        }

        if (customerID.trim().isEmpty() ||
                firstName.trim().isEmpty() ||
                lastName.trim().isEmpty() ||
                electricity.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid information");
            return;
        }

        if (!electricity.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Invalid electricity usage");
            return;
        }

        double usage = Double.parseDouble(electricity);

        if (usage < 0 || Double.isInfinite(usage)) {
            JOptionPane.showMessageDialog(null, "Invalid electricity usage");
            return;
        }

        if (!overdue.equals("Y") && !overdue.equals("N")) {
            JOptionPane.showMessageDialog(null, "Enter Y or N");
            return;
        }

        double charge = 0;
        double lateFee = 0;

        if (usage <= 100) {
            charge = usage * 0.10;
        }
        else if (usage <= 300) {
            charge = 10 + (usage - 100) * 0.15;
        }
        else {
            charge = 40 + (usage - 300) * 0.20;
        }

        if (overdue.equals("Y")) {
            lateFee = 15;
        }

        double total = charge + lateFee;

        String payment = "Previous bill not overdue";

        if (overdue.equals("Y")) {
            payment = "Previous bill overdue";
        }

        String bill = String.format(
                "MONTHLY UTILITY BILL\n" +
                        "================================\n" +
                        "Customer ID: %s\n" +
                        "Customer Name: %s %s\n" +
                        "Electricity Usage: %.0f kWh\n" +
                        "Electricity Charge: $%.2f\n" +
                        "Late-Payment Fee: $%.2f\n" +
                        "TOTAL AMOUNT DUE: $%.2f\n" +
                        "Payment Status: %s",
                customerID, firstName, lastName, usage,
                charge, lateFee, total, payment
        );

        PrintWriter output = new PrintWriter("UtilityBillReport.txt");
        output.println(bill);
        output.close();

        JOptionPane.showMessageDialog(null, bill);
    }
}
