package com.eucalyptus.component.id;

import org.jboss.netty.channel.ChannelPipelineFactory;
import com.eucalyptus.component.ComponentId;

public class Cluster extends ComponentId {

  @Override
  public Integer getPort( ) {
    return 8774;
  }

  @Override
  public String getLocalEndpointName( ) {
    return "vm://ClusterEndpoint";
  }

  @Override
  public String getUriPattern( ) {
    return "http://%s:%d/axis2/services/EucalyptusCC";
  }

  @Override
  public Boolean hasDispatcher( ) {
    return false;
  }

  @Override
  public Boolean isAlwaysLocal( ) {
    return false;
  }

  @Override
  public Boolean isCloudLocal( ) {
    return false;
  }

  private static ChannelPipelineFactory clusterPipeline = helpGetClientPipeline( "com.eucalyptus.ws.client.pipeline.ClusterClientPipelineFactory" );
  private static ChannelPipelineFactory logPipeline     = helpGetClientPipeline( "com.eucalyptus.ws.client.pipeline.GatherLogClientPipeline" );
  @Override
  public ChannelPipelineFactory getClientPipeline( ) {
    return clusterPipeline;
  }

  /**
   * This was born under a bad sign.  No touching.
   * @return
   */
  public static ChannelPipelineFactory getLogClientPipeline( ) {
    return logPipeline;
  }
}
