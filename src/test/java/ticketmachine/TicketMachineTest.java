package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals( PRICE, machine.getPrice(),
			"Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals(10 + 20, machine.getBalance(),
			"La balance n'est pas correctement mise à jour");               
	}

	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	@Test
	public void needsMoneyBeforePrintingTicket() {
		// Si on n'a pas mis d'argent le ticket ne s'imprime pas
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(), 
			"Le ticket ne doit pas s'imprimer");
	}

	// S4 : on imprime le ticket si le montant inséré est suffisant
	@Test
	public void PrintTicketWithEnoughMoney() {
		// Si on met le montant nécessaire, le ticket s'imprime
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(),
			"La machine doit imprimer le ticket");
	}
	
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	@Test
	public void printTicketUpdatesBalance() {
		machine.insertMoney(40);
		machine.insertMoney(20); // on a mis 60 centimes
		machine.printTicket();
		assertEquals(40 + 20 - PRICE, machine.getBalance(), // il me reste 10cts
			"L'impression du ticket doit décrémenter la balance"); 
	}

	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
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
	
	// S7 : refund() rend correctement la monnaie
	@Test
	public void refundWithZeroBalance() {
		assertEquals(0, machine.refund(),
		"Doit rendre 0cts quand la machine est vide");
	}

	// S7 : refund() rend correctement la monnaie
	@Test
	public void refundWithNonZeroBalance() {
		int amount = 10;
		machine.insertMoney(amount);
		assertEquals(amount, machine.refund(),
		"Le montant rendu est incorrect");
	}

	// S8 : refund() remet la balance à zéro
	@Test
	public void refundClearsBalance() {
		machine.insertMoney(40);
		machine.insertMoney(20); // on a mis 60 centimes
		machine.printTicket();
		assertEquals(40 + 20 - PRICE, machine.refund(), // la machine doit me rendre 10cts
			"La machine ne rend pas correctement la monnaie"); 
		assertEquals(0, machine.getBalance(),
			"Refund ne remet pas la balance à zero");
	}	


	// S9 : on ne peut pas insérer un montant négatif
	@Test
	public void insertedAmountMustBePositive() {
		try {
			machine.insertMoney(-1);
			// Si j'arrive ici, c'est pas normal, le test doit échouer
			fail("On aurait du avoir une exception !"); // On force l'échec du test
		} catch (IllegalArgumentException e) {
			// Si j'arrive ici, c'est normal, c'est ce qu'on souhaite
		}
	}

	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	@Test 
	public void ticketPriceMustBePositiveV1() {
		try {
			new TicketMachine(0);
			// Si j'arrive ici, c'est pas normal, le test doit échouer
			fail("On aurait du avoir une exception !"); // On force l'échec du test
		} catch (IllegalArgumentException e) {
			// Si j'arrive ici, c'est normal, c'est ce qu'on souhaite
		}
	}	

	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	@Test 
	public void ticketPriceMustBePositiveV2() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-1);
		});
		assertTrue(e.getMessage().contains("must be positive"));
	}	
	
}
