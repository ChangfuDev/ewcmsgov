/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.crawler.crawl.crawler4j.frontier;

import com.ewcms.crawler.crawl.crawler4j.url.WebURL;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public final class WebURLTupleBinding extends TupleBinding<WebURL> {

	@Override
	public WebURL entryToObject(TupleInput input) {
		WebURL webURL = new WebURL();
		webURL.setURL(input.readString());
		webURL.setDocid(input.readInt());
		webURL.setParentDocid(input.readInt());
		webURL.setDepth(input.readShort());
		return webURL;
	}

	@Override
	public void objectToEntry(WebURL url, TupleOutput output) {		
		output.writeString(url.getURL());
		output.writeInt(url.getDocid());
		output.writeInt(url.getParentDocid());
		output.writeShort(url.getDepth());
	}
}
