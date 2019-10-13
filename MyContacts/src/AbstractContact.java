import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractContact {
    private String number;
    final private LocalDateTime createDate;
    private LocalDateTime editDate;

    AbstractContact() {
        this.number = "";
        this.createDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.editDate = this.createDate;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setEditDate() {
        this.editDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public LocalDateTime getEditDate() {
        return this.editDate;
    }

    public void setNumber(String number) {
        if (checkNumber(number)) {
            this.number = number;
        } else {
            this.number = "";
            System.out.println("Wrong number format!");
        }
        this.setEditDate();
    }
    public String getNumber() {
        if (hasNumber()) {
            return this.number;
        } else {
            return "[no number]";
        }
    }

    private boolean hasNumber() {
        if ("".equals(number)) {
            return false;
        }
        return true;
    }

    private boolean checkNumber(String number) {
        Pattern pattern = Pattern.compile("\\+?([(]?\\w{1,}[)]?[ -]?(\\w{2,})?|\\w{1,}[ -][(]?\\w{2,}[)]?)[ -]?(\\w{2,})?[ -]?(\\w{2,})?[ -]?(\\w{2,})?");
        Matcher matcher = pattern.matcher(number);

       return matcher.matches();
    }

    abstract public String[] getFieldsToEdit();

    abstract public void setField(String field, String value);

    abstract public String getField(String field);

    abstract public String getAllFields();
}