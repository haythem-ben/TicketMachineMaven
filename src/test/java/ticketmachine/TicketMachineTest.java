package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	public void priceIsCorrectlyInitialized() {
		// Le prix correspond au paramètre passé lors de l'initialisation
		assertEquals("Le prix du ticket est mal initialisé", 
			PRICE, machine.getPrice());
	}

	
	@Test
	public void refundWithZeroBalance() {
		assertEquals("Doit rendre 0cts quand la machine est vide", 
			0, machine.refund());
	}


	@Test
	public void refundClearsBalance() {
		machine.insertMoney(40);
		machine.insertMoney(20); // on a mis 60 centimes
		machine.printTicket();
		assertEquals("La machine ne rend pas correctement la monnaie", 
			40 + 20 - PRICE, machine.refund()); // la machine doit me rendre 10cts
		assertEquals("Refund ne remet pas la balance à zero", 
			0, machine.getBalance());
	}	

        
	@Test
	public void insertMoneyChangesBalance() {
		assertEquals(0, machine.getBalance()); // Au début la balance est égale à 0
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("L'insertion d'argent ne change pas correctement la balance", 
			10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}

	// tester qu'on n'imprime le ticket que si le montant nécessaire a été introduit
	@Test
	public void needsMoneyBeforePrintingTicket() {
		// Si on n'a pas mis d'argent le ticket ne s'imprime pas
		assertFalse(machine.printTicket());
		// Après avoir mis le montant nécessaire, le ticket s'imprime
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket());
	}

	// tester qu'on n'imprime le ticket que si le montant nécessaire a été introduit
	@Test
	public void machineCollectsTotal() {
		// Au départ, le total collecté est de 0
		assertEquals(0, machine.getTotal());
		machine.insertMoney(PRICE * 2 + 1);
		// Le total n'est pas encore mis à jour
		assertEquals(0, machine.getTotal());
		// On imprime 2 tickets
		machine.printTicket();
		machine.printTicket();
		// On a collecté 2x le prix du ticket
		assertEquals(PRICE * 2, machine.getTotal());
	}
	
	@Test
	public void insertedAmountMustBePositive() {
		try {
			machine.insertMoney(-1);
			// Si j'arrive ici, c'est pas normal, le test doit échouer
			fail(); // On force l'échec du test
		} catch (IllegalArgumentException e) {
			// Si j'arrive ici, c'est normal, c'est ce qu'on souhaite
		}
	}

	@Test
	public void ticketPriceMustBePositive() {
		try {
			new TicketMachine(0);
			// Si j'arrive ici, c'est pas normal, le test doit échouer
			fail(); // On force l'échec du test
		} catch (IllegalArgumentException e) {
			// Si j'arrive ici, c'est normal, c'est ce qu'on souhaite
		}
	}	
	
	@Test
	public void printTicketUpdatesBalance() {
		machine.insertMoney(40);
		machine.insertMoney(20); // on a mis 60 centimes
		machine.printTicket();
		assertEquals(40 + 20 - PRICE, machine.getBalance()); // il me reste 10cts
	}

}
