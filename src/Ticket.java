public class Ticket {
    private int row;
    private int seat;
    private int ticket_price;
    private Person person;


    public Ticket(int row, int seat, int ticket_price, Person person) {
        this.row = row;
        this.seat = seat;
        this.person = person;
        this.ticket_price = ticket_price;
    }

    public int getRow() {
        return row;
    }

    public int getTicket_price() {
        return ticket_price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setTicket_price(int ticket_price) {
        this.ticket_price = ticket_price;
    }

    public Person getPerson() {
        return person;
    }

    void ticket_info() {
        System.out.println("^^^^^^^^^^^^^^^TICKET  INFO^^^^^^^^^^^^^^^^^");
        System.out.println("Row number: " + (this.row + 1));
        System.out.println("Seat number: " + (this.seat + 1));
        System.out.println("Ticket price : $" + ticket_price);
        person.printinfo();
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        System.out.println();
    }
}
