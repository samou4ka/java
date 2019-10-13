import java.util.Scanner;
import java.util.ArrayList;

public class MyContacts {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final ArrayList<AbstractContact> list = new ArrayList<>();

        while (true) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): > ");
            switch(scanner.nextLine()) {
                case "add":
                    System.out.print("Enter the type (person, organization): > ");
                    AbstractContact contact = null;
                    switch(scanner.nextLine()) {
                        case "person":
                            contact = new Person();
                            break;
                        case "organization":
                            contact = new Organisation();
                            break;
                        default: break;
                    }
                    fillContact(contact, scanner);
                    list.add(contact);
                    System.out.println("The record added.");
                    break;
                case "list":
                    showList(list);

                    System.out.print("[list] Enter action ([number], back): > ");
                    final String action = scanner.nextLine();
                    if ("back".equals(action)) {
                        break;
                    }
                    int index = -1;
                    try {
                        index = Integer.parseInt(action) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Only numbers!");
                    }

                    if (index >= 0) {
                        infoElement(list.get(index));

                        boolean exit = false;
                        while (!exit) {
                            System.out.print("[record] Enter action (edit, delete, menu): > ");
                            switch (scanner.nextLine()) {
                                case "edit":
                                    System.out.print("Select a field (name, surname, birth, gender, number): > ");
                                    final String field = scanner.nextLine();
                                    System.out.print("Enter " + field + ": > ");
                                    final String valueField = scanner.nextLine();

                                    list.get(index).setField(field, valueField);
                                    System.out.println("Saved");
                                    //show result
                                    infoElement(list.get(index));
                                    System.out.println();
                                    break;
                                case "delete":
                                    list.remove(index);
                                    System.out.println("Record removed.");
                                    return;
                                case "menu":
                                    exit = true;
                                    break;
                                default: break;
                            }
                        }
                    }
                    break;
                case "search":
                    boolean exit = false;
                    while (!exit) {
                        System.out.print("Enter search query: > ");
                        final String query = scanner.nextLine();
                        final ArrayList<AbstractContact> foundList = search(query, list);
                        System.out.println("Found " + foundList.size() + " results:");
                        showList(foundList);

                        System.out.print("[search] Enter action ([number], back, again): > ");
                        final String userChoice = scanner.nextLine();

                        if (!"again".equals(userChoice)) {
                            if ("back".equals(userChoice)) {
                                break;
                            }
                            //if input number
                            int number = -1;
                            try {
                                number = Integer.parseInt(userChoice) - 1;
                            }catch(NumberFormatException e) {
                                System.out.println("Only numbers!");
                            }
                            if (number >= 0) {
                                infoElement(foundList.get(number));

                                while (!exit) {
                                    System.out.print("[record] Enter action (edit, delete, menu): > ");
                                    switch (scanner.nextLine()) {
                                        case "edit":
                                            System.out.print("Select a field (name, surname, birth, gender, number): > ");
                                            final String field = scanner.nextLine();
                                            System.out.print("Enter " + field + ": > ");
                                            final String valueField = scanner.nextLine();

                                            list.get(number).setField(field, valueField);
                                            System.out.println("Saved");
                                            //show result
                                            infoElement(list.get(number));
                                            System.out.println();
                                            break;
                                        case "delete":
                                            list.remove(number);
                                            System.out.println("Record removed.");
                                            return;
                                        case "menu":
                                            exit = true;
                                            break;
                                        default: break;
                                    }
                                }
                            }
                        }
                        System.out.println();
                    }
                    break;
                case "count":
                    System.out.println("The Phone Book has " + list.size() + " records.");
                    break;
                case "exit":
                    return;
                default:
                    break;
            }
            System.out.println();
        }

    }

    private static void fillContact(AbstractContact contact, Scanner scanner) {
        for(String fieldToEdit : contact.getFieldsToEdit()) {
            System.out.print("Enter " + fieldToEdit.toLowerCase() + ": > ");
            contact.setField(fieldToEdit.toLowerCase(), scanner.nextLine());
        }
    }

    private static ArrayList<AbstractContact> search(String query, ArrayList<AbstractContact> list) {
        final ArrayList<AbstractContact> foundList = new ArrayList<>();
        //use linear search
        for(AbstractContact contact : list){
            if (contact.getAllFields().toLowerCase().contains(query.toLowerCase())) {
                foundList.add(contact);
            }
        }
        return foundList;
    }

    private static void showList(ArrayList<AbstractContact> list) {
        //show list of contacts
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        System.out.println();
    }


    private static void infoElement(AbstractContact contact) {
        final String[] fieldsToEdit = contact.getFieldsToEdit();
        for(String field : fieldsToEdit){
            System.out.println(field + ": " + contact.getField(field.toLowerCase()));
        }
        System.out.println("Time created: " + contact.getCreateDate());
        System.out.println("Time last edit: " + contact.getEditDate());
        System.out.println();
    }
}
