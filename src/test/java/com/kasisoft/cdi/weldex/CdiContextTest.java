package com.kasisoft.cdi.weldex;

import static org.testng.Assert.*;

import org.testng.annotations.*;

import javax.inject.*;

import lombok.experimental.*;

import lombok.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
/* @ManagedBean */ @Singleton
public class CdiContextTest {

  @Test
  public void basic() {
    assertNotNull( CdiContext.component( DummyComponent.class ) );
  }
  
  
} /* ENDCLASS */
