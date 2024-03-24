import java.util.*;
import java.io.*;
public class PlaneManagement {
    public static void main(String Args[]){
        Scanner input = new Scanner(System.in);
        //a 2D array to print the seat map
        int [][] seats ={{0,0,0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        //2D array to store tickets
        Ticket [][] tickets = new Ticket[4][20];
        File ticket_directory = new File("Tickets");//the file directory to store printed tickets
        if (!ticket_directory.exists()){
            ticket_directory.mkdir();}
        int repeat = 1;
        while(repeat == 1) {//while loop repeats until the user input 0
            try{
                int row_num, column_num;
                System.out.println("**************************************************");
                System.out.println("*                 MENU OPTIONS                   *");
                System.out.println("**************************************************");
                System.out.println("    1) Buy a seat");
                System.out.println("    2) Cancel a seat");
                System.out.println("    3) Find first available seat");
                System.out.println("    4) Show seating plan");
                System.out.println("    5) Print ticket information and sales");
                System.out.println("    6) Search ticket");
                System.out.println("    0) Quit");
                System.out.println("**************************************************");
                System.out.println("Please select an option");
                int option = input.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("Enter the row number: ");
                        row_num = input.nextInt() - 1;
                        System.out.println("Enter the Seat: ");
                        String seat = input.next();
                        column_num = find_seat_number(seat)-1;
                        buy_seat(seat,row_num, column_num, seats,tickets);
                        break;
                    case 2:
                        System.out.println("Enter the row number: ");
                        row_num = input.nextInt() - 1;
                        System.out.println("Enter the Seat: ");
                        seat = input.next();
                        column_num = find_seat_number(seat)-1;//to find the seat number of the letter
                        cancel_seat(row_num, column_num, seats,tickets);
                        break;
                    case 3:
                        find_first_available(seats);
                        break;
                    case 4:
                        show_seating_plan(seats);
                        break;
                    case 5:
                        tickets_info(tickets);
                        break;
                    case 6:
                        System.out.println("Enter the row number: ");
                        row_num = input.nextInt() - 1;
                        System.out.println("Enter the Seat: ");
                        seat = input.next();
                        column_num = find_seat_number(seat);
                        search_ticket(row_num,column_num,seats,tickets);
                        break;
                    case 0:
                        repeat = 0;
                        break;
                    default:
                        System.out.println("Enter a valid option number!");
                }
            }catch (java.lang.ArrayIndexOutOfBoundsException e){//if the row and seat values are not in the range
                    System.out.println("Please enter a valid seat number.....!");
            }catch (InputMismatchException e){//if the menu option input is out of the range
                    System.out.println("Invalid Input....!");
                    input.nextLine();
            }

        }
    }
    static int buy_seat(String seat,int row_num,int seat_num,int seats[][],Ticket [][] tickets) {
        if (seats[seat_num][row_num] == 0) {
            seats[seat_num][row_num] = 1;

            int ticket_price;
            Scanner input = new Scanner(System.in);
            if(row_num<6){
                ticket_price = 200;
            } else if (5<row_num && row_num<10) {
                ticket_price = 150;
            }else{ticket_price=180;}
            System.out.println("$"+ticket_price);

            System.out.println("Name: ");
            String name = input.nextLine();
            System.out.println("Surname: ");
            String sname = input.nextLine();
            System.out.println("e-mail: ");
            String email = input.nextLine();

            Person person = new Person(name,sname,email);

            Ticket ticket = new Ticket(row_num,seat_num,ticket_price,person);
            ticket.ticket_info();
            tickets[seat_num][row_num] = ticket;
            save(seat_num,row_num,tickets);
        } else {
            System.out.println("Already booked..!");
        }
        return seats[seat_num][row_num];
    }
    static int cancel_seat(int row_num,int seat_num,int seats[][],Ticket [][] tickets ){
        Ticket ticket = tickets[seat_num][row_num];
        if(seats[seat_num][row_num] == 1){
            seats[seat_num][row_num] = 0;
            String seat_letter=find_seat_letter(ticket.getSeat()+1);
            File file = new File("Tickets"+File.separator+(seat_letter)+" "+(ticket.getRow()+1)+".txt");
            file.delete();
            tickets[seat_num][row_num]=null;
            System.out.println("Seat cancelled successfully...!");
        } else{
            System.out.println("The seat is already not available");
        }
        return seats[seat_num][row_num];
    }
    static void find_first_available(int seats[][]){
        int row;
        int seat;
        loop:
        for(int k = 0;k<seats.length;k++) {
            for (int j = 0; j < seats[k].length; j++) {
                if (seats[k][j] == 0) {//checks the values of seat array until a 0
                    row= k+1;
                    seat= j+1;
                    String seat_letter = find_seat_letter(seat);
                    System.out.println("Row:"+row+" Seat:"+seat_letter);
                    break loop;
                }
            }
        }
    }
    static void show_seating_plan(int seats[][]){
        System.out.println("  1  2  3  4  5  6  7  8  9 10 11 12 13 14");
        System.out.println("  ----------------------------------------");
        for(int k = 0;k<seats.length;k++) {
            if(k==0){
                System.out.print("A");
            }
            else if(k==1){
                System.out.print("B");
            }
            else if(k==2){
                System.out.print("C");
            }
            else{System.out.print("D");}
                for (int j = 0; j < seats[k].length; j++) {
                    if (seats[k][j] == 0){
                        System.out.print(" "+0+" ");
                    }else{System.out.print(" "+'X'+" ");}
                }
                System.out.println();
            }
        System.out.println();
    }
    static void tickets_info(Ticket[][] tickets) {
        int total=0;
        int ticket_number=1;
        for (int k = 0; k < tickets.length; k++) {
            for (int j = 0; j < tickets[k].length; j++) {

                if (tickets[k][j] != null) {
                    Ticket p= tickets[k][j];
                    String seat_letter=find_seat_letter(p.getSeat()+1);
                    System.out.println(">>>>>>>>>>>Ticket "+ticket_number+"<<<<<<<<<<<<<");
                    System.out.println("Name: "+p.getPerson().getName());
                    System.out.println("Surname: "+p.getPerson().getSurname());
                    System.out.println("e-mail: "+p.getPerson().getEmail());
                    System.out.println("Row: "+(p.getRow()+1));
                    System.out.println("Seat: "+seat_letter);
                    System.out.println("Price: $"+p.getTicket_price());
                    System.out.println("#################################");
                    System.out.println();
                    total= total+p.getTicket_price();
                    ticket_number=ticket_number+1;
                }
            }
        }
        System.out.println("The total amount of sales is $"+total);
    }
    static void search_ticket(int row_num,int seat_num,int[][] seats,Ticket[][] tickets){
        if (seats[seat_num-1][row_num] == 1) {
            Ticket ticket = tickets[seat_num-1][row_num];
            String seat_letter=find_seat_letter(ticket.getSeat()+1);
            System.out.println(">>>>>>>>>>>Ticket<<<<<<<<<<<<<");
            System.out.println("Name: "+ticket.getPerson().getName());
            System.out.println("Surname: "+ticket.getPerson().getSurname());
            System.out.println("e-mail: "+ticket.getPerson().getEmail());
            System.out.println("Seat: "+seat_letter);
            System.out.println("Row: "+(ticket.getRow()+1));
            System.out.println("Price: $"+ticket.getTicket_price());
            System.out.println("###############################");}
        else{System.out.println("No ticket to show.!\n please your inputs..!");}
    }
    static void save(int seat,int row,Ticket[][] tickets){

        Ticket ticket = tickets[seat][row];
        try{
            String seat_letter=find_seat_letter(ticket.getSeat()+1);
            FileWriter file = new FileWriter("Tickets"+File.separator+(seat_letter)+" "+(ticket.getRow()+1)+".txt");
            file.write("Seat:"+seat_letter+"\n");
            file.write("Row: "+(ticket.getRow()+1)+"\n");
            file.write("Name: "+ticket.getPerson().getName()+"\n");
            file.write("Surname: "+ticket.getPerson().getSurname()+"\n");
            file.write("e-mail: "+ticket.getPerson().getEmail()+"\n");
            file.write("Price: "+ticket.getTicket_price());
            file.close();
        }catch(IOException e){System.out.println("Error");}
    }
    static String find_seat_letter(int seat_num){//To find the seat letter of the seat number
        String seat_letter;
        if(seat_num==1){
            seat_letter="A";
        }
        else if(seat_num==2){
            seat_letter="B";
        } else if (seat_num==3) {
            seat_letter="C";
        }
        else{seat_letter="D";}
        return seat_letter;
    }
    static int find_seat_number(String seat_letter){//To find the seat number of the seat letter
        int seat_num;
        if(seat_letter.equals("A") || seat_letter.equals("a")){
            seat_num=1;
        }
        else if(seat_letter.equals("B") || seat_letter.equals("b")){
            seat_num=2;
        } else if (seat_letter.equals("C") || seat_letter.equals("c")) {
            seat_num=3;
        }
        else{seat_num=4;}
        return seat_num;
    }
}