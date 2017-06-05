package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Models.*;

public class MainView<Registros> {

	private JFrame frame;
	private JButton btnSubida;
	private JButton btnCaja;
	private ListaOrdenada<Registros> Registro;
	private JButton btnFin;
	private Date HoraEntrada;
	private Date HoraSalida;
	private float PrecioTotal;
	private long DiferenciaHora;
	private long hola;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
		setProperties();
		setAdapters();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		btnSubida = new JButton("Subida Viajero");
		btnCaja = new JButton("Caja del dia");
		btnFin = new JButton("Fin del Trayecto");
		btnFin.setVisible(false);
	}

	private void setProperties() {
		// FrameGeneral
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Boton Subida
		btnSubida.setBounds(51, 134, 146, 79);
		btnSubida.setFont(new Font("Sitka Text", Font.BOLD, 15));
		frame.getContentPane().add(btnSubida);

		// Boton Caja del dia
		btnCaja.setBounds(232, 134, 146, 79);
		btnCaja.setFont(new Font("Sitka Text", Font.BOLD, 15));
		frame.getContentPane().add(btnCaja);

		// Boton Fin de trayecto
		btnFin.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnFin.setBounds(116, 72, 209, 141);
		frame.getContentPane().add(btnFin);

	}

	private void setAdapters() {

		btnSubida.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				HoraEntrada = new Date();
				btnSubida.setVisible(false);
				btnCaja.setVisible(false);
				btnFin.setVisible(true);

			}
		});

		btnFin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				HoraSalida = new Date();
				DiferenciaHora(HoraEntrada, HoraSalida, TimeUnit.MINUTES);
				PrecioTotal = ObtenerPrecio();
				Registro r = new Registro(HoraEntrada, HoraSalida, PrecioTotal);
				ListaOrdenada<Registros> Lista= new ListaOrdenada<Registros>();
				Lista.aniadir((Registros) r);
				System.out.println(Lista);
				

				
			}
		});
	}

	private long DiferenciaHora(Date d1, Date d2, TimeUnit timeUnit) {
		long DiferenciaHora = d2.getTime() - d1.getTime();
		return timeUnit.convert(DiferenciaHora, TimeUnit.MILLISECONDS);
	}

	private float ObtenerPrecio() {
		int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (dia == 1 || dia == 7) {
			PrecioTotal = 4.55f;
			PrecioTotal += 0.26 * DiferenciaHora;
		} else {
			PrecioTotal = 3.65f;
			PrecioTotal += 0.18 * DiferenciaHora;
		}
		int suplementoNoche = JOptionPane.showConfirmDialog(frame, "Suplemento de noche", "Warning",
				JOptionPane.YES_NO_OPTION);

		switch (suplementoNoche) {
		case 0:
			PrecioTotal += 2;
			break;
		}

		int suplementoAeropuerto = JOptionPane.showConfirmDialog(frame, "Suplemento de aeropuerto", "Warning",
				JOptionPane.YES_NO_OPTION);

		switch (suplementoAeropuerto) {
		case 0:
			PrecioTotal += 5;
			break;
		}
		return PrecioTotal;
	}

}
