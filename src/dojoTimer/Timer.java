package dojoTimer;

import java.util.concurrent.TimeUnit;

public class Timer extends Thread{
	private long total_time, actual_time, last_time;
	protected long clock_time;
	private boolean rum, stop;
	
	public Timer( int minuts ){
		this.total_time = TimeUnit.NANOSECONDS.convert( minuts, TimeUnit.MINUTES );
		this.actual_time = 0;
		this.last_time = 0;				
		
		this.rum = false;
		this.stop = false;
		start();
		
	}
	
	public long time(){
		return TimeUnit.SECONDS.convert( total_time, TimeUnit.NANOSECONDS ) - 
				TimeUnit.SECONDS.convert(  clock_time, TimeUnit.NANOSECONDS );
		
	}
	
	@Override
	public void run() {
	
		while( !stop ){			
			try {   
				
				if( this.rum ){					
					actual_time = System.nanoTime();
					long aux = actual_time;
					clock_time+= actual_time - last_time;
					last_time = aux;
					
					System.out.println( clock_time + "\t" + total_time +"\t" + time());
					if( time() < 0)
						stop = true;
				}				
				
				Thread.sleep( 1000 );
				
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
	}
	
	
	public void stopClock(){
		stop = true;
	}

	public void startPause() {
		
		if ( !isAlive() ) start();
		last_time  = System.nanoTime();
		this.rum = !this.rum;
		
		
		
		
	}
	
}
