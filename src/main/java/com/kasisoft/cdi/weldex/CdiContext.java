package com.kasisoft.cdi.weldex;

import static com.kasisoft.cdi.weldex.internal.Messages.*;

import org.jboss.weld.environment.se.*;

import lombok.experimental.*;

import lombok.extern.slf4j.*;

import lombok.*;

import javax.enterprise.inject.*;

import java.util.*;

import java.lang.annotation.*;

/**
 * Essentially an alternative to {@link StartMain} which provides the functionality to access bean instances
 * programmatically. An application is therefore being started while accessing the main class.  
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CdiContext {

  private static final CdiContext INSTANCE = new CdiContext();
  
  Weld            weld;
  WeldContainer   container;
  String          id;

  private CdiContext() {
    weld      = new Weld();
    Runtime.getRuntime().addShutdownHook( new Thread( weld::shutdown ) );
    container = weld.initialize();
    id        = container.getId();
  }
  
  /**
   * Returns the bean instance associated with the supplied type.
   * 
   * @param type   The type for the desired bean instance. Not <code>null</code>.
   * 
   * @return   The bean instance. Maybe <code>null</code>.
   */
  public static <R> R component( @NonNull Class<R> type ) {
    Instance<R> instance = INSTANCE.container.instance( INSTANCE.id ).select( type );
    return instance( type, instance );
  }

  /**
   * Returns all instances matching the supplied list of annotations.
   * 
   * @param annotations   The annotations used to select the components.
   * 
   * @return   A set of matching components. Not <code>null</code>.
   */
  public static Set<Object> components( @NonNull Annotation ... annotations ) {
    Instance<Object> instance = INSTANCE.container.instance( INSTANCE.id ).select( annotations );
    return collect( instance );
  }

  /**
   * Returns all instances of a certain type.
   * 
   * @param type   The desired type. Not <code>null</code>.
   * 
   * @return   A set of matching components. Not <code>null</code>.
   */
  public static <R> Set<R> components( @NonNull Class<R> type ) {
    Instance<R> instance = INSTANCE.container.instance( INSTANCE.id ).select( type );
    return collect( instance );
  }

  /**
   * Returns all instances matching the supplied list of annotations for a specific type.
   * 
   * @param type          The desired type. Not <code>null</code>.
   * @param annotations   The annotations used to select the components. Not <code>null</code>.
   * 
   * @return   A set of matching components. Not <code>null</code>.
   */
  public static <R> Set<R> components( @NonNull Class<R> type, @NonNull Annotation ... annotations ) {
    Instance<R> instance = null;
    if( annotations.length > 0 ) {
      instance = INSTANCE.container.instance( INSTANCE.id ).select( type, annotations );
    } else {
      instance = INSTANCE.container.instance( INSTANCE.id ).select( type );
    }
    return collect( instance );
  }

  /**
   * Returns a a set of matching instances supplied by the instance.
   * 
   * @param instance   The instance object providing the desired components. Not <code>null</code>.
   * 
   * @return   A set of matching components. Not <code>null</code>.
   */
  private static <R> Set<R> collect( Instance<R> instance ) {
    Set<R> result = Collections.emptySet();
    if( instance.isUnsatisfied() ) {
      // unavailable, so empty result
    } else {
      result = new HashSet<>();
      if( instance.isAmbiguous() ) {
        instance.forEach( result::add );
      } else {
        result.add( instance.get() );
      }
    }
    return result;
  }

  private static <R> R instance( Class<R> type, Instance<R> instance ) {
    if( instance.isAmbiguous() ) {
      log.warn( error_ambiguous_instance.format( type.getName() ) );
    } else if( instance.isUnsatisfied() ) {
      log.warn( error_unsatisfied_instance.format( type.getName() ) );
    }
    return instance.get();
  }

} /* ENDCLASS */
