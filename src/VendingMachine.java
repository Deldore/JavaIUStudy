import java.util.Scanner;

enum Drinks {
    COKE_COLA("Coke Cola", 1.5),
    SPRITE("Sprite", 1.2),
    FANTA("Fanta", 1.3);

    private final String name;
    private final double price;

    Drinks(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

enum Money {
    HALF(0.5),
    ONE(1.0),
    TWO(2.0),
    FIVE(5.0),
    TEN(10.0),
    TWENTY(20.0);

    private final double denomination;

    Money(double denomination) {
        this.denomination = denomination;
    }

    public double getDenomination() {
        return denomination;
    }
}

public class VendingMachine {
    private double balance = 0.0;

    public void displayMenu() {
        System.out.println("Welcome to the Vending Machine!");
        System.out.println("Available Drinks:");
        for (Drinks drink : Drinks.values()) {
            System.out.println(drink.ordinal() + 1 + ". " + drink.getName() + " - $" + drink.getPrice());
        }
    }

    public void insertMoney(double amount) {
        if (amount < 0) {
            System.out.println("Error: Cannot insert negative amount.");
            return;
        }
        balance += amount;
        System.out.println("Inserted $" + amount + ". Current balance: $" + balance);
    }

    public void buyDrink(int choice) {
        if (choice < 1 || choice > Drinks.values().length) {
            System.out.println("Error: Invalid choice.");
            return;
        }

        Drinks selectedDrink = Drinks.values()[choice - 1];
        if (balance < selectedDrink.getPrice()) {
            System.out.println("Error: Not enough money to buy " + selectedDrink.getName() + ".");
            return;
        }

        balance -= selectedDrink.getPrice();
        System.out.println("Enjoy your " + selectedDrink.getName() + "!");
        returnChange();
    }

    private void returnChange() {
        System.out.println("Returning change: $" + balance);
        while (balance > 0) {
            double closestDenomination = findClosestLesserDenomination(balance);
            if (closestDenomination == 0) {
                System.out.println("Error: Cannot return exact change. Returning remaining balance: $" + balance);
                break;
            }
            System.out.println("Returning $" + closestDenomination);
            balance -= closestDenomination;
        }
    }

    private double findClosestLesserDenomination(double amount) {
        double closest = 0;
        for (Money money : Money.values()) {
            if (money.getDenomination() <= amount && money.getDenomination() > closest) {
                closest = money.getDenomination();
            }
        }
        return closest;
    }

    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();
        Scanner scanner = new Scanner(System.in);

        vendingMachine.displayMenu();

        while (true) {
            System.out.println("Enter the amount of money to insert (or 0 to finish):");
            double amount = scanner.nextDouble();
            if (amount == 0) {
                break;
            }
            vendingMachine.insertMoney(amount);
        }

        System.out.println("Enter the number of the drink you want to buy:");
        int choice = scanner.nextInt();
        vendingMachine.buyDrink(choice);

        scanner.close();
    }
}