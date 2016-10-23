package com.kasisoft.cdi.weldex.internal;

import com.kasisoft.libs.common.i18n.*;

import lombok.experimental.*;

import lombok.*;

/**
 * Collection of messages that could be overridden if necessary.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Messages {

  @I18N("The instance '%s' cannot be provided as it's ambiguous !")
  static I18NFormatter   error_ambiguous_instance;

  @I18N("The instance '%s' could not be satisfied !")
  static I18NFormatter   error_unsatisfied_instance;

  static {
    I18NSupport.initialize( Messages.class );
  }
  
} /* ENDCLASS */
