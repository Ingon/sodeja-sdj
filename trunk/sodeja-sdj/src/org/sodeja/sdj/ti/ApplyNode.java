package org.sodeja.sdj.ti;

import org.sodeja.sdj.util.Address;

public class ApplyNode implements Node {
	public final Address left;
	public final Address right;
	
	public ApplyNode(final Address left, final Address right) {
		this.left = left;
		this.right = right;
	}
}
