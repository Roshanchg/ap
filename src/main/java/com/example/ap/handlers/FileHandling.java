package com.example.ap.handlers;

import com.example.ap.classes.*;
import com.example.ap.classes.enums.*;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandling {
    public static String email_regex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static Pattern email_pattern=Pattern.compile(email_regex);




    public static boolean isEmail(String string){
        if(string.isEmpty()){
            return false;
        }
        Matcher matcher =email_pattern.matcher(string);
        return matcher.matches();
    }


    public static String TouristFile="tourists.csv";
    public static String GuideFile="Guides.csv";
    public static String AdminFile="Admins.csv";
    public static String ReportBarFile ="Exports/ReportBar.csv";
    public static String ReportPieFile ="Exports/ReportPie.csv";
    public static String AttractionsFile="Attractions.csv";
    public static String FestivalsFile="Festivals.csv";
    public static String BookingsFile="Bookings.csv";
    public static String LogFile="Log.txt";
    public static String AlertsFile="Alerts.csv";




    public static void init() throws IOException{
        List<File> files = Arrays.asList(
                new File(TouristFile),
                new File(GuideFile),
                new File(AttractionsFile),
                new File(FestivalsFile),
                new File(BookingsFile),
                new File(LogFile),
                new File(AlertsFile)
        );

        for(File file:files){
            if(!file.exists()){
                file.createNewFile();
            }
        }
        AdminInitializer.init();
    }

    public static boolean phoneExists(String phonenumber,USERTYPE usertype) throws IOException{
        List<User> users=new ArrayList<>();
        users=AllUsers(usertype);
        assert users != null;
        for(User user: users){
            if (user.getPhoneNumber().equals(phonenumber)){
                return true;
            }
        }
        return false;
    }

    public static boolean emailExists(String email,USERTYPE usertype) throws IOException{
        List<User> users=new ArrayList<>();
        users=AllUsers(usertype);
        assert users != null;
        for(User user: users){
            if (user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public static List<User> AllUsers(USERTYPE usertype) throws IOException{
        List<User> users= new ArrayList<>();
        assert usertype !=null;
        String line;
        String[] parts;
        int id;
        String name;
        String email;
        String phoneNumber;
        String password;
        switch(usertype){
            case Admin -> {
                Admin admin;
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AdminFile))) {
                    while((line=br.readLine())!=null){
                        if(line.trim().isEmpty()) continue;
                        parts=line.split(",");
                        id=Integer.parseInt(parts[0]);

                            name=parts[1];
                            email=parts[2];
                            phoneNumber=parts[3];
                            password=parts[4];
                            users.add(new Admin(id,name,email,phoneNumber,password));
                    }
                }
                return users;
            }
            case Guide -> {
                Guide guide;
                LANGUAGES language;
                int YearsOfExperience;
                boolean availability;
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.GuideFile))){
                    while((line=br.readLine())!=null){
                        if(line.trim().isEmpty()) continue;
                        parts=line.split(",");
                        id=Integer.parseInt(parts[0]);
                            name=parts[1];
                            email=parts[2];
                            phoneNumber=parts[3];
                            password=parts[4];
                            language=switch(parts[5]){
                                case "Nepali"-> LANGUAGES.Nepali;
                                case "English"-> LANGUAGES.English;
                                default -> throw new IllegalStateException("Unexpected value: " + parts[5]);
                            };
                            YearsOfExperience=Integer.parseInt(parts[6]);
                            availability=Boolean.parseBoolean(parts[7]);
                            guide=new Guide(id,name,email,phoneNumber,password,language,YearsOfExperience);
                            guide.updateAvailability(availability);
                            users.add(guide);
                    }

                }
                return users;

            }
            case Tourist -> {
                Tourist tourist;
                LANGUAGES language;
                String nationality;
                String emergencyNumber;
                try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.TouristFile))){
                    while((line=br.readLine())!=null){
                        if(line.trim().isEmpty()) continue;
                        parts=line.split(",");
                        id=Integer.parseInt(parts[0]);
                            name=parts[1];
                            email=parts[2];
                            phoneNumber=parts[3];
                            password=parts[4];
                            language=switch(parts[5]){
                                case "Nepali"-> LANGUAGES.Nepali;
                                case "English"-> LANGUAGES.English;
                                default -> throw new IllegalStateException("Unexpected value: " + parts[5]);
                            };
                            nationality=parts[6];
                            emergencyNumber=parts[7];
                            tourist=new Tourist(id,name,email,phoneNumber,password,language,nationality,emergencyNumber);
                            users.add(tourist);
                    }
                }
                return users;
            }

        }
        return null;
    }

    public static void WriteUser(USERTYPE usertype,User user){
        switch(usertype){
            case Admin -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(AdminFile,true))){
                    String line=user.getDetails();
                    bw.write(line);
                    bw.newLine();
                }
                catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            case Guide -> {
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(GuideFile,true))){
                    String line=user.getDetails();
                    bw.write(line);
                    bw.newLine();
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }

            }
            case Tourist -> {

                try(BufferedWriter bw=new BufferedWriter(new FileWriter(TouristFile,true))){
                    String line=user.getDetails();
                    bw.write(line);
                    bw.newLine();
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }
            }
            default -> {

            }
        }

    }
    public static void removeUser(USERTYPE usertype,int uid) throws IOException {
        switch(usertype){
            case Admin -> {

            }
            case Guide -> {
                File originalFile=new File(GuideFile);
                File tempFile=new File("temp",GuideFile);
                tempFile.createNewFile();
                try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
                    User user= ObjectFinder.getUser(uid,usertype);
                    assert user != null;
                    String userInfo=user.getDetails();
                    String line;
                    while((line=br.readLine())!=null){
                        if(line.equals(userInfo)){
                            continue;
                        }
                        try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                               tempFile,true))){
                            bw.write(line);
                            bw.newLine();
                        }
                    }

                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }
                if (!originalFile.delete()) {
                    throw new IOException("Could not delete original file");
                }
                if (!tempFile.renameTo(originalFile)) {
                    throw new IOException("Could not rename temp file to original");
                }


            }
            case Tourist -> {
                File originalFile=new File(TouristFile);
                File tempFile=new File("temp",TouristFile);
                tempFile.createNewFile();
                try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
                    User user= ObjectFinder.getUser(uid,usertype);
                    assert user != null;
                    String userInfo=user.getDetails();
                    String line;
                    while((line=br.readLine())!=null){
                        if(line.equals(userInfo)){
                            continue;
                        }
                        try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                               tempFile,true))){
                            bw.write(line);
                            bw.newLine();
                        }
                    }
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }

                if (!originalFile.delete()) {
                    throw new IOException("Could not delete original file");
                }
                if (!tempFile.renameTo(originalFile)) {
                    throw new IOException("Could not rename temp file to original");
                }

            }
        }
    }
    public static void editUser(USERTYPE usertype, int uid, User newUser) throws IOException {
        switch(usertype) {
            case Admin -> {}
            case Guide -> {
                File originalFile=new File(GuideFile);
                File tempFile=new File("temp",GuideFile);
                tempFile.createNewFile();
                try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
                    User user= ObjectFinder.getUser(uid,usertype);
                    assert user != null;
                    String userInfo=user.getDetails();
                    String line;
                    while((line=br.readLine())!=null){
                        if(line.equals(userInfo)){
                            line=newUser.getDetails();
                        }
                        try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                                tempFile,true))){
                            bw.write(line);
                            bw.newLine();
                        }
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                    if (!originalFile.delete()) {
                        throw new IOException("Could not delete original file");
                    }
                    if (!tempFile.renameTo(originalFile)) {
                        throw new IOException("Could not rename temp file to original");
                }

            }
            case Tourist -> {
                File originalFile=new File(TouristFile);
                File tempFile=new File("temp",TouristFile);
                tempFile.createNewFile();
                try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
                    User user= ObjectFinder.getUser(uid,usertype);
                    assert user != null;
                    String userInfo=user.getDetails();
                    String line;
                    while((line=br.readLine())!=null){
                        if(line.equals(userInfo)){
                            line=newUser.getDetails();
                        }
                        try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                                tempFile,true))){
                            bw.write(line);
                            bw.newLine();
                        }
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
                if (!originalFile.delete()) {
                    throw new IOException("Could not delete original file");
                }
                if (!tempFile.renameTo(originalFile)) {
                    throw new IOException("Could not rename temp file to original");
                }
            }
        }
    }


    public static int getSize(String filename){
        int i=0;
        try(BufferedReader br=new BufferedReader(new FileReader(filename))) {
            while(br.readLine()!=null){
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public static int getNextId(String filename){
        int i=1;
        String line;
        try(BufferedReader br=new BufferedReader(new FileReader(filename))) {
            while((line=br.readLine())!=null){
                if( line.trim().isEmpty() ) continue;
                i=Integer.parseInt(line.split(",")[0])+1;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public static boolean authenticate(USERTYPE type,String username,String password){
        String line;
        String[] parts;
        String typeFile=
                switch(type){
                    case Tourist ->TouristFile;
                    case Admin -> AdminFile;
                    case Guide ->GuideFile;
                    };

        if(isEmail(username)){
            try(BufferedReader br=new BufferedReader(new FileReader(typeFile))){
                // index 2, 3, 4 email, phone, password
                while((line=br.readLine())!=null){
                    if(line.trim().isEmpty()) return false;
                    parts=line.split(",");
                    if(parts[2].equals(username)){
                        return parts[4].equals(password);
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
        else {
            try (BufferedReader br = new BufferedReader(new FileReader(typeFile))) {
                // index 2, 3, 4 email, phone, password
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) return false;
                    parts = line.split(",");
                    if (parts[3].equals(username)) {
                        return parts[4].equals(password);
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static List<Booking> AllBookings() throws IOException{
        List<Booking> bookings=new ArrayList<>();
        File file=new File(BookingsFile);
        if(!file.exists()){
            file.createNewFile();
        }
        try(BufferedReader br=new BufferedReader(new FileReader(BookingsFile))){
            String line;
            String[] parts;
            int id;
            int uid;
            int gid;
            int aid;
            LocalDate date;
            double discount;
            boolean isCancelled;
            int fid;
            Booking booking;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                uid=Integer.parseInt(parts[1]);
                gid=Integer.parseInt(parts[2]);
                aid=Integer.parseInt(parts[3]);
                date=LocalDate.parse(parts[4]);
                discount=Double.parseDouble(parts[5]);
                isCancelled=Boolean.parseBoolean(parts[6]);
                fid=Integer.parseInt(parts[7]);
                booking=new Booking(id,uid,gid,aid,date,discount,isCancelled,fid);
                bookings.add(booking);
                }
        }
        return bookings;
    }


    public static void MakeBooking(Booking booking)throws IOException{
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.BookingsFile,true))){
            bw.write(booking.getDetails());
            bw.newLine();
        }
    }
    public static void editBooking(int bid,Booking newBooking)throws IOException{
        File originalFile=new File(BookingsFile);
        File tempFile=new File("temp",BookingsFile);
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Booking booking= ObjectFinder.getBooking(bid);
            assert booking != null;
            String bookinginfo=booking.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(bookinginfo)){
                    line=newBooking.getDetails();
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }

        }
        if (!originalFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Could not rename temp file to original");
        }

    }
    public static void removeBooking(int bid) throws IOException{
        File originalFile=new File(BookingsFile);
        File tempFile=new File("temp",BookingsFile);
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Booking booking= ObjectFinder.getBooking(bid);
            assert booking != null;
            String bookinginfo=booking.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(bookinginfo)){
                    continue;
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }
        }

        if (!originalFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Could not rename temp file to original");
        }

    }


    public static List<Attraction> AllAttraction() throws IOException{
        List<Attraction> attractions=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AttractionsFile))){
            String line;
            String[] parts;
            int id;
            String name;
            String location;
            ATTRACTIONTYPE type;
            ATTRACTIONDIFFICULTY difficulty;
            String altitude;
            boolean restrictedMonsoon;
            Attraction attraction;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                name=parts[1];
                location=parts[2];
                type=switch(parts[3]){
                    case "Camping"-> ATTRACTIONTYPE.Camping;
                    case "Trekking" ->  ATTRACTIONTYPE.Trekking;
                    case "Rafting"-> ATTRACTIONTYPE.Rafting;
                    default -> throw new IllegalStateException("Unexpected value: " + parts[3]);
                };
                difficulty=switch(parts[4]){
                    case "HIGH" ->ATTRACTIONDIFFICULTY.HIGH;
                    case "LOW" -> ATTRACTIONDIFFICULTY.LOW;
                    default -> throw new IllegalStateException("Unexpected value: " + parts[4]);
                };
                altitude=parts[5];
                restrictedMonsoon=Boolean.parseBoolean(parts[6]);
                attraction=new Attraction(id,name,location,type,difficulty,altitude,restrictedMonsoon);
                attractions.add(attraction);
            }
        }
        catch (FileNotFoundException e){
            File file=new File(AttractionsFile);
            file.createNewFile();
            return null;

        }
        return attractions;
    }


    public static void AddAttraction(Attraction attraction) throws IOException{
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.AttractionsFile,true))){
            bw.write(attraction.getDetails());
            bw.newLine();
        }
    }
    public static void editAttraction(int aid,Attraction newAttraction) throws IOException{
        File originalFile=new File(AttractionsFile);
        File tempFile=new File("temp", AttractionsFile);
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Attraction attraction= ObjectFinder.getAttraction(aid);
            assert attraction != null;
            String attractionInfo=attraction.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(attractionInfo)){
                    line=newAttraction.getDetails();
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }

        }
        if (!originalFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Could not rename temp file to original");
        }
    }
    public static void removeAttraction(int aid)throws IOException{
        File originalFile=new File(AttractionsFile);
        File tempFile=new File("temp", AttractionsFile);
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))) {
            Attraction attraction = ObjectFinder.getAttraction(aid);
            assert attraction != null;
            String attractionInfo = attraction.getDetails();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(attractionInfo)) {
                    continue;
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(
                        tempFile, true))) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        }
            if(tempFile.exists()) {
                if (!originalFile.delete()) {
                    throw new IOException("Could not delete original file");
                }
                if (!tempFile.renameTo(originalFile)) {
                    throw new IOException("Could not rename temp file to original");
                }
            }

    }

    public static LocalDate parseLocalDate(String raw) {
        if (raw == null || raw.isBlank() || raw.equalsIgnoreCase("null")) return null;
        try {
            return LocalDate.parse(raw); // assumes yyyy-MM-dd
        } catch (Exception e) {
            System.err.println("Invalid date format: " + raw);
            return null;
        }
    }

    public static List<Festival> AllFestival() throws IOException{
        List<Festival> festivals=new ArrayList<>();
        File file=new File(FestivalsFile);
        if(!file.exists()){file.createNewFile();}

        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.FestivalsFile))){
            String line;
            String[] parts;
            int fid;
            String name;
            LocalDate start;
            LocalDate end;
            double discount;
            Festival festival;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                fid=Integer.parseInt(parts[0]);
                name=parts[1];

                start= parseLocalDate(parts[2]);
                end=parseLocalDate(parts[3]);
                if(start==null || end==null){continue;}
                discount=Double.parseDouble(parts[4]);
                festival=new Festival(fid,name,start,end,discount);
                festivals.add(festival);
            }
        }
        return festivals;
    }


    public static void addFestival(Festival festival) throws IOException{
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.FestivalsFile,true))){
            bw.write(festival.getDetails());
            bw.newLine();
        }
    }
    public static void removeFestival(int fid)throws IOException{
        File originalFile=new File(FestivalsFile);
        File tempFile=new File("temp", FestivalsFile);
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Festival festival= ObjectFinder.getFestive(fid);
            assert festival != null;
            String festivalInfo=festival.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(festivalInfo)){
                    continue;
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }
        }

        if (!originalFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Could not rename temp file to original");
        }
    }

    public static List<Alerts> AllAlerts()throws IOException{
        List<Alerts> alerts=new ArrayList<>();
        File file=new File(AlertsFile);
        if(!file.exists()){file.createNewFile();}

        try(BufferedReader br=new BufferedReader(new FileReader(FileHandling.AlertsFile))){
            int id;
            ALERTRISK risk;
            String message;
            int monthsActive;
            Alerts alert;
            String line;
            String[] parts;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                parts=line.split(",");
                id=Integer.parseInt(parts[0]);
                risk=switch(parts[1]){
                    case "HEAVY_RAINFALL" -> ALERTRISK.HEAVY_RAINFALL;
                    default->
                            throw new IllegalStateException("Unexpected value: " + parts[1]);
                    };
                    message=parts[2];
                    monthsActive=Integer.parseInt(parts[3]);
                    alert=new Alerts(id,risk,message,monthsActive);
                    alerts.add(alert);
                }
        }
        return alerts;
    }
    public static void editAlert(int aid,Alerts newAlert) throws IOException {
        File originalFile=new File(AlertsFile);
        File tempFile=new File("temp",AlertsFile );
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Alerts alert= ObjectFinder.getAlert(aid);
            assert alert != null;
            String alertInfo=alert.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(alertInfo)){
                    line=newAlert.getDetails();
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }

        }
        if (!originalFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Could not rename temp file to original");
        }
    }

    public static void ExportAlert() throws IOException {
        Files.copy(
                Paths.get(AlertsFile),
                Paths.get("Exports", Paths.get(AlertsFile).getFileName().toString()),
                StandardCopyOption.REPLACE_EXISTING
        );    }

    public static void AddAlerts(Alerts alert) throws IOException{
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.AlertsFile,true))){
            bw.write(alert.getDetails());
            bw.newLine();
        }
    }
    public static void removeAlerts(int aid)throws IOException{
        File originalFile=new File(AlertsFile);
        File tempFile=new File("temp", AlertsFile);
        tempFile.createNewFile();
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Alerts alerts= ObjectFinder.getAlert(aid);
            assert alerts != null;
            String alertsInfo=alerts.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(alertsInfo)){
                    continue;
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }

        }
        if (!originalFile.delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Could not rename temp file to original");
        }
    }

    public static void makeReport() throws IOException{
        LocalDate date;
        Attraction attraction;
        int atCount;
        int bkCount;
        Map<LocalDate,Integer> DateCountMap=AdminDashboardHandler.getBookingDateChartMap();
        Map<Attraction,Integer> AttractionCountMap=AdminDashboardHandler.getAttractionBookingMap();
        File reportBar=new File(ReportBarFile);
        File reportPie=new File(ReportPieFile);
        if(reportBar.exists()){
            reportBar.delete();
        }
        if(reportPie.exists()){
            reportPie.delete();
        }
        reportBar.createNewFile();
        reportPie.createNewFile();

        try(BufferedWriter bw=new BufferedWriter(new FileWriter(reportBar))){
            for(Map.Entry<LocalDate,Integer> entry:DateCountMap.entrySet()){
                date=entry.getKey();
                bkCount=entry.getValue();
                bw.write(date+","+bkCount);
                bw.newLine();
            }
        }
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(reportPie))){
            for(Map.Entry<Attraction,Integer> entry:AttractionCountMap.entrySet()){
                attraction=entry.getKey();
                atCount=entry.getValue();
                bw.write(attraction.getName()+","+atCount);
                bw.newLine();
            }
        }
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reports Export Success");
        alert.setContentText("Reports Exported at Exports/ReportBar.csv &\n Exports/ReportPie.csv");
        alert.showAndWait();

    }

    public static List<EmergencyLog> AllLogs() throws IOException{
        List<EmergencyLog> logs=new ArrayList<>();
        File logsFile=new File(LogFile);
        if(!logsFile.exists()){
            makeLogs("Created Log File");
        }
        try(BufferedReader br=new BufferedReader(new FileReader(logsFile))) {
            String line;
            String[] parts;
            String dateTime;
            String message;
            EmergencyLog log;
            while((line=br.readLine())!=null){
                if(line.trim().isEmpty()) continue;
                parts=line.split(" ",2);
                dateTime=parts[0];
                message=parts[1];
                log=new EmergencyLog(dateTime,message);
                logs.add(log);
            }
        }
        logs=logs.reversed();
        return logs;
    }

    public static void makeLogs(String message)throws IOException{
        LocalDateTime currentDateTime=LocalDateTime.now();
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(LogFile,true))){
            bw.write(currentDateTime+" "+message);
            bw.newLine();
        }
    }

    public static void getLogs()throws IOException{
        Files.copy(
                Paths.get(LogFile),
                Paths.get("Exports", Paths.get(LogFile).getFileName().toString()),
                StandardCopyOption.REPLACE_EXISTING
        );
    }
    public static void exportEmergencyLogs()throws IOException{
        List<EmergencyLog> emergencyLog=AdminDashboardHandler.getEmergencyLogs();
        if(emergencyLog==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Emergency Log Export Error");
            alert.setContentText("No Emergency Logs Found to export!! \nCancelling export");
            alert.showAndWait();
            return;
        }
        File emergencyLogFile=new File("Exports/EmergencyLogs.txt");
        if(emergencyLogFile.exists()){
            emergencyLogFile.delete();
        }
        emergencyLogFile.createNewFile();
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(emergencyLogFile,true))){
            for(EmergencyLog log:emergencyLog){
                bw.write(log.getDetails());
                bw.newLine();
            }
        }
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Emergency Log Export Success");
        alert.setContentText("Emergency Logs Exported at Exports/EmergencyLogs.txt");
        alert.showAndWait();
    }



}
