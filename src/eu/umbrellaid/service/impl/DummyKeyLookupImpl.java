package eu.umbrellaid.service.impl;

import java.util.HashMap;

import eu.umbrellaid.service.KeyLookup;

public class DummyKeyLookupImpl implements KeyLookup {

	@Override
	public String lookup(String eaahash) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("abcd1234", "1234abcd");
		return map.get(eaahash);
	}

}
