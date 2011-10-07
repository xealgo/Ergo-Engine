package ryad.games.ergoengine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Allows us to load game script files and execute them
 * as game code using the Mozilla Rhino JavaScript engine.
 * @author Jason Welch
 */
public class ScriptingSystem {
	//===================================
	// Members
	//===================================
	protected String scriptDir = "";
	protected Bindings bindings = null;
	protected ScriptEngineManager mgr = null;
	protected ScriptEngine engine = null;
	protected JTextArea textArea = null;
	protected JFrame errorWindow = null;
	protected List<String> scriptFiles;
	protected List<String> errors;
	protected List<String> debugMessages;	
    protected Invocable invocable;
    
	//===================================
	// The required script functions..
	//===================================
    protected String initGameFunction = "initGame";
    protected String preBeginPlayFunction = "preBeginPlay";
    protected String postBeginPlayFunction= "postBeginPlay";
	
	//===================================
	// Constructors
	//===================================
	public ScriptingSystem(String scriptsDir) {
		this.scriptDir = scriptsDir;
		this.scriptFiles = new ArrayList<String>();
		this.errors = new ArrayList<String>();
		this.debugMessages = new ArrayList<String>();
		this.mgr = new ScriptEngineManager();
		this.engine = mgr.getEngineByName("JavaScript");
		this.bindings = engine.createBindings();
		this.bindData("scripts", this);
	}
	
	//===================================
	// Getters & Setters
	//===================================
	public void setScriptDir(String scriptDir){
		this.scriptDir = scriptDir;
	}
	
	public String getScriptDir(){
		return this.scriptDir;
	}
	
	public boolean hasErrors(){
		return !this.errors.isEmpty();
	}
	
	public String getInitGameFunction() {
		return initGameFunction;
	}

	public void setInitGameFunction(String initGameFunction) {
		this.initGameFunction = initGameFunction;
	}

	public String getPreBeginPlayFunction() {
		return preBeginPlayFunction;
	}

	public void setPreBeginPlayFunction(String preBeginPlayFunction) {
		this.preBeginPlayFunction = preBeginPlayFunction;
	}

	public String getPostBeginPlayFunction() {
		return postBeginPlayFunction;
	}

	public void setPostBeginPlayFunction(String postBeginPlayFunction) {
		this.postBeginPlayFunction = postBeginPlayFunction;
	}

	//===================================
	// methods
	//===================================
	/**
	 * Creates a new window that displays all of
	 * the JS errors.
	 */
	public void createDebugWindow() {
		
		String text = "";
		
		if(!this.errors.isEmpty()){
			for(String str:this.errors){
				text += str+"\n\n";
			}
		} 
		
		if(!this.debugMessages.isEmpty()){
			for(String str:this.debugMessages){
				text += str+"\n\n";
			}
		} 
		
		if(text == "") return; //no need to bring up the debug window
		
		String header ="";
		header += "================================================\n";
		header += "A simple tool for viewing scripting errors\nBy Jason Welch";
		header += "\n================================================\n";
		
		JLabel label = new JLabel("ErgoEngine Javascript Error Window");
		label.setFont(new Font("Serif",Font.BOLD, 25));
		label.setForeground(new Color(0.05f,0.05f,0.05f,1.0f));
		label.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		
		textArea = new JTextArea("",25,50);
		textArea.setFont(new Font("Serif",Font.BOLD, 14));
		textArea.setLineWrap(true);
		textArea.setText(header+text);
		textArea.setBackground(new Color(0.05f,0.05f,0.05f,1.0f));
		textArea.setForeground(Color.gray);
		textArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		textArea.setFocusable(true);
		textArea.setEditable(true);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setWheelScrollingEnabled(true);
	
		errorWindow = new JFrame("Script Debug Window");
		errorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		errorWindow.getContentPane().add(label,BorderLayout.PAGE_START);
		errorWindow.getContentPane().add(scrollPane,BorderLayout.AFTER_LAST_LINE);
		errorWindow.getContentPane().setBackground(Color.darkGray);
		errorWindow.setSize(800,530);
		errorWindow.setVisible(true);
		errorWindow.setResizable(false);
	}
	
	/**
	 * Add a script file to be loaded.
	 * @param filename
	 */
	public void addFile(String filename){
		if(filename.length() > 4){
			this.scriptFiles.add(filename);
		} else {
			System.out.println("script filename is invalid: "+filename);
		}
	}
	
	/**
	 * Adds a debug message.
	 * @param filename
	 */
	public void addDebugString(String msg){
		if(this.errorWindow != null && this.textArea != null){
			this.textArea.append(msg);
		}
		this.debugMessages.add("Debug Msg: "+msg);
	}
	
	/**
	 * Bind data to the script environment
	 * @param key
	 * @param value
	 */
	public void bindData(String key, Object value){
		this.bindings.put(key, value);
	}
	
	/**
	 * Public script loader.
	 * @param filename
	 */
	public void load(String filename){
		try {
			if(filename.length() > 4){
				this.loadFile(filename);
				engine.put(engine.FILENAME, filename);
			} 
		} catch(IOException e){
			//empty
		}
	}
	
	/**
	 * Load a script file
	 * @param filename
	 * @return string
	 * @throws FileNotFoundException
	 */
	protected void loadFile(String filename) throws FileNotFoundException {				
		try {
			FileHandle file = Gdx.files.internal(this.scriptDir+filename);
			String data = file.readString();
			engine.eval(data);
		} catch(Exception e){
			if(this.scriptFiles.contains(filename)){
				this.errors.add("Internally loaded script error!\nFilename: "+filename+"\n"+e.getMessage());
			} else {
				this.errors.add(e.getMessage());
			}
		}
	}
	
	/**
	 * Load all scripts
	 */
	public void loadAll(){
		engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
		try {
			if(!this.scriptFiles.isEmpty()){
				for(String script:this.scriptFiles){
					this.loadFile(script);
				}
			} 
		} catch(IOException e){
			this.errors.add(e.getMessage());
		}
		
		// check to make sure the required script functions are present.
		String funcs[] = {this.initGameFunction,this.preBeginPlayFunction,this.postBeginPlayFunction};
		for(String f:funcs){
			if(this.engine.get(f) == null) {
				this.errors.add("Could not find function: "+f);
			}
		}
	}
	
	/**
	 * Call the game's initialization method.
	 */
	public void initGame(){
		  Invocable inv = (Invocable) engine;

		  try {
			inv.invokeFunction(this.initGameFunction,null);
		} catch (ScriptException e) {
			this.errors.add(e.getMessage());
		} catch (NoSuchMethodException e) {
			this.errors.add(e.getMessage());
		}
	}
	
	/**
	 * Called after the scene has been loaded.
	 */
	public void preBeginPlay(){
		  Invocable inv = (Invocable) engine;
		  
		  try {
			inv.invokeFunction(this.preBeginPlayFunction);
		} catch (ScriptException e) {
			this.errors.add(e.getMessage());
		} catch (NoSuchMethodException e) {
			this.errors.add(e.getMessage());
		}
	}
	
	/**
	 * Called after before the game play starts.
	 */
	public void postBeginPlay(){
		  Invocable inv = (Invocable) engine;
		  
		  try {
			inv.invokeFunction(this.postBeginPlayFunction);
		} catch (ScriptException e) {
			this.errors.add(e.getMessage());
		} catch (NoSuchMethodException e) {
			this.errors.add(e.getMessage());
		}
	}
}
