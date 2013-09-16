package dojoTimer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View extends JFrame{
	private JLabel visor;
	private JButton new_clock, pause, stop;
	private Timer timer;
	private int minuts;
	
	public View(){
		super("Dojô Timer");
		setDefaultCloseOperation( super.EXIT_ON_CLOSE );
		setAlwaysOnTop( true );
		setSize(200, 135);
		
		setContentPane( new JPanel( new BorderLayout() ));
		
		this.minuts = 0;
		
		this.visor = new JLabel("00:00");
		this.visor.setBackground( Color.white );
		this.visor.setOpaque( true );
		this.visor.setFont( new Font( Font.SERIF, Font.BOLD, 60) );		
		this.visor.setBorder( BorderFactory.createEmptyBorder(3, 3, 3, 3) );		

		ClassLoader classLoader = this.getClass().getClassLoader();  
		
		this.new_clock = new JButton(
				new ImageIcon( 
						classLoader.getResource("img/new.png") ) );
		this.new_clock.setToolTipText("Configura um novo relógio");
		this.new_clock.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ( timer != null )
					timer.stopClock();
				
				boolean ask = true;
				while( ask  ){
					try{
						minuts = Integer.parseInt(
										JOptionPane.showInputDialog( null, "Digite a quantidade de minutos")
										);								
						ask = false;
						
					}catch( NumberFormatException ex ){
						JOptionPane.showInputDialog( null, "Digite um número válido");
					}
				}
				
				timer = new Timer( minuts );
				
			}
		});
		
		
		//this.pause = new JButton( new ImageIcon("img/play.png") );
		this.pause = new JButton( 
				new ImageIcon( 
						classLoader.getResource("img/play.png") ) );
		
		this.pause.setToolTipText("Inicia e Paula o Relógio");
		this.pause.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.startPause();				
			}
		});
		
		
		this.stop = new JButton( 
				new ImageIcon( 
						classLoader.getResource("img/again.png") ) );
		this.stop.setToolTipText("Reinicia o relógio. Padrão igual a 5");		
		this.stop.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if( minuts == 0 )
					minuts = 5;
				
				if ( timer != null )
					timer.stopClock();
				
				timer = new Timer( minuts );
				timer.startPause();
			}
		});
		
		
		JPanel commands_panel = new JPanel( new GridLayout() );
		commands_panel.add( new_clock );
		commands_panel.add( pause );
		commands_panel.add( stop );
		
		
		add( this.visor, BorderLayout.CENTER );
		add( commands_panel, BorderLayout.NORTH );
		
		new Thread(){
			public void run() {
				while( true )
					try {
						Thread.sleep( 100 );
						
						visor.setForeground( Color.black );
						
						if ( timer == null )
							continue;
						
						long total = timer.time();
						long minuts = ( total / 60 );
						long seconds = ( total % 60 );

						String str = "";
						if( minuts < 10 ) str = "0";
						str+= minuts + ":";
												
						if( seconds < 10 ) str+="0";
						str+= seconds;
						
						visor.setText( str );
						
						if( minuts < 1 ) visor.setForeground( Color.red );
						
						if ( total < 0 )
							visor.setText( "--:--");
						
					} catch (InterruptedException e) {					
						e.printStackTrace();
					}	
			};
		}.start();
	}
	
	public static void main(String[] args) {
		
		new View().show();
	}
	
	
}
