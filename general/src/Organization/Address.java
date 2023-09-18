package Organization;

/**
 * Класс адреса организации.
 */
public class Address implements Comparable<Address> {
    private String street; //Строка не может быть пустой, Поле не может быть null
    private String zipCode; //Длина строки не должна быть больше 20, Поле может быть null

    public Address(String street, String zipCode){
        this.street=street;
        this.zipCode=zipCode;
    }

    /**
     * Устанавливает улицу в адресе.
     * @param street Улица в адресе.
     */
    public void setStreet(String street){
        this.street=street;
    }
    /**
     * @return Улица в адресе.
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * Устанавливает почтовый индекс в адресе.
     * @param zipCode Почтовый индекс.
     */
    public void setZipCode(String zipCode){
        this.zipCode=zipCode;
    }
    /**
     * @return Почтовый индекс в адресе.
     */
    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public String toString(){
        return "Street: "+ street +", zipCode: "+zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        if (street.equals(address.getStreet())) {
            boolean x = zipCode == null;
            boolean y = address.getZipCode() == null;
            if ((x & y) | (!x & !y )) {
                if (x) return true;
                return zipCode.equals(address.getZipCode());
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int compareTo(Address address) {
        int result = street.compareTo(address.getStreet());
        if (result==0) {
            boolean x = zipCode == null;
            boolean y = address.getZipCode() == null;
            if (x) {
                if(!y) result = -1;
            } else if (y) result = 1;
            else result = zipCode.compareTo(address.getZipCode());
        }
        return result;
    }

}