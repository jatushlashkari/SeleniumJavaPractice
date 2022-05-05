package ExtentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();
    public synchronized static ExtentReports createExtentReports() {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/ExtentTestReport_"+timeStamp+".html");
        reporter.config().setReportName("Automation Practice Test Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Blog Name", "Agile Infoways");
        extentReports.setSystemInfo("Author", "Jatush Lashkari");
        return extentReports;
    }
}