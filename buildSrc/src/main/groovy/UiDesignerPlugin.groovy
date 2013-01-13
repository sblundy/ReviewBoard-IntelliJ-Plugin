import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask

class UiDesignerPlugin implements Plugin<Project> {
  void apply(Project project) {
    project.extensions.create("uiDesigner", UiDesignerPluginExtension)
    project.task('compileJava', type: Javac2Task, overwrite: true)
  }
}

class Javac2Task extends DefaultTask {
  @TaskAction
  def compileJava() {
    project.sourceSets.main.output.classesDir.mkdirs()

    project.ant {
      taskdef(name: 'javac2',
              classname: 'com.intellij.ant.Javac2',
              classpath: taskClasspath().asPath)

      javac2(srcdir: mainSrcDir,
             classpath: classpath().asPath,
             destdir: destDir,
             debug: debug,
             debugLevel: debugLevel,
             deprecation: deprecation,
             includeAntRuntime: includeAntRuntime,
             optimize: optimize,
             source: javaSourceCompatibility,
             target: javaTargetCompatibility
      )
    }
  }

  FileCollection taskClasspath() {
    return project.files(
            project.uiDesigner.intellijLibDir + "javac2.jar",
            project.uiDesigner.intellijLibDir + "annotations.jar",
            project.uiDesigner.intellijLibDir + "asm.jar",
            project.uiDesigner.intellijLibDir + "jdom.jar",
            project.uiDesigner.intellijLibDir + "asm-commons.jar")

  }
  def mainSrcDir = "${project.projectDir}/src/main/java" //HACK
  def destDir = project.sourceSets.main.output.classesDir
  def javaSourceCompatibility = project.sourceCompatibility
  def javaTargetCompatibility = project.targetCompatibility
  def optimize = "off"
  def deprecation = "off"
  def includeAntRuntime = false
  def debugLevel = "lines,vars,source"
  def debug = "on"
  FileCollection classpath() {
    return project.sourceSets.main.compileClasspath
  }
}

class UiDesignerPluginExtension {
  String intellijLibDir
}