import java.util.*;

public class test {
    public static void main(String[] args) {
        String[] tokens = "2021/2/12".split(",");
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
    }

    // private String itemName;
    // private double price;
    // private int quantity;

    // public test(String itemName, double price, int quantity) {
    // this.setItemName(itemName);
    // this.setPrice(price);
    // this.setQuantity(quantity);
    // }

    // public String getItemName() {
    // return itemName;
    // }

    // public void setItemName(String itemName) {
    // this.itemName = itemName;
    // }

    // public double getPrice() {
    // return price;
    // }

    // public void setPrice(double price) {
    // this.price = price;
    // }

    // public int getQuantity() {
    // return quantity;
    // }

    // public void setQuantity(int quantity) {
    // this.quantity = quantity;
    // }

    // public static void printInvoiceHeader() {
    // System.out.println(String.format("%30s %25s %10s", "Item", "|", "Price($)"));
    // System.out.println(
    // String.format("%s",
    // "-----------------------------------------------------------------------------"));
    // }

    // public void printInvoice() {
    // System.out.println(String.format("%30s %25s %10.2f", this.getItemName(), "|",
    // this.getPrice()));
    // }

    // public static List<test> buildInvoice() {
    // List<test> itemList = new ArrayList<>();
    // itemList.add(new test("Nestle Decaff Coffee", 759.99, 2));
    // itemList.add(new test("Brown's Soft Tissue Paper", 15.80, 2));
    // itemList.add(new test("LG 500Mb External Drive", 700.00, 2));
    // return itemList;
    // }

    // public static void main(String[] args) {

    // test.printInvoiceHeader();
    // test.buildInvoice().forEach(test::printInvoice);
    // }
}