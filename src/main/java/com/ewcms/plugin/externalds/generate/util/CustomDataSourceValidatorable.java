/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.externalds.generate.util;

import org.springframework.validation.Errors;

import com.ewcms.plugin.externalds.model.CustomDS;

public interface CustomDataSourceValidatorable {
	void validatePropertyValues(CustomDS ds, Errors errors);
}
