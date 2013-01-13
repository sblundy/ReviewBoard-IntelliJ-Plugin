import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.file.FileCollection

class IntelliJPluginDevPlugin implements Plugin<Project> {
  void apply(Project project) {
    project.extensions.create("intellijPluginDev", IntelliJPluginDevPluginExtension)
    project.task('copyToPluginSandbox') << {
      def pluginDirFile = new File(project.intellijPluginDev.sandboxDir.toString(), "plugins/${project.intellijPluginDev.pluginName}")
      def libDir = new File(pluginDirFile, "lib")

      if (!pluginDirFile.exists() && !pluginDirFile.mkdirs()) {
        throw new RuntimeException("Failed to create plugin directory at " + pluginDirFile.getAbsolutePath())
      }

      if (!libDir.exists() && !libDir.mkdirs()) {
        throw new RuntimeException("Failed to create plugin lib directory at " + libDir.getAbsolutePath())
      }

      project.intellijPluginDev.deployables.each { File file -> project.copy {
        from file.absolutePath into libDir.absolutePath
      }}
    }
  }
}

class IntelliJPluginDevPluginExtension {
  String sandboxDir
  String pluginName
  FileCollection deployables
}