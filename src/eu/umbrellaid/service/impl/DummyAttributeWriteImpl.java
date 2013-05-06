package eu.umbrellaid.service.impl;

import eu.umbrellaid.entity.Address;
import eu.umbrellaid.service.AttributeWrite;

public class DummyAttributeWriteImpl implements AttributeWrite {

	@Override
	public void write(Address address) {
		System.out.println(address);
	}

}
