package com.eucalyptus.ws.client;

import java.util.NavigableSet;
import org.apache.log4j.Logger;
import com.eucalyptus.bootstrap.Component;
import com.eucalyptus.component.Components;
import com.eucalyptus.component.Service;
import com.eucalyptus.component.event.StartComponentEvent;
import com.eucalyptus.component.id.Database;
import com.eucalyptus.component.id.Storage;
import com.eucalyptus.component.id.Walrus;
import com.eucalyptus.event.ClockTick;
import com.eucalyptus.event.Event;
import com.eucalyptus.event.EventListener;
import com.eucalyptus.event.ListenerRegistry;
import com.eucalyptus.util.LogUtil;

public class ServiceEventListener implements EventListener {
  private static Logger LOG = Logger.getLogger( ServiceEventListener.class );

  public static void register( ) {
    ServiceEventListener me = new ServiceEventListener( );
    for( Component c : Component.values() ) {
      ListenerRegistry.getInstance( ).register( c, me );        
    }
    if( Components.lookup( "eucalyptus" ).isLocal( ) ) {
      ListenerRegistry.getInstance( ).register( ClockTick.class, RemoteBootstrapperClient.getInstance( ) ); 
      ListenerRegistry.getInstance( ).register( Walrus.class, RemoteBootstrapperClient.getInstance( ) );
      ListenerRegistry.getInstance( ).register( Storage.class, RemoteBootstrapperClient.getInstance( ) );
    }
  }


  @Override
  public void fireEvent( Event event ) {
    if ( event instanceof StartComponentEvent ) {
      StartComponentEvent e = ( StartComponentEvent ) event;
      if ( Database.class.equals( e.getIdentity( ).getClass( ) ) ) {
        LOG.info( LogUtil.header( "Got information for the " + e.getIdentity( ) + " " + LogUtil.dumpObject( e.getConfiguration( ) ) ) );
      }
      NavigableSet<Service> services = Components.lookup( e.getIdentity( ) ).getServices( );
      for( Service s : services ) {
        LOG.info( "Registered service dispatchers: " + s.getName( ) + " " + s.getUri( ).toASCIIString( ) + " " + LogUtil.dumpObject( e.getConfiguration( ) ) );
      }
    }
  }

  
  
}
