
class Organisation extends AbstractContact {
    private String organizationName;
    private String address;

    Organisation() {
        this.organizationName = "";
        this.address = "";
    }

    public void setOrganizationName(String organizationName) {
        this.setEditDate();
        this.organizationName = organizationName;
    }
    public String getOrganizationName() {return this.organizationName;}

    public void setAddress(String address) {
        this.setEditDate();
        this.address = address;
    }
    public String getAddress() {return this.address;}

    public String[] getFieldsToEdit()
    {
        return new String[]{"Organization name", "Address", "Number"};
    }

    public void setField(String field, String value)
    {
        switch(field) {
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
        switch(field) {
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
    public String toString() {
        return this.getOrganizationName();
    }

    @Override
    public String getAllFields() {
        return this.getOrganizationName() + " " + this.getAddress() + " "  + this.getNumber();
    }
}