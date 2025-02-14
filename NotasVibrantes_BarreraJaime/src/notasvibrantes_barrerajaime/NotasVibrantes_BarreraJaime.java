package notasvibrantes_barrerajaime;

/**
 *
 * @author Jaime Barrera
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


class Cliente{
    int id;
    String nombre;
    String apellido;
    String correo;
    String telefono;

    public Cliente(int id, String nombre, String apellido, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }    
}

class Concierto{
    int id;
    String nombre;
    String artista;
    String lugar;
    float precioBase;

    public Concierto(int id, String nombre, String artista, String lugar, float precioBase) {
        this.id = id;
        this.nombre = nombre;
        this.artista = artista;
        this.lugar = lugar;
        this.precioBase = precioBase;
    }
}

class Ticket{
    int id;
    int id_cliente;
    int id_concierto;
    String zona;
    float precioFinal;
    String fechaCompra;

    public Ticket(int id, int id_cliente, int id_concierto, String zona, float precioFinal, String fechaCompra) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_concierto = id_concierto;
        this.zona = zona;
        this.precioFinal = precioFinal;
        this.fechaCompra = fechaCompra;
    }
}

class Zona{
    int id;
    String nombreZona;
    int capacidad;
    float precioAdicional;

    public Zona(int id, String nombreZona, int capacidad, float precioAdicional) {
        this.id = id;
        this.nombreZona = nombreZona;
        this.capacidad = capacidad;
        this.precioAdicional = precioAdicional;
    }
}

class NotasDAO {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Concierto> conciertos = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<Zona> zonas = new ArrayList<>();
    private int contadorId = 1;
    private int contadorId2 = 1;
    
    
    
    public NotasDAO() {
        // Conciertos predefinidos
        conciertos.add(new Concierto(1, "Rock Fest", "Metallica", "Estadio Nacional", 600000));
        conciertos.add(new Concierto(2, "Pop Live", "Taylor Swift", "Arena Movistar", 800000));
        conciertos.add(new Concierto(3, "Reggaeton Night", "Bad Bunny", "Estadio Azteca", 700000));
        conciertos.add(new Concierto(4, "Electro Beats", "David Guetta", "Club Ibiza", 600000));
        conciertos.add(new Concierto(5, "Latin Party", "Karol G", "Coliseo Medellín", 750000));

        // Zonas predefinidas
        zonas.add(new Zona(1, "General", 5000, 150000));
        zonas.add(new Zona(2, "VIP", 1000, 500000));
        zonas.add(new Zona(3, "Platino", 500, 1000000));

    }
    
     // listar conciertos disponibles
    public void listarConciertos() {
        System.out.println("Conciertos disponibles:");
        for (Concierto c : conciertos) {
            System.out.println("ID: " + c.id + " | Nombre: " + c.nombre + " | Artista: " + c.artista + " | Lugar: " + c.lugar + " | Precio Base: $" + c.precioBase);
        }
    }
    
    // listar clientes registrados
    public void ListarClientes(){
        System.out.println("Clientes registrados: ");
        for (Cliente c : clientes){
            System.out.println("ID: " + c.id + " | Nombre: " + c.nombre + " | Apellido: " + c.apellido + " | Correo: " + c.correo + " | Telefono: " + c.telefono );
        }
    }
    
    //Registrar clientes
    public void registrarCliente(Cliente cliente) {
        cliente.id = contadorId++;
        clientes.add(cliente);
        System.out.println("Cliente Registado Exitosamente");
    }
    
    //vender ticket, asignar zona y calculando el precio final
    public void comprarTicket(int idCliente, int idConcierto, int idZona) {
        Cliente clienteEncontrado = null;
        Concierto conciertoEncontrado = null;
        Zona zonaEncontrada = null;

        // Buscar el cliente
        for (Cliente c : clientes) {
            if (c.id == idCliente) {
                clienteEncontrado = c;
                break;
            }
        }

        // Buscar el concierto
        for (Concierto co : conciertos) {
            if (co.id == idConcierto) {
                conciertoEncontrado = co;
                break;
            }
        }

        // Buscar la zona
        for (Zona z : zonas) {
            if (z.id == idZona) {
                zonaEncontrada = z;
                break;
            }
        }

        // Verificar que todo se encontro
        if (clienteEncontrado == null || conciertoEncontrado == null || zonaEncontrada == null) {
            System.out.println("Error: Cliente, concierto o zona no encontrados.");
            return;
        }

        // Calcular precio final (precio base del concierto + precio adicional de la zona)
        float precioFinal = conciertoEncontrado.precioBase + zonaEncontrada.precioAdicional;

        // Generar fecha actual
        String fechaCompra = java.time.LocalDate.now().toString();

        // Crear el ticket y agregarlo a la lista
        Ticket nuevoTicket = new Ticket(contadorId2++, idCliente, idConcierto, zonaEncontrada.nombreZona, precioFinal, fechaCompra);
        tickets.add(nuevoTicket);

        System.out.println("Ticket comprado con éxito. Precio final: $" + precioFinal);
    }
    
    
    // listar tickets de un concierto
    public void listarTicketsPorConcierto(int idConcierto) {
        System.out.println("Tickets vendidos para el concierto " + idConcierto + ":");
        for (Ticket t : tickets) {
            if (t.id_concierto == idConcierto) {
                System.out.println("ID Ticket: " + t.id + " | Cliente: " + t.id_cliente + " | Zona: " + t.zona + " | Precio: $" + t.precioFinal);
            }
        }
    }
    
    // ver conciertos de un cliente
    public void listarConciertosPorCliente(int idCliente) {
        System.out.println("Conciertos del cliente " + idCliente + ":");
        for (Ticket t : tickets) {
            if (t.id_cliente == idCliente) {
                System.out.println("ID Concierto: " + t.id_concierto + " | Zona: " + t.zona + " | Precio: $" + t.precioFinal);
            }
        }
    }
    
    // cancelar un ticket
    public void cancelarTicket(int idTicket) {
        Ticket ticketAEliminar = null;
        
        System.out.println("Tickets: ");
        for (Ticket c : tickets){
            System.out.println("ID: " + c.id + " | Precio : " + c.precioFinal + " | Fecha de Compra: " + c.fechaCompra );
        }

        for (Ticket t : tickets) {
            if (t.id == idTicket) {
                ticketAEliminar = t;
                break;
            }
        }

        if (ticketAEliminar != null) {
            tickets.remove(ticketAEliminar);
            System.out.println("Ticket cancelado con éxito.");
        } else {
            System.out.println("Ticket no encontrado.");
        }
    }
    
    // calcular ingresos de un concierto
    public void calcularIngresosConcierto(int idConcierto) {
        float totalIngresos = 0;

        for (Ticket t : tickets) {
            if (t.id_concierto == idConcierto) {
                totalIngresos += t.precioFinal;
            }
        }

        System.out.println("Ingresos totales del concierto " + idConcierto + ": $" + totalIngresos);
    }
}
public class NotasVibrantes_BarreraJaime {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        NotasDAO dao = new NotasDAO();
        
        while (true){
            System.out.println("\n GESTION NOTAS VIBRANTES");
            System.out.println("");
            System.out.println("1. Ver conciertos disponibles");
            System.out.println("2. Registrar cliente");
            System.out.println("3. Comprar ticket");
            System.out.println("4. Ver tickets por cliente");
            System.out.println("5. Cancelar ticket");
            System.out.println("6. Ingresos por concierto");
            System.out.println("7. Listar clientes");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.print("Elige una opcion: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion){
                case 1:
                    dao.listarConciertos();
                    break;
                
                case 2: 
                    System.out.print("Ingrese el Nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Ingrese el Apellido: ");
                    String nuevoApellido = scanner.nextLine();
                    System.out.print("Ingrese el Correo: ");
                    String nuevoCorreo = scanner.nextLine();
                    System.out.print("Ingrese el Telefono: ");
                    String nuevoTelefono = scanner.nextLine();
                    dao.registrarCliente(new Cliente(0,nuevoNombre,nuevoApellido,nuevoCorreo,nuevoTelefono));
                    break;
                    
                case 3:
                    System.out.print("ID Cliente: ");
                    int idClient = scanner.nextInt();
                    System.out.print("ID Concierto: ");
                    int idConciert = scanner.nextInt();
                    System.out.print("ID Zona: ");
                    int idZon = scanner.nextInt();
                    dao.comprarTicket(idClient, idConciert, idZon);
                    break;
                    
                case 4:
                    System.out.print("ID Cliente: ");
                    int idClienteTickets = scanner.nextInt();
                    dao.listarConciertosPorCliente(idClienteTickets);
                    break;
                    
                case 5:
                    System.out.print("ID del ticket a cancelar: ");
                    int idTicke = scanner.nextInt();
                    dao.cancelarTicket(idTicke);
                    break;
                    
                case 6:
                    System.out.print("ID Concierto: ");
                    int idConciertoIngresos = scanner.nextInt();
                    dao.calcularIngresosConcierto(idConciertoIngresos);
                    break;
                    
                case 7:
                    dao.ListarClientes();
                    break;
                    
                case 0:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");    
            }
        }
    }
    
}
