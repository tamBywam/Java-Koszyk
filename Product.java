public class Product {
    // Kod produktu (unikalny identyfikator)
    private String code;
    
    // Nazwa produktu
    private String name;
    
    // Cena produktu bez zniżki
    private double price;
    
    // Cena produktu po uwzględnieniu zniżek
    private double discountPrice;

    // Konstruktor - inicjalizuje kod, nazwę i cenę produktu
    public Product(String code, String name, double price) {
        this.code = code; // Przypisuje kod produktu
        this.name = name; // Przypisuje nazwę produktu
        this.price = price; // Przypisuje cenę produktu
        this.discountPrice = price; // Ustawia cenę ze zniżką na początkową cenę
    }

    // Zwraca kod produktu
    public String getCode() {
        return code;
    }

    // Zwraca nazwę produktu
    public String getName() {
        return name;
    }

    // Zwraca cenę produktu bez zniżki
    public double getPrice() {
        return price;
    }

    // Zwraca cenę produktu po uwzględnieniu zniżek
    public double getDiscountPrice() {
        return discountPrice;
    }

    // Ustawia nową cenę produktu uwzględniającą zniżki
    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
