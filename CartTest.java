import org.junit.*;
import static org.junit.Assert.*;

public class CartTest {

    // Testuje metodę findCheapestProduct, która zwraca najtańszy produkt w koszyku
    @Test
    public void testFindCheapestProduct() {
        Product[] products = { ProductList.getProducts()[5], ProductList.getProducts()[1] }; // USB Cable, Mouse
        Cart cart = new Cart(products);
        assertEquals("USB Cable", cart.findCheapestProduct().getName()); // Sprawdza, czy najtańszy produkt to USB Cable
    }

    // Testuje metodę findMostExpensiveProduct, która zwraca najdroższy produkt w koszyku
    @Test
    public void testFindMostExpensiveProduct() {
        Product[] products = { ProductList.getProducts()[0], ProductList.getProducts()[6] }; // Laptop, Smartphone
        Cart cart = new Cart(products);
        assertEquals("Laptop", cart.findMostExpensiveProduct().getName()); // Sprawdza, czy najdroższy produkt to Laptop
    }

    // Testuje automatyczne sortowanie produktów po dodaniu nowego produktu do koszyka
    @Test
    public void testAddProductAutoSort() {
        Product[] products = { ProductList.getProducts()[0], ProductList.getProducts()[1] }; // Laptop, Mouse
        Cart cart = new Cart(products);

        cart.addProduct(ProductList.getProducts()[5]); // Dodaje USB Cable
        assertEquals("USB Cable", cart.findCheapestProduct().getName()); // Sprawdza, czy najtańszy produkt to USB Cable
    }

    // Testuje metodę calculateTotalPrice, która oblicza całkowitą cenę produktów w koszyku
    @Test
    public void testCalculateTotalPrice() {
        Product[] products = { ProductList.getProducts()[0], ProductList.getProducts()[1] }; // Laptop, Mouse
        Cart cart = new Cart(products);
        assertEquals(1550.0, cart.calculateTotalPrice(), 0.001); // Sprawdza, czy całkowita cena wynosi 1550.0
    }

    // Testuje metodę applyPromotions, która stosuje promocje do produktów w koszyku
    @Test
    public void testApplyPromotions() {
        Product[] products = { 
            ProductList.getProducts()[0], // Laptop
            ProductList.getProducts()[0], // Laptop
            ProductList.getProducts()[0], // Laptop
            ProductList.getProducts()[3], // Monitor
            ProductList.getProducts()[3], // Monitor
            ProductList.getProducts()[6]  // Smartphone
        };
        Cart cart = new Cart(products);

        cart.applyPromotions(); // Zastosowanie promocji
        double totalPrice = cart.calculateTotalDiscountedPrice();
        
        // Debugowanie: Wyświetla ceny po zastosowaniu promocji
        System.err.println("Prices after applying promotions:");
        for (Product product : products) {
            System.err.println("Product: " + product.getName() + ", Original Price: " + product.getPrice() + ", Discounted Price: " + product.getDiscountPrice());
        }
        System.err.println("Total discounted price: " + totalPrice);

        // Sprawdza, czy 30% zniżka została zastosowana do najdroższego produktu
        assertEquals(1050.0, products[3].getDiscountPrice(), 0.001); // Monitor z 30% zniżką

        // Sprawdza, czy 5% zniżka została zastosowana po 30% zniżce
        assertEquals(285.0, products[1].getDiscountPrice(), 0.001); // Monitor z kaskadową zniżką 5%

        // Sprawdza, czy gratisowy kubek został dodany do koszyka
        Product freeMug = cart.findNMostExpensiveProducts(products.length + 1)[products.length];
        assertEquals("Company Mug", freeMug.getName()); // Sprawdza, czy dodano "Company Mug"
        assertEquals(0.0, freeMug.getDiscountPrice(), 0.001); // Sprawdza, czy cena kubka to 0.0

        // Sprawdza, czy najtańszy produkt z trzech jest darmowy
        assertEquals(0.0, products[0].getDiscountPrice(), 0.001); // Smartphone za darmo

        // Sprawdza całkowitą cenę po zastosowaniu promocji
        assertEquals(5135.0, totalPrice, 0.001); // Porównuje z oczekiwaną całkowitą ceną
    }
}
