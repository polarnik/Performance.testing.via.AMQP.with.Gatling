import io.gatling.app.Gatling
import io.gatling.core.ConfigKeys.core
import io.gatling.core.config.GatlingPropertiesBuilder

/**
	* Класс для отладки работы теста.
	* Опции в классе, переопределяют опции в pom.xml
	*
	* Следует применять для отладки из IDEA отдельных сценариев
	*/
object Engine extends App {


	var config = scala.collection.mutable.Map(
		core.directory.Resources -> IDEPathHelper.resourcesDirectory.toString,
		core.directory.Results -> IDEPathHelper.resultsDirectory.toString,
		core.directory.Binaries -> IDEPathHelper.binariesDirectory.toString,

		core.SimulationClass ->  "io.github.qaload.simulations.TestJmsDsl",
		core.RunDescription -> "JMS" //,
		//core.directory.ReportsOnly -> s"${IDEPathHelper.resultsDirectory.toString}/testjmsdsl-20191113154848370/simulation.log"

	)


	Gatling.fromMap(config)

}
