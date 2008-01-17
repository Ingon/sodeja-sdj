package org.sodeja.silan.objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.files.FileUtils;
import org.sodeja.functional.Pair;
import org.sodeja.lang.StringUtils;
import org.sodeja.silan.CompiledBlock;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.SILClass;
import org.sodeja.silan.SILClassClass;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.TypeSupplier;
import org.sodeja.silan.VirtualMachine;
import org.sodeja.silan.context.Context;

public class ImageObjectManager implements TypeSupplier {
	private static final String BASE_FOLDER = "image/";
	private static final String INIT_FILENAME = "image_init.silan";
	
	protected final VirtualMachine vm;
	
	private final ScriptLexer lexer;
	private final ScriptParser parser;
	
	private SILClass nilClass;
	private SILClassClass nilClassClass;
	private SILObject nilInstance = new SILPrimitiveObject<Void>(this, "Nil", null);
	
	private SILObject trueInstance = new SILPrimitiveObject<Boolean>(this, "True", Boolean.TRUE);
	private SILObject falseInstance = new SILPrimitiveObject<Boolean>(this, "False", Boolean.FALSE);
	
	private final Map<String, SILClass> types;
	
	public ImageObjectManager(VirtualMachine vm) {
		this.vm = vm;
		
		this.lexer = new ScriptLexer();
		this.parser = new ScriptParser();
		
		this.types = new HashMap<String, SILClass>();
		
		try {
			init();
		} catch (IOException e) {
			throw new RuntimeException("Image init failed: " + e.getLocalizedMessage(), e);
		}
	}
	
	private void init() throws IOException {
		String initScript = FileUtils.readFully(BASE_FOLDER + INIT_FILENAME);
		String[] initScripts = initScript.split("\\s");
		
		for(String str : initScripts) {
			if(str.startsWith(";")) {
				continue;
			}
			if(StringUtils.isTrimmedEmpty(str)) {
				continue;
			}
			executeScript(BASE_FOLDER + str);
		}
	}

	private void executeScript(String scriptFileName) throws IOException {
		String scriptText = FileUtils.readFully(scriptFileName);
		List<Token> tokens = lexer.lexify(scriptText);
		
		Script script = parser.parse(tokens);
		script.execute(this);
	}

	public SILClass getByTypeName(String name) {
		SILClass type = types.get(name);
		if(type == null) {
			throw new RuntimeException("Unable to find type " + name);
		}
		return type;
	}

	public SILClass getNilClass() {
		return nilClass;
	}
	
	public SILObject nil() {
		return nilInstance;
	}
	
	public SILObject newInteger(Integer value) {
		return new SILPrimitiveObject<Integer>(this, "Integer", value);
	}

	public SILObject newString(String value) {
		return new SILPrimitiveObject<String>(this, "String", value);
	}

	public SILObject newCharacter(Character value) {
		return new SILPrimitiveObject<Character>(this, "Character", value);
	}
	
	public SILObject newBoolean(Boolean value) {
		if(value) {
			return trueInstance;
		}
		return falseInstance;
	}
	
	public SILObject newBlock(CompiledBlock block, Context ctx) {
		return new SILPrimitiveObject<Pair<CompiledBlock, Context>>(this, "CompiledBlock", Pair.of(block, ctx));
	}
	
	public SILObject newValueIfNeeded(Object obj) {
		if(obj instanceof SILObject) {
			return (SILObject) obj;
		} else if(obj instanceof String) {
			return newString((String) obj);
		} else if(obj instanceof Character) {
			return newCharacter((Character) obj);
		} else if(obj instanceof Integer) {
			return newInteger((Integer) obj);
		} else if(obj instanceof Boolean) {
			return newBoolean((Boolean) obj);
		}
		throw new UnsupportedOperationException();
	}
	
	public void subclass(String parentName, String newClassName, List<String> instanceVariables) {
		if(parentName.equals("Nil")) {
			initObject();
			return;
		}
		
		if(newClassName.equals("Nil")) {
			return;
		}
		
		if(types.containsKey(newClassName)) {
			throw new RuntimeException("Not able to override class definition");
		}
		
		SILClass parent = getByTypeName(parentName);
		
		SILClassClass newClassClass = new SILClassClass(parent.getType());
		
		SILClass newClass = new SILClass(parent, instanceVariables);
		newClass.setType(newClassClass);
		
		types.put(newClassName, newClass);
	}

	public void attach(String className, CompiledMethod method) {
		SILClass clazz = getByTypeName(className);
		clazz.addMethod(method);
	}
	
	private void initObject() {
		SILClass objectClass = new SILClass(null);
		SILClassClass objectClassClass = new SILClassClass(objectClass);
		
		objectClass.setType(objectClassClass);
		
		nilClass = new SILClass(objectClass);
		nilClassClass = new SILClassClass(objectClassClass);
		
		objectClass.setSuperclass(nilClass);
		nilClass.setType(nilClassClass);
		
		types.put("Object", objectClass);
		types.put("Nil", nilClass);
	}

	public SILObject getGlobal(String reference) {
		return getByTypeName(reference);
	}
}
