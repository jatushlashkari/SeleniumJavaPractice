package Utils;

import com.github.javafaker.Faker;
import org.aeonbits.owner.ConfigFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {

    private static Properties properties;

    public static File getLastModifiedFile(String directoryFilePath) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }


    public static void sendReportsInEmail(Properties properties) {

        try {
            convertDirToZip(System.getProperty("user.dir") + "\\allure-report", System.getProperty("user.dir") + "\\allure-report.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.ionos.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("jatush@gmail.com", "123456789");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("email.from")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("jatush7@gmail.com"));
            message.setSubject("Test Report");
            message.setText("Please find below the test report:");


            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart1 = new MimeBodyPart();
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String file1 = Util.getLastModifiedFile(System.getProperty("user.dir") + "\\extent-reports").getAbsolutePath();
            String file2 = System.getProperty("user.dir") + "\\allure-report.zip";
            String fileName1 = "ExtentReport.html";
            String fileName2 = "AllureReport.zip";
            DataSource source1 = new FileDataSource(file1);
            DataSource source2 = new FileDataSource(file2);
            messageBodyPart1.setDataHandler(new DataHandler(source1));
            messageBodyPart2.setDataHandler(new DataHandler(source2));
            messageBodyPart1.setFileName(fileName1);
            messageBodyPart2.setFileName(fileName2);
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);

            Transport.send(message);
            Log.info("Email sent successfully!");
            Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + "\\allure-report.zip"));

        } catch (MessagingException e) {
            Log.info("Failed to send reports to email!!!" + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertDirToZip(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }

    public static String getBrowserName() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        return config.browserName();
    }

    public static String getBaseUrl() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        return config.url();
    }

    public static String getOsName() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        return config.osName();
    }

    public static boolean getHeadless() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        return config.headless().equals("true");
    }

    public static String getUsername() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        return config.email();
    }

    public static String getPassword() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        return config.password();
    }

    public static String getRandomEmail(){
        Faker faker = new Faker();
        return faker.name().username()+"@gmail.com";

    }

    public static String getRandomString(int length){
        Faker faker = new Faker();
        return faker.random().hex(length);

    }


}
