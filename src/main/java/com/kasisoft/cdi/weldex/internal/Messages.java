package com.kasisoft.cdi.weldex.internal;

import com.kasisoft.libs.common.i18n.*;

/**
 * Collection of messages that could be overridden if necessary.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class Messages {

  @I18N("The instance '%s' cannot be provided as it's ambiguous !")
  public static I18NFormatter   error_ambiguous_instance;

  @I18N("The instance '%s' could not be satisfied !")
  public static I18NFormatter   error_unsatisfied_instance;

  static {
    I18NSupport.initialize( Messages.class );
  }
  
} /* ENDCLASS */
