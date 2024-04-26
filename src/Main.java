import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private  String brand;
    private  String model;
    private  double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId,String brand,String model,double basePricePerDay,boolean isAvailable){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;

    }
    public String getCarId(){
        return carId;
    }
  public String getBrand(){
        return brand;
  }
  public String getModel(){
        return model;
    }
    public double calculatePrice(int rentaldays){
        return  basePricePerDay* rentaldays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}
class Customer{
    private String name;
    private String  CustomerId;

    Customer(String name, String CustomerId){
        this.name = name;
        this.CustomerId = CustomerId;

    }
    public String getName(){
        return  name;
    }
    public String getCustomerId(){
        return CustomerId;
    }
}
  class Rental{
    private Car car;
    private Customer customer;
    private  int days;

    public Rental(Car car,Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;

    }
    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return  days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Rental> rentals;
    private List<Customer> customers;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);

    }
    public  void addCustomer(Customer customer){
        customers.add(customer);
    }
    public  void rentCar(Car car, Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else{
            System.out.println("Car is not available for Rent");
        }
    }
    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove=null;
        for(Rental rental : rentals){
            if(rental.getCar()== car){
                rentalToRemove= rental;
                break;
            }
        }
        if(rentalToRemove!=null){
            rentals.remove(rentalToRemove);

        }else{
            System.out.println("Car was not rented");

        }
    }
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Car Rental System ====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3.Exit");
            System.out.println("Enter your Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\n Rent a car \n");
                System.out.println("Enter your Name");
                String customerName = scanner.nextLine();

                System.out.println("\n Available Cars\n");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " _ " + car.getBrand() + " _ " + car.getModel());
                    }
                }
                System.out.println("\n Enter the Car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n  Rental Information \n");
                    System.out.println(" Customer Id: " + newCustomer.getName());
                    System.out.println(" Customer Name: " + newCustomer.getCustomerId());
                    System.out.println(" Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println(" Rental Days: " + rentalDays);
                    System.out.println(" Total Price: $" + totalPrice);
                    System.out.println("\n Confirm Rental (Y/N): ");
                    String confirm = scanner.nextLine();


                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n Car rented Successfully");
                    } else {
                        System.out.println("Rental Cancelled");
                    }
                } else {
                    System.out.println("\n Invalid Car Selection or Car not available for Rent");
                }
            } else if (choice == 2) {
                System.out.println("\n == Return a Car == \n");
                System.out.println("Enter a Car Id you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned Successfully by " + customer.getCustomerId());
                    } else {
                        System.out.println("Car was not rented or rental information is missing");
                    }
                } else {
                    System.out.println("Invalid Car id or Car is not rented");
                }


            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice, Please enter a valid option");
            }

        }
        System.out.println("Thank for using Car Rental System");
    }
    }

public class Main {
    public static void main(String[] args){
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001","BMW","A10",200,true);
        Car car2 = new Car("C002","AUDI","Q8",150,true);
        Car car3 = new Car("C003","Rools Royce","King",300,true);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.menu();
    }
}
