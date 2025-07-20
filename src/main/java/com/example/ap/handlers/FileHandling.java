package com.example.ap.handlers;

import com.example.ap.classes.*;
import com.example.ap.classes.enums.ATTRACTIONDIFFICULTY;
import com.example.ap.classes.enums.ATTRACTIONTYPE;
import com.example.ap.classes.enums.LANGUAGES;
import com.example.ap.classes.enums.USERTYPE;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public static String ReportFile="Report.csv";
    public static String AttractionsFile="Attractions.csv";
    public static String FestivalsFile="Festivals.csv";
    public static String BookingsFile="Bookings.csv";
    public static String LogFile="Log.txt";
    public static String AlertsFile="Alerts.csv";




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
    public void removeUser(USERTYPE usertype,int uid){
        switch(usertype){
            case Admin -> {

            }
            case Guide -> {
                File originalFile=new File(GuideFile);
                File tempFile=new File("temp",GuideFile);
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
                    if (!originalFile.delete()) {
                        throw new IOException("Could not delete original file");
                    }
                    if (!tempFile.renameTo(originalFile)) {
                        throw new IOException("Could not rename temp file to original");
                    }
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }

            }
            case Tourist -> {
                File originalFile=new File(TouristFile);
                File tempFile=new File("temp",TouristFile);
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
                    if (!originalFile.delete()) {
                        throw new IOException("Could not delete original file");
                    }
                    if (!tempFile.renameTo(originalFile)) {
                        throw new IOException("Could not rename temp file to original");
                    }
                }
                catch (IOException e){
                    System.out.println(e.getMessage());

                }
            }
        }
    }
    public void editUser(USERTYPE usertype,int uid, User newUser){
        switch(usertype) {
            case Admin -> {}
            case Guide -> {
                File originalFile=new File(GuideFile);
                File tempFile=new File("temp",GuideFile);
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
                    if (!originalFile.delete()) {
                        throw new IOException("Could not delete original file");
                    }
                    if (!tempFile.renameTo(originalFile)) {
                        throw new IOException("Could not rename temp file to original");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }

            }
            case Tourist -> {
                File originalFile=new File(TouristFile);
                File tempFile=new File("temp",TouristFile);
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
                    if (!originalFile.delete()) {
                        throw new IOException("Could not delete original file");
                    }
                    if (!tempFile.renameTo(originalFile)) {
                        throw new IOException("Could not rename temp file to original");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());

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

    public static void MakeBooking(Booking booking)throws IOException{
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(FileHandling.BookingsFile,true))){
            bw.write(booking.getDetails());
            bw.newLine();
        }
    }
    public static void editBooking(int bid,Booking newBooking)throws IOException{
        File originalFile=new File(BookingsFile);
        File tempFile=new File("temp",BookingsFile);
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
            if (!originalFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temp file to original");
            }
        }

    }
    public static void removeBooking(int bid) throws IOException{
        File originalFile=new File(BookingsFile);
        File tempFile=new File("temp",BookingsFile);
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
            if (!originalFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temp file to original");
            }
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
            if (!originalFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temp file to original");
            }
        }
    }
    public static void removeAttraction(int aid)throws IOException{
        File originalFile=new File(AttractionsFile);
        File tempFile=new File("temp", AttractionsFile);
        try(BufferedReader br=new BufferedReader(new FileReader(originalFile))){
            Attraction attraction= ObjectFinder.getAttraction(aid);
            assert attraction != null;
            String attractionInfo=attraction.getDetails();
            String line;
            while((line=br.readLine())!=null){
                if(line.equals(attractionInfo)){
                    continue;
                }
                try(BufferedWriter bw=new BufferedWriter(new FileWriter(
                        tempFile,true))){
                    bw.write(line);
                    bw.newLine();
                }
            }
            if (!originalFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temp file to original");
            }
        }

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
            if (!originalFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temp file to original");
            }
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
            if (!originalFile.delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temp file to original");
            }
        }
    }

    public static void makeReport() throws IOException{

    }

    public static void getReport()throws IOException{
        Files.copy(
                Paths.get(ReportFile),
                Paths.get("Exports", Paths.get(ReportFile).getFileName().toString()),
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    public static void makeLogs(String message)throws IOException{
        LocalDate currentDate=LocalDate.now();
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(LogFile))){
            bw.write(currentDate+" "+message);
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



}
