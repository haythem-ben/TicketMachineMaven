package ticketmachine;
/**
 * Un exemple d'utilisation
 */
public class Main {
        /**
         * Programme principal
         * @param args inutilisé
         */
	public static void main(String[] args) {
		TicketMachine machine = new TicketMachine(50);
		System.out.println("L'utilisateur insère 60 centimes");
		machine.insertMoney(60);
		System.out.println("L'utilisateur appuie sur 'Impression ticket'");
		machine.printTicket();
		System.out.println("L'utilisateur appuie sur 'Cancel'");
		machine.refund();
	}
}
