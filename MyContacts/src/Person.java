class Person extends AbstractContact {
    private String name;
    private String surname;
    private String birthDate;
    private String gender;

    Person() {
        this.name = "";
        this. surname = "";
        this.birthDate = "";
        this.gender = "";
    }

    public void setName(String name) {
        this.setEditDate();
        this.name = name;
    }
    public String getName() {return this.name;}

    public void setSurname(String surname) {
        this.setEditDate();
        this.surname = surname;
    }
    public String getSurname() {return this.surname;}

    public void setBirthDate(String birthDate) {
        this.setEditDate();
        if (birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            this.birthDate = birthDate;
        }
        else {
            System.out.println("Bad birth date!");
            this.birthDate = "";
        }
    }
    public String getBirthDate() {
        if ("".equals(this.birthDate)) {
            return "[no data]";
        }
        return this.birthDate;
    }


    public void setGender(String gender) {
        this.setEditDate();
        if (gender.matches("[MF]")) {
            this.gender = gender;
        }
        else {
            this.gender = "";
            System.out.println("Bad gender!");
        }
    }
    public String getGender() {
        if ("".equals(this.gender)) {
            return "[no data]";
        }
        return this.gender;
    }

    @Override
    public String[] getFieldsToEdit() {
        return new String[] {"Name", "Surname", "Birth date", "Gender", "Number"};
    }

    @Override
    public void setField(String field, String value) {
        switch(field) {
            case "name":
                this.setName(value);
                break;
            case "surname":
                this.setSurname(value);
                break;
            case "birth":
            case "birth date":
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
        switch(field) {
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
    public String toString() {
        return this.getName() + " " + this.getSurname();
    }

    @Override
    public String getAllFields() {
        return this.getName() + " " + this.getSurname() + " " + this.getBirthDate() + " " + this.getGender() + " " + this.getNumber();
    }
}