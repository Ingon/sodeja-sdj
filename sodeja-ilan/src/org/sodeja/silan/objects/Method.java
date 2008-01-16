package org.sodeja.silan.objects;

import org.sodeja.silan.CompiledMethod;

public interface Method {
	CompiledMethod compile(ImageObjectManager manager);
}
