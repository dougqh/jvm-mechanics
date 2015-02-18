package support;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.FilenameFilter;



public class GenSh {
  //private static final String JAVA = "/Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home/bin/java";
  private static final String JAVA = "java";
  private static final File THIS_DIR = new File(".").getAbsoluteFile();
  
  private static final File SRC_DIR = new File("src");
  private static final File SCRIPTS_DIR = new File("scripts");
  
  private static final FileFilter EXAMPLE_DIR_FILTER = new FileFilter() {
    @Override
    public final boolean accept(final File file) {
      return file.isDirectory() && 
        file.getName().startsWith("example") &&
        !file.getName().equals("example");
    }
  };
  
  private static final FilenameFilter JAVA_FILTER = new FilenameFilter() {
    @Override
    public boolean accept(final File dir, final String name) {
      return name.endsWith(".java");
    }
  };
  
  public static final void main(final String[] args) throws Exception {
    for ( File exampleDir: SRC_DIR.listFiles(EXAMPLE_DIR_FILTER) ) {
      writeScript(exampleDir);
    }
  }
  
  private static final void writeScript(final File exampleDir) throws Exception {
    SCRIPTS_DIR.mkdirs();
    
    File javaFile = javaFile(exampleDir);
    if ( javaFile == null ) return;
    
    String className = className(exampleDir, javaFile);
    String scriptName = exampleDir.getName() + ".sh";
    
    Class<?> classObj = Class.forName(className);
    RunWith runWithAnno = classObj.getAnnotation(RunWith.class);
    Output outputAnno = classObj.getAnnotation(Output.class);
    
    String vmArgs = toVmArgs(runWithAnno);
    String postprocessors = pipeTo(outputAnno);
    if ( !postprocessors.isEmpty() ) {
      postprocessors = " | " + postprocessors;
    }

    try ( FileWriter writer = new FileWriter(new File(SCRIPTS_DIR, scriptName)) ) {
      writer.write("#!/bin/bash\n");
      // hack just to get things working
      writer.write("cd ..\n");
      writer.write("less src/" + exampleDir.getName() + "/" + javaFile.getName() + "\n");
      writer.write("clear\n");
      writer.write("cat src/" + exampleDir.getName() + "/" + javaFile.getName() + "\n");
      writer.write("echo\n");
      writer.write("echo \"--------------------------------------------------------------------------------------------------\"\n");
      writer.write("echo \"java " + vmArgs + " -cp bin " + className + "\"\n");
      writer.write("echo \"--------------------------------------------------------------------------------------------------\"\n");
      //writer.write("if [ \"$1\" -ne \"-\" ]\n");
      //writer.write("then\n");
      writer.write(JAVA + " " + vmArgs + " -cp bin " + className + " 2>&1 " + postprocessors + " \n");
      //writer.write("fi\n");
    }
  }
  
  private static final File javaFile(final File exampleDir) {
    File[] javaFiles = exampleDir.listFiles(JAVA_FILTER);
    if ( javaFiles.length == 0 ) {
      return null;
    } else if ( javaFiles.length == 1 ) {
      return javaFiles[0];
    } else {
      throw new IllegalStateException(exampleDir.getName());
    }
  }
  
  private static final String className(final File exampleDir, final File javaFile) {
    String packageName = exampleDir.getName();
      
    String javaFileName = javaFile.getName();
    String bareClassName = javaFileName.substring(0, javaFileName.length() - 5); // ".java"
      
    return packageName + "." + bareClassName;
  }
  
  private static final String toVmArgs(final RunWith runWithAnno) {
    if ( runWithAnno == null ) {
      return "";
    } else {
      StringBuilder builder = new StringBuilder();
      boolean first = true;
      for ( String arg: runWithAnno.value() ) {
        if ( first ) {
          first = false;
        } else {
          builder.append(" ");
        }
        builder.append(arg);
      }
      return builder.toString();
    }
  }
  
  private static final String pipeTo(final Output outputAnno) {
    if ( outputAnno == null ) {
      return "";
    }
    
    StringBuilder postprocessors = new StringBuilder();
    if ( outputAnno.highlight().length != 0 ) {
      postprocessors.append(" ack --color --passthru ");
      
      if ( outputAnno.less() ) {
        postprocessors.append("--pager=\"${PAGER:-less -R}\" ");
      }
      
      postprocessors.append("\"");
      boolean first = true;
      for ( String pattern: outputAnno.highlight() ) {
        if ( first ) {
          first = false;
        } else {
          postprocessors.append("|");
        }
        postprocessors.append(pattern);
      }
      postprocessors.append("\"");
    } else if ( outputAnno.less() ) {
      postprocessors.append(" | less");
    }
    
    return postprocessors.toString();
  }
}