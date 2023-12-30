package progII.trabalhopratico.projetofinal.Controllers.Employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import progII.trabalhopratico.projetofinal.Entities.Appointment;
import progII.trabalhopratico.projetofinal.Entities.Repository;
import progII.trabalhopratico.projetofinal.OtherClasses.CurrentUser;
import progII.trabalhopratico.projetofinal.OtherClasses.LoadFXML;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EmployeeStatsController implements Initializable {

    @FXML
    private Label dailyReceiptLabel;

    @FXML
    private Label monthlyReceiptLabel;

    @FXML
    private Label petCareLabel;

    @FXML
    private Label totalServicesLabel;

    @FXML
    private Label yearlyReceiptLabel;

    @FXML
    void back(MouseEvent event) {
        LoadFXML.getInstance().loadResource("employeeMenu.fxml", "Menu Funcionário", event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Repository repo = Repository.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String systemTimestamp = dateFormat.format(new Date());


        double yearlyTotal = 0, monthlyTotal = 0, dailyTotal = 0, petCare;
        int totalServices = 0;

        for (Appointment a : repo.getAppointments().values()) {
            if (a.getEmployee().equalsIgnoreCase(CurrentUser.employee.getNome())) {
                if (isSameYear(a.getAppointmentDate(), systemTimestamp)) {
                    yearlyTotal += a.getAppointmentTotal();
                }
                if (isSameMonth(a.getAppointmentDate(), systemTimestamp)) {
                    monthlyTotal += a.getAppointmentTotal();
                }
                if (isSameDay(a.getAppointmentDate(), systemTimestamp)){
                    dailyTotal += a.getAppointmentTotal();
                }

                totalServices++;

            }
        }

        petCare = yearlyTotal * 0.07;

        totalServicesLabel.setText(String.valueOf(totalServices));
        dailyReceiptLabel.setText(String.valueOf(dailyTotal));
        monthlyReceiptLabel.setText(String.valueOf(monthlyTotal));
        yearlyReceiptLabel.setText(String.valueOf(yearlyTotal));
        petCareLabel.setText(String.valueOf(petCare));
    }

    private static boolean isSameDay(String timestamp1, String timestamp2) {
        return isSameUnit(timestamp1, timestamp2, Calendar.DAY_OF_MONTH);
    }

    private static boolean isSameMonth(String timestamp1, String timestamp2) {
        return isSameUnit(timestamp1, timestamp2, Calendar.MONTH);
    }

    private static boolean isSameYear(String timestamp1, String timestamp2) {
        return isSameUnit(timestamp1, timestamp2, Calendar.YEAR);
    }

    private static boolean isSameUnit(String timestamp1, String timestamp2, int calendarUnit) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = dateFormat.parse(timestamp1);
            Date date2 = dateFormat.parse(timestamp2);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            return cal1.get(calendarUnit) == cal2.get(calendarUnit);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
