import java.nio.file.Path


object IDEPathHelper {

	val projectRootDir = java.nio.file.Paths.get(System.getProperty("user.dir"))
	val sourcesDirectory = projectRootDir.resolve("src/test/scala")
	val simulationsDirectory = sourcesDirectory.resolve("io/github/qaload/simulations")
	val mavenResourcesDirectory = projectRootDir.resolve("src/test/resources")
	val mavenTargetDirectory = projectRootDir.resolve("target")
	val binariesDirectory = mavenTargetDirectory.resolve("test-classes")

	val resourcesDirectory = mavenResourcesDirectory
	val resultsDirectory = mavenTargetDirectory.resolve("gatling")
}
