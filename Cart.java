import java.util.Arrays;
import java.util.Comparator;

public class Cart {
    // Tablica przechowująca produkty w koszyku
    private Product[] products;
    
    // Całkowita cena koszyka
    private double totalPrice;

    // Konstruktor - inicjalizuje koszyk produktami oraz oblicza początkową cenę całkowitą
    public Cart(Product[] products) {
        this.products = products;
        this.totalPrice = calculateTotalPrice(); // Oblicza początkową cenę całkowitą
        sortProductsByPrice(); // Automatycznie sortuje produkty według ceny
    }

    // Dodaje nowy produkt do koszyka
    public void addProduct(Product product) {
        products = Arrays.copyOf(products, products.length + 1); // Powiększa tablicę produktów
        products[products.length - 1] = product; // Dodaje nowy produkt na końcu
        sortProductsByPrice(); // Sortuje produkty po dodaniu nowego
    }

    // Znajduje najtańszy produkt w koszyku
    public Product findCheapestProduct() {
        return Arrays.stream(products)
                     .min(Comparator.comparing(Product::getPrice)) // Porównuje produkty według ceny
                     .orElse(null); // Zwraca null, jeśli koszyk jest pusty
    }

    // Znajduje najdroższy produkt w koszyku
    public Product findMostExpensiveProduct() {
        return Arrays.stream(products)
                     .max(Comparator.comparing(Product::getPrice)) // Porównuje produkty według ceny
                     .orElse(null); // Zwraca null, jeśli koszyk jest pusty
    }

    // Znajduje N najtańszych produktów w koszyku
    public Product[] findNCheapestProducts(int n) {
        return Arrays.stream(products)
                     .sorted(Comparator.comparing(Product::getPrice)) // Sortuje produkty według ceny rosnąco
                     .limit(n) // Pobiera pierwsze N produktów
                     .toArray(Product[]::new); // Konwertuje wynik na tablicę
    }

    // Znajduje N najdroższych produktów w koszyku
    public Product[] findNMostExpensiveProducts(int n) {
        return Arrays.stream(products)
                     .sorted(Comparator.comparing(Product::getPrice).reversed()) // Sortuje produkty według ceny malejąco
                     .limit(n) // Pobiera pierwsze N produktów
                     .toArray(Product[]::new); // Konwertuje wynik na tablicę
    }

    // Sortuje produkty w koszyku według ceny rosnąco
    public void sortProductsByPrice() {
        Arrays.sort(products, Comparator.comparing(Product::getPrice));
    }

    // Sortuje produkty w koszyku według nazwy alfabetycznie
    public void sortProductsByName() {
        Arrays.sort(products, Comparator.comparing(Product::getName));
    }

    // Oblicza całkowitą cenę produktów w koszyku
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice(); // Dodaje cenę każdego produktu do sumy
        }
        this.totalPrice = total; // Aktualizuje pole totalPrice
        return total;
    }

    // Oblicza całkowitą cenę produktów uwzględniając zniżki
    public double calculateTotalDiscountedPrice() {
        return Arrays.stream(products)
                     .mapToDouble(Product::getDiscountPrice) // Pobiera cenę ze zniżką każdego produktu
                     .sum(); // Sumuje ceny
    }

    // Stosuje promocje do produktów w koszyku
    public void applyPromotions() {
        // Zniżka 30% na najdroższy produkt
        Product mostExpensive = findMostExpensiveProduct();
        if (mostExpensive != null) {
            mostExpensive.setDiscountPrice(mostExpensive.getPrice() * 0.7); // Ustawia cenę ze zniżką
        }

        // Aktualizuje cenę całkowitą po zastosowaniu 30% zniżki
        double discountedTotalPrice = calculateTotalDiscountedPrice();

        // Zniżka 5% dla zamówień powyżej 300 zł (nie dotyczy najdroższego produktu)
        if (discountedTotalPrice > 300) {
            for (Product product : products) {
                if (product != mostExpensive) {
                    product.setDiscountPrice(product.getDiscountPrice() * 0.95); // Ustawia cenę z dodatkową zniżką
                }
            }
        }

        // Promocja: kup 3 produkty, najtańszy gratis
        Product cheapest = findCheapestProduct();
        if (products.length >= 3) {
            if (cheapest != null) {
                cheapest.setDiscountPrice(0.0); // Ustawia cenę najtańszego produktu na 0
            }
        }

        // Gratisowy kubek dla zamówień powyżej 200 zł
        if (discountedTotalPrice > 200) {
            Product mug = ProductList.getProducts()[7]; // Zakładamy, że kubek jest na indeksie 7
            if (Arrays.stream(products).noneMatch(p -> p.getName().equals(mug.getName()))) { // Unikamy duplikatów
                Product freeMug = new Product(mug.getCode(), mug.getName(), mug.getPrice());
                freeMug.setDiscountPrice(0.0); // Cena gratisowego kubka to 0
                addProduct(freeMug); // Dodaje kubek do koszyka
            }
        }

        // Promocja: kup 1, drugi za 50% ceny (zakomentowana)
        /*Map<String, Integer> productCount = new HashMap<>();
        for (Product product : products) {
            productCount.put(product.getName(), productCount.getOrDefault(product.getName(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : productCount.entrySet()) {
            String productName = entry.getKey();
            int count = entry.getValue();

            // Zastosowanie zniżki do co drugiego produktu
            if (count > 1) {
                int halfOffCount = count / 2; // Liczba produktów z 50% zniżką
                int applied = 0;
                for (Product product : products) {
                    if (product.getName().equals(productName) && applied < halfOffCount) {
                        product.setDiscountPrice(product.getPrice() * 0.5);
                        applied++;
                    }
                }
            }
        }*/

        // Aktualizuje cenę całkowitą po zastosowaniu wszystkich promocji
        this.totalPrice = calculateTotalDiscountedPrice();
    }
}
