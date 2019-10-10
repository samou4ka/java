import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.temporal.ChronoUnit;

public class MyContacts {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Contact> list = new ArrayList<>();

        while(true){
            System.out.print("[menu] Enter action (add, list, search, count, exit): > ");
            switch(scanner.nextLine()){
                //------------ADD-----------------------
                case "add":
                    System.out.print("Enter the type (person, organization): > ");
                    switch(scanner.nextLine()){
                        case "person":
                            Person person = new Person();
                            for(String string : person.getFieldsToEdit()){
                                System.out.print("Enter " + string.toLowerCase() + ": > ");
                                person.setField(string.toLowerCase().split(" ")[0], scanner.nextLine());
                            }
                            list.add(person);
                            System.out.println("The record added.");
                            break;
                        case "organization":
                            Organisation organisation = new Organisation();
                            for(String string : organisation.getFieldsToEdit()){
                                System.out.print("Enter " + string.toLowerCase() + ": > ");
                                organisation.setField(string.toLowerCase(), scanner.nextLine());
                            }
                            list.add(organisation);
                            System.out.println("The record added.");
                            break;
                        default: break;
                    }
                    break;
                //-------------LIST-----------------------
                case "list":
                    showList(list);

                    System.out.print("[list] Enter action ([number], back): > ");
                    String action = scanner.nextLine();
                    if("back".equals(action)){
                        break;
                    }
                    int index = -1;
                    try {
                        index = Integer.parseInt(action) - 1;
                    }catch(Exception e) {
                        System.out.println("Only numbers!");
                    }

                    if(index >= 0) {
                        infoElement(index, list);

                        boolean exit = false;
                        while (true) {
                            System.out.print("[record] Enter action (edit, delete, menu): > ");
                            switch (scanner.nextLine()) {
                                case "edit":
                                    System.out.print("Select a field (name, surname, birth, gender, number): > ");
                                    String field = scanner.nextLine();
                                    System.out.print("Enter " + field + ": > ");
                                    String valueField = scanner.nextLine();

                                    list.get(index).setField(field, valueField);
                                    System.out.println("Saved");
                                    //show result
                                    infoElement(index, list);
                                    System.out.println();
                                    break;
                                case "delete":
                                    list.remove(index);
                                    System.out.println("Record removed.");
                                    return;
                                case "menu":
                                    exit = true;
                                    break;
                            }
                            //exit to menu
                            if(exit){
                                break;
                            }

                        }
                    }
                    break;
                //-------------SEARCH------------------
                case "search":
                    while(true) {
                        boolean exit = false;
                        System.out.print("Enter search query: > ");
                        String query = scanner.nextLine();
                        ArrayList<Contact> foundList = search(query, list);
                        System.out.println("Found " + foundList.size() + " results:");
                        showList(foundList);

                        System.out.print("[search] Enter action ([number], back, again): > ");
                        String userChoice = scanner.nextLine();

                        if (!"again".equals(userChoice)) {
                            if("back".equals(userChoice)){
                                break;
                            }
                            //if input number
                            int number = -1;
                            try {
                                number = Integer.parseInt(userChoice) - 1;
                            }catch(Exception e) {
                                System.out.println("Only numbers!");
                            }
                            if(number >= 0){
                                infoElement(number, foundList);

                                while (true) {
                                    System.out.print("[record] Enter action (edit, delete, menu): > ");
                                    switch (scanner.nextLine()) {
                                        case "edit":
                                            System.out.print("Select a field (name, surname, birth, gender, number): > ");
                                            String field = scanner.nextLine();
                                            System.out.print("Enter " + field + ": > ");
                                            String valueField = scanner.nextLine();

                                            list.get(number).setField(field, valueField);
                                            System.out.println("Saved");
                                            //show result
                                            infoElement(number, list);
                                            System.out.println();
                                            break;
                                        case "delete":
                                            list.remove(number);
                                            System.out.println("Record removed.");
                                            return;
                                        case "menu":
                                            exit = true;
                                            break;
                                    }
                                    //exit to menu
                                    if(exit){
                                        break;
                                    }
                                }
                            }
                        }
                        System.out.println();
                        //exit to menu;
                        if(exit){
                            break;
                        }
                    }
                    break;
                //--------------COUNT------------------
                case "count":
                    System.out.println("The Phone Book has " + list.size() + " records.");
                    break;
                //---------------EXIT----------------------
                case "exit":
                    return;
                default:
                    break;
            }
            System.out.println();
        }

    }

    private static ArrayList<Contact> search(String query, ArrayList<Contact> list){
        ArrayList<Contact> foundList = new ArrayList<>();
        //use linear search
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getAllFields().toLowerCase().contains(query.toLowerCase())){
                foundList.add(list.get(i));
            }
        }
        return foundList;
    }

    private static void showList(ArrayList<Contact> list){
        //show list of contacts
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
        System.out.println();
    }


    private static void infoElement(int index, ArrayList<Contact> list){
        String[] elements = list.get(index).getFieldsToEdit();
        for(String string : elements){
            System.out.println(string + ": " + list.get(index).getField(string.toLowerCase()));
        }
        System.out.println("Time created: " + list.get(index).getCreateDate());
        System.out.println("Time last edit: " + list.get(index).getEditDate());
        System.out.println();
    }
}

abstract class Contact{
    private String number;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    Contact(){
        this.number = "";
        this.createDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.editDate = this.createDate;
    }

    public LocalDateTime getCreateDate(){
        return this.createDate;
    }

    public void setEditDate(){
        this.editDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public LocalDateTime getEditDate(){
        return this.editDate;
    }

    public void setNumber(String number){
        if(checkNumber(number)){
            this.number = number;
        }else{
            this.number = "";
            System.out.println("Wrong number format!");
        }
        this.setEditDate();
    }
    public String getNumber(){
        if(hasNumber()){
            return this.number;
        }else{
            return "[no number]";
        }
    }

    private boolean hasNumber(){
        if("".equals(number)){
            return false;
        }
        return true;
    }

    private boolean checkNumber(String number){
        Pattern pattern = Pattern.compile("\\+?([(]?\\w{1,}[)]?[ -]?(\\w{2,})?|\\w{1,}[ -][(]?\\w{2,}[)]?)[ -]?(\\w{2,})?[ -]?(\\w{2,})?[ -]?(\\w{2,})?");
        Matcher matcher = pattern.matcher(number);

        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }

    abstract public String[] getFieldsToEdit();

    abstract public void setField(String field, String value);

    abstract public String getField(String field);

    abstract public String getAllFields();
}

class Person extends Contact {
    private String name;
    private String surname;
    private String birthDate;
    private String gender;

    Person(){
        this.name = "";
        this. surname = "";
        this.birthDate = "";
        this.gender = "";
    }

    public void setName(String name){
        this.setEditDate();
        this.name = name;
    }
    public String getName(){return this.name;}

    public void setSurname(String surname){
        this.setEditDate();
        this.surname = surname;
    }
    public String getSurname(){return this.surname;}

    public void setBirthDate(String birthDate){
        this.setEditDate();
        if(birthDate.matches("\\d{4}-\\d{2}-\\d{2}")){
            this.birthDate = birthDate;
        }
        else{
            System.out.println("Bad birth date!");
            this.birthDate = "";
        }
    }
    public String getBirthDate(){
        if("".equals(this.birthDate)){
            return "[no data]";
        }
        return this.birthDate;
    }


    public void setGender(String gender){
        this.setEditDate();
        if(gender.matches("[MF]")) {
            this.gender = gender;
        }
        else{
            this.gender = "";
            System.out.println("Bad gender!");
        }
    }
    public String getGender(){
        if("".equals(this.gender)){
            return "[no data]";
        }
        return this.gender;
    }

    @Override
    public String[] getFieldsToEdit()
    {
        return new String[] {"Name", "Surname", "Birth date", "Gender", "Number"};
    }

    @Override
    public void setField(String field, String value)
    {
        switch(field){
            case "name":
                this.setName(value);
                break;
            case "surname":
                this.setSurname(value);
                break;
            case "birth":
                this.setBirthDate(value);
                break;
            case "gender":
                this.setGender(value);
                break;
            case "number":
                this.setNumber(value);
                break;
            default:break;
        }
    }

    @Override
    public String getField(String field)
    {
        switch(field){
            case "name":
                return this.getName();
            case "surname":
                return this.getSurname();
            case "birth":
            case "birth date":
                return this.getBirthDate();
            case "gender":
                return this.getGender();
            case "number":
                return this.getNumber();
            default:break;
        }
        return "";
    }

    @Override
    public String toString(){
        return this.getName() + " " + this.getSurname();
    }

    @Override
    public String getAllFields(){
        return this.getName() + " " + this.getSurname() + " " + this.getBirthDate() + " " + this.getGender() + " " + this.getNumber();
    }
}

class Organisation extends Contact {
    private String organizationName;
    private String address;

    Organisation(){
        this.organizationName = "";
        this.address = "";
    }

    public void setOrganizationName(String organizationName){
        this.setEditDate();
        this.organizationName = organizationName;
    }
    public String getOrganizationName(){return this.organizationName;}

    public void setAddress(String address){
        this.setEditDate();
        this.address = address;
    }
    public String getAddress(){return this.address;}

    public String[] getFieldsToEdit()
    {
        return new String[]{"Organization name", "Address", "Number"};
    }

    public void setField(String field, String value)
    {
        switch(field){
            case "name":
            case "organization name":
                this.setOrganizationName(value);
                break;
            case "address":
                this.setAddress(value);
                break;
            case "number":
                this.setNumber(value);
                break;
            default:break;
        }
    }

    public String getField(String field)
    {
        switch(field){
            case "name":
            case "organization name":
                return this.getOrganizationName();
            case "address":
                return this.getAddress();
            case "number":
                return this.getNumber();
            default:break;
        }
        return "";
    }

    @Override
    public String toString(){
        return this.getOrganizationName();
    }

    @Override
    public String getAllFields(){
        return this.getOrganizationName() + " " + this.getAddress() + " "  + this.getNumber();
    }
}

